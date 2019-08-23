/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: FFMpeg.java
 * @Prject: libMedia
 * @Package: com.yeshj.pacman
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年2月2日 下午5:25:20
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman;

import com.yeshj.pacman.utils.FileHelper;

/**
 * TODO
 * @Class: FFMpeg
 * @author: zhangxinyu
 * @date: 2015年2月2日 下午5:25:20
 */
public final class FFMpeg {

	private static boolean gJNILoaded = false;
	
	static {
		
		try {
			if (FileHelper.isWindow())
				System.loadLibrary("libffmpeg");
			else
				System.loadLibrary("ffmpeg");
			gJNILoaded = true;
		} catch (UnsatisfiedLinkError e) {
			
			e.printStackTrace();
		}
	}
	
	private native int nativeTransRegularAudio(String srcFile, String destFile);
	private native int nativeTransSpecAudio(String srcFile, String destFile);
	private native int nativeTransRegularVideo(String srcFile, String destFile);
	private native int nativeTransSpecVideo(String srcFile, String destFile);
	
	public int transRegularAudio(String srcFile, String destFile) {
		
		if (gJNILoaded) {
			
			return nativeTransRegularAudio(srcFile, destFile);
		}
		return -1;
	}
	
	public int transSpecAudio(String srcFile, String destFile) {
		
		if (gJNILoaded) {
			
			return nativeTransSpecAudio(srcFile, destFile);
		}
		return -1;
	}
	
	public int transRegularVideo(String srcFile, String destFile) {
		
		if (gJNILoaded) {
			
			return nativeTransRegularVideo(srcFile, destFile);
		}
		return -1;
	}
	
	public int transSpecVideo(String srcFile, String destFile) {
		
		if (gJNILoaded) {
			
			return nativeTransSpecVideo(srcFile, destFile);
		}
		return -1;
	}
}
