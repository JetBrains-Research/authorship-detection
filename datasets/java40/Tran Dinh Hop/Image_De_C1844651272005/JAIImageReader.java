package Viewer;

/**
 *************************************************************************************
 * File: JAIImageReader.java         Edited Date: 18/06/2004		  Version: 1.01	 *
 *-----------------------------------------------------------------------------------*
 * @(#)JAIImageReader.java	15.2 03/05/20
 *
 * Copyright (c) 2003 Sun Microsystems, Inc.
 * All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduct the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO
 * EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT
 * OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN
 * IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed,licensed or intended for
 * use in the design, construction, operation or maintenance of any nuclear
 * facility.
 */


import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.SeekableStream;

public class JAIImageReader {
	
	public static PlanarImage readImage(String filename) {
		PlanarImage image = null;
		
		// Use the JAI API unless JAI_IMAGE_READER_USE_CODECS is set
		if (System.getProperty("JAI_IMAGE_READER_USE_CODECS") == null) {
			image = JAI.create("fileload", filename);
		} else {
			try {
				// Use the ImageCodec APIs
				SeekableStream stream = new FileSeekableStream(filename);
				String[] names = ImageCodec.getDecoderNames(stream);
				ImageDecoder dec =
					ImageCodec.createImageDecoder(names[0], stream, null);
				RenderedImage im = dec.decodeAsRenderedImage();
				image = PlanarImage.wrapRenderedImage(im);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		// If the source image is colormapped, convert it to 3-band RGB.
		if(image.getColorModel() instanceof IndexColorModel) {
			// Retrieve the IndexColorModel
			IndexColorModel icm = (IndexColorModel)image.getColorModel();
			
			// Cache the number of elements in each band of the colormap.
			int mapSize = icm.getMapSize();
			
			// Allocate an array for the lookup table data.
			byte[][] lutData = new byte[3][mapSize];
			
			// Load the lookup table data from the IndexColorModel.
			icm.getReds(lutData[0]);
			icm.getGreens(lutData[1]);
			icm.getBlues(lutData[2]);
			
			// Create the lookup table object.
			LookupTableJAI lut = new LookupTableJAI(lutData);
			
			// Replace the original image with the 3-band RGB image.
			image = JAI.create("lookup", image, lut);
		}
		
		return image;
	}
}
