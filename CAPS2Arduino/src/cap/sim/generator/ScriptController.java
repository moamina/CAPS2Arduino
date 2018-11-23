package cap.sim.generator;

import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.entity.CupcarbonElement;
import cap.sim.entity.Component;
import cap.sim.entity.NaturalEvent;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.gui.CAPSToArduino;
import cap.sim.utility.FileUtility;
import cap.sim.utility.FolderUtil;

public class ScriptController {

	public boolean createScript(String path) {
		// create normal simple script
		SenScriptGenerator generator=new SenScriptGenerator();
		String scriptFolder=CAPSToArduino.cubPath+"\\scripts";
		FolderUtil.checkPath(scriptFolder);
		
		for (CupcarbonElement comp : ModelAnalyzer.Components) {
			if (comp instanceof Component) {
				Component s = (Component) comp;
					String script="";
					try {
						script = generator.generateScript(s);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					String scriptfile="sensorScript" + s.getDevice_id()+".csc";
					FileUtility.createAndWriteToFile(scriptFolder+"\\"+scriptfile, script);
					s.setScriptfile(scriptfile);
					if(s.getModes()==null || s.getModes().size()==0 )continue;
					for (int i = 0; i < s.getModes().get(0).getSensors().size(); i++) {
						if(s.getModes().get(0).getSensors().get(i).getType().equals(SensorType.AnalogSenesor))
						{//Natural Event
							Sensor sensor= s.getModes().get(0).getSensors().get(i);
							NaturalEvent event=new NaturalEvent();
							event.setDevice_id(++ModelAnalyzer.EventId);
							String fileName=s.getName().replace(" ", "_");
							event.setNatural_event_file_name(fileName+".evt");
							sensor.setEvent(event);
							break;
						}
					}
					
			}

		}		
		return true;
	}
}
