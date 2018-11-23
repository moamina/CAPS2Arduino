#include <SPI.h>
#include "pins_arduino.h"
// ==== Define Vars ====
volatile int action;
const int 6 =  6;
// ==== Setup() ====
void setup (void)
{
// have to send on master in, *slave out*
 pinMode(MISO, OUTPUT)
 pinMode(6, OUTPUT)
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
     digitalWrite(6, LOW);
  else
      digitalWrite(6, HIGH);
}// end of loop
// ==== SPI Interrupt Routine ====
ISR (SPI_STC_vect)
{
  byte c = SPDR;
  action=(int)c;
}
