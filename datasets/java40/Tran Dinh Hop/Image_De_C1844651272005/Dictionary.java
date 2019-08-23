package Algorithms.LZW;

/**
 	*************************************************************************************
	* File: Dictionary.java                Date: 21/06/2004		      Version: 1.01 		*
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


import java.util.*; 
import java.io.*; 

class Dictionary 
{ 
	//datamembers
	private final int hashSize = 65535;
	private int nextCode = 256;
	private HashNode[] data;

	public Dictionary() 
	{
		//create chained array 
		data = new HashNode[hashSize]; 

		//fill atomic entries for hashtable: codes[0-255]
		for(int i=0; i<=255 ;i++) 
		{ 
			byte[] temp = {(byte) i}; 
			HashNode tempNode = new HashNode(i, temp); 
			data[i] = tempNode; 
		} 
	} 

	/* Compression Dictionary Insert */
	public void cInsert(byte[] entry) 
	{ 
		//get hashCode for entry 
		int hashCode = getHashCode(entry); 

		//create hashCode with given entry
		HashNode newNode = new HashNode(nextCode, entry); 

		//find a spot in the hashChain for it
		if(null == data[hashCode]) 
		{ 
			data[hashCode] = newNode; 
		} 
		else 
		{ 
			HashNode current = data[hashCode]; 
			while(current.next != null) 
			{ 
				current = current.getNext(); 
			} 

			// Create Reference to new hashNode
			current.setNext(newNode); 
		} 
		
		nextCode++;
	}	 

	/* Decomopression Dictionary Insert */
	public void dInsert(int code, byte[] entry) 
	{ 
		// Calculate hashCode from given code
		int hashCode = code % hashSize; 

		// Create new hashNode	
		HashNode newNode = new HashNode(code, entry); 
		
		// Find spot for it
		if(null == data[hashCode]) 
		{ 
			data[hashCode] = newNode; 
		} 
		else 
		{ 
			HashNode current = data[hashCode]; 
			while(current.next != null) 
			{ 
				current = current.getNext(); 
			} 

			// Create a new reference for it
			current.setNext(newNode); 
		} 
	}	 

	// Determines if hasNode exists for given code
	public boolean hasCode(int code) 
	{ 
		// calculate hashCode
		int hashCode = code % hashSize; 

		// If the array has no chain
		// there is no match
		if(data[hashCode] == null) 
		{ 
			return false; 
		} 
		else 
		{
			// chain exists
			// chain is searched for given code 
			HashNode current = data[hashCode]; 
			boolean found = current.hasCode(code); 

			while(current.getNext() != null && found == false) 
			{ 
				current = current.getNext(); 
				found = current.hasCode(code); 
			} 
			return found; 
		}	 
	} 

	public boolean hasEntry(byte[] ba) 
	{ 

		//get hashCode for ba
		int hashCode = getHashCode(ba); 

		if(data[hashCode] == null) 
		{ 
			return false; 
		} 
		else 
		{ 
			HashNode current = data[hashCode]; 
			boolean found = current.hasEntry(ba); 

			while(current.getNext() != null && found == false) 
			{ 
				current = current.getNext(); 
				found = current.hasEntry(ba); 
			} 
			return found; 
		} 

	} 

	public int fetchCode(byte[] ba) 
	{ 
		if(!hasEntry(ba)) 
		System.err.print("Dictionary could not fetch code"); 

		//get hashCode for ba 
		int hashCode = getHashCode(ba); 

		//System.out.println("hashCode = "+hashCode); 
		HashNode current = data[hashCode]; 
		//System.out.println("Initial Hash Node: "+current); 
		while(!current.hasEntry(ba)) 
		{ 
			current = current.getNext(); 
			//System.out.println("Additional Hash Node: "+current); 
		} 
		return current.getCode(); 
	} 

	public byte[] fetchEntry(int code) 
	{ 
		int hashCode = code % hashSize; 

		if(!hasCode(code)) 
			System.err.print("Tried to Fetch entry that is not in Dictionary"); 

		HashNode current = data[hashCode]; 

		while(!current.hasCode(code)) 
		{ 
			current = current.getNext(); 
		} 
		return current.getEntry(); 
	} 

	private int getHashCode(byte[] ba) 
	{ 
		//get hashCode for ba 
		int out = ba[0];
		
		// make unsigned
		if( out < 0 )
			out += 256;
		
		return out;
	} 

	public String toString() 
	{ 
		StringBuffer sb = new StringBuffer(); 

		for(int i=0; (i < data.length); i++) 
		{ 
			if(data[i] != null) 
			{ 
				sb.append("("+i+") "+data[i]+"\n"); 
			} 
		} 
		return sb.toString(); 
	} 
 
}//~ End class Dictionary 

