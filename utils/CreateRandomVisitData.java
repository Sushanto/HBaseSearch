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



public class CreateRandomVisitData
{
	public static void main(String args[])
	throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		
		HTable visitTable = new HTable(conf, "Visit");

		int numberOfWords = 1000;
		String[] randomStrings = generateRandomWords(numberOfWords);

		Random rn = new Random();

		for(int i = 3 ; i <= 1000 ; i++)
		{
			String visitId = "Visit_" + String.format("%04d",(i));
			Put data = new Put(Bytes.toBytes(visitId));

			String patientId = "Patient_01_" + String.format("%03d",(rn.nextInt(100) + 1));
			data.add(Bytes.toBytes("ForeignKeys"), Bytes.toBytes("PatientID"), Bytes.toBytes(patientId));

			String doctorId = "Doctor_" + String.format("%02d",(rn.nextInt(5) + 1));
			data.add(Bytes.toBytes("ForeignKeys"), Bytes.toBytes("DoctorID"), Bytes.toBytes(doctorId));

			String complaint = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("Complaint"), Bytes.toBytes(complaint));

			String complaintDate = "" + rn.nextInt(29) + "-" + rn.nextInt(13) + "-16";
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("ComplaintDate"), Bytes.toBytes(complaintDate));

			String weight = "" + (rn.nextInt(100) + 5);
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("Weight"), Bytes.toBytes(weight));

			String bmi = "" + (rn.nextInt(7) + 21);
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("BMI"), Bytes.toBytes(bmi));

			String bp = "" + (rn.nextInt(50) + 50) + "/" + (rn.nextInt(50) + 100);
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("BP"), Bytes.toBytes(bp));

			String pulse = "" + (rn.nextInt(30) + 55);
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("Pulse"), Bytes.toBytes(pulse));

			String temperature = "" + (rn.nextInt(4) + 35);
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("Temperature"), Bytes.toBytes(temperature));

			String spo2 = "" + (rn.nextInt(15) + 85);
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("SpO2"), Bytes.toBytes(spo2));

			String onExamination = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("onExamination"), Bytes.toBytes(onExamination));

			String fileNames = randomStrings[rn.nextInt(numberOfWords)] + ".pdf";
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("FileNames"), Bytes.toBytes(fileNames));

			String coOrdinator = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("CoOrdinator"), Bytes.toBytes(coOrdinator));

			String prevDiagnosis = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("PatientComplaint"), Bytes.toBytes("PreviousDiagnosis"), Bytes.toBytes(prevDiagnosis));

			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("PrescriptionDate"), Bytes.toBytes(complaintDate));

			String finalDiagnosis = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("FinalDiagnosis"), Bytes.toBytes(finalDiagnosis));

			String provisionalDiagnosis = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("ProvisionalDiagnosis"), Bytes.toBytes(provisionalDiagnosis));

			String advice = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("Advice"), Bytes.toBytes(advice));

			String medication = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("Medication"), Bytes.toBytes(medication));

			String referral = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("Referral"), Bytes.toBytes(finalDiagnosis));

			String diagnosis = randomStrings[rn.nextInt(numberOfWords)];
			data.add(Bytes.toBytes("DoctorPrescription"), Bytes.toBytes("Diagnosis"), Bytes.toBytes(diagnosis));




			visitTable.put(data);
			// System.out.println(visitId + "\n" + patientId + "\n" + doctorId + "\n" + complaint + "\n" + complaintDate + "\n" + 
			// 	weight + "\n" + bmi + "\n" + bp + "\n" + pulse + "\n" + temperature + "\n" + spo2 + "\n" + onExamination + "\n" + 
			// 	fileNames + "\n" + prevDiagnosis + "\n" + coOrdinator + "\n" + provisionalDiagnosis + "\n" + 
			// 	finalDiagnosis + "\n" + advice + "\n" + medication + "\n" + referral + "\n" + diagnosis);
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