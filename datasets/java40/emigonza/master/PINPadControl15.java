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
// PINPadControl15
//
//   Interface definining all new capabilities, properties and
//   methods that are specific to PIN Pad for release 1.5.
//
// Modification history
// ------------------------------------------------------------------
// 00-04-17 JavaPOS Release 1.5                                   BS
//
/////////////////////////////////////////////////////////////////////

package jpos;

import jpos.events.*;

public interface PINPadControl15 extends PINPadControl14
{
    // Properties
    public byte[]  getTrack4Data() throws JposException;
    public void    setTrack4Data(byte[] track4Data) throws JposException;
}