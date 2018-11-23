package cap.sim.generator;

import com.spml.entity.SPML;

import cap.sim.entity.Component;
import cap.sim.entity.NaturalEvent;
import cap.sim.entity.Sensor;
import cap.sim.utility.FileUtility;
import cap.sim.utility.SPMLUtil;

public class NodeGenerator {
	
	public static boolean createNode(Component node,String path)
	{
		SPML spObj=SPMLUtil.getSPMLByID(node.getDevice_id()+"");
		
		path += "sensor_" + node.getDevice_id();
		String content="";
		content+="List of parameters\n";
		content+="------------------------------------------\n";
		content+="device_type:"+spObj.getDevice_type()+"\n";
		content+="device_id:"+node.getDevice_id()+"\n";
		content+="device_longitude:"+spObj.getDevice_longitude()+"\n";
		content+="device_latitude:"+spObj.getDevice_latitude()+"\n";
		content+="device_elevation:"+spObj.getDevice_elevation()+"\n";
		content+="device_radius:"+spObj.getDevice_radius()+"\n";
		content+="device_hide:"+spObj.getDevice_hide()+"\n";
		content+="device_draw_battery:"+spObj.getDevice_draw_battery()+"\n";
		content+="device_sensor_unit_radius:"+spObj.getDevice_sensor_unit_radius()+"\n";
		content+="device_script_file_name:"+node.getScriptfile()+"\n";
		
		return FileUtility.createAndWriteToFile(path, content);
	}
	public static boolean createNaturalEvent(NaturalEvent event,String path)
	{
		path += "gas_" + event.getDevice_id();
		String content="";
		content+="List of parameters\n";
		content+="------------------------------------------\n";
		content+="device_type:"+event.getDevice_type()+"\n";
		content+="device_id:"+event.getDevice_id()+"\n";
		content+="device_longitude:-4.491734504699707\n";
		content+="device_latitude:48.392217799586646\n";
		content+="device_elevation:0.0\n";
		content+="device_radius:10.0\n";
		content+="device_hide:0\n";
		content+="natural_event_file_name:"+event.getNatural_event_file_name()+"\n";
		
		return FileUtility.createAndWriteToFile(path, content);
	}
}
