package cap.sim.analyzer;

import java.util.ArrayList;
import java.util.List;

import cap.model.entity.BehaviouralElements;
import cap.model.entity.Mode;
import cap.model.entity.Port;
import cap.model.entity.SAElement;
import cap.model.entity.SoftwareArchitecture;
import cap.sim.entity.CupcarbonElement;
import cap.sim.entity.Connection;
import cap.sim.entity.ElementType;
import cap.sim.entity.Component;
import cap.sim.entity.Sensor;
import cap.sim.entity.SensorType;
import cap.sim.entity.SimMode;
import cap.sim.generator.ArduinoBoard;
import cap.sim.utility.StringUtility;


public class ArduinoModelAnalyzer {

	public static List<ArduinoBoard> Boards = new ArrayList<ArduinoBoard>();
	public static int ComponentId = 0;
	public static int SensorId=0;
	public static int EventId=0;
	
	public static boolean analyzeModel(SoftwareArchitecture sa) {
		for (SAElement element : sa.getElements()) {
			analyzeElement(element);
		}
		for (ArduinoBoard comp : Boards) {
			analyzeConnection(comp);
		}
		return true;
	}	
	
	private static void analyzeConnection(CupcarbonElement comp) {

		if (!comp.getType().equals(ElementType.Connection))
			return;
		int source=Integer.parseInt(StringUtility.getElementID(((Connection)comp).getSource()));
		int target=Integer.parseInt(StringUtility.getElementID(((Connection)comp).getTarget()));
		
		//((Component)Components.get(source)).getConnectedCommponents().add(target);
	}

	private static void analyzeElement(SAElement element) {
		// TODO Auto-generated method stub
		if (element.getType().equals("components:Component")) {
			if (element.getPorts() == null || element.getPorts().size() == 0)
				return;

			boolean receiver, sender;
			receiver = sender = false;

			for (Port port : element.getPorts()) {
				if (port.getType().equals("components:InMessagePort")
						|| port.getType().contains("InMessagePort")) {
					receiver = true;
				}
				if (port.getType().equals("components:OutMessagePort")
						|| port.getType().contains("OutMessagePort")) {
					sender = true;
				}
			}
			Component comp = new Component();
			comp.setDevice_id(++ComponentId);
			comp.setDevice_elevation(0);
			comp.setDevice_radius(0);
			comp.setDevice_hide(0);
			comp.setDevice_draw_battery(false);
			comp.setDeviceType(1);
			comp.setDevice_sensor_unit_radius(20.0);
			comp.setDevice_latitude(0);
			comp.setDevice_longitude(0);
			if (receiver && sender == false)
				comp.setType(ElementType.Receiver);
			else if (receiver == false && sender)
				comp.setType(ElementType.Sender);
			else if (receiver && sender)
				comp.setType(ElementType.Sender_Receiver);
			
			comp.setName(element.getName());
			comp.getData().addAll(element.getApps());
			// Analyzing Modes
			analyzeSensorModes(element,comp);
			AnalyzeSensorConnection(element,comp);
			
			//Components.add(comp);
		} else {
						
			Connection connection=new Connection();
			connection.setType(ElementType.Connection);
			connection.setTarget(element.getTarget());
			connection.setSource(element.getSource());
			
			//Components.add(connection);			
		}
	}

	private static void AnalyzeSensorConnection(SAElement element,
			Component comp) {
		
		
	}

	private static void analyzeSensorModes(SAElement e, Component comp) {
		
		for (Mode m : e.getModes()) {
			
			SimMode mod=new SimMode();
			mod.setName(m.getName());
			mod.setType(m.getType());
			mod.getEntries().addAll(m.getEntries());
			mod.getExites().addAll(m.getExites());
			
			comp.getModes().add(mod);
			
			for (BehaviouralElements b : m.getBehaviouralElements()) {
				
				Sensor s=new Sensor();
				s.setDevice_id(++SensorId);
				s.setName(b.getName());
				s.setOrginalType(b.getType());
				if(b.getType().contains("components:Link"))
				{					
					s.setType(SensorType.Link);
					s.setSource(b.getSource());
					s.setTarget(b.getTarget());
					s.setCondition(b.getCondition());					
				}
				
				else
				{	
					if (b.getType().contains("components:StartTimer")) {
						s.setType(SensorType.Start);
						try {
							s.setCyclic(Boolean.parseBoolean(b.getCyclic()));
							s.setPeriod(Integer.parseInt(b.getPeriod()));
						} catch (Exception ex) {
							s.setCyclic(true);
						}
					} 
					else if (b.getType().contains("components:TimerFired")||b.getType().contains("components:Actuate"))
					{// connected with timer
						s.setType(SensorType.Event);
						s.setOutgoing(b.getOutgoing());
						s.setTimer(b.getTimer());
					}
					 else if (b.getType().contains("components:Fork"))
						{
						 	s.setType(SensorType.GroupLink);
							s.setOutgoing(b.getOutgoing());
							s.setIncoming(b.getIncoming());
						}
					else if( b.getType().contains("components:Count")|| b.getType().contains("components:PeopleCounter")|| b.getType().contains("components:Sense")) //Count
					{
						s.setType(SensorType.AnalogSenesor);
						s.setIncoming(b.getIncoming());
						s.setOutgoing(b.getOutgoing());
						int num=comp.getNumberOfAnalog();
						num+=1;
						comp.setNumberOfAnalog(num);						
					}	
					
					else if(b.getType().contains("components:UnicastSendMessage"))
					{
						s.setType(SensorType.UnicastSendMessage);
						s.setReceiverName(b.getReceiverName());
						s.setIncoming(b.getIncoming());
						s.setOutgoing(b.getOutgoing());
						s.setToMessagePorts(b.getToMessagePorts());
						s.setDataRecipient(b.getDataRecipient());
					}
					else if(b.getType().contains("components:MulticastSendMessage"))
					{
						s.setType(SensorType.MulticastSendMessage);
						s.setReceiverNames(b.getReceiverNames());
						s.setIncoming(b.getIncoming());
						s.setOutgoing(b.getOutgoing());
						s.setToMessagePorts(b.getToMessagePorts());
						s.setDataRecipient(b.getDataRecipient());
					}
					else if(b.getType().contains("components:BroadcastSendMessage"))
					{
						s.setType(SensorType.BroadcastSendMessage);
						s.setReceiverName("All");
						s.setIncoming(b.getIncoming());
						s.setOutgoing(b.getOutgoing());
						s.setToMessagePorts(b.getToMessagePorts());
						s.setDataRecipient(b.getDataRecipient());
					}
					else if(b.getType().contains("components:ReceiveMessage"))
					{
						s.setType(SensorType.ReceiveMessage);
						s.setDataRecipientName(b.getDataRecipientName());
						s.setIncoming(b.getIncoming());
						s.setOutgoing(b.getOutgoing());
						s.setFromMessagePorts(b.getFromMessagePorts());
					}
					else if(b.getType().contains("components:Choice"))
					{
						s.setType(SensorType.Choice);
						s.setIncoming(b.getIncoming());
						s.setOutgoing(b.getOutgoing());
					}					
					else
					{
						s.setType(SensorType.other);
					}
				}
				mod.getSensors().add(s);
			}
		}
	}

}
