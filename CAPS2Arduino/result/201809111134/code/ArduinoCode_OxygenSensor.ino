#include <SPI.h>
#include "pins_arduino.h"
// ==== Define Vars ====
const int sensorPin = 0;
volatile int sensorValue;
volatile int val1;
volatile int val2;
byte command = 0;
// ==== Setup() ====
		void setup (void)
		{
		  // have to send on master in, *slave out*
		  pinMode(MISO, OUTPUT);
		  pinMode(MOSI,INPUT);
		  pinMode(SCK,INPUT);
		  pinMode(SS,INPUT);
		  // turn on SPI in slave mode
		  SPCR |= _BV(SPE);
		  // turn on interrupts
		  SPCR |= _BV(SPIE);
		  // interrupt for SS falling edge
		  attachInterrupt (0, ss_falling, FALLING);
		}  // end of setup
// ==== Loop() ====
void loop (void)
{
  noInterrupts();
  sensorValue = analogRead(sensorPin);
  val1=highByte(sensorValue);//sensorValue << 8;
  val2=lowByte(sensorValue);//sensorValue >> 2;
  interrupts();
  // if SPI not active, clear current command
  if (digitalRead (SS) == HIGH)
	    command = 0;
if(sensorValue<0.19)
	delay (100000)
if(sensorValue>0.23)
	delay (1000)
}  // end of loop
// ==== SPI Interrupt Routine ====
ISR (SPI_STC_vect)
{
	byte c = SPDR;
	command=c;
	switch (command)
	{
		// no command? then this is the command
		case 0:
			command = c;
			SPDR = 0;
			break;
		// add to incoming byte, return result
		case 'f':// Send first byte
			SPDR = val1;
			break;
		// subtract from incoming byte, return result
		case 's':// Send second byte
			SPDR = val2;
			break;
	} // end of switch
}  // end of interrupt service routine (ISR) SPI_STC_vect

// ==== start of transaction ====
void ss_falling ()
{
  command = 0;
}
