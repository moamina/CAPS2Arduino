package cap.sim.generator;

import cap.sim.utility.FileUtility;

public class ProjectGenerator {
	
	public static boolean createDefaultSimulationParams(String path)
	{
		 path += "\\config\\simulationParams.cfg";
		String content = null;
		content = "simulationtime:864000.0\n";
		content += "mobility:true\n";
		content += "simulationspeed:100\n";
		content += "arrowspeed:50\n";
		content += "log:true\n";
		content += "results:true\n";
		content += "acktype:0.0\n";
		content += "ackproba:1.0\n";
		content += "acklinks:false\n";
		content += "ack:false\n";
		content += "symmetricalinks:false\n";
		content += "clockdrift:false\n";
		content += "visibility:false\n";
		content += "results_writing_period:0.01";
		
		return FileUtility.createAndWriteToFile(path, content);
	}
	
	public static boolean createProjectFile(String path,String projectName)
	{
		path+="\\"+projectName+".cup";
		String content="";
		content+="CupCarbon v. 3.0.0 (U-One)\n";
		content+="----------------\n";
		content+="name:"+projectName+"\n";
		content+="zoom:1\n";
		content+="centerposition_la:48.390329839365656\n";
		content+="centerposition_lo:-4.477577805519104\n";
		content+="map:6\n";
		content+="display_details:true\n";
		content+="draw_radio_links:true\n";
		content+="draw_sensor_arrows:true\n";
		content+="radio_links_color:2\n";
		content+="draw_marker_arrows:false\n";
		content+="display_rl_distance:false\n";
		content+="propagation:false\n";
		content+="display_marker_distance:false\n";
		content+="display_radio_messages:true\n";
		content+="draw_script_file_name:true";
		
		return FileUtility.createAndWriteToFile(path, content);
	}
}
