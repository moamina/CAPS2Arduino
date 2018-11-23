#include <SPI.h>
#include "pins_arduino.h"
volatile byte ss1=1
volatile byte ss2=2
volatile byte highVal;
volatile byte lowVal;
volatile byte val;
// ==== Setup() ====
void setup (void){
	digitalWrite(ss1, HIGH);
	digitalWrite(ss2, HIGH);
	SPI.begin();
}
// ==== Loop() ====
void loop (void){
  digitalWrite(ss1, LOW);
  highVal=SPI.transfer('f');
  highVal=highVal*256;
  lowVal=SPI.transfer('s');
  val=highVal|lowVal;
  digitalWrite(ss1, HIGH);
  if(val>0.23){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(ss5, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(ss5, HIGH);
  }
  if(val<0.19){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(ss5, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(ss5, HIGH);
  }
  digitalWrite(ss2, LOW);
  highVal=SPI.transfer('f');
  highVal=highVal*256;
  lowVal=SPI.transfer('s');
  val=highVal|lowVal;
  digitalWrite(ss2, HIGH);
  if(val>35){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(ss6, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(ss6, HIGH);
  }
  if(val<35){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(ss6, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(ss6, HIGH);
  }
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(ss4, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(ss4, HIGH);
}