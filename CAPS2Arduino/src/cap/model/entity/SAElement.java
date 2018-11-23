package cap.model.entity;

import java.util.ArrayList;
import java.util.List;


public class SAElement {
	
	private String type;
	private String name;
	private String source;
	private String target;
	
	private List<Port> ports=null;
	private List<Mode> modes=null;
	private List<ApplicationData> apps=null;

	public SAElement() {
		// TODO Auto-generated constructor stub
		setPorts(new ArrayList<Port>());
		setModes(new ArrayList<Mode>());
		setApps(new ArrayList<ApplicationData>());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Port> getPorts() {
		return ports;
	}

	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}

	public List<Mode> getModes() {
		return this.modes;
	}

	public void setModes(List<Mode> modes) {
		this.modes = modes;
	}

	public List<ApplicationData> getApps() {
		return apps;
	}

	public void setApps(List<ApplicationData> apps) {
		this.apps = apps;
	}
	
	
}
