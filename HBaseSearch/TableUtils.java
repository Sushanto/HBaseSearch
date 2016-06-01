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
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;


public class TableUtils
{
	protected static boolean isExists(String tableName)
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		boolean exists = admin.tableExists(tableName);
		admin.close();
		return exists;
	}

	protected static String getDataFromTable(String tableName, String rowKey, String columnFamily, String[] columnNames)
	throws Exception
	{
		String output = "";
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, tableName);
		Get g = new Get(Bytes.toBytes(rowKey));
		Result result = table.get(g);

		for( int i = 0 ; i < columnNames.length ; i++ )
			output += columnNames[i] + "  -->  " + Bytes.toString(result.getValue(Bytes.toBytes(columnFamily), Bytes.toBytes(columnNames[i]))) + "\n";
		return output.trim();
	}
}