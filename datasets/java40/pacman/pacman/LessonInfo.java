/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: LessonInfo.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.dom
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 下午6:11:37
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.dom;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * TODO
 * @Class: LessonInfo
 * @author: zhangxinyu
 * @date: 2014年12月24日 下午6:11:37
 */
public class LessonInfo {
	
	private int classID;
	private int lessonID;
	private String lessonName;
	private String media;
	private int mediaType;
	private List<String> allFiles;
	
	public LessonInfo() {
		allFiles = new ArrayList<String>();
	}
	
	/**
	 * @return the classID
	 */
	public int getClassID() {
		return classID;
	}
	/**
	 * @param classID the classID to set
	 */
	public void setClassID(int classID) {
		this.classID = classID;
	}
	/**
	 * @return the lessonID
	 */
	public int getLessonID() {
		return lessonID;
	}
	/**
	 * @param lessonID the lessonID to set
	 */
	public void setLessonID(int lessonID) {
		this.lessonID = lessonID;
	}
	/**
	 * @return the lessonName
	 */
	public String getLessonName() {
		return lessonName;
	}
	/**
	 * @param lessonName the lessonName to set
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	/**
	 * @return the media
	 */
	public String getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(String media) {
		this.media = media;
	}
	/**
	 * @return the mediaType
	 */
	public int getMediaType() {
		return mediaType;
	}
	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}
	/**
	 * @return the allFiles
	 */
	public List<String> getAllFiles() {
		return allFiles;
	}
	/**
	 * @param allFiles the allFiles to set
	 */
	public void addFile(String file) {
		if (!StringUtils.isEmpty(file))
			this.allFiles.add(file);
	}
	
}
