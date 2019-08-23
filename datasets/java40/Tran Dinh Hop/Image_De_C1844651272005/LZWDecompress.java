package Algorithms.LZW;

/**
 	*************************************************************************************
	* File: LZWDecompress.java                Date: 21/06/2004		      Version: 1.01 		*
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
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class LZWDecompress extends Thread
{
	private JButton jbt;
	//private JScrollPane jsp;
	//private JTextArea log;
	private FileInputStream in;  
	private FileOutputStream out; 
	private int fileSize;
	private int status;
	private int code; 
	private int nextCode;
	private byte[] previous; 
	private byte[] outBytes; 
	private Dictionary dict;
	
	public LZWDecompress(File in, File out, JButton btPercent) throws IOException{
		jbt = btPercent;
		//jsp = pane;
		//log = jta;
		this.in = new FileInputStream(in);
		this.out = new FileOutputStream(out);
		fileSize = this.in.available();
		status = -1;
		code = 0;
		nextCode = 256;
	}
 
	public void decompress() throws Exception { 

		//create dictionary 
		dict = new Dictionary(); 

		//process first byte which is atomic by definition
		code = getCode(in); 
		outBytes = dict.fetchEntry(code); 
		previous = outBytes; 

		// output corresponding data
		out.write(outBytes[0]);

		// fetch next code
		code = getCode(in);

		// precess all remaining bytes
		while(in.available() > 0) { 
			worker();
		} 
		
		worker();

		// close streams
		in.close(); 
		out.close(); 
		
		//jsp.setViewportView(log);
	} 

	private void worker() throws IOException{
			if(dict.hasCode(code)) { 
				// Case1: the given code is found

				outBytes = dict.fetchEntry(code); 

				//add (previous Entry U first byte of outBytes) to Dictionary
				byte[] newEntry = new byte[previous.length+1];
				System.arraycopy(previous, 0, newEntry, 0, previous.length);
				newEntry[previous.length] = outBytes[0];

				dict.dInsert(nextCode, newEntry); 

				// Dictionary has the code, output its Entry 
				for(int i=0; i<outBytes.length; i++) 
					out.write(outBytes[i]); 

				previous = outBytes; 

				code = getCode(in);
			} else { 
				// Case2: given code is not in dictionary

				// output previous code's ([Entry] U [previous first byte]) 
				byte[] outBytes = new byte[previous.length+1];
				System.arraycopy(previous, 0, outBytes, 0, previous.length);
				outBytes[previous.length] = previous[0];



				for(int i=0; i<outBytes.length; i++) 
					out.write(outBytes[i]); 

				// insert new Entry for dictionary 
				dict.dInsert(code, outBytes);

				// save previously inputed data
				previous = outBytes; 

				code = getCode(in);
			} 

			nextCode++; 
			
			// calculate progress
			double dbl_status = (((double) fileSize - (double)in.available())/(double)fileSize) * 100;
			
			int new_status = (int) dbl_status;
					
			// output % done
			if(new_status != status)
			{
				status = new_status;
				jbt.setForeground(Color.red);
				jbt.setFont (new Font("Arial", Font.BOLD, 15));
				jbt.setLabel(status+"%");			

				//jsp.setViewportView(new JButton(status+"%"));			
			}			
	}

	// calculates the int value of the next 16-bit word
	private static int getCode(FileInputStream in) throws IOException 
	{ 
		int hi = in.read(); 

		if(hi < 0)
			hi += 256;

		int lo = in.read(); 
		
		if(lo < 0)
			lo += 256;

		hi = hi << 8; 
	
		return hi + lo; 
	} 

	// returns string representation of a byte array
	// used for debugging
	public String bstr(byte[] ba)	{
		StringBuffer sb = new StringBuffer();
	
		for(int i=0; i<ba.length; i++){
			 sb.append("["+ba[i]+"]");
		}
		
		return sb.toString();
	}

	public void run()
	{
		try
		{
			decompress();
		}
		catch(Exception e)
		{
			//jsp.setViewportView(new JButton("Decompression failed:\n"+e));
		}
	}

}//~ End Class Algorithm 

