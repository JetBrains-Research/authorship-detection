/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: FFmpegHelper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 上午11:02:06
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.utils;

import org.apache.commons.lang3.StringUtils;

import com.yeshj.pacman.config.AppConfig;

/**
 * TODO
 * @Class: FFmpegHelper
 * @author: zhangxinyu
 * @date: 2015年1月5日 上午11:02:06
 */
public final class ExternalTool {

	private final static String FFMPEG_TOOL = "/usr/bin/trans.sh";//AppConfig.getInstance().getExtool_ffmpeg(); 
	
	//private final static String FFMPEG_TOOL = AppConfig.getInstance().getExtool_ffmpeg();
	
	private final static String PYTHON_TOOL = AppConfig.getInstance().getExtool_python();
	
	private final static String PY_SCRIPT_PATH = AppConfig.getInstance().getExtool_flvslicer();
	/**
	 * 
	 * 
	 * @Title: buildAudioCommand
	 * @param bitrate
	 * @param sampling
	 * @param duration
	 * @param audioSrc
	 * @throws InvalidMediaParameterException
	 * @return
	 * @return: String
	 */
	public static String buildAudioCommand(
			String	acodec, 
			int 	bitrate,
			int 	sampling,
			int 	duration,
			String 	audioSrc,
			String	outputFile) throws InvalidMediaParameterException {
		
		if (StringUtils.isEmpty(acodec)) {
			throw new InvalidMediaParameterException("Empty audio codec.");
		}
		
		if (bitrate < 1) {
			throw new InvalidMediaParameterException("Invalid audio bitrate.[" + bitrate + "]");
		}
		
		if (sampling < 11025) {
			throw new InvalidMediaParameterException("Audio sampling rate too low.[" + sampling + "]");
		}
		
		if (duration < 5) {
			throw new InvalidMediaParameterException("Too short audio, are you sure?[" + duration + "]");
		}
		
		if (!FileHelper.exists(audioSrc)) {
			throw new InvalidMediaParameterException("Audio doesn't exist![" + audioSrc + "]");
		}
		
		ShellCommand cmd = ShellCommand.create(FFMPEG_TOOL);
		
		/*cmd.add("-i", audioSrc)
		   .add("-y")
		   .add("-xerror")
		   .add("-vn")
		   .add("-ar", "44100");*/
		
		if ("aac".equalsIgnoreCase(acodec) || "mpeg audio".equalsIgnoreCase(acodec)) {
			if (sampling == 44100 || sampling == 22050 || sampling == 11025) {
				cmd.add("-acopy");
			} else {
				cmd.add("-m44");
			}
		} else {
			cmd.add("-aac");
		}
		
		cmd.add(audioSrc)
		   .add(outputFile);
		
		return cmd.toString();
	}
	
	public static String buildVideoCommand(
			String	vcodec,
			String  acodec,
			int 	bitrate_v,
			int 	bitrate_a,
			int 	sampling,
			String 	videoSrc,
			String	outputFile) throws InvalidMediaParameterException {
		
//		if (StringUtils.isEmpty(acodec)) {
//			throw new InvalidMediaParameterException("Empty audio codec.");
//		}
		
		if (bitrate_v < 1) {
			throw new InvalidMediaParameterException("Invalid video bitrate.[" + bitrate_v + "]");
		}
		
		if (bitrate_a < 1) {
			throw new InvalidMediaParameterException("Invalid audio bitrate.[" + bitrate_a + "]");
		}
		
//		if (sampling < 11025) {
//			throw new InvalidMediaParameterException("Audio sampling rate too low.[" + sampling + "]");
//		}

		ShellCommand cmd = ShellCommand.create(FFMPEG_TOOL);
		if ("aac".equalsIgnoreCase(acodec) || "mpeg audio".equalsIgnoreCase(acodec)) {
			if (sampling == 44100 || sampling == 22050 || sampling == 11025) {
				cmd.add("-acopy");
			} else {
				cmd.add("-m44");
			}
		} else {
			cmd.add("-aac");
		}
		
		if ("avc".equalsIgnoreCase(vcodec) && bitrate_v < 600) {
			cmd.add("-vcopy")
			   .add("-g4x3");
		} else {
			cmd.add("-x264")
			   .add("-q5")
			   .add("-g4x3");
		}
		
		cmd.add(videoSrc)
		   .add(outputFile);
		
		return cmd.toString();
	}
	
	/**
	 * 
	 * 
	 * @Title: buildFlvSlicerCommand
	 * @param flvFile
	 * @return
	 * @return: String
	 */
	public static String buildFlvSlicerCommand(String flvFile) {
		
		return ShellCommand.create(PYTHON_TOOL)
				.add(PY_SCRIPT_PATH)
				.add(flvFile)
				.toString();
	}
}
