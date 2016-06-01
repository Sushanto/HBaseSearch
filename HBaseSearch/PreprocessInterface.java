package HBaseSearch;


import java.io.*;
import javax.swing.*;
import java.awt.event.*;


public class PreprocessInterface
{
	private JFrame preprocessFrame;
	private JButton preprocessButton, backButton;
	private JLabel frameLabel, tableLabel, attrLabel;
	private JComboBox<String> tableComboBox, attrComboBox;

	public PreprocessInterface()
	{
		preprocessFrame = new JFrame();
		final JFrame jframe = preprocessFrame;
		preprocessFrame.setTitle("Preprocess");
		preprocessFrame.setSize(Constants.SIZE_X, Constants.SIZE_Y);
		preprocessFrame.setResizable(false);
		preprocessFrame.setVisible(true);
		preprocessFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		preprocessFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,"Are you sure") == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					preprocessFrame.dispose();
				}
			}
		});

		preprocessButton = new JButton("Preprocess");
		backButton = new JButton("Back");
		frameLabel = new JLabel("Preprocess");
		tableLabel = new JLabel("Choose table :");
		attrLabel = new JLabel("Choose attribute :");

		String[] tables = {"Patient", "Doctor", "Visit"};
		String[] patientAttr = {"Name", "RegistrationDate", "Reference", "Gender", "Age", "Phone", "Address", "Occupation", "Status",
								"Height", "FamilyHistory", "MedicalHistory"};
		String[] doctorAttr = {"Name", "Signature", "Degree", "RegistrationNo"};
		String[] visitAttr = {"PatientID", "DoctorID"};

		tableComboBox = new JComboBox<String>(tables);
		attrComboBox = new JComboBox<String>(patientAttr);


		tableComboBox.setBounds(350,320,200,30);
		attrComboBox.setBounds(350,360,200,30);
		preprocessButton.setBounds(350,400,200,30);
		backButton.setBounds(20,600,200,30);
		frameLabel.setBounds(320,10,400,100);

		preprocessButton.setFont(Constants.SMALLBUTTONFONT);
		backButton.setFont(Constants.SMALLBUTTONFONT);
		tableComboBox.setFont(Constants.SMALLBUTTONFONT);
		attrComboBox.setFont(Constants.SMALLBUTTONFONT);
		frameLabel.setFont(Constants.HEADERFONT);
		frameLabel.setForeground(Constants.HEADERCOLOR1);

		tableComboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String tableName = (String)tableComboBox.getSelectedItem();
				if(tableName.equals("Patient"))
					attrComboBox.setModel(new DefaultComboBoxModel<String>(patientAttr));
				else if(tableName.equals("Doctor"))
					attrComboBox.setModel(new DefaultComboBoxModel<String>(doctorAttr));
				else if(tableName.equals("Visit"))
					attrComboBox.setModel(new DefaultComboBoxModel<String>(visitAttr));
			}
		});


		preprocessButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String[] arguments = new String[6];
				arguments[0] = (String)tableComboBox.getSelectedItem();
				arguments[1] = arguments[0].equals("Visit") ? "ForeignKeys" : "BasicData";
				arguments[2] = (String)attrComboBox.getSelectedItem();
				arguments[3] = arguments[2] + "X" + arguments[0];
				arguments[4] = "Rowkey";
				arguments[5] = arguments[0] + "Key";

				Preprocessor preprocessor = new Preprocessor(arguments);
				try
				{
					if(preprocessor.preprocess())
						JOptionPane.showMessageDialog(jframe, "Preprocessing done!");
					else 
						JOptionPane.showMessageDialog(jframe, "Preprocessing failed!! Try again later!!");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe, "Preprocessing failed!! Try again later!!");
				}
			}
		});

		backButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new MainInterface();
				preprocessFrame.dispose();
			}
		});

		preprocessFrame.add(preprocessButton);
		preprocessFrame.add(backButton);
		preprocessFrame.add(tableComboBox);
		preprocessFrame.add(attrComboBox);
		preprocessFrame.add(tableLabel);
		preprocessFrame.add(attrLabel);
		preprocessFrame.add(frameLabel);

		preprocessFrame.add(Constants.JPANEL2);
		preprocessFrame.add(Constants.JPANEL1);
	}
}