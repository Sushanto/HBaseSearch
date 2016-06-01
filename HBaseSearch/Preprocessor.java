package HBaseSearch;

import java.io.*;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;




public class Preprocessor
{
	protected static String sourceTableName, sourceColumnFamily, sourceColumnName, resultTableName, resultColumnFamily, resultColumnName;

	protected void createResultTable(Configuration conf, String tableName, String columnFamily)
	throws Exception
	{
		HBaseAdmin admin = new HBaseAdmin(conf);

		if(admin.tableExists(tableName))
		{
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}

		HTableDescriptor resultTable = new HTableDescriptor(tableName);
		resultTable.addFamily(new HColumnDescriptor(Bytes.toBytes(columnFamily)));
		admin.createTable(resultTable);
	}


	protected Preprocessor(String[] args)
	{
		sourceTableName = args[0];
		sourceColumnFamily = args[1];
		sourceColumnName = args[2];
		resultTableName = args[3];
		resultColumnFamily = args[4];
		resultColumnName = args[5];
	}

	protected boolean preprocess()
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		createResultTable(conf, resultTableName, resultColumnFamily);

		Job job = new Job(conf, "Preprocessing");
		job.setJarByClass(Preprocessor.class);

		Scan scan = new Scan();
		scan.setCaching(500);
		scan.setCacheBlocks(false);
		scan.addColumn(Bytes.toBytes(sourceColumnFamily), Bytes.toBytes(sourceColumnName));

		TableMapReduceUtil.initTableMapperJob(
			sourceTableName,
			scan,
			PreprocessorMapper.class,
			Text.class,
			Text.class,
			job);
		TableMapReduceUtil.initTableReducerJob(
			resultTableName,
			PreprocessorReducer.class,
			job);

		job.setNumReduceTasks(1);
		return job.waitForCompletion(true);
	}

	public static class PreprocessorMapper extends TableMapper<Text, Text>
	{
		private Text key = new Text();
		private Text val = new Text();
		private final byte[] CF = sourceColumnFamily.getBytes();
		private final byte[] ATTR = sourceColumnName.getBytes();

		public void map(ImmutableBytesWritable row, Result value, Context context)
		throws IOException, InterruptedException
		{
			String str = new String(value.getValue(CF, ATTR));
			key.set(str);
			val.set(new String(value.getRow()));
			context.write(key, val);
		}
	}

	public static class PreprocessorReducer extends TableReducer<Text, Text, ImmutableBytesWritable>
	{
		Text result = new Text();

		public void reduce(Text key, Iterable<Text> values, Context context)
		throws IOException, InterruptedException
		{
			String str = "";
			for(Text val : values)
				str += ":" + val.toString();
			result.set(str);

			Put put = new Put(Bytes.toBytes(key.toString()));
			put.add(Bytes.toBytes(resultColumnFamily), Bytes.toBytes(resultColumnName), Bytes.toBytes(str));
			context.write(null, put);
		}
	}
}