package cap.sim.entity;

import java.util.ArrayList;
import java.util.List;

import cap.model.entity.ApplicationData;

public class Component extends CupcarbonElement{	
	
	private List<Integer> connectedCommponents;
	private List<SimMode> modes;
	private List<ApplicationData> data;
	private int numberOfAnalog=0;
	private String name;
	//==========================================
	private int device_id;
	private int deviceType;
	private double device_longitude;
	private double device_latitude;
	private double device_elevation;
	private double device_radius;
	private int device_hide;
	private boolean device_draw_battery;
	private double device_sensor_unit_radius;
	private String device_script_file_name;
	private String scriptfile;
	public Component() {
		// TODO Auto-generated constructor stub
		setConnectedCommponents(new ArrayList<Integer>());
//		setSensors(new ArrayList<Sensor>());
		modes=new ArrayList<SimMode>();
		setData(new ArrayList<ApplicationData>());
	}	

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public double getDevice_longitude() {
		return device_longitude;
	}

	public void setDevice_longitude(double device_longitude) {
		this.device_longitude = device_longitude;
	}

	public double getDevice_latitude() {
		return device_latitude;
	}

	public void setDevice_latitude(double device_latitude) {
		this.device_latitude = device_latitude;
	}

	public double getDevice_elevation() {
		return device_elevation;
	}

	public void setDevice_elevation(double device_elevation) {
		this.device_elevation = device_elevation;
	}

	public double getDevice_radius() {
		return device_radius;
	}

	public void setDevice_radius(double device_radius) {
		this.device_radius = device_radius;
	}

	public int getDevice_hide() {
		return device_hide;
	}

	public void setDevice_hide(int device_hide) {
		this.device_hide = device_hide;
	}

	public boolean isDevice_draw_battery() {
		return device_draw_battery;
	}

	public void setDevice_draw_battery(boolean device_draw_battery) {
		this.device_draw_battery = device_draw_battery;
	}

	public double getDevice_sensor_unit_radius() {
		return device_sensor_unit_radius;
	}

	public void setDevice_sensor_unit_radius(double device_sensor_unit_radius) {
		this.device_sensor_unit_radius = device_sensor_unit_radius;
	}

	public String getDevice_script_file_name() {
		return device_script_file_name;
	}

	public void setDevice_script_file_name(String device_script_file_name) {
		this.device_script_file_name = device_script_file_name;
	}

	

	public String getScriptfile() {
		return scriptfile;
	}

	public void setScriptfile(String scriptfile) {
		this.scriptfile = scriptfile;
	}

	public List<Integer> getConnectedCommponents() {
		return connectedCommponents;
	}

	public void setConnectedCommponents(ArrayList<Integer> connectedCommponents) {
		this.connectedCommponents = connectedCommponents;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SimMode> getModes() {
		return modes;
	}

	public void setModes(List<SimMode> modes) {
		this.modes = modes;
	}

	public int getNumberOfAnalog() {
		return numberOfAnalog;
	}

	public void setNumberOfAnalog(int numberOfAnalog) {
		this.numberOfAnalog = numberOfAnalog;
	}

	public List<ApplicationData> getData() {
		return data;
	}

	public void setData(List<ApplicationData> data) {
		this.data = data;
	}
}
