package cap.sim.generator;

import java.util.ArrayList;
import java.util.Hashtable;

import cap.sim.entity.CommunicationType;
import cap.sim.entity.Component;

public class ArduinoBoard extends Component{
	
	private int boardID;
	private String name;
	private CommunicationType communicationType;//SPI | LORA | nb_IoT ...
	private Hashtable<Integer, Boolean> usedPins;
	private ArrayList<ArduinoBoard> connectedArduino;
	private String arduino_Instruction_File_Name;
	private String arduino_Instructions;
	// ...
	public ArduinoBoard() {		
		
		setUsedPins(new Hashtable<Integer, Boolean>());
		setConnectedArduino(new ArrayList<ArduinoBoard>());
	}
	
	public int getBoardID() {
		return boardID;
	}

	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}

	public ArrayList<ArduinoBoard> getConnectedArduino() {
		return connectedArduino;
	}

	public void setConnectedArduino(ArrayList<ArduinoBoard> connectedArduino) {
		this.connectedArduino = connectedArduino;
	}

	public Hashtable<Integer, Boolean> getUsedPins() {
		return usedPins;
	}

	public void setUsedPins(Hashtable<Integer, Boolean> usedPins) {
		this.usedPins = usedPins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommunicationType getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(CommunicationType communicationType) {
		this.communicationType = communicationType;
	}

	public String getArduino_Instructions() {
		return arduino_Instructions;
	}

	public void setArduino_Instructions(String arduino_Instructions) {
		this.arduino_Instructions = arduino_Instructions;
	}

	public String getArduino_Instruction_File_Name() {
		return arduino_Instruction_File_Name;
	}

	public void setArduino_Instruction_File_Name(String arduino_Instruction_File_Name) {
		this.arduino_Instruction_File_Name = arduino_Instruction_File_Name;
	}

	
}
