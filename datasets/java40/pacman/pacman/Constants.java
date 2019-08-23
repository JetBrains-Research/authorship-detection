/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: Constants.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月4日 下午11:24:33
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

/**
 * @ClassName: Constants
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月4日 下午11:24:33
 */
public interface Constants {

	//lesson xml url
	public final static String LESSON_XML_URL = "lesson.xml.url";
	
	//the address of all images, small vedioes and small audioes.
	public final static String CONTEXT_WEB_RESOURCES = "context.web.resources";
	
	//base dir for all images, videoes and audioes.
	public final static String CONTEXT_WEB_RESOURCES_TARGET_DIR = "context.web.resources.target";

	public final static String CONTEXT_WEB_RESOURCES_TEMP_DIR = "context.web.resources.temp";
	
	//audio's codec.
	public final static String CONTEXT_AUDIO_CODEC = "context.audio.codec";
	
	//media is audio only.
	public final static String CONTEXT_AUDIONLY = "context.audio.only";
	
	//audio file's bitrate.
	public final static String CONTEXT_AUDIO_BITRATE = "context.audio.bitrate";
	
	//video's codec.
	public final static String CONTEXT_VIDEO_CODEC = "context.video.codec";
	
	//video file's bitrate.
	public final static String CONTEXT_VIDEO_BITRATE = "context.video.bitrate";
	
	//media's duration.
	public final static String CONTEXT_DURATION = "context.video.duration";
	
	//audio's sampling rate.
	public final static String CONTEXT_SAMPLING = "context.sampling";
	
	//video's width
	public final static String CONTEXT_WIDTH = "context.vedio.width";
	
	//video's height
	public final static String CONTEXT_HEIGHT = "context.vedio.height";
	
	//media's file path.
	public final static String CONTEXT_MEDIA_PATH = "context.media.path";
	
	//media's publishing path.
	public final static String CONTEXT_MEDIA_FULLPATH = "context.publish.path";
	
	//slicer files target path.
	public final static String CONTEXT_SLICER_PUBLISH_PATH = "context.slicer.pub.path";
	
	//media file for pack.
	public final static String CONTEXT_TOBEPACK_FILE = "context.pack.file";
	
	//media file
	public final static String CONTEXT_TEMP_DIR = "context.temp.dir";
	
	//resources for pack
	public final static String CONTEXT_RESOURCE_DIR = "context.res.dir";
	
	public final static String CONTEXT_HJPACK = "context.hjpack";
	
	public final static String CONTEXT_CLASS_ID = "context.class.id";
	
	public final static String CONTEXT_LESSON_ID = "context.lesson.id";

	public static final String CONTEXT_IDENTITY = "context.identity";
	
	public final static String CONTEXT_ISFREE_CLASS = "context.free";
}
