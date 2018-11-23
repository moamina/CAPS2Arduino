#include <SPI.h>
#include "pins_arduino.h"
// ==== Define Vars ====
volatile int action;
const int  ledPin=  6;
// ==== Setup() ====
void setup (void)
{
// have to send on master in, *slave out*
 pinMode(MISO, OUTPUT);
 pinMode(ledPin, OUTPUT);
// turn on SPI in slave mode
 SPCR |= _BV(SPE);
// turn on interrupts
 SPCR |= _BV(SPIE);
 action=0;
}// end of setup
// ==== Loop() ====
void loop (void)
{
  if(action==0)
     digitalWrite(ledPin, LOW);
  else
      digitalWrite(ledPin, HIGH);
}// end of loop
// ==== SPI Interrupt Routine ====
ISR (SPI_STC_vect)
{
  byte c = SPDR;
  action=(int)c;
}
