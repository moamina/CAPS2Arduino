package cap.sim.generator;

import java.util.ArrayList;
import java.util.logging.FileHandler;

import cap.model.entity.ApplicationData;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.entity.CupcarbonElement;
import cap.sim.entity.Component;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.entity.SimMode;
import cap.sim.generator.helper.ModelHelper;
import cap.sim.gui.CAPSToArduino;
import cap.sim.utility.FileUtility;
import cap.sim.utility.FolderUtil;
import cap.sim.utility.HWMLUtil;

public class ConfigurationGenerator {

	public static boolean creatConfigration() {
		String sensor_radios=CAPSToArduino.cubPath+"\\config\\sensor_radios\\";
		FolderUtil.checkPath(sensor_radios);
		// create Sensor Radios files
		for (CupcarbonElement comp : ModelAnalyzer.Components) {
			if (comp instanceof Component) {
				Component s = (Component) comp;
				SensorRadios.createSensorRadios(s, sensor_radios,HWMLUtil.getHWMLByID(((Component) comp).getDevice_id()+""));

			}
		}
		String nodesfolder=CAPSToArduino.cubPath+"\\config\\nodes\\";
		FolderUtil.checkPath(nodesfolder);
		
		String NaturalEvent=CAPSToArduino.cubPath+"\\natevents\\";
		FolderUtil.checkPath(NaturalEvent);
		// Create Sensor files
		for (CupcarbonElement comp : ModelAnalyzer.Components) {
			if (comp instanceof Component) {
				Component s = (Component) comp;
				NodeGenerator.createNode(s,nodesfolder);
				if(s.getModes()==null || s.getModes().size()==0 )continue;
				for (int i = 0; i < s.getModes().get(0).getSensors().size(); i++) {
;
					Sensor sensor = s.getModes().get(0).getSensors().get(i);
					if (sensor.getType().equals(SensorType.AnalogSenesor)) {
						NodeGenerator.createNaturalEvent(s.getModes().get(0).getSensors().get(i)
								.getEvent(),nodesfolder);

						String condition="";
						try
						{
						condition= new ModelHelper().getCondition(s.getModes().get(0),
								sensor);
						}
					
					catch (Exception e) {
						// TODO: handle exception
					}
						
						String logicOp = "";
						if (condition != null && !condition.equals("")) {
							
							if (condition.contains(">=")) {
								logicOp = ">=";
							}
							else if (condition.contains("<=")) {
								logicOp = "<=";
							}
							else if (condition.contains(">")) {
								logicOp = ">";
							}  
							else if (condition.contains("<") || condition.contains("&lt;")) {
								logicOp = "<";
							}  else {
								logicOp = "==";
							}
							condition = condition.split(logicOp)[1];
							
						
							
						}
						if(condition.equals(""))
							condition="35";
						double mean =35;
						try
						{
							 mean = Double.parseDouble(condition);
						}
						catch(Exception ex)
						{
							try
							{
							ArrayList<ApplicationData> appls= (ArrayList<ApplicationData>) s.getData();
							for (ApplicationData appdata : appls) {
								if(appdata.getDataName().contains(condition))
								{
									mean = Double.parseDouble(appdata.getValue());
								}
							}
							}
							catch(Exception e ){
								e.printStackTrace();
								}
						}
						NaturalEventGenerator event = new NaturalEventGenerator(
								mean, 4, 500);
						String data=event.generateFormatedData();
						FileUtility.createAndWriteToFile(NaturalEvent+sensor.getEvent().getNatural_event_file_name(), data);
						break;
					}
					
				}
			}
		}
		return false;
	}

}
