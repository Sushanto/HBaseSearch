import java.io.*;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;



public class CreatePatientData
{
	public static void main(String args[])
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		
		HTable patientTable = new HTable(conf, "Patient");

		Put data = new Put(Bytes.toBytes("Patient_01_001"));

		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Name"), Bytes.toBytes("Sushanto Halder"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("RegistrationDate"), Bytes.toBytes("29-11-16"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Reference"), Bytes.toBytes("Son of Mr. Halder"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Gender"), Bytes.toBytes("Male"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Age"), Bytes.toBytes("21"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Phone"), Bytes.toBytes("8276851718"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Address"), Bytes.toBytes("Kashimpur"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Occupation"), Bytes.toBytes("Student"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Status"), Bytes.toBytes("New"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Height"), Bytes.toBytes("156"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("FamilyHistory"), Bytes.toBytes("None"));
		data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("MedicalHistory"), Bytes.toBytes("Asthma"));

		patientTable.put(data);
		System.out.println("Row inserted");
	}
}