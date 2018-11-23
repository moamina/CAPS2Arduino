#include <SPI.h>
#include "pins_arduino.h"

volatile int highVal;
volatile int lowVal;
volatile int val;
// ==== Setup() ====
void setup (void)
{}
void loop (void){
  
  digitalWrite(1, LOW);
  highVal=SPI.transfer('f');
  highVal=highVal*256;
  lowVal=SPI.transfer('s');
  val=highVal|lowVal;
  digitalWrite(1, HIGH);

  if(val>0.23){
    val1=highByte(sensorValue);
    val2=lowByte(sensorValue);
    digitalWrite(5, LOW);
    SPI.transfer(val1);
    SPI.transfer(val2);
    digitalWrite(5, HIGH);
  }
  
  if(val<0.19){
    val1=highByte(sensorValue);
    val2=lowByte(sensorValue);
    digitalWrite(5, LOW);
    SPI.transfer(val1);
    SPI.transfer(val2);
    digitalWrite(5, HIGH);
  }
  
  digitalWrite(2, LOW);
  highVal=SPI.transfer('f');
  highVal=highVal*256;
  lowVal=SPI.transfer('s');
  val=highVal|lowVal;
  digitalWrite(2, HIGH);


if(val>35)
val1=highByte(sensorValue);
val2=lowByte(sensorValue);
digitalWrite(6, LOW);
SPI.transfer(val1);
SPI.transfer(val2);
digitalWrite(6, HIGH);
end
if(val<35)
val1=highByte(sensorValue);
val2=lowByte(sensorValue);
digitalWrite(6, LOW);
SPI.transfer(val1);
SPI.transfer(val2);
digitalWrite(6, HIGH);
end
val1=highByte(sensorValue);
val2=lowByte(sensorValue);
digitalWrite(4, LOW);
SPI.transfer(val1);
SPI.transfer(val2);
digitalWrite(4, HIGH);
}
