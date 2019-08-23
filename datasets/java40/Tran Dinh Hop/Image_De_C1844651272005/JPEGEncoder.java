/**
 	*************************************************************************************
	* File: JPEGEncoder.java                Date: 21/06/2004		      Version: 1.01 		*
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

	* @Author: this source come from http://java.sun.com/
	* @Edited by Tran Dinh Hop, 6 May 2004
	* @Please visit http://www.freewebs.com/DigitZone for updated version
*/

package Algorithms.Convert;

import java.awt.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.io.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*;

public class JPEGEncoder{

     private ImageEncoder encoder = null;
     private JPEGEncodeParam encodeParam = null;
	private static PlanarImage srcImage, dstImage;
	String outFileName;
		
     // Create some Quantization tables.
         private static int[] qtable1 = {
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1,
             1,1,1,1,1,1,1,1
         };

         private static int[] qtable2 = {
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2,
             2,2,2,2,2,2,2,2
         };
/*
         private static int[] qtable3 = {
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3,
             3,3,3,3,3,3,3,3
         };

*/	
     public JPEGEncoder(String inFile) {
		// Lay ra ten file de ghep vao phan mo rong jpg
		File f = new File(inFile);
		int pos = f.getName().indexOf(".");
		String fileName =(f.getName().substring (0,pos));

		outFileName = "C:\\CoDecImage\\DesImages\\"+fileName+".jpg";
	     FileOutputStream fosImage = createOutputStream(outFileName);
	
	     // Create the source op image.
	     srcImage = loadImage(inFile);
	     double[] constants = new double[3];
	     constants[0] = 0.0;
	     constants[1] = 0.0;
	     constants[2] = 0.0;
	     ParameterBlock pb = new ParameterBlock();
		
	     pb.addSource(srcImage);
	     pb.add(constants);

	     // Create a new srcImage image with weird tile sizes
	     ImageLayout layout = new ImageLayout();
	     layout.setTileWidth(57);
	     layout.setTileHeight(57);
	     RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT,
	                                                       layout);
	     PlanarImage src1 = JAI.create("addconst", pb, hints);

	     // ----- End srcImage loading ------

	     // Set the encoding parameters if necessary.
	     encodeParam = new JPEGEncodeParam();
	     encodeParam.setQuality(0.5F);

	     encodeParam.setHorizontalSubsampling(0, 1);
	     encodeParam.setHorizontalSubsampling(1, 2);
	     encodeParam.setHorizontalSubsampling(2, 2);
	
	     encodeParam.setVerticalSubsampling(0, 1);
	     encodeParam.setVerticalSubsampling(1, 1);
	     encodeParam.setVerticalSubsampling(2, 1);
	
	     encodeParam.setRestartInterval(64);
	
	     encodeParam.setLumaQTable(qtable1);
	     encodeParam.setChromaQTable(qtable2);
	
	     encodeImage(srcImage, fosImage);
	     dstImage = loadImage(outFileName);
	}
		

     // Load the source image.
     private PlanarImage loadImage(String imageName) {
		     ParameterBlock pb = (new ParameterBlock()).add(imageName);
		     PlanarImage piSrc = JAI.create("fileload", pb);
	          if (piSrc == null) {
	             System.out.println("Error in loading image " + imageName);
	          }
	          return piSrc;
     }

     // Create the image encoder.
     private void encodeImage(PlanarImage img, FileOutputStream out){
   		  encoder = ImageCodec.createImageEncoder("JPEG", out, encodeParam);
             try {
                 encoder.encode(img);
                 out.flush();
                 out.close();
             } catch (IOException e) {
                 System.out.println("IOException at encoding " + e);
             }
     }

     private FileOutputStream createOutputStream(String outFile) {
             FileOutputStream out = null;
             try {
                 out = new FileOutputStream(outFile);
             } catch(IOException e) {
                 System.out.println("IOException "+e);
                 }

             return out;
         }

	public String getOutFileName(){
		return (outFileName);
	}
	
}


