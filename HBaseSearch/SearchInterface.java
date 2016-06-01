package HBaseSearch;


import java.io.*;
import javax.swing.*;
import java.awt.event.*;


public class SearchInterface
{
	private JFrame searchFrame;
	private JButton searchButton, backButton, patientSelectButton, patientConfirmButton, visitSelectButton, visitNextButton, visitPrevButton;
	private JTextField searchField;
	private JTextArea basicDataArea, patientComplaintArea, doctorPrescriptionArea;
	private JLabel frameLabel, tableLabel, attrLabel, patientLabel, visitLabel, basicDataLabel, patientComplaintLabel, doctorPrescriptionLabel;
	private JComboBox<String> tableComboBox, attrComboBox, patientComboBox, visitComboBox;
	private String tableName, attrName;

	public SearchInterface()
	{
		searchFrame = new JFrame();
		final JFrame jframe = searchFrame;
		searchFrame.setTitle("Search");
		searchFrame.setSize(Constants.SIZE_X, Constants.SIZE_Y);
		searchFrame.setResizable(false);
		searchFrame.setVisible(true);
		searchFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		searchFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,"Are you sure") == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					searchFrame.dispose();
				}
			}
		});

		searchButton = new JButton("Search");
		backButton = new JButton("Back");
		patientSelectButton = new JButton("Select");
		patientConfirmButton = new JButton("Confirm");
		visitSelectButton = new JButton("select");
		visitNextButton = new JButton("Next");
		visitPrevButton = new JButton("Prev");

		searchField = new JTextField();

		basicDataArea = new JTextArea();
		patientComplaintArea = new JTextArea();
		doctorPrescriptionArea = new JTextArea();

		frameLabel = new JLabel("Search");
		tableLabel = new JLabel("Select table :");
		attrLabel = new JLabel("Select attribute :");
		patientLabel = new JLabel("Select patient : ");
		visitLabel = new JLabel("Select Visit : ");
		basicDataLabel = new JLabel("Basic Data");
		patientComplaintLabel = new JLabel("Patient Complaint");
		doctorPrescriptionLabel = new JLabel("Doctor Prescription");

		String[] tables = {"Patient"};
		final String[] patientAttr = {"Name", "RegistrationDate", "Reference", "Gender", "Age", "Phone", "Address", "Occupation", "Status",
								"Height", "FamilyHistory", "MedicalHistory"};

		tableComboBox = new JComboBox<String>(tables);
		attrComboBox = new JComboBox<String>(patientAttr);
		patientComboBox = new JComboBox<String>();
		visitComboBox = new JComboBox<String>();

		frameLabel.setBounds(370,10,400,100);


		tableLabel.setBounds(15,115,200,25);
		tableComboBox.setBounds(10,135,200,25);
		attrLabel.setBounds(15,165,200,25);
		attrComboBox.setBounds(10,195,200,25);
		searchField.setBounds(10,225,200,25);
		searchButton.setBounds(10,255,200,25);
		patientLabel.setBounds(15,285,200,25);
		patientComboBox.setBounds(10,315,200,25);
		patientSelectButton.setBounds(10,345,200,25);
		patientConfirmButton.setBounds(10,375,200,25);
		visitLabel.setBounds(15,405,200,25);
		visitComboBox.setBounds(10,435,200,25);
		visitSelectButton.setBounds(10,465,200,25);
		visitNextButton.setBounds(140,495,70,20);
		visitPrevButton.setBounds(10,495,70,20);


		backButton.setBounds(20,600,200,30);

		basicDataLabel.setBounds(500,105,150,25);
		basicDataArea.setBounds(230,130,650,190);
		patientComplaintLabel.setBounds(235,322,150,25);
		patientComplaintArea.setBounds(230,350,320,330);
		doctorPrescriptionLabel.setBounds(565,322,150,25);
		doctorPrescriptionArea.setBounds(560,350,320,330);

		searchButton.setFont(Constants.SMALLBUTTONFONT);
		patientSelectButton.setFont(Constants.SMALLBUTTONFONT);
		patientConfirmButton.setFont(Constants.SMALLBUTTONFONT);
		visitSelectButton.setFont(Constants.SMALLBUTTONFONT);
		visitNextButton.setFont(Constants.SMALLBUTTONFONT);
		visitPrevButton.setFont(Constants.SMALLBUTTONFONT);
		backButton.setFont(Constants.SMALLBUTTONFONT);


		tableComboBox.setFont(Constants.SMALLBUTTONFONT);
		attrComboBox.setFont(Constants.SMALLBUTTONFONT);
		patientComboBox.setFont(Constants.SMALLBUTTONFONT);
		visitComboBox.setFont(Constants.SMALLBUTTONFONT);

		tableLabel.setFont(Constants.SMALLLABELFONT);
		attrLabel.setFont(Constants.SMALLLABELFONT);
		patientLabel.setFont(Constants.SMALLLABELFONT);
		visitLabel.setFont(Constants.SMALLLABELFONT);
		basicDataLabel.setFont(Constants.SMALLLABELFONT);
		patientComplaintLabel.setFont(Constants.SMALLLABELFONT);
		doctorPrescriptionLabel.setFont(Constants.SMALLLABELFONT);

		searchField.setFont(Constants.SMALLLABELFONT);

		frameLabel.setFont(Constants.HEADERFONT);
		frameLabel.setForeground(Constants.HEADERCOLOR1);

		setVisibility(false);


		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				setVisibility(false);
				tableName = (String)tableComboBox.getSelectedItem();
				attrName = (String)attrComboBox.getSelectedItem();
				try
				{
					if(TableUtils.isExists(attrName + "X" + tableName))
					{
						String key = searchField.getText();
						String[] rowKeyAttr = {tableName + "Key"};
						String patientID = TableUtils.getDataFromTable(attrName + "X" + tableName, key, "Rowkey", rowKeyAttr);
						if(patientID == null)
						{
							JOptionPane.showMessageDialog(jframe, "No such patient!");
							setVisibility(false);
							return;
						}
						String[]  patientIDList = (patientID.split("  -->  "))[1].substring(1).split(":");
						patientComboBox.setModel(new DefaultComboBoxModel<String>(patientIDList));

						patientLabel.setVisible(true);
						patientComboBox.setVisible(true);
						patientSelectButton.setVisible(true);
						patientConfirmButton.setVisible(true);
					}
					else
					{
						JOptionPane.showMessageDialog(jframe, "Preprocess: " + tableName + " X " + attrName + " first!");
						setVisibility(false);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe, "Some error occured! Try again later!!");
					setVisibility(false);
				}
			}
		});

		patientSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					visitLabel.setVisible(false);
					visitComboBox.setVisible(false);
					visitSelectButton.setVisible(false);
					visitNextButton.setVisible(false);
					visitPrevButton.setVisible(false);
					String basicData = TableUtils.getDataFromTable(tableName, (String)patientComboBox.getSelectedItem(), "BasicData", patientAttr); 
					basicDataArea.setText(basicData);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe, "Some error occured! Try again later!!");
					setVisibility(false);
				}
			}
		});

		patientConfirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					String key = (String)patientComboBox.getSelectedItem();
					String[] rowKeyAttr = {"VisitKey"};
					String visitID = TableUtils.getDataFromTable("PatientIDXVisit", key, "Rowkey", rowKeyAttr);
					if(visitID == null)
					{
						JOptionPane.showMessageDialog(jframe, "No Visit yet");
						setVisibility(false);
						return;
					}
					String[]  visitIDList = (visitID.split("  -->  "))[1].substring(1).split(":");
					visitComboBox.setModel(new DefaultComboBoxModel<String>(visitIDList));
					visitLabel.setVisible(true);
					visitComboBox.setVisible(true);
					visitSelectButton.setVisible(true);
					visitNextButton.setVisible(true);
					visitPrevButton.setVisible(true);
					visitPrevButton.setEnabled(false);
					visitNextButton.setEnabled(visitComboBox.getItemCount() > 1);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe, "Some error occured! Try again later!!");
					setVisibility(false);
				}
			}
		});

		visitSelectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				getVisitData();
			}
		});

		visitNextButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				visitComboBox.setSelectedIndex(visitComboBox.getSelectedIndex() + 1);
				getVisitData();
			}
		});

		visitPrevButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				visitComboBox.setSelectedIndex(visitComboBox.getSelectedIndex() - 1);
				getVisitData();
			}
		});

		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new MainInterface();
				searchFrame.dispose();
			}
		});

		searchFrame.add(searchButton);
		searchFrame.add(patientSelectButton);
		searchFrame.add(patientConfirmButton);
		searchFrame.add(visitSelectButton);
		searchFrame.add(visitNextButton);
		searchFrame.add(visitPrevButton);
		searchFrame.add(backButton);

		searchFrame.add(tableComboBox);
		searchFrame.add(attrComboBox);
		searchFrame.add(patientComboBox);
		searchFrame.add(visitComboBox);

		searchFrame.add(searchField);
		searchFrame.add(basicDataArea);
		searchFrame.add(patientComplaintArea);
		searchFrame.add(doctorPrescriptionArea);


		searchFrame.add(tableLabel);
		searchFrame.add(attrLabel);
		searchFrame.add(patientLabel);
		searchFrame.add(visitLabel);
		searchFrame.add(frameLabel);
		searchFrame.add(basicDataLabel);
		searchFrame.add(patientComplaintLabel);
		searchFrame.add(doctorPrescriptionLabel);

		searchFrame.add(Constants.JPANEL2);
		searchFrame.add(Constants.JPANEL1);
	}

	private void setVisibility(boolean visible)
	{
		patientLabel.setVisible(visible);
		patientComboBox.setVisible(visible);
		patientSelectButton.setVisible(visible);
		patientConfirmButton.setVisible(visible);
		visitLabel.setVisible(visible);
		visitComboBox.setVisible(visible);
		visitSelectButton.setVisible(visible);
		visitNextButton.setVisible(visible);
		visitPrevButton.setVisible(visible);
	}

	private void getVisitData()
	{
		try
		{
			visitNextButton.setEnabled(visitComboBox.getSelectedIndex() < visitComboBox.getItemCount() - 1);
			visitPrevButton.setEnabled(visitComboBox.getSelectedIndex() > 0);
			String key = (String)visitComboBox.getSelectedItem();
			String[] patientComplaintAttr = {"Complaint","ComplaintDate","Weight","BMI","BP","Pulse","Temperature","SpO2",
												"onExamination","FileNames","CoOrdinator","PreviousDiagnosis"};
			String[] doctorPrescriptionAttr = {"PrescriptionDate","FinalDiagnosis","ProvisionalDiagnosis","Advice",
												"Medication","Referral","Diagnosis"};
			String[] foreignKeysAttr = {"DoctorID"};
			String[] basicDataAttr = {"Name"};

			String patientComplaint = TableUtils.getDataFromTable("Visit", key, "PatientComplaint", patientComplaintAttr);
			String doctorPrescription = TableUtils.getDataFromTable("Visit", key, "DoctorPrescription", doctorPrescriptionAttr);
			String doctorKey = ((TableUtils.getDataFromTable("Visit", key, "ForeignKeys", foreignKeysAttr)).split("  -->  "))[1];
			String doctorName = TableUtils.getDataFromTable("Doctor", doctorKey, "BasicData", basicDataAttr);
			patientComplaintArea.setText(patientComplaint);
			doctorPrescriptionArea.setText("Doctor " + doctorName + "\n" + doctorPrescription);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(searchFrame, "Some error occured! Try again later!!");
			setVisibility(false);
		}
	}
}