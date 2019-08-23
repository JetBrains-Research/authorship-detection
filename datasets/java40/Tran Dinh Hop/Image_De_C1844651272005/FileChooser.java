package Utils;

/**
 	*************************************************************************************
	* File: FileChooser.java                Date: 21/06/2004		      Version: 1.01 		*
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
	* @Author Tran Dinh Hop, 6 May 2004
	* @Please visit http://www.freewebs.com/DigitZone for updated version
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.io.File;
	
public class FileChooser extends JComponent {
	protected int returnVal=0;
	protected String fileName="";
  	protected JFileChooser fc;
	

	// _action = OPEN or SAVE; _fileType = IMAGE, ZIP, HUFFMAN, RLE, LZW
	public FileChooser (String _fileType, String _action){	
		
		if (fc == null){ 
	     	fc = new JFileChooser();
	          fc.setAcceptAllFileFilterUsed(false);
	     }
		
		if (_fileType == "image" && _action == "Open"){ // Source image to Display or Compress
            	fc.addChoosableFileFilter(new ImageFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/SamImages"));
	        	returnVal = fc.showDialog(FileChooser.this, "Open");
			
		} else if (_fileType == "image" && _action == "Save"){ // Target image to Compress
            	fc.addChoosableFileFilter(new ImageFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/DesImages"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
			
		} else if (_fileType == "zip" && _action == "Save"){ // Target file to Compress with Zip
            	fc.addChoosableFileFilter(new ZipFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
			
		} else if (_fileType == "zip" && _action == "Open"){ // Source file to Compress with Zip
            	fc.addChoosableFileFilter(new ZipFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Open");
			
		} else if (_fileType == "huffman" && _action == "Save"){ // Target file to Compress with Zip
            	fc.addChoosableFileFilter(new HuffmanFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
			
		} else if (_fileType == "huffman" && _action == "Open"){ // Source file to Compress with Zip
            	fc.addChoosableFileFilter(new HuffmanFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Open");
			
		} else if (_fileType == "rle" && _action == "Save"){ // Target file to Compress with Zip
            	fc.addChoosableFileFilter(new RLEFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
			
		} else if (_fileType == "rle" && _action == "Open"){ // Source file to Compress with Zip
            	fc.addChoosableFileFilter(new RLEFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Open");
			
		} else if (_fileType == "lzw" && _action == "Save"){ // Target file to Compress with lzw
            	fc.addChoosableFileFilter(new LZWFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
			
		} else if (_fileType == "lzw" && _action == "Open"){ // Source file to DeCompress with lzw
            	fc.addChoosableFileFilter(new LZWFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/Compressed"));
	        	returnVal = fc.showDialog(FileChooser.this, "Open");
			
		} else if (_fileType == "zipdecompress" && _action == "Save"){ // Target file for Decompress
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/DesImages"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
		} else if (_fileType == "otherdecompress" && _action == "Save"){ // Target file for Decompress
            	fc.addChoosableFileFilter(new ImageFilter());
			fc.setCurrentDirectory(new File(System.getProperty("user.dir"),"/DesImages"));
	        	returnVal = fc.showDialog(FileChooser.this, "Save");
		}
		
	   //Process the results.
	     if (returnVal == JFileChooser.APPROVE_OPTION) {
	     	fileName = fc.getSelectedFile().getAbsolutePath ();
			System.out.println(fileName);
			
		} else {
	     	fc=null;
	   	}
		
		//return (fileName);
	}
	
		public String getFileName(){
		return fileName;
	}

}	

