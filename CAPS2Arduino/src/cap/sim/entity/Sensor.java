package cap.sim.entity;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
	private String dataType;
	private String orginalType;
	private String name;
	private int device_id;
	private String file_name;
	private SensorType type;
	private boolean cyclic;
	private int period;
	private String outgoing;
	private String incoming;
	private String timerName;
	private String timer;
	private String receiverName;
	private String source;
	private String target;
	private String fromMessagePorts;
	private String toMessagePorts;
	private String dataRecipient;
	private String dataRecipientName;
	private String condition;
	private List<String> receiverNames;	
	private ArrayList<Integer> connectedSensors;
	private NaturalEvent event;
	
	public Sensor() {
		setConnectedSensors(new ArrayList<Integer>());
		setReceiverNames(new ArrayList<String>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SensorType getType() {
		return type;
	}

	public void setType(SensorType type) {
		this.type = type;
	}

	public boolean isCyclic() {
		return cyclic;
	}

	public void setCyclic(boolean cyclic) {
		this.cyclic = cyclic;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(String outgoing) {
		this.outgoing = outgoing;
	}

	public String getIncoming() {
		return incoming;
	}

	public void setIncoming(String incoming) {
		this.incoming = incoming;
	}

	public String getTimerName() {
		return timerName;
	}

	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getFromMessagePorts() {
		return fromMessagePorts;
	}

	public void setFromMessagePorts(String fromMessagePorts) {
		this.fromMessagePorts = fromMessagePorts;
	}

	public String getToMessagePorts() {
		return toMessagePorts;
	}

	public void setToMessagePorts(String toMessagePorts) {
		this.toMessagePorts = toMessagePorts;
	}

	public String getDataRecipient() {
		return dataRecipient;
	}

	public void setDataRecipient(String dataRecipient) {
		this.dataRecipient = dataRecipient;
	}

	public ArrayList<Integer> getConnectedSensors() {
		return connectedSensors;
	}

	public void setConnectedSensors(ArrayList<Integer> connectedSensors) {
		this.connectedSensors = connectedSensors;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public List<String> getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(List<String> receiverNames) {
		this.receiverNames = receiverNames;
	}

	public NaturalEvent getEvent() {
		return event;
	}

	public void setEvent(NaturalEvent event) {
		this.event = event;
	}

	public String getOrginalType() {
		return orginalType;
	}

	public void setOrginalType(String orginalType) {
		this.orginalType = orginalType;
	}

	public String getDataRecipientName() {
		return dataRecipientName;
	}

	public void setDataRecipientName(String dataRecipientName) {
		this.dataRecipientName = dataRecipientName;
	}
	
	

}
