#include <SPI.h>
#include "pins_arduino.h"
volatile byte highVal;
volatile byte lowVal;
volatile byte val;
// ==== Setup() ====
void setup (void){
	SPI.begin();
}
// ==== Loop() ====
void loop (void){
  digitalWrite(1, LOW);
  highVal=SPI.transfer('f');
  highVal=highVal*256;
  lowVal=SPI.transfer('s');
  val=highVal|lowVal;
  digitalWrite(1, HIGH);

  if(val<1200){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(5, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(5, HIGH);
  }

  if(val>=1200){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(5, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(5, HIGH);
  }

  digitalWrite(2, LOW);
  highVal=SPI.transfer('f');
  highVal=highVal*256;
  lowVal=SPI.transfer('s');
  val=highVal|lowVal;
  digitalWrite(2, HIGH);

  if(val>35){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(6, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(6, HIGH);
  }

  if(val<35){
		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(6, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(6, HIGH);
  }

		highVal=highByte(val);
		lowVal=lowByte(val);
		digitalWrite(4, LOW);
		SPI.transfer(highVal);
		SPI.transfer(lowVal);
		digitalWrite(4, HIGH);
}