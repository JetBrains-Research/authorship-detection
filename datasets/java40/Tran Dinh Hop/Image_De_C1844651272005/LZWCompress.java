package Algorithms.LZW;

/**
 	*************************************************************************************
	* File: LZWCompress.java                Date: 21/06/2004		      Version: 1.01 		*
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
import java.awt.*; 
import javax.swing.*;

public class LZWCompress extends Thread { 

	private final int codeMax; 
	private JButton jbt;
	//private JScrollPane jsp;
	//private JTextArea log;
	private File inFile;
	private File outFile;
	private FileInputStream in; 
	private FileOutputStream out; 
	private int currentByte; 
	private int codeCnt;
	private int outCode;
	private int fileSize;
	private int status;
	private byte[] cPattern; // current pattern 
	private byte[] lPattern; // last pattern 
	private Dictionary dict; 
	
	public LZWCompress(File in, File out, JButton btPercent) throws IOException{
		codeMax = 65335;
		jbt = btPercent;
		//jsp = pane;
		//log = jta;
		inFile = in;
		outFile = out;
		codeCnt = 256;
		status = -1;
		
		dict = new Dictionary();
		this.in = new FileInputStream(in);
		this.out = new FileOutputStream(out);
		fileSize = this.in.available();
			
	}

	public void compress() throws IOException { 

		// first byte processed 
		// must be primitive code 
		currentByte = in.read();
		if(in.available() == 0){ 
			System.err.print(inFile.getName()+" is empty"); 
			System.exit(1); 
		} 

		lPattern = null;
	
		// processes all other bytes
		while(in.available() > 0) 
		{ 
			worker();
		}


		/* available() returns 0 when the last byte is in the stream  *
		 * effectively, the algorithm must be run one more time after *
		 * in.available() is 0													  */ 
		
		worker();
		
		// output last pattern if != null
		if(lPattern != null)
		{
			outCode = dict.fetchCode(lPattern);
			out.write(getHiByte(outCode));
			out.write(getLoByte(outCode));
		}
		
		// output last byte
		byte[] temp = {(byte) currentByte};
		outCode = dict.fetchCode(temp);
		out.write(getHiByte(outCode));
		out.write(getLoByte(outCode));
		
		//jsp.setViewportView(new JButton(status+"100%"));	

		// streams are closed
		in.close(); 
		out.close(); 

		//Change panel back to original text box		
		//jsp.setViewportView(log);	
	} 


	// calculates the first byte of a 16-bit word
	private static int getHiByte(int num) { 
		num = num & 65280; 
		return( num >>> 8 ); 
	} 

	// calculates the second byte of a 16-bit word
	private static int getLoByte(int num){ 
		return( num & 255 ); 
	} 
	
	private void worker() throws IOException{
			//current pattern is set to last pattern + new byte
			if(lPattern == null){
				lPattern = new byte[1];
				lPattern[0] = (byte) currentByte;

				currentByte = in.read();
			}else{
				// get current pattern by augmenting previous pattern
				cPattern = new byte[lPattern.length+1];
				System.arraycopy(lPattern, 0, cPattern, 0, lPattern.length);
				cPattern[lPattern.length] = (byte) currentByte;
								
				if(dict.hasEntry(cPattern)){
					// current pattern is in dictionary
					lPattern = cPattern;
					currentByte = in.read();
				}else{
					// output previous pattern
					outCode = dict.fetchCode(lPattern);
					out.write(getHiByte(outCode));
					out.write(getLoByte(outCode));
					
					// add current pattern to dictionary as long as it is not full
					if(codeCnt < codeMax){
						dict.cInsert(cPattern);
						codeCnt++;
					}
					// re-initialize control
					lPattern = null;

				}
			}
		
			// calculate progress
			double dbl_status = (((double) fileSize - (double)in.available())/(double)fileSize) * 100;
			
			int new_status = (int) dbl_status;
					
			// output % done
			if(new_status != status){
				status = new_status;
				jbt.setForeground(Color.red);
				jbt.setFont (new Font("Arial", Font.BOLD, 15));
				jbt.setLabel(status+"%");			
				//jsp.setViewportView(new JButton(status+"%"));			
			}			
	}

	public void run(){
		try{
			compress();
		}catch(Exception e){
			//jsp.setViewportView(new JButton("Compression failed:\n"+e));
			e.printStackTrace();
		}
	}

}//~ End Class LZWCompress 

