package Viewer;

/**
 	*************************************************************************************
	* File: OpenImage.java                Date: 21/06/2004		  		 Version: 1.01		*
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;

import java.lang.String;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.filechooser.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.image.renderable.ParameterBlock;
import java.util.Vector;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import com.sun.media.jai.codec.FileSeekableStream;

//import Utils.FileChooser;

public class OpenImage extends JPanel{

	private static PlanarImage image = null;
	private static JLabel picture = null;
	private String ImFileName;
  	//private JFileChooser fc;
	int width, height;
	long fileSize=0;
	
	public OpenImage(String file){
		
		this.setLayout(new BorderLayout());
		ImFileName = file;
	/*	
		FileChooser fc = new FileChooser("image", "Open");
		file = fc.getFileName();
	*/	
		File f = new File(file);
		if (f.isFile()){		
			// Read a image from the given file name
			image = JAIImageReader.readImage(file);
			width = image.getWidth();
			height = image.getHeight();
			
			// add the image to a Picture panel 
			picture = new JLabel();
			picture.setHorizontalAlignment(SwingConstants.CENTER);
			Icon icon = new IconJAI(image);
			picture.setIcon(icon);
			
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			picture.setPreferredSize(new Dimension(w, h));
			JScrollPane pictureScrollPane = new JScrollPane(picture);
			
			
			Date date = new Date(f.lastModified());
			DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yy");
			DateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
			
			fileSize = f.length()/1024;
	
			//JTextArea fileInf = new JTextArea ("Image: "+file+ "\n"+
			JTextArea fileInf = new JTextArea (
						"  Size: "+fileSize+"KB - "+width+"x"+height+" - "+
						dateFormat1.format(date)+" "+dateFormat2.format(date));
			//fileInf.setBackground(Color.white);
			//fileInf.setForeground(Color.blue);
			fileInf.setFont (new Font("Arial", Font.PLAIN, 11));
	
			this.add(new JScrollPane(picture), BorderLayout.CENTER);
			this.add(fileInf, BorderLayout.SOUTH);
		}	
	}	
	
	public String getImageName(){
		return ImFileName;
	}
	
	public long getImageSize(){
		return fileSize;
	}
	
//****** dung de thu ************
	public static void main(String[] args){
	
		JFrame frame=new JFrame();
		OpenImage objTestDesk = new OpenImage(args[0]);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setTitle(getImageName());
		frame.getContentPane().add(objTestDesk);
		frame.pack();
		frame.setSize(250,350);
		frame.setVisible(true);
	}


}


