/******************************************************************  
 * Copyright ? 2014 hujiang.com. All rights reserved.
 *
 * @Title: MediaInfo.java
 * @Prject: libMediaInfo
 * @Package: com.yeshj.pacman
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014-12-22 12:16:57
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * @ClassName: MediaInfo
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014-12-22 12:16:57
 */
public final class MediaInfo {

	//private final static ILog logger = LogFactory.getLog(MediaInfo.class);
	private static boolean gJNILoaded = false; 
	static {
		//logger.info("==========>" + System.getProperty("java.library.path"));
		try {
			if ("\\".equals(System.getProperties().getProperty("file.separator"))) {
				
				System.loadLibrary("libMediaInfo");
			} else {
				
				System.loadLibrary("zen");
				System.loadLibrary("media");
				System.loadLibrary("MediaInfo");
			}
			gJNILoaded = true;
		} catch (UnsatisfiedLinkError e) {
			
			gJNILoaded = false;
e.printStackTrace();
		}
	}
	
	//Video codec
	private String videoFormat;
	
	//Audio codec
	private String audioFormat;
	
	/**
	 * Analyze the specified media.
	 * 
	 * @param mediaFile
	 * @return success or not
	 */
	public boolean analyzeMedia(String mediaFile) {
		
		if (gJNILoaded) {
			return nativeAnalyzeMedia(mediaFile);
		}
		return false;
	}

	/**
	 * Gets the media type.
	 * 
	 * @return
	 */
	public String getMediaTypeKey() {
		
		if (gJNILoaded)
			return nativeGetMediaType();
		
		return "UNKNOWN";
	}

	/**
	 * @return the videoFormat
	 */
	public String getVideoFormat() {
		
		if (gJNILoaded) {
			if (null == videoFormat || videoFormat.trim().length() < 1) {
				videoFormat = nativeGetVideoCodec();
			}
			return videoFormat;
		} else {
			return "UNKNOWN";
		}
	}

	/**
	 * @return the audioFormat
	 */
	public String getAudioFormat() {
		
		if (gJNILoaded) {
			if (null == audioFormat || audioFormat.trim().length() < 1) {
				audioFormat = nativeGetAudioCodec();
			}
			return audioFormat;
		} else {
			
			return "UNKNOWN";
		}
	}

	/**
	 * @return the width
	 */
	public int getWidth() {

		if (gJNILoaded) {
			return nativeGetWidth();
		} else {
			return -1;
		}
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		
		if (gJNILoaded)
			return nativeGetHeight();
		else
			return -1;
	}

	/**
	 * @return the audioBitrate
	 */
	public int getAudioBitrate() {
		
		if (gJNILoaded)
			return nativeGetAudioBitrate();
		else
			return -1;
	}

	/**
	 * @return the videoBitrate
	 */
	public int getVideoBitrate() {
		
		if (gJNILoaded)
			return nativeGetVideoBitrate();
		else
			return -1;
	}

	/**
	 * @return the audioSamplingRate
	 */
	public int getAudioSamplingRate() {
		
		if (gJNILoaded)
			return nativeGetSamplingRate();
		else
			return -1;
	}

	/**
	 * @return the audioDuration
	 */
	public long getAudioDuration() {
		
		if (gJNILoaded)
			return nativeGetAudioDuration();
		else
			return -1;
	}

	/**
	 * @return the videoDuration
	 */
	public long getVideoDuration() {
		
		if (gJNILoaded)
			return nativeGetVideoDuration();
		else
			return -1;
	}

	/**
	 * @return the isAudioOnly
	 */
	public boolean isAudioOnly() {
		
		String vformat = getVideoFormat();
		return null == vformat || "UNKNOWN".equalsIgnoreCase(vformat) || vformat.length() < 1;
	}
	
	/**
	 * Release all informations of the previous analyzing.
	 */
	public void release() {
		
		if (gJNILoaded)
			nativeRelease();
	}
	
	//////////////////////JNI METHODS///////////////////////////////////

	private native boolean nativeAnalyzeMedia(String mediaFile);

	private native String nativeGetMediaType();
	
	private native String nativeGetAudioCodec();
	
	private native String nativeGetVideoCodec();
	
	private native int nativeGetWidth();
	
	private native int nativeGetHeight();
	
	private native int nativeGetAudioBitrate();
	
	private native int nativeGetVideoBitrate();
	
	private native int nativeGetSamplingRate();
	
	private native long nativeGetAudioDuration();
	
	private native long nativeGetVideoDuration();

	private native void nativeRelease();
}
