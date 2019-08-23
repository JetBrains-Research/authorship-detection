/////////////////////////////////////////////////////////////////////
//
// This software is provided "AS IS".  The JavaPOS working group (including
// each of the Corporate members, contributors and individuals)  MAKES NO
// REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE,
// EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NON-INFRINGEMENT. The JavaPOS working group shall not be liable for
// any damages suffered as a result of using, modifying or distributing this
// software or its derivatives.Permission to use, copy, modify, and distribute
// the software and its documentation for any purpose is hereby granted.
//
// ToneIndicatorService113
//
//   Interface definining all new capabilities, properties and
//   methods that are specific to Tone Indicator for release 1.13.
//
// Modification history
// ------------------------------------------------------------------
// 2009-Feb-23 JavaPOS Release 1.13                                BS
//
/////////////////////////////////////////////////////////////////////

package jpos.services;

import jpos.*;

public interface ToneIndicatorService113 extends ToneIndicatorService112
{
  // Capabilities
  public int getCapMelody() throws JposException;

  // Properties
  public int  getMelodyType() throws JposException;
  public void setMelodyType(int melodyType) throws JposException;
  public int  getMelodyVolume() throws JposException;
  public void setMelodyVolume(int melodyVolume) throws JposException;
}