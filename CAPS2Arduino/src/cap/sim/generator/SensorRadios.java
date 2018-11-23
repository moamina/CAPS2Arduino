package cap.sim.generator;


import com.hwml.entity.HWML;

import cap.sim.entity.Component;
import cap.sim.utility.FileUtility;

public class SensorRadios {

	
	public static boolean createSensorRadios(Component node, String path,HWML hwObj) {
			path += "sensor_" + node.getDevice_id();
		
			String content = " List of radio Modules for the Sensor"
					+ node.getDevice_id()+"\n";
			content += "";
			content += "------------------------------------------\n";
			content += "current_radio_name:"+hwObj.getCurrent_radio_name()+"\n";
			content += "radio_standard:"+hwObj.getRadio_standard()+"\n";
			content += "radio_my:"+hwObj.getRadio_my()+"\n";
			content += "radio_channel:"+hwObj.getRadio_channel()+"\n";
			content += "radio_network_id:"+hwObj.getRadio_network_id()+"\n";
			content += "radio_radiu:"+hwObj.getRadio_radius()+"\n";
			content += "radio_etx:"+hwObj.getRadio_etx()+"\n";
			content += "radio_erx:"+hwObj.getRadio_erx()+"\n";
			content += "radio_econtentleep:"+hwObj.getRadio_esleep()+"\n";
			content += "radio_elicontentten:"+hwObj.getRadio_elisten()+"\n";
			content += "radio_data_rate:"+hwObj.getRadio_data_rate()+"\n";
			
			return FileUtility.createAndWriteToFile(path, content);
	}
}
