import java.io.*;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.conf.Configuration;


public class CreateTables
{
	public static void main(String args[])
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);

		HTableDescriptor patientTable = new HTableDescriptor(TableName.valueOf("Patient"));
		HTableDescriptor doctorTable = new HTableDescriptor(TableName.valueOf("Doctor"));
		HTableDescriptor visitTable = new HTableDescriptor(TableName.valueOf("Visit"));

		patientTable.addFamily(new HColumnDescriptor("BasicData"));


		doctorTable.addFamily(new HColumnDescriptor("BasicData"));

		visitTable.addFamily(new HColumnDescriptor("ForeignKeys"));
		visitTable.addFamily(new HColumnDescriptor("PatientComplaint"));
		visitTable.addFamily(new HColumnDescriptor("DoctorPrescription"));

		admin.createTable(patientTable);
		admin.createTable(doctorTable);
		admin.createTable(visitTable);
		System.out.println("Table created");
	}
}