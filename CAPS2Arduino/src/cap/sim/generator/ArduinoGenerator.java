package cap.sim.generator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cap.model.entity.ApplicationData;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.entity.Component;
import cap.sim.entity.Connection;
import cap.sim.entity.CupcarbonElement;
import cap.sim.entity.ElementType;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.entity.SimMode;
import cap.sim.generator.helper.ArduinoHelper;
import cap.sim.generator.helper.ModelHelper;
import cap.sim.utility.StringUtility;

public class ArduinoGenerator {

	private ModelHelper modelHelper;
	private ArduinoHelper arduinoHelper;
	
	public ArduinoGenerator() {

		modelHelper = new ModelHelper();
		setArduinoHelper(new ArduinoHelper());
	}

	public String generateArduinoCode(Component board) throws NumberFormatException, Exception {
		String script = "";

		if (board.getType().equals(ElementType.Receiver)) {
			script += new ArduinoHelper().getRecievingArduinoCode(6, 0);
		} else if (board.getType().equals(ElementType.Sender)) {
			script += getSenderCode(board);
		} else if (board.getType().equals(ElementType.Sender_Receiver)) {
			
			try {
				script+=generateTransceiver(board);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else//
		{
			return "";
		}

		return script;
	}

	private String generateTransceiver(Component board) throws NumberFormatException, Exception {
		
		ArrayList<String> ls= getComponentList(board);
		
		String script="#include <SPI.h>\n";
		script+= "#include \"pins_arduino.h\"\n";
		
		//String senderVar="sender";
		String highValVar="highVal";
		String lowValVar="lowVal";
		String valVar="val";
		
		String varH="volatile byte "+highValVar+";";
		String varL="volatile byte "+lowValVar+";";
		String varC="volatile byte "+valVar+";";
		
//		for (String ss : ls) {				
//			script+="volatile byte ss"+ss+"="+ss+"\n";
//		}
		
		script+=varH+"\n"+varL+"\n"+varC+"\n";
				
		script+="// ==== Setup() ====\n"
				+ "void setup (void){\n";
//				for (String ss : ls) {				
//					script+= "	digitalWrite("+ss+", HIGH);\n";
//				}
				script+= "	SPI.begin();\n";
				script+= "}\n";
				
		
		script+="// ==== Loop() ====\n"
				+ "void loop (void){\n";
		
		for (Sensor sensor : board.getModes().get(0).getSensors()) {
			if(sensor.getType().equals(SensorType.ReceiveMessage))
			{
				String senderID=getSenderID(sensor);
				//String senderName="s"+sensor.getDevice_id();
				//script+="if($"+senderVar+"==s"+senderID+")\n";
				script+="  digitalWrite("+senderID+", LOW);\n";
				script+="  "+highValVar+"=SPI.transfer('f');\n";
				script+="  "+highValVar+"="+highValVar+"*256;\n";
				script+="  "+lowValVar+"=SPI.transfer('s');\n";
				script+="  "+valVar+"="+highValVar+"|"+lowValVar+";\n";
				script+="  digitalWrite("+senderID+", HIGH);\n\n";
				
				script+=getSenderConditionScript(board,board.getModes().get(0),valVar,sensor);
				//script+="end\n";
			}
		}
		//script+="println $"+readingVar+"\n";
		script+=CheckOtherSendingData(board,board.getModes().get(0),valVar);
		
		script+="}";
		return script;
	}
	
	private ArrayList<String> getComponentList(Component board)
	{
		ArrayList<String> ls=new ArrayList<String>();
		for (Sensor sensor : board.getModes().get(0).getSensors()) {
			if(sensor.getType().equals(SensorType.ReceiveMessage))
			{
				try{
					String senderID=getSenderID(sensor);
					ls.add(senderID);
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return ls;
	}	
	
		
	private String getSenderConditionScript(Component comp,SimMode mod,String valVar,Sensor sensor) throws NumberFormatException, Exception
	{
		if(sensor.getType().equals(SensorType.Link))
		{
			return getSenderConditionScript(comp,mod,valVar,mod.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getTarget()))));
		}
		if(sensor.getType().equals(SensorType.ReceiveMessage))
		{
			return getSenderConditionScript(comp,mod,valVar,mod.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getOutgoing()))));
		}
		if(sensor.getType().equals(SensorType.Choice))// we have two direction
		{
			String script="";
			for(int i=0;i<sensor.getOutgoing().split(" ").length;i++)
			{
				String condition=modelHelper.getCondition(mod, mod.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getOutgoing().split(" ")[i]))));
				script+="  if"+getConditionWithVar(comp, valVar, condition)+"{\n";
				String data=modelHelper.getAppData(mod, sensor,i);
				String dataVal="";
				if(data!=null && !data.equals("")){
					String appID=StringUtility.getAppDataID(data);
					ApplicationData app= comp.getData().get(Integer.parseInt(appID));
					dataVal=app.getValue();
				}
				else
				{
					dataVal=""+valVar;
				}
				
				String toPort=modelHelper.getSendingPort(comp.getModes().get(0), sensor);
				script +=getSendingCommands(comp,toPort,dataVal);
				//script+=getSendingCommands(comp, dataVal);//getSendingCommands(comp.getModes().get(0),sensor,dataVal,i);
				script+="  }\n\n";				
			}
			return script;
		}
		return "";
		
	}
	
	private String getConditionWithVar(Component comp,String val,String condition)
	{
		String logicOp;
		if (condition.contains(">=")) {
			logicOp = ">=";
		} else if (condition.contains("<=")) {
			logicOp = "<=";
		} else if (condition.contains(">") || condition.contains("&gt;")) {
			logicOp = ">";
		} else if (condition.contains("<") || condition.contains("&lt;")) {
			logicOp = "<";
		} else {
			logicOp = "==";
		}
		condition = condition.split(logicOp)[1];
		String appDataValue= modelHelper.getApplicationValue(comp, condition);
		if(appDataValue!=null && !appDataValue.equals(""))
			condition=appDataValue;
		condition = "("+val + logicOp + condition+")";
		
		return condition;
	}

	
	private String CheckOtherSendingData(Component comp, SimMode simMode,
			String valVar) {

		String script = "";
		for (Sensor sensor : comp.getModes().get(0).getSensors()) {
			if (sensor.getType().equals(SensorType.Event)) {
				
				//String out=StringUtility.getBehaviouralElementID(sensor.getOutgoing());
				String toPort="";
				try {
					toPort = modelHelper.getSendingPort(comp.getModes().get(0), sensor);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!toPort.equals(""))
					script +=getSendingCommands(comp,toPort,valVar);
				//script+=getSendingCommands(comp,"$"+valVar);//getSendingCommands(comp.getModes().get(0), sensor,"$"+valVar);
				//script+="\n";
			}

		}
		return script;
	}
	
	private List<String> getMultipleDestination(String[] destinations) {
		ArrayList<String> targets = new ArrayList<String>();
		for (CupcarbonElement comp : ModelAnalyzer.Components) {

			if (!comp.getType().equals(ElementType.Connection)
					&& !comp.equals(ElementType.Sender)) {
				Component com = (Component) comp;
				for (String target : destinations) {
					if (com.getName().equals(target)) {
						targets.add(com.getDevice_id() + "");
						break;
					}

				}

			}
		}
		return targets;
	}

	private String getSingleDestination(String name) {
		// TODO Auto-generated method stub
		for (CupcarbonElement comp : ModelAnalyzer.Components) {

			if (!comp.getType().equals(ElementType.Connection)
					&& !comp.equals(ElementType.Sender)) {
				Component com = (Component) comp;
				if (com.getName().equals(name))
					return com.getDevice_id() + "";
			}
		}
		return "";
	}
	
	public String getSendingCommands(SimMode mode, Sensor sensor,String data,int index) throws NumberFormatException, Exception
	{
		String script="";
		String names = modelHelper.getReceiverName(mode, sensor,index);
		if (names.equals("")) {
			script += "send "+data+"\n";
		} else {
			String[] dest = names.split(",");
			if (dest.length == 1) {
				String target = getSingleDestination(dest[0]);
				script += "send "+data+" " + target + "\n";
			} else if (dest.length > 1) {
				String[] targets = (String[]) getMultipleDestination(dest)
						.toArray();
				for (String target : targets) {
					script += "send "+data+" " + target + "\n";
				}
			}
		}
		return script;
	}
	
	public String getSendingCommands(Component comp,String toPorts,String data)
	{
		String script="";
		String target="";
//		String toPort=sensor.getToMessagePorts();
		ArrayList<Integer> targets=getReciverIds(comp,toPorts);
//			if (comp.getConnectedCommponents().size() == 1) {
//				String target = ((Component)ModelAnalyzer.Components.get(comp.getConnectedCommponents().get(0))).getDevice_id()+"";
				//script += "send "+data+" " + target + "\n";
//			}
//			else if (comp.getConnectedCommponents().size() > 1) {
//				String target="";
				script+="		highVal=highByte("+data+");\n";//sensorValue << 8;
				script+="		lowVal=lowByte("+data+");\n";
				for (int i=0;i<targets.size();i++) {
					target = targets.get(i)+"";
					script+="		digitalWrite("+target+", LOW);\n";
					
					script+="		SPI.transfer(highVal);\n";					
					script+="		SPI.transfer(lowVal);\n";
					
					script+="		digitalWrite("+target+", HIGH);\n";
				}
//				script += "send "+data+" " + target + "\n";
//			}

		return script;
	}
	private ArrayList<Integer> getReciverIds(Component comp,String toPort) {
		// TODO Auto-generated method stub
		ArrayList<Integer>recievers=new ArrayList<Integer>();
		
		if(!toPort.equals("") && toPort.split(" ").length>=1)
		{
			String[] toPorts=toPort.split(" ");
		for(int i=0;i<ModelAnalyzer.Components.size();i++)
		{
			CupcarbonElement elem= ModelAnalyzer.Components.get(i);			
			if(elem.getType().equals(ElementType.Connection) ){
				Connection connection=(Connection) elem;
				for (String port : toPorts) {
					if(connection.getSource().contains(port))
					{
						Component targetCom=(Component) ModelAnalyzer.Components.get(Integer.parseInt(StringUtility.getElementID(((Connection)connection).getTarget())));
						recievers.add(targetCom.getDevice_id());
					}
				}
				
			}
		}
		}
		else //if(!toPort.equals("") )
		{
			for (int i=0;i<comp.getConnectedCommponents().size();i++) {
				recievers.add(((Component)ModelAnalyzer.Components.get(comp.getConnectedCommponents().get(i))).getDevice_id());
			}
		}		
		return recievers;
	}
	
	private String getSenderID(Sensor sensor) {
		// TODO Auto-generated method stub
		String fromport=sensor.getFromMessagePorts();
		Component sourceCom;
		
		fromport=fromport.split(" ")[0].trim();
		for(int i=0;i<ModelAnalyzer.Components.size();i++)
		{
			CupcarbonElement elem= ModelAnalyzer.Components.get(i);			
			if(elem.getType().equals(ElementType.Connection) ){
				Connection connection=(Connection) elem;
				//for (String port : toPorts) {
					if(connection.getTarget().contains(fromport))
					{
						sourceCom=(Component) ModelAnalyzer.Components.get(Integer.parseInt(StringUtility.getElementID(((Connection)connection).getSource())));
						//recievers.add(targetCom.getDevice_id());
						return sourceCom.getDevice_id()+"";
					}
				//}
				
			}
		
		}
		return "";
		
	}

	private String getSenderCode(Component board) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		int normal = 1000;
		int critical = 100;
		if (board.getModes().size() == 1) {
			String conditionScript = "";
			for (Sensor sensor : board.getModes().get(0).getSensors()) {
				if(sensor.getType().equals(SensorType.AnalogSenesor) && sensor.getOrginalType().contains("components:Count"))
				{
					//script+=CheckSingleMode(comp);//comp.getModes().get(0),comp.getName());
					critical = modelHelper.getPeriod(board.getModes().get(0), board.getModes().get(0).getSensors().get(0));
					String logicCondition = getLogicCondition(board, board.getModes().get(0),
							board.getModes().get(0).getSensors().get(0));
					
					
					if(logicCondition!=null && !logicCondition.equals(""))
					{
						
					conditionScript += "if(sensorValue" + logicCondition + ")\n";
					conditionScript += "	delay (" + critical + ");\n";
					conditionScript += "else\n";
					conditionScript += "	delay (" + normal + ");\n";
					}
					else
						conditionScript="delay (" + critical + ");\n";
					break;
					
				}
			}
			return new ArduinoHelper().getSenderArduinoCode(0, conditionScript);
			
		} else if (board.getModes().size() > 1) {
			String conditionScript = "";
			for (int i = 0; i < board.getModes().size(); i++) {
				SimMode mod = board.getModes().get(i);
				for (Sensor sensor : mod.getSensors()) {
					if (sensor.getType().equals(SensorType.AnalogSenesor)) {
						String logicCondition = getLogicCondition(board, mod, sensor);
						int period = getPeriodForMode(mod);

						conditionScript += "if(sensorValue" + logicCondition + ")\n";
						conditionScript += "	delay (" + period + ");\n";
						
						break;
					}
					
				}
				
			}
//			conditionScript += "else\n";
//			conditionScript += "	delay (" + normal + ")\n";
			
			return new ArduinoHelper().getSenderArduinoCode(0, conditionScript);
		}

		return "";
	}

	private String getLogicCondition(Component comp, SimMode mode, Sensor sensor) {
		String script = "";
		String logicOp = "";

		String condition = "";
		try {
			condition = modelHelper.getCondition(mode, sensor);
			;
		} catch (Exception ex) {
			return "";
		}

		if (condition != null && !condition.equals("")) {

			if (condition.contains(">=")) {
				logicOp = ">=";
			} else if (condition.contains("<=")) {
				logicOp = "<=";
			} else if (condition.contains(">")) {
				logicOp = ">";
			} else if (condition.contains("<")) {
				logicOp = "<";
			} else {
				logicOp = "==";
			}
			condition = condition.split(logicOp)[1];
			double mean = 0;
			try {
				mean = Double.parseDouble(condition.trim());
			} catch (Exception ex) {
				try {
					ArrayList<ApplicationData> appls = (ArrayList<ApplicationData>) comp.getData();
					for (ApplicationData appdata : appls) {
						if (appdata.getDataName().contains(condition)) {
							mean = Double.parseDouble(appdata.getValue());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			condition = logicOp + mean;

		}
		return condition;
	}

	private int getPeriodForMode(SimMode mod) {
		// TODO Auto-generated method stub
		int period = 1000;
		for (Sensor sensor : mod.getSensors()) {
			if (sensor.getType().equals(SensorType.Start)) {
				period = sensor.getPeriod();
				break;
			}
		}
		return period;
	}

	/*
	 * private String generateTransceiver(Component comp) throws
	 * NumberFormatException, Exception {
	 * 
	 * String readingVar="var"; String senderVar="sender"; String valVar="val";
	 * String script=""; script+="loop\n"; script+="wait\n";
	 * script+="read "+readingVar+"\n";
	 * script+="rdata $"+readingVar+" "+senderVar+" "+valVar+"\n";
	 * 
	 * for (Sensor sensor : comp.getModes().get(0).getSensors()) {
	 * if(sensor.getType().equals(SensorType.ReceiveMessage)) { String
	 * senderID=getSenderID(sensor); String
	 * senderName="s"+sensor.getDevice_id();//getDataRecipientName();
	 * script+="if($"+senderVar+"==s"+senderID+")\n"; //
	 * script+=getSendingCommands(comp.getModes().get(0),sensor,"$"+valVar);
	 * script+=getSenderConditionScript(comp,comp.getModes().get(0),valVar,
	 * sensor); script+="end\n"; } } //script+="println $"+readingVar+"\n";
	 * script+=CheckOtherSendingData(comp,comp.getModes().get(0),valVar); return
	 * script; }
	 * 
	 * private String getSenderID(Sensor sensor) { // TODO Auto-generated method
	 * stub String fromport=sensor.getFromMessagePorts(); Component sourceCom;
	 * 
	 * fromport=fromport.split(" ")[0].trim(); for(int
	 * i=0;i<ModelAnalyzer.Components.size();i++) { CupcarbonElement elem=
	 * ModelAnalyzer.Components.get(i);
	 * if(elem.getType().equals(ElementType.Connection) ){ Connection
	 * connection=(Connection) elem; //for (String port : toPorts) {
	 * if(connection.getTarget().contains(fromport)) { sourceCom=(Component)
	 * ModelAnalyzer.Components.get(Integer.parseInt(StringUtility.getElementID(
	 * ((Connection)connection).getSource())));
	 * //recievers.add(targetCom.getDevice_id()); return
	 * sourceCom.getDevice_id()+""; } //}
	 * 
	 * }
	 * 
	 * } return "";
	 * 
	 * }
	 * 
	 * private String CheckOtherSendingData(Component comp, SimMode simMode,
	 * String valVar) {
	 * 
	 * String script = ""; for (Sensor sensor :
	 * comp.getModes().get(0).getSensors()) { if
	 * (sensor.getType().equals(SensorType.Event)) {
	 * 
	 * //String out=StringUtility.getBehaviouralElementID(sensor.getOutgoing());
	 * String toPort=""; try { toPort =
	 * modelHelper.getSendingPort(comp.getModes().get(0), sensor); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * if(!toPort.equals("")) script
	 * +=getSendingCommands(comp,toPort,"$"+valVar);
	 * //script+=getSendingCommands(comp,"$"+valVar);//getSendingCommands(comp.
	 * getModes().get(0), sensor,"$"+valVar); //script+="\n"; }
	 * 
	 * } return script; }
	 * 
	 * private String getSenderConditionScript(Component comp,SimMode mod,String
	 * valVar,Sensor sensor) throws NumberFormatException, Exception {
	 * if(sensor.getType().equals(SensorType.Link)) { return
	 * getSenderConditionScript(comp,mod,valVar,mod.getSensors().get(
	 * Integer.parseInt(StringUtility
	 * .getBehaviouralElementID(sensor.getTarget())))); }
	 * if(sensor.getType().equals(SensorType.ReceiveMessage)) { return
	 * getSenderConditionScript(comp,mod,valVar,mod.getSensors().get(
	 * Integer.parseInt(StringUtility
	 * .getBehaviouralElementID(sensor.getOutgoing())))); }
	 * if(sensor.getType().equals(SensorType.Choice))// we have two direction {
	 * String script=""; for(int
	 * i=0;i<sensor.getOutgoing().split(" ").length;i++) { String
	 * condition=modelHelper.getCondition(mod, mod.getSensors().get(
	 * Integer.parseInt(StringUtility
	 * .getBehaviouralElementID(sensor.getOutgoing().split(" ")[i]))));
	 * script+="if"+getConditionWithVar(comp, valVar, condition)+"\n"; String
	 * data=modelHelper.getAppData(mod, sensor,i); String dataVal="";
	 * if(data!=null && !data.equals("")){ String
	 * appID=StringUtility.getAppDataID(data); ApplicationData app=
	 * comp.getData().get(Integer.parseInt(appID)); dataVal=app.getValue(); }
	 * else { dataVal="$"+valVar; }
	 * 
	 * String toPort=modelHelper.getSendingPort(comp.getModes().get(0), sensor);
	 * script +=getSendingCommands(comp,toPort,dataVal);
	 * //script+=getSendingCommands(comp,
	 * dataVal);//getSendingCommands(comp.getModes().get(0),sensor,dataVal,i);
	 * script+="end\n"; } return script; } return "";
	 * 
	 * }
	 * 
	 * private String generateModeScript(Component comp) throws
	 * NumberFormatException, Exception { // TODO Auto-generated method stub
	 * String script = ""; int period = 1000; // String condStr = ""; if
	 * (comp.getModes().size() == 1) { for (Sensor sensor :
	 * comp.getModes().get(0).getSensors()) {
	 * if(sensor.getType().equals(SensorType.AnalogSenesor) &&
	 * sensor.getOrginalType().contains("components:Count")) {
	 * script+=CheckSingleMode(comp);//comp.getModes().get(0),comp.getName()); }
	 * else if (sensor.getType().equals(SensorType.AnalogSenesor) &&
	 * sensor.getOrginalType().contains("components:Sense") ) { if
	 * (modelHelper.isCyclic(comp.getModes().get(0), sensor)) script +=
	 * "loop\n";
	 * 
	 * String var = "var"; script += scriptHelper.reading_NaturalEvent(var);
	 * String vars = "val0 x val1";// generateVars(comp); // vars= var0 val0
	 * var1 val1 .... script += "rdata $" + var + " " + vars + "\n"; script +=
	 * getCondationScript(comp,comp.getModes().get(0), sensor); script +=
	 * scriptHelper.writVarToFileCode("val0"); period =
	 * modelHelper.getPeriod(comp.getModes().get(0), sensor); script += "delay "
	 * + period + "\n"; break; } } } else if (comp.getModes().size() > 1) {
	 * String sensorVal = "sensorVal1"; script += "set mod 0\n"; script +=
	 * "loop\n"; for (int i = 0; i < comp.getModes().size(); i++) { SimMode mod
	 * = comp.getModes().get(i);
	 * 
	 * script += "if($mod=="+(i)+")\n"; int sensorCount=0; for (Sensor sensor :
	 * mod.getSensors()) { if
	 * (sensor.getType().equals(SensorType.AnalogSenesor)) { sensorCount+=1;
	 * String compName =
	 * "s"+comp.getDevice_id();//+comp.getName();//sensor.getOrginalType().split
	 * (":")[1]; if (mod.getType().contains("InitialMode")) {
	 * 
	 * script += "areadsensor var\n"; script += "rdata $var t x " + sensorVal +
	 * "\n"; String data="p"; script += "data "+data+ " " + compName + " $" +
	 * sensorVal+ "\n";
	 * 
	 * String toPort=""; try { toPort = modelHelper.getSendingPort(mod, sensor);
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * //e.printStackTrace(); toPort=""; } if(!toPort.equals("")) script
	 * +=getSendingCommands(comp,toPort,"$"+data);
	 * 
	 * //getSendingCommands(mod,sensor,"$"+data);// need to remove // script +=
	 * "send $p 3\n"; //script += "printfile $"+data+"\n"; //period =
	 * modelHelper.getPeriod(mod,sensor); } // get the condition String
	 * condition=getWhileCondition(comp,"$"+sensorVal,modelHelper.
	 * getConditionMode(mod, sensor)); String
	 * rcondition=reverseCondition(condition);
	 * 
	 * script+="while"+rcondition+"\n"; script+="areadsensor var\n";
	 * script+="rdata $var t x "+sensorVal+"\n"; String data="p"; script +=
	 * "data "+data+ " " + compName + " $" + sensorVal+ "\n"; String toPort="";
	 * try { toPort = modelHelper.getSendingPort(mod, sensor); } catch
	 * (Exception e) { // TODO Auto-generated catch block //e.printStackTrace();
	 * toPort=""; } if(!toPort.equals("")) script
	 * +=getSendingCommands(comp,toPort,"$"+data); //script
	 * +=getSendingCommands(comp,
	 * "$"+data);//getSendingCommands(mod,sensor,"$"+data); //script +=
	 * "printfile $"+data+"\n";
	 * 
	 * period=getPeriodForMode(mod); script+="delay "+period+"\n";
	 * script+="end\n";// end while script+="if"+condition+"\n";
	 * script+="set mod "+getTargetMod(comp,mod, sensor)+"\n";
	 * script+="end\n";// end if
	 * 
	 * break; } } if(sensorCount==0) { // check the app data value // get App
	 * Data ArrayList<ApplicationData> appls=(ArrayList<ApplicationData>)
	 * comp.getData(); for (Sensor sensor : mod.getSensors()) { if
	 * (sensor.getType().equals(SensorType.BroadcastSendMessage) ||
	 * sensor.getType().equals(SensorType.MulticastSendMessage) ||
	 * sensor.getType().equals(SensorType.UnicastSendMessage)) { String
	 * id=StringUtility.getAppDataID( sensor.getDataRecipient()); int
	 * aid=Integer.parseInt(id); ApplicationData appdata =appls.get(aid);
	 * 
	 * String toPort=modelHelper.getSendingPort(mod, sensor); script
	 * +=getSendingCommands(comp,toPort,appdata.getValue());
	 * //script+=getSendingCommands(comp, appdata.getValue());
	 * period=getPeriodForMode(mod); script+="delay "+period+"\n"; } } // check
	 * the destionation } script+="end\n";// end if }
	 * 
	 * } return script; }
	 * 
	 * 
	 * 
	 * private String CheckSingleMode(Component comp) { // TODO Auto-generated
	 * method stub int period=1000; boolean cyclic=true; String script="";
	 * String sendingVar="sendVar"; Sensor mysensor=null; for (Sensor sensor :
	 * comp.getModes().get(0).getSensors()) {
	 * if(sensor.getType().equals(SensorType.Start)) { cyclic=sensor.isCyclic();
	 * period=sensor.getPeriod(); }
	 * if(sensor.getType().equals(SensorType.UnicastSendMessage)) {
	 * mysensor=sensor; //sensor.getre } } if(cyclic) script+="loop\n";
	 * script+="areadsensor var\n"; script+="rdata $var t x count\n";
	 * script+="data "+sendingVar+" s"+comp.getDevice_id()+" $count\n";
	 * 
	 * String toPort=""; try { toPort =
	 * modelHelper.getSendingPort(comp.getModes().get(0), mysensor); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * if(!toPort.equals("")) script +=getSendingCommands(comp,toPort,
	 * "$"+sendingVar); //script+=getSendingCommands(comp,
	 * "$"+sendingVar);//getSendingCommands(comp.getModes().get(0),mysensor,"$"+
	 * sendingVar); //script+="printfile $"+sendingVar+"\n";
	 * script+="delay "+period+"\n"; return script; }
	 * 
	 * private String getTargetMod(Component comp,SimMode mod, Sensor sensor)
	 * throws NumberFormatException, Exception { int
	 * exitID=Integer.parseInt(modelHelper.getTargetExit(mod, sensor));; Exit
	 * exit=mod.getExites().get(exitID); return
	 * StringUtility.getModeID(exit.getTargetMode()); }
	 * 
	 * private String reverseCondition(String condition) { // TODO
	 * Auto-generated method stub if(condition.contains(">")) return
	 * condition=condition.replace(">", "<"); else return
	 * condition=condition.replace("<", ">"); }
	 * 
	 * private String getConditionWithVar(Component comp,String val,String
	 * condition) { String logicOp; if (condition.contains(">=")) { logicOp =
	 * ">="; } else if (condition.contains("<=")) { logicOp = "<="; } else if
	 * (condition.contains(">") || condition.contains("&gt;")) { logicOp = ">";
	 * } else if (condition.contains("<") || condition.contains("&lt;")) {
	 * logicOp = "<"; } else { logicOp = "=="; } condition =
	 * condition.split(logicOp)[1]; String appDataValue=
	 * modelHelper.getApplicationValue(comp, condition); if(appDataValue!=null
	 * && !appDataValue.equals("")) condition=appDataValue; condition = "($"+val
	 * + logicOp + condition+")";
	 * 
	 * return condition; }
	 * 
	 * private String getWhileCondition(Component comp,String val,String
	 * condition) { // TODO Auto-generated method stub if (condition != null &&
	 * !condition.equals("")) {
	 * 
	 * String logicOp; if (condition.contains(">=")) { logicOp = ">="; } else if
	 * (condition.contains("<=")) { logicOp = "<="; } else if
	 * (condition.contains(">") || condition.contains("&gt;")) { logicOp = ">";
	 * } else if (condition.contains("<") || condition.contains("&lt;")) {
	 * logicOp = "<"; } else { logicOp = "=="; } condition =
	 * condition.split(logicOp)[1];
	 * 
	 * double mean=0; try { mean = Double.parseDouble(condition.trim()); }
	 * catch(Exception ex) { try { ArrayList<ApplicationData> appls=
	 * (ArrayList<ApplicationData>) comp.getData(); for (ApplicationData appdata
	 * : appls) { if(appdata.getDataName().contains(condition)) { mean =
	 * Double.parseDouble(appdata.getValue()); } } } catch(Exception e ){
	 * e.printStackTrace();
	 * 
	 * } }
	 * 
	 * condition = "("+val + logicOp + mean+")"; } return condition; }
	 * 
	 * 
	 * private String getCondationScript(Component comp,SimMode mode, Sensor
	 * sensor) { // TODO Auto-generated method stub String script = ""; String
	 * logicOp = "";
	 * 
	 * String condition = ""; try{ condition = modelHelper.getCondition(mode,
	 * sensor); } catch(Exception ex) { return ""; }
	 * 
	 * 
	 * if (condition != null && !condition.equals("")) {
	 * 
	 * if (condition.contains(">=")) { logicOp = ">="; } else if
	 * (condition.contains("<=")) { logicOp = "<="; } else if
	 * (condition.contains(">")) { logicOp = ">"; } else if
	 * (condition.contains("<")) { logicOp = "<"; } else { logicOp = "=="; }
	 * condition = condition.split(logicOp)[1]; double mean=0; try { mean =
	 * Double.parseDouble(condition.trim()); } catch(Exception ex) { try {
	 * ArrayList<ApplicationData> appls= (ArrayList<ApplicationData>)
	 * comp.getData(); for (ApplicationData appdata : appls) {
	 * if(appdata.getDataName().contains(condition)) { mean =
	 * Double.parseDouble(appdata.getValue()); } } } catch(Exception e ){
	 * e.printStackTrace();
	 * 
	 * } } condition = "$val1" + logicOp + mean; script += "if(" + condition +
	 * ")\n";
	 * 
	 * String toPort=""; try { toPort =
	 * modelHelper.getSendingPort(comp.getModes().get(0), sensor); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * if(!toPort.equals("")) script +=getSendingCommands(comp,toPort, "$val0");
	 * //xxxxxx // String names = modelHelper.getReceiverName(mode, sensor); //
	 * if (names.equals("")) { // script += "send $val0\n"; // } else { //
	 * String[] dest = names.split(","); // if (dest.length == 1) { // String
	 * target = getSingleDestination(dest[0]); // script += "send $val0 " +
	 * target + "\n"; // } else if (dest.length > 1) { // String[] targets =
	 * (String[]) getMultipleDestination(dest) // .toArray(); // for (String
	 * target : targets) { // script += "send $val0 " + target + "\n"; // } // }
	 * // } script += "end\n"; } return script; }
	 * 
	 * public String getSendingCommands(SimMode mode, Sensor sensor,String
	 * data,int index) throws NumberFormatException, Exception { String
	 * script=""; String names = modelHelper.getReceiverName(mode,
	 * sensor,index); if (names.equals("")) { script += "send "+data+"\n"; }
	 * else { String[] dest = names.split(","); if (dest.length == 1) { String
	 * target = getSingleDestination(dest[0]); script += "send "+data+" " +
	 * target + "\n"; } else if (dest.length > 1) { String[] targets =
	 * (String[]) getMultipleDestination(dest) .toArray(); for (String target :
	 * targets) { script += "send "+data+" " + target + "\n"; } } } return
	 * script; }
	 * 
	 * public String getSendingCommands(Component comp,String toPorts,String
	 * data) { String script=""; String target=""; // String
	 * toPort=sensor.getToMessagePorts(); ArrayList<Integer>
	 * targets=getReciverIds(comp,toPorts); // if
	 * (comp.getConnectedCommponents().size() == 1) { // String target =
	 * ((Component)ModelAnalyzer.Components.get(comp.getConnectedCommponents().
	 * get(0))).getDevice_id()+""; //script += "send "+data+" " + target + "\n";
	 * // } // else if (comp.getConnectedCommponents().size() > 1) { // String
	 * target=""; for (int i=0;i<targets.size();i++) { target +=
	 * targets.get(i)+" "; } script += "send "+data+" " + target + "\n"; // }
	 * 
	 * return script; }
	 * 
	 * 
	 * 
	 * private ArrayList<Integer> getReciverIds(Component comp,String toPort) {
	 * // TODO Auto-generated method stub ArrayList<Integer>recievers=new
	 * ArrayList<Integer>();
	 * 
	 * if(!toPort.equals("") && toPort.split(" ").length>=1) { String[]
	 * toPorts=toPort.split(" "); for(int
	 * i=0;i<ModelAnalyzer.Components.size();i++) { CupcarbonElement elem=
	 * ModelAnalyzer.Components.get(i);
	 * if(elem.getType().equals(ElementType.Connection) ){ Connection
	 * connection=(Connection) elem; for (String port : toPorts) {
	 * if(connection.getSource().contains(port)) { Component
	 * targetCom=(Component)
	 * ModelAnalyzer.Components.get(Integer.parseInt(StringUtility.getElementID(
	 * ((Connection)connection).getTarget())));
	 * recievers.add(targetCom.getDevice_id()); } }
	 * 
	 * } } } else //if(!toPort.equals("") ) { for (int
	 * i=0;i<comp.getConnectedCommponents().size();i++) {
	 * recievers.add(((Component)ModelAnalyzer.Components.get(comp.
	 * getConnectedCommponents().get(i))).getDevice_id()); } } return recievers;
	 * }
	 * 
	 * public String getSendingCommandsx(Component comp, String data) { String
	 * script=""; if (comp.getConnectedCommponents().size() == 1) { String
	 * target =
	 * ((Component)ModelAnalyzer.Components.get(comp.getConnectedCommponents().
	 * get(0))).getDevice_id()+""; script += "send "+data+" " + target + "\n"; }
	 * else if (comp.getConnectedCommponents().size() > 1) { String target="";
	 * for (int i=0;i<comp.getConnectedCommponents().size();i++) { target +=
	 * ((Component)ModelAnalyzer.Components.get(comp.getConnectedCommponents().
	 * get(i))).getDevice_id()+" "; } script += "send "+data+" " + target +
	 * "\n"; }
	 * 
	 * return script; }
	 * 
	 * public String getSendingCommands(SimMode mode, Sensor sensor,String data)
	 * throws NumberFormatException, Exception { String script=""; String names
	 * = modelHelper.getReceiverName(mode, sensor); if (names.equals("")) {
	 * script += "send "+data+"\n"; } else { String[] dest = names.split(",");
	 * if (dest.length == 1) { String target = getSingleDestination(dest[0]);
	 * script += "send "+data+" " + target + "\n"; } else if (dest.length > 1) {
	 * String[] targets = (String[]) getMultipleDestination(dest) .toArray();
	 * for (String target : targets) { script += "send "+data+" " + target +
	 * "\n"; } } } return script; }
	 * 
	 * 
	 * private List<String> getMultipleDestination(String[] destinations) {
	 * ArrayList<String> targets = new ArrayList<String>(); for
	 * (CupcarbonElement comp : ModelAnalyzer.Components) {
	 * 
	 * if (!comp.getType().equals(ElementType.Connection) &&
	 * !comp.equals(ElementType.Sender)) { Component com = (Component) comp; for
	 * (String target : destinations) { if (com.getName().equals(target)) {
	 * targets.add(com.getDevice_id() + ""); break; }
	 * 
	 * }
	 * 
	 * } } return targets; }
	 * 
	 * private String getSingleDestination(String name) { // TODO Auto-generated
	 * method stub for (CupcarbonElement comp : ModelAnalyzer.Components) {
	 * 
	 * if (!comp.getType().equals(ElementType.Connection) &&
	 * !comp.equals(ElementType.Sender)) { Component com = (Component) comp; if
	 * (com.getName().equals(name)) return com.getDevice_id() + ""; } } return
	 * ""; }
	 * 
	 * private String generateVars(Component comp) { // TODO Auto-generated
	 * method stub String var = "var"; String val = "val";
	 * 
	 * String vars = ""; for (int i = 0; i < comp.getNumberOfAnalog(); i++) {
	 * vars = "" + var + "" + i; vars += " " + val + "" + i; } return vars; }
	 */
	public ModelHelper getModelHelper() {
		return modelHelper;
	}

	public void setModelHelper(ModelHelper modelHelper) {
		this.modelHelper = modelHelper;
	}

	public ArduinoHelper getArduinoHelper() {
		return arduinoHelper;
	}

	public void setArduinoHelper(ArduinoHelper arduinoHelper) {
		arduinoHelper = arduinoHelper;
	}

}
