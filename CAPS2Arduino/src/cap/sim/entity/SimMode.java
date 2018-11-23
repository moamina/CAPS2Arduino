package cap.sim.entity;

import java.util.ArrayList;
import java.util.List;

import cap.model.entity.Entry;
import cap.model.entity.Exit;

public class SimMode {
	
	private String type;
	private String name;
	private ArrayList<Sensor> sensors;
	private List<Entry> entries;
	private List<Exit> exites;
	public SimMode() {
		// TODO Auto-generated constructor stub
		this.entries=new ArrayList<Entry>();
		this.exites=new ArrayList<Exit>();
		sensors=new ArrayList<Sensor>();
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
	public ArrayList<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(ArrayList<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<Exit> getExites() {
		return exites;
	}
	public void setExites(List<Exit> exites) {
		this.exites = exites;
	}
	public List<Entry> getEntries() {
		return entries;
	}
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	
	

}
