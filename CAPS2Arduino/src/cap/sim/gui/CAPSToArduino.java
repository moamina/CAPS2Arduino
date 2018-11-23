package cap.sim.gui;

import static org.junit.Assert.assertNotEquals;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.SystemColor;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import cap.model.entity.SoftwareArchitecture;
import cap.model.handler.HWML_XMLParser;
import cap.model.handler.SPML_XMLParser;
import cap.model.handler.XMLParser;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.generator.AduinoScriptController;
import cap.sim.generator.ConfigurationGenerator;
import cap.sim.generator.ProjectGenerator;
import cap.sim.generator.ScriptController;
import cap.sim.utility.FolderUtil;
import cap.sim.utility.GUIUtilities;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class CAPSToArduino extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> filterls;
	private JTextField txtOutFile;
	private JTextField txtSAMLfile;
	
	public static String capsPath;
	public static String cubPath;
	private JTextField txtHWMLFile;
	
	private static String spml;
	private static String hwml;
	private JProgressBar progressBar;
	private JTextArea textArea;
	public static Task Thread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//GUIUtilities.GetSubstanceNebulaBrickWallLookAndFeel();
		//GetSubstanceBusinessLookAndFeel();
		//GetSubstanceMistAquaLookAndFeel();
		GUIUtilities.GetSubstanceModerateLookAndFeel();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CAPSToArduino frame = new CAPSToArduino();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CAPSToArduino() {

		filterls = new ArrayList<String>();
		
		
		setTitle("Arduino Code Generator Framwork");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				setVisible(false);
				System.exit(0);
			}
		});
		setBackground(SystemColor.desktop);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 695, 487);
		// if(user.getUserLevel()==2)
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);

			JMenu mnFile = new JMenu("File");
			menuBar.add(mnFile);

			JMenuItem mntmChangePassword = new JMenuItem("Exit");
			mntmChangePassword.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					System.exit(0);
				}
			});
			mnFile.add(mntmChangePassword);
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnConvertCapsTo = new JButton("Generate Arduino Code");
		btnConvertCapsTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//CAPSToArduino.capsPath="cases/finalcasestudy.xml";
				Thread=new Task();
				Thread.start();
				Thread.textString+=">>> Parsing the SAML file ...\n";
				XMLParser handler=new XMLParser(CAPSToArduino.capsPath);
				handler.loadDataFromXML();
				Thread.textString+=">>> END of Parsing <<<\n";
				Thread.progress+=10;
				
				SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
				
//				SPML_XMLParser spml=new SPML_XMLParser("");//CAPSToArduino.spml);
//				spml.loadDataFromXML();
				Thread.textString+=">>> Parsing the HWML file ...\n";
				HWML_XMLParser hwml=new HWML_XMLParser(CAPSToArduino.hwml);
				hwml.loadDataFromXML();
				Thread.textString+=">>> END of Parsing <<<\n";
				Thread.progress+=10;
				
				
				ModelAnalyzer.ComponentId=0;
				ModelAnalyzer.SensorId=0;
				ModelAnalyzer.EventId=0;
				
				Thread.textString+=">>> Analyzing ...\n";
				ModelAnalyzer.analyzeModel(sa);
			
				
				Thread.textString+=">>> END Of Analyzing <<<\n";
				Thread.progress+=30;
				
				try {
					Thread.textString+=">>> Generating Arduino Code <<< \n";
					new AduinoScriptController().createCode("");
					Thread.textString+=">>> END Of Generating Arduino Code <<<\n";
					Thread.progress=100;
					
					Thread.textString+=">>> Finished <<< ";
					Thread.setStop(true);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
//				XMLParser handler=new XMLParser(CAPSToArduino.capsPath);
//				handler.loadDataFromXML();
//				SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
//				
//				SPML_XMLParser spml=new SPML_XMLParser(CAPSToArduino.spml);
//				spml.loadDataFromXML();
//				
//				HWML_XMLParser hwml=new HWML_XMLParser(CAPSToArduino.hwml);
//				hwml.loadDataFromXML();
//				
////				assertNotEquals("Elements are empty", 0, SoftwareArchitecture.getSoftwareArchitechure().getElements().size());
//				
//				ModelAnalyzer.ComponentId=0;
//				ModelAnalyzer.SensorId=0;
//				ModelAnalyzer.EventId=0;
////				// Analyzing Model >>> 
//				ModelAnalyzer.analyzeModel(sa);	
////				// Create SenScript code files
//				new ScriptController().createScript("");
////				// create Sensor Radios && Nodes(sensors) Files
//				ConfigurationGenerator.creatConfigration();
//				
//				//add other folder
//				FolderUtil.checkPath(cubPath+"\\gps");
//				FolderUtil.checkPath(cubPath+"\\logs");
//				FolderUtil.checkPath(cubPath+"\\network");
//				FolderUtil.checkPath(cubPath+"\\results");
//				FolderUtil.checkPath(cubPath+"\\tmp");
//				FolderUtil.checkPath(cubPath+"\\xbee");
//				
//				ProjectGenerator.createDefaultSimulationParams(cubPath);
//				// add simulationParams.cfg file under config folder
//				// add .cup file under main folder
//				
//				System.out.println("END");
				
			}
		});
		btnConvertCapsTo.setBounds(13, 173, 648, 42);
		panel.add(btnConvertCapsTo);
		
		JButton btnCupcarbon = new JButton("Save To");
		btnCupcarbon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				fc.setDialogTitle("Select Destination Folder");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				
				int returnVal = fc.showOpenDialog(CAPSToArduino.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//File file2=fc.getCurrentDirectory();
					// This is where a real application would open the file.
					txtOutFile.setText(file.getPath());
				} else {
					txtOutFile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				};
				CAPSToArduino.cubPath=txtOutFile.getText();
			}
		});
		//btnCupcarbon.setToolTipText("Load Element using filter");
		btnCupcarbon.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCupcarbon.setBounds(541, 123, 120, 42);
		panel.add(btnCupcarbon);
		
		txtOutFile = new JTextField();
		txtOutFile.setToolTipText("Project Name");
		txtOutFile.setEditable(false);
		txtOutFile.setColumns(10);
		txtOutFile.setBounds(13, 123, 517, 42);
		panel.add(txtOutFile);
		
		txtSAMLfile = new JTextField();
		txtSAMLfile.setToolTipText("File Path");
		txtSAMLfile.setEditable(false);
		txtSAMLfile.setColumns(10);
		txtSAMLfile.setBounds(13, 13, 517, 42);
		panel.add(txtSAMLfile);
		
		JButton btnCaps = new JButton("SAML");
		btnCaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				int returnVal = fc.showOpenDialog(CAPSToArduino.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					txtSAMLfile.setText(file.getPath());
				} else {
					txtSAMLfile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				}
				CAPSToArduino.capsPath=txtSAMLfile.getText();
			}
		});
		btnCaps.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCaps.setBounds(541, 13, 120, 42);
		panel.add(btnCaps);
		
		txtHWMLFile = new JTextField();
		txtHWMLFile.setToolTipText("File Path");
		txtHWMLFile.setEditable(false);
		txtHWMLFile.setColumns(10);
		txtHWMLFile.setBounds(13, 68, 517, 42);
		panel.add(txtHWMLFile);
		
		JButton btnHm = new JButton("HWML");
		btnHm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				int returnVal = fc.showOpenDialog(CAPSToArduino.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					txtHWMLFile.setText(file.getPath());
				} else {
					txtHWMLFile.setText("No File Selected");
					// log.append("Open command cancelled by user." + newline);
				}
				CAPSToArduino.hwml=txtHWMLFile.getText();
			}
		});
		btnHm.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHm.setBounds(541, 68, 120, 42);
		panel.add(btnHm);
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(12, 228, 649, 30);
		panel.add(progressBar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(13, 271, 648, 120);
		panel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		
	}

	protected void checkSecondFilter(ArrayList<String> filter) {
	
		
	}

	protected ArrayList<String> getCheckedRows() {
		// TODO Auto-generated method stub
		ArrayList<String> ls=new ArrayList<String>();
//		for (int i = 0; i < table.getRowCount(); i++) {
//			try
//			{
//		     Boolean isChecked = Boolean.valueOf(table.getValueAt(i, 2).toString());
//
//		     if (isChecked) {
//		        //get the values of the columns you need.
//		    	 ls.add(table.getValueAt(i, 1).toString());
//		    } else {
//		        System.out.printf("Row %s is not checked \n", i);
//		    }
//		     }catch(Exception ex){
//		    	 System.out.println("Value is "+table.getValueAt(i, 2));
//		     }
//		}
//		System.out.println(ls.size());
		return ls;
	}



	public ArrayList<String> getFilterls() {
		return filterls;
	}

	public void setFilterls(ArrayList<String> filterls) {
		this.filterls = filterls;
	}
	
	private class Task extends Thread
	{
		private boolean stop;
		private String textString;
		private int progress;
		
		public Task() {
			stop=false;
			setTextString("");
			progress=0;
		}
		
		@Override
		public void run() {
			
			while(!isStop())
			{
				SwingUtilities.invokeLater(new Runnable() {
		               public void run() {
		            	   textArea.setText(textString);
		            	   progressBar.setValue(progress);		            	   
		               }
		            });
		            try {
		               Thread.sleep(10000);
		            } catch (InterruptedException e) {}
			}			
		}
		
		/*
		@Override
		public void run() {
			t="Started ...\n";
			 for(int i =0; i<= 100; i+=1){
		            final int progress = i;
		            
	                  if(i==1)
	                  {
	                	  t+="Parsing the SAML file ...\n";
	                	  
	                  }
	                  if(i==30)
	                  {
	                	  t+="Analyzing ...\n";
	                	 
	                  }
	                  if(i==60)
	                  {
	                	  t+="Parsing HWML File && Generating Arduino Code ...\n";
	                	  
	                  }
	                  if(i==100)
	                  {
	                	  t+="===== Finished =====";
	                	  
	                  }
		            SwingUtilities.invokeLater(new Runnable() {
		               public void run() {
		            	   textArea.setText(t);
		            	   progressBar.setValue(progress);
		               }
		            });
		            try {
		               Thread.sleep(100);
		            } catch (InterruptedException e) {}
		         }
		}*/

		public boolean isStop() {
			return stop;
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public String getTextString() {
			return textString;
		}

		public void setTextString(String textString) {
			this.textString = textString;
		}
	}
}
