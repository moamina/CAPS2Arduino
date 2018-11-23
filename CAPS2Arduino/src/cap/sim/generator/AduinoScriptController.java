package cap.sim.generator;

import cap.sim.analyzer.ArduinoModelAnalyzer;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.entity.CupcarbonElement;
import cap.sim.entity.Component;
import cap.sim.entity.NaturalEvent;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.gui.CAPSToArduino;
import cap.sim.utility.FileUtility;
import cap.sim.utility.FolderUtil;

public class AduinoScriptController {
	public boolean createCode(String path) 
			throws NumberFormatException, Exception {		
		ArduinoGenerator generator=new ArduinoGenerator();
		String codeFolder=CAPSToArduino.cubPath+"\\code";
		FolderUtil.checkPath(codeFolder);		
		for (CupcarbonElement comp : ModelAnalyzer.Components) {
			
			if (comp instanceof Component) {
			Component board = (Component) comp;
			String code = "";
			code = generator.generateArduinoCode(board);
			String codefile = "ArduinoCode_" + board.getName() + ".ino";
			FileUtility.createAndWriteToFile(codeFolder + "\\" + codefile, code);	
			}
		}				
		return true;
	}
}
