package cap.testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cap.model.entity.SoftwareArchitecture;
import cap.model.handler.HWML_XMLParser;
import cap.model.handler.SPML_XMLParser;
import cap.model.handler.XMLParser;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.generator.ConfigurationGenerator;
import cap.sim.generator.ScriptController;
import cap.sim.gui.CAPSToArduino;

public class TestReadingFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
				
		XMLParser handler=new XMLParser(CAPSToArduino.capsPath);
		handler.loadDataFromXML();
		SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
		
		SPML_XMLParser spml=new SPML_XMLParser("");//CAPSToArduino.spml);
		spml.loadDataFromXML();
		
		HWML_XMLParser hwml=new HWML_XMLParser("");//CAPSToArduino.hwml);
		hwml.loadDataFromXML();
		
		assertNotEquals("Elements are empty", 0, SoftwareArchitecture.getSoftwareArchitechure().getElements().size());
		
		ModelAnalyzer.ComponentId=0;
		ModelAnalyzer.SensorId=0;
		ModelAnalyzer.EventId=0;
////		// Analyzing Model >>> 
		ModelAnalyzer.analyzeModel(sa);	
////		// Create SenScript code files
		new ScriptController().createScript("");
////		// create Sensor Radios && Nodes(sensors) Files
		ConfigurationGenerator.creatConfigration();
//		
//		//add other folder
//		FolderUtil.checkPath(cubPath+"\\gps");
//		FolderUtil.checkPath(cubPath+"\\logs");
//		FolderUtil.checkPath(cubPath+"\\network");
//		FolderUtil.checkPath(cubPath+"\\results");
//		FolderUtil.checkPath(cubPath+"\\tmp");
//		FolderUtil.checkPath(cubPath+"\\xbee");
//		
//		ProjectGenerator.createDefaultSimulationParams(cubPath);
//		// add simulationParams.cfg file under config folder
//		// add .cup file under main folder
//		
//		System.out.println("END");
		
//		XMLParser handler=new XMLParser("finalcasestudy.xml");
//		handler.loadDataFromXML();		
//		SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
//		
//		
//		assertNotEquals("Elements are empty", 0, SoftwareArchitecture.getSoftwareArchitechure().getElements().size());
//		
//		ModelAnalyzer.ComponentId=0;
//		ModelAnalyzer.SensorId=0;
////		// Analyzing Model >>> 
//		ModelAnalyzer.analyzeModel(sa);	
////		// Create SenScript code files
//		new ScriptController().createScript("");
////		// create Sensor Radios && Nodes(sensors) Files
//		ConfigurationGenerator.creatConfigration();
//		
//		System.out.println("END");
	}

}
