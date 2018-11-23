package cap.sim.generator.helper;

public class SenScriptHelper {
	
	/**
	 * reading natural event value and broadcast it
	 * 
	 * @return
	 */
	public String reading_NaturalEvent(String var) {

		return "areadsensor " + var + "\n";
	}

	/**
	 * send data : broadcast the data to all sensors
	 * @param var
	 * @return
	 */
	public String sendData(String var) {
		return "send $" + var + "\n";
	}
	
	public String sendData(String var,String destination) {
		return "send $" + var + " "+destination+"\n";
	}

	/**
	 * reading data from sensor
	 * 
	 * @return
	 */
	public String readingData(String var) {
		
		String script = "loop\n";
		script += "wait\n";
		script += "read " + var + "\n";
		//script += "println $" + var + "\n";
		script += "mark $"+var+"\n";
		//script += writVarToFileCode(var);
		return script;
	}

	/**
	 * write data to file with time
	 * 
	 * @param var
	 * @return
	 */
	public String writVarToFileCode(String var) {
		String script="";
//		script = "time t\n";
//		script += "printfile $t $" + var + "\n";
		return script;
	}
}
