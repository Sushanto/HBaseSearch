package HBaseSearch;


import java.io.*;
import javax.swing.*;
import java.awt.event.*;


public class MainInterface
{
	private JFrame mainFrame;
	private JButton searchButton, preprocessButton;
	private JLabel frameLabel;

	public static void main(String args[])
	{

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
            		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            		{
                		if ("Nimbus".equals(info.getName()))
                		{
							javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    		break;
                		}
            		}
        		}
        		catch (ClassNotFoundException ex)
        		{
            		java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (InstantiationException ex)
        		{
            		java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (IllegalAccessException ex)
        		{
            		java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}
        		catch (javax.swing.UnsupportedLookAndFeelException ex)
        		{
            		java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        		}

				new MainInterface();
			}
		});
	}

	public MainInterface()
	{
		mainFrame = new JFrame();
		final JFrame jframe = mainFrame;
		mainFrame.setSize(Constants.SIZE_X, Constants.SIZE_Y);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				if(JOptionPane.showConfirmDialog(jframe,"Are you sure") == JOptionPane.OK_OPTION)
				{
                    System.exit(0);
					mainFrame.dispose();
				}
			}
		});

		searchButton = new JButton("Search");
		preprocessButton = new JButton("Preprocess");
		frameLabel = new JLabel("HBASE SEARCH");

		searchButton.setBounds(350,260,200,30);
		preprocessButton.setBounds(350,320,200,30);
		frameLabel.setBounds(320,10,400,100);

		searchButton.setFont(Constants.SMALLBUTTONFONT);
		preprocessButton.setFont(Constants.SMALLBUTTONFONT);
		frameLabel.setFont(Constants.HEADERFONT);
		frameLabel.setForeground(Constants.HEADERCOLOR1);


		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					if( !TableUtils.isExists("PatientIDXVisit") || ! TableUtils.isExists("DoctorIDXVisit"))
					{
						JOptionPane.showMessageDialog(jframe, "First preprocess Visit table!");
						return;
					}
					new SearchInterface();
					mainFrame.dispose();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					JOptionPane.showMessageDialog(jframe, "Some error occured! Try again later!!");
				}
			}
		});


		preprocessButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				new PreprocessInterface();
				mainFrame.dispose();
			}
		});

		mainFrame.add(searchButton);
		mainFrame.add(preprocessButton);
		mainFrame.add(frameLabel);

		mainFrame.add(Constants.JPANEL2);
		mainFrame.add(Constants.JPANEL1);
	}
}