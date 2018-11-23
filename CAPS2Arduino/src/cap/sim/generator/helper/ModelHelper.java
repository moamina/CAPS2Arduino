package cap.sim.generator.helper;

import cap.model.entity.ApplicationData;
import cap.sim.entity.Component;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.entity.SimMode;
import cap.sim.utility.StringUtility;

public class ModelHelper {

	
	public String getApplicationValue(Component comp,String varName)
	{
		for (ApplicationData data : comp.getData()) {
			if(data.getDataName().equals(varName))
			{
				return data.getValue();
			}
		}
		return "";
	}
	/**
	 * get the delay value
	 * 
	 * @param s
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public int getPeriod(SimMode mode, Sensor s) throws NumberFormatException, Exception {
		if (s.getType().equals(SensorType.Start))
			return s.getPeriod();
		else if (s.getType().equals(SensorType.Link))
			return getPeriod(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getSource()))));
		else if (s.getType().equals(SensorType.GroupLink))
			return getPeriod(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getIncoming()))));
		else if (s.getType().equals(SensorType.Event))
			return getPeriod(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getTimer()))));
		else if (s.getType().equals(SensorType.AnalogSenesor))
			return getPeriod(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getIncoming()))));
		else
			return 1000;
	}

	/**
	 * check if the script contain loop or not
	 * 
	 * @param s
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public boolean isCyclic(SimMode mode, Sensor s) throws NumberFormatException, Exception {
		if (s.getType().equals(SensorType.Start))
			return s.isCyclic();
		else if (s.getType().equals(SensorType.Link))
			return isCyclic(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getSource()))));
		else if (s.getType().equals(SensorType.GroupLink))
			return isCyclic(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getIncoming()))));
		else if (s.getType().equals(SensorType.Event))
			return isCyclic(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getTimer()))));
		else if (s.getType().equals(SensorType.AnalogSenesor))
			return isCyclic(
					mode,
					mode.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getIncoming()))));
		else
			return true;
	}

	public String getCondition(SimMode mod, Sensor s) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		if (s.getType().equals(SensorType.Link) && !s.getCondition().equals(""))
			return s.getCondition();
		if (s.getType().equals(SensorType.Link) && s.getCondition().equals(""))
			return getCondition(
					mod,
					mod.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getTarget()))));
		
		return getCondition(
				mod,
				mod.getSensors().get(
						Integer.parseInt(StringUtility
								.getBehaviouralElementID(s.getOutgoing()))));
		
	}
	public String getAppData(SimMode mod, Sensor s,int index) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		if (s.getType().equals(SensorType.Link))
			return s.getDataRecipientName();
		return getAppData(
				mod,
				mod.getSensors().get(
						Integer.parseInt(StringUtility
								.getBehaviouralElementID(s.getOutgoing().split(" ")[index]))),index);
	}
	
	public String getConditionMode(SimMode mode,Sensor sensor) throws NumberFormatException, Exception
	{
		if(sensor.getType().equals(SensorType.Link) && sensor.getTarget().contains("exits"))
		{
			return sensor.getCondition();
		}
		else if(sensor.getType().equals(SensorType.Link))
		{
			return getConditionMode( mode,mode.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getTarget()))));
		}
		else if(sensor.getType().toString().contains("SendMessage"))
		{
			return getConditionMode( mode,mode.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getOutgoing()))));
		}
		return getConditionMode( mode,mode.getSensors().get(
				Integer.parseInt(StringUtility
						.getBehaviouralElementID(sensor.getOutgoing()))));
		
	}
	public String getTargetExit(SimMode mode,Sensor sensor) throws NumberFormatException, Exception
	{
		if(sensor.getType().equals(SensorType.Link) && sensor.getTarget().contains("exits") && sensor.getTarget().contains("modes"))
		{
			return StringUtility.getExitID(sensor.getTarget());
		}
		else if(sensor.getType().equals(SensorType.Link))
		{
			return getTargetExit( mode,mode.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getTarget()))));
		}
		else if(sensor.getType().toString().contains("SendMessage"))
		{
			return getTargetExit( mode,mode.getSensors().get(
					Integer.parseInt(StringUtility
							.getBehaviouralElementID(sensor.getOutgoing()))));
		}
		return getTargetExit( mode,mode.getSensors().get(
				Integer.parseInt(StringUtility
						.getBehaviouralElementID(sensor.getOutgoing()))));
		
	}

	public String getReceiverName(SimMode mod, Sensor s,int index) throws NumberFormatException, Exception {
		// getReceivers(com,s);
		if (s.getType().equals(SensorType.UnicastSendMessage))
			return s.getReceiverName();
		if (s.getType().equals(SensorType.MulticastSendMessage))
			{
			String rnames="";
				for (int i = 0; i < s.getReceiverNames().size(); i++) {
					rnames+=s.getReceiverNames().get(i)+",";
				}
				return rnames;
			}
		if (s.getType().equals(SensorType.BroadcastSendMessage))
			{			
				return "";
			}
		if (s.getType().equals(SensorType.Link)) {
			return getReceiverName(
					mod,
					mod.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getTarget()))));
		}
		return getReceiverName(
				mod,
				mod.getSensors().get(
						Integer.parseInt(StringUtility
								.getBehaviouralElementID(s.getOutgoing().split(" ")[index]))));

	}
	
	
	
	public String getReceiverName(SimMode mod, Sensor s) throws NumberFormatException, Exception {
		// getReceivers(com,s);
		if (s.getType().equals(SensorType.UnicastSendMessage))
			return s.getReceiverName();
		if (s.getType().equals(SensorType.MulticastSendMessage))
			{
			String rnames="";
				for (int i = 0; i < s.getReceiverNames().size(); i++) {
					rnames+=s.getReceiverNames().get(i)+",";
				}
				return rnames;
			}
		if (s.getType().equals(SensorType.BroadcastSendMessage))
			{			
				return "";
			}
		if (s.getType().equals(SensorType.Link)) {
			return getReceiverName(
					mod,
					mod.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getTarget()))));
		}
		return getReceiverName(
				mod,
				mod.getSensors().get(
						Integer.parseInt(StringUtility
								.getBehaviouralElementID(s.getOutgoing()))));

	}
	public String getSendingPort(SimMode mod, Sensor s) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		
		
		if (s.getType().equals(SensorType.BroadcastSendMessage))
			{			
				return "";
			}
		else if (s.getType().equals(SensorType.UnicastSendMessage)||s.getType().equals(SensorType.MulticastSendMessage))
			return s.getToMessagePorts();
		
		else if (s.getType().equals(SensorType.Choice)) {
			return getSendingPort(
					mod,
					mod.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getOutgoing().split(" ")[0]))));
		}
		
		else if (s.getType().equals(SensorType.Link)) {
			return getSendingPort(
					mod,
					mod.getSensors().get(
							Integer.parseInt(StringUtility
									.getBehaviouralElementID(s.getTarget()))));
		}
		return getSendingPort(
				mod,
				mod.getSensors().get(
						Integer.parseInt(StringUtility
								.getBehaviouralElementID(s.getOutgoing()))));
		
	}
}
