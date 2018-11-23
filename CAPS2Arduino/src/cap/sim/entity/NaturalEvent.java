package cap.sim.entity;

public class NaturalEvent {
	
	private int device_type;
	private int device_id;
	private String natural_event_file_name;
	
	public NaturalEvent() {
		// TODO Auto-generated constructor stub
		this.setDevice_type(2);
	}


	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public String getNatural_event_file_name() {
		return natural_event_file_name;
	}

	public void setNatural_event_file_name(String natural_event_file_name) {
		this.natural_event_file_name = natural_event_file_name;
	}


	public int getDevice_type() {
		return device_type;
	}


	private void setDevice_type(int device_type) {
		this.device_type = device_type;
	}
	
	

}
