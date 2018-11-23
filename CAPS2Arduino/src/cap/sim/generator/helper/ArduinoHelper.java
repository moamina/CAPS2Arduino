package cap.sim.generator.helper;

import java.util.Hashtable;
import java.util.Map;

public class ArduinoHelper {
	
	private String libs;
	private String script;
	
	
	public ArduinoHelper() {
		// TODO Auto-generated constructor stub
		this.setScript("");
	}
			
	public String readAnalog(String pinNum,String val) {

		return val+" = analogRead("+pinNum+");\n";
	}
	
	public String digitalWrite(String pinNum,DigitalStatus status)
	{
		return "digitalWrite("+pinNum+", "+status+");\n";
	}
	
	public String spi_transfer(String value)
	{
		return "SPI.transfer("+value+");\n";
	}
	
	public String delay(int value)
	{
		return "delay("+value+");\n";
	}
	public String defineVars(Hashtable<String,Integer> vars)
	{
		String varStr="";
		for (Map.Entry<String, Integer> var : vars.entrySet()) {
			varStr+="int "+ var.getKey()+ " = "+var.getValue()+";\n";
		}
		return varStr;
	}
	
	public String getRecievingArduinoCode(int ledPin,int value)
	{
//		String script="// ==== Adding Libs Parts ====\n";				
		script+="#include <SPI.h>\n";
		script+="#include \"pins_arduino.h\"\n";
		//adding libs
		//script+=libs;
		String ledPinVar="ledPin";
		script+="// ==== Define Vars ====\n";
		script+="volatile int action;\n";
		script+="const int  ledPin=  "+ledPin+";\n";

		script+="// ==== Setup() ====\n";
		script+="void setup (void)\n";
		script+="{\n";

		script+="// have to send on master in, *slave out*\n";
		script+=" pinMode(MISO, OUTPUT);\n";
		script+=" pinMode(ledPin, OUTPUT);\n";
 
		script+="// turn on SPI in slave mode\n";
		script+=" SPCR |= _BV(SPE);\n";
 
		script+="// turn on interrupts\n";
		script+=" SPCR |= _BV(SPIE);\n"; 
		script+=" action=0;\n";
		script+="}// end of setup\n";

		script+="// ==== Loop() ====\n"; 
		script+="void loop (void)\n";
		script+="{\n";
		script+="  if(action=="+value+")\n";
		script+="     "+digitalWrite(ledPinVar, DigitalStatus.LOW);
		script+="  else\n";
		script+="      "+digitalWrite(ledPinVar, DigitalStatus.HIGH);
		script+="}// end of loop\n";

		script+="// ==== SPI Interrupt Routine ====\n";
		script+="ISR (SPI_STC_vect)\n";
		script+="{\n";
		script+="  byte c = SPDR;\n";
		script+="  action=(int)c;\n";
		script+="}\n";

		return script;
	}
	
	public String getSenderArduinoCode(int sensorPin,String condition)
	{
//		String script="// ==== Adding Libs Parts ====\n";				
		script+="#include <SPI.h>\n";
		script+="#include \"pins_arduino.h\"\n";
		//adding libs
		//script+=libs;
		script+="// ==== Define Vars ====\n";
		script+="const int sensorPin = "+sensorPin+";\n";
		script+="volatile int sensorValue;\n";
		script+="volatile int val1;\n";
		script+="volatile int val2;\n";
		script+="byte command = 0;\n";
		script+="// ==== Setup() ====\n";
		script+="		void setup (void)\n";
		script+="		{\n";
		script+="		  // have to send on master in, *slave out*\n";
		script+="		  pinMode(MISO, OUTPUT);\n";
		script+="		  pinMode(MOSI,INPUT);\n";
		script+="		  pinMode(SCK,INPUT);\n";
		script+="		  pinMode(SS,INPUT);\n";
		script+="		  // turn on SPI in slave mode\n";
		script+="		  SPCR |= _BV(SPE);\n";
		script+="		  // turn on interrupts\n";
		script+="		  SPCR |= _BV(SPIE);\n";
		script+="		  // interrupt for SS falling edge\n";
		script+="		  attachInterrupt (0, ss_falling, FALLING);\n";
		script+="		  // now turn on interrupts\n";
		script+="		  SPI.attachInterrupt();\n";
		script+="		}  // end of setup\n";
		
		script+="// ==== Loop() ====\n"; 
		script+="void loop (void)\n";
		script+="{\n";
		script+="  noInterrupts();\n";
		script+="  sensorValue = analogRead(sensorPin);\n";
		script+="  val1=highByte(sensorValue);//sensorValue << 8;\n";
		script+="  val2=lowByte(sensorValue);//sensorValue >> 2;\n";
		script+="  interrupts();\n";

		script+="  // if SPI not active, clear current command\n";
		script+="  if (digitalRead (SS) == HIGH)\n";
		script+="	    command = 0;\n";

		  //condition part
		if(condition!=null && !condition.equals(""))
		{  
			script+=condition;  
		}
		
		script+="}  // end of loop\n";
		script+=addSPIInterruptRoutine();
		script+="\n";
		script+="// ==== start of transaction ====\n";
		script+="void ss_falling ()\n";
		script+="{\n";
		script+="  command = 0;\n";
		script+="}\n";
		
		return script;
	}
	
	public String addSPIInterruptRoutine()
	{
		String routine="// ==== SPI Interrupt Routine ====\n";
		routine+="ISR (SPI_STC_vect)\n";
		routine+="{\n";
		routine+="	byte c = SPDR;\n";
		routine+="	command=c;\n";
		routine+="	switch (command)\n";
		routine+="	{\n";
		routine+="		// no command? then this is the command\n";
		routine+="		case 0:\n";
		routine+="			command = c;\n";
		routine+="			SPDR = 0;\n";
		routine+="			break;\n";
		routine+="		// add to incoming byte, return result\n";
		routine+="		case 'f':// Send first byte\n";
		routine+="			SPDR = val1;\n";  
		routine+="			break;\n";
    
		routine+="		// subtract from incoming byte, return result\n";
		routine+="		case 's':// Send second byte\n";
		routine+="			SPDR = val2;\n";
		routine+="			break;\n";
		routine+="	} // end of switch\n";
		routine+="}  // end of interrupt service routine (ISR) SPI_STC_vect\n";

		return routine;
	}

	public String getLibs() {
		return libs;
	}

	public void setLibs(String libs) {
		this.libs = libs;
	}
	
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
}
