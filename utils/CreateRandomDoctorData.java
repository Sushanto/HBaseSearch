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



public class CreateRandomDoctorData
{
	public static void main(String args[])
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		
		HTable doctorTable = new HTable(conf, "Doctor");

		int numberOfWords = 100;
		String[] randomStrings = generateRandomWords(numberOfWords);

		Random rn = new Random();

		for(int i = 1 ; i <= 5 ; i++)
		{
			String doctorId = "Doctor_" + String.format("%02d",(i));
			Put data = new Put(Bytes.toBytes(doctorId));

			String name = "Dr. " + randomStrings[rn.nextInt(numberOfWords)] + " " + randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Name"), Bytes.toBytes(name));

			String signature = randomStrings[rn.nextInt(numberOfWords)] + ".jpg"; 
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Signature"), Bytes.toBytes(signature));


			String degree = randomStrings[rn.nextInt(numberOfWords)]; 
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("Degree"), Bytes.toBytes(degree));

			String registration = "" + (rn.nextInt(1000) + 4000);
			data.add(Bytes.toBytes("BasicData"), Bytes.toBytes("RegistrationNo"), Bytes.toBytes(registration));

			doctorTable.put(data);
			// System.out.println(doctorId + "\n" + name + "\n" + signature + "\n" + degree + "\n" + registration + "\n");
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