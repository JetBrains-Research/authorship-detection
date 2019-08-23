package Algorithms.LZW;

/**
 	*************************************************************************************
	* File: HashNode.java                Date: 21/06/2004		      Version: 1.01 		*
	*-----------------------------------------------------------------------------------*
	* This program is released under the GNU General Public License 2.00. 	              
	* Details of GNU GPL at http://www.opensource.org/licenses/gpl-license.php  		    
	* You must agree to this license before using, copying or modifying this code.								    
	*
 	* Redistribution and use in source and binary forms, with or without
 	* modification, are permitted provided that the following conditions
 	* are met:
 	*
 	* 1. Redistributions of source code must retain the above copyright
 	*    notice, this list of conditions and the following disclaimer.
 	* 2. The source can be used and modified by individual/organizations, 
	*	  ONLY IF the source will not be used 
 	*    for commercial purposes or incorporated into commercial applications.
	*
 	* --------------------------------- WARRANTY --------------------------------- 
	* THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED 
	* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
	* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
	* IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
	* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
	* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
	* OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
	* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
	* OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
	* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	*
 	* --------------------------------- CONTACT --------------------------------- 
	* This project was written in a burning hurry, so it is not a model of efficient
	* nor even good code.
	* Please send comments, bug reports, improvements to: hoptrandinh@yahoo.com  
	*
	* Author:  Robby at http://www.cise.ufl.edu/~sahni/cop3530
	* his/her email is robbyp@grove.ufl.edu
	*
	* @Edited by Tran Dinh Hop, 6 May 2004
	* @Please visit http://www.freewebs.com/DigitZone for updated version
*/

import java.io.*; 
import java.util.*; 

class HashNode 
{ 
int code; 
byte[] entry; 
HashNode next; 

public HashNode(int c, byte[] e) 
{ 
	code = c; 
	entry = e; 
	next = null; 
} 

public byte[] getEntry() 
{ 
	return entry; 
} 

public int getCode() 
{ 
	return code; 
} 

public boolean hasCode(int c) 
{ 
	return(c == code); 
} 

public boolean hasEntry(byte[] b) 
{ 
	return Arrays.equals(b, entry);	
} 

public HashNode getNext() 
{ 
	return next; 
} 

public void setNext(HashNode hs) 
{ 
	next = hs; 
} 

public String toString() 
{ 
StringBuffer sb = new StringBuffer(); 
sb.append("("+code+")"); 
for(int i=0; i<entry.length; i++) 
{ 
int temp = entry[i]; 

if(temp < 0) 
temp += 256; 

sb.append("["+temp+"]"); 
} 

return sb.toString(); 
} 
} 

