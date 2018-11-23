package cap.testing;

import static org.junit.Assert.assertNotEquals;

import cap.model.entity.SoftwareArchitecture;
import cap.model.handler.HWML_XMLParser;
import cap.model.handler.SPML_XMLParser;
import cap.model.handler.XMLParser;
import cap.sim.analyzer.ArduinoModelAnalyzer;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.generator.AduinoScriptController;
import cap.sim.gui.CAPSToArduino;

public class ArduinoTest {

	public static void main(String[] args) throws NumberFormatException, Exception {
		
		CAPSToArduino.capsPath="cases/finalcasestudy.xml";
		
		XMLParser handler=new XMLParser(CAPSToArduino.capsPath);
		handler.loadDataFromXML();
		SoftwareArchitecture sa=SoftwareArchitecture.getSoftwareArchitechure();
		
//		SPML_XMLParser spml=new SPML_XMLParser("");//CAPSToArduino.spml);
//		spml.loadDataFromXML();
		
//		HWML_XMLParser hwml=new HWML_XMLParser("");//CAPSToArduino.hwml);
//		hwml.loadDataFromXML();
		
		assertNotEquals("Elements are empty", 0, SoftwareArchitecture.getSoftwareArchitechure().getElements().size());
		
		ModelAnalyzer.ComponentId=0;
		ModelAnalyzer.SensorId=0;
		ModelAnalyzer.EventId=0;
		// Analyzing Model >>> 
		ModelAnalyzer.analyzeModel(sa);
		//ArduinoModelAnalyzer.analyzeModel(sa);
		new AduinoScriptController().createCode("");
		
		
				
	}
}
