import java.io.*;
import java.util.Random;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;



public class CreateRandomPatientData
{
	public static void main(String args[])
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		
		HTable patientTable = new HTable(conf, "Patient");

		int numberOfWords = 1000;
		String[] randomStrings = generateRandomWords(numberOfWords);
		String[] occupation = {"Student", "Teacher", "Professor", "Farmer", "Worker", "ShopKeeper", "Businessman"};
		String[] gender = {"Male", "Female"};
		String[] reference = {"Son", "Daughter"};
		String[] status = {"New", "Revisit"};

		Random rn = new Random();

		for(int i = 2 ; i <= 100 ; i++)
		{
			String patientId = "Patient_01_" + String.format("%03d",(i));
			Put data = new Put(Bytes.toBytes(patientId));

			String name = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Name"), Bytes.toBytes(name));

			String date = "" + rn.nextInt(29) + "-" + rn.nextInt(13) + "-16"; 
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("RegistrationDate"), Bytes.toBytes(date));

			int gen = rn.nextInt(2);
			String refer = reference[gen] + " of " + randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Reference"), Bytes.toBytes(refer));
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Gender"), Bytes.toBytes(gender[gen]));


			String age = "" + (rn.nextInt(100) + 1);
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Age"), Bytes.toBytes(age));

			String phone = "" + (rn.nextInt(1000)+ 80000);
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Phone"), Bytes.toBytes(phone));

			String address = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Address"), Bytes.toBytes(address));

			String occup = occupation[rn.nextInt(7)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Occupation"), Bytes.toBytes(occup));

			String stat = status[rn.nextInt(2)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Status"), Bytes.toBytes(stat));

			String height = "" + (rn.nextInt(100) + 100);
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Height"), Bytes.toBytes(height));

			String fh = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("FamilyHistory"), Bytes.toBytes(fh));

			String mh = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("MedicalHistory"), Bytes.toBytes(mh));

			patientTable.put(data);
			// System.out.println(patientId + "\n" + name + "\n" + date + "\n" + gen + "\n" + refer + "\n" + age + "\n" + phone
				 // + "\n" + address + "\n" + occup + "\n" + stat + "\n" + height + "\n" + fh + "\n" + mh);
		}
		System.out.println("Row inserted");
	}

	public static String[] generateRandomWords(int numberOfWords)
	{
	    String[] randomStrings = new String[numberOfWords];
	    Random random = new Random();
	    for(int i = 0; i < numberOfWords ; i++)
	    {
	        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + random.nextInt(26));
	        }
	        randomStrings[i] = new String(word);
	        // System.out.println(randomStrings[i]);
	    }
	    return randomStrings;
	}
}