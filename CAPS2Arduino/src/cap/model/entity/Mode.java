package cap.model.entity;

import java.util.ArrayList;
import java.util.List;


public class Mode {
		
	private String name;
	private List<BehaviouralElements> behaviouralElements;
	private List<Entry> entries;
	private List<Exit> exites;
	private String type;
	
	public Mode() {
		// TODO Auto-generated constructor stub
		setBehaviouralElements(new ArrayList<BehaviouralElements>());
		setEntries(new ArrayList<Entry>());
		setExites(new ArrayList<Exit>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BehaviouralElements> getBehaviouralElements() {
		return behaviouralElements;
	}

	public void setBehaviouralElements(List<BehaviouralElements> behaviouralElements) {
		this.behaviouralElements = behaviouralElements;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public List<Exit> getExites() {
		return exites;
	}

	public void setExites(List<Exit> exites) {
		this.exites = exites;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
