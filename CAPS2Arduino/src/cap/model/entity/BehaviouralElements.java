package cap.model.entity;

import java.util.ArrayList;
import java.util.List;

public class BehaviouralElements {
	
	private String name;
	private String type;
	private String fromMessagePorts;
	private String toMessagePorts;
	private String outgoing;
	private String incoming;
	private String source;
	private String target;
	private String condition;
	private String dataRecipient;
	private String dataRecipientName;
	private String cyclic;	
	private String period;
	private String timerName;
	private String timer;
	private String receiverName;	
	private List<String> receiverNames;
	
	public BehaviouralElements() {
		// TODO Auto-generated constructor stub
		setReceiverNames(new ArrayList<String>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFromMessagePorts() {
		return fromMessagePorts;
	}

	public void setFromMessagePorts(String fromMessagePorts) {
		this.fromMessagePorts = fromMessagePorts;
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDataRecipient() {
		return dataRecipient;
	}

	public void setDataRecipient(String dataRecipient) {
		this.dataRecipient = dataRecipient;
	}

	public String getCyclic() {
		return cyclic;
	}

	public void setCyclic(String cyclic) {
		this.cyclic = cyclic;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public String getToMessagePorts() {
		return toMessagePorts;
	}

	public void setToMessagePorts(String toMessagePorts) {
		this.toMessagePorts = toMessagePorts;
	}

	public List<String> getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(List<String> receiverNames) {
		this.receiverNames = receiverNames;
	}

	public String getDataRecipientName() {
		return dataRecipientName;
	}

	public void setDataRecipientName(String dataRecipientName) {
		this.dataRecipientName = dataRecipientName;
	}
	
}
