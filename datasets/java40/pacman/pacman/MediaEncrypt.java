/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: MediaEncrypt.java
 * @Prject: libMedia
 * @Package: com.yeshj.pacman
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月5日 下午10:40:13
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.utils.FileHelper;

/**
 * @ClassName: MediaEncrypt
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月5日 下午10:40:13
 */
public final class MediaEncrypt {

	private final static ILog logger = LogFactory.getLog(MediaEncrypt.class);
	private static boolean gJNILoaded = false;
	
	static {
		
		try {
			if (FileHelper.isWindow())
				System.loadLibrary("libdec");
			else
				System.loadLibrary("dec");
			gJNILoaded = true;
		} catch (UnsatisfiedLinkError e) {
			
			logger.error("Can not load dynamic library![libdec]", e);
		}
	}
	
	private native int encode(String srcFile, String destFile);
	private native int decode(String srcFile, String destFile);
	
	/**
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public boolean encodeMedia(String src, String dest) {

		if (gJNILoaded) {
			return encode(src, dest) == 0;
		}
		return false;
	}
	
	/**
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public boolean decodeMedia(String src, String dest) {
		System.out.println(System.getProperty("java.library.path"));
		if (gJNILoaded) {
			
			return decode(src, dest) == 0;
		}
		return false;
	}
}
