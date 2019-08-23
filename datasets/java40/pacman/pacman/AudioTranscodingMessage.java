/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: AudioTranscodingMessage.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.model
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 下午5:04:29
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.message;

import com.yeshj.pacman.annotation.Transmit;

/**
 * TODO
 * @Class: AudioTranscodingMessage
 * @author: zhangxinyu
 * @date: 2014年12月24日 下午5:04:29
 */
public class AudioTranscodingMessage extends BaseMessage {

	/**
	 * @return the mediaFile
	 */
	public String getMediaFile() {
		return mediaFile;
	}

	/**
	 * @param mediaFile the mediaFile to set
	 */
	public void setMediaFile(String mediaFile) {
		this.mediaFile = mediaFile;
	}

	/**
	 * @return the classId
	 */
	public int getClassId() {
		return classId;
	}

	/**
	 * @param classId the classId to set
	 */
	public void setClassId(int classId) {
		this.classId = classId;
	}

	/**
	 * @return the lessonId
	 */
	public int getLessonId() {
		return lessonId;
	}

	/**
	 * @param lessonId the lessonId to set
	 */
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	@Transmit(key = "aubr")
	protected int audioBitRate;
	
	@Transmit(key = "ausamr")
	protected int audioSamplingRate;
	
	@Transmit(key = "audur")
	protected int audioDuration;

	@Transmit(key = "acodec")
	protected String audioCodec;
	
	@Transmit(key = "media")
	protected String mediaFile;
	
	@Transmit(key = "cid")
	protected int classId;
	
	@Transmit(key = "lid")
	protected int lessonId;
	
	@Transmit(key = "guid")
	protected String guid;
	
	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the audioCodec
	 */
	public String getAudioCodec() {
		return audioCodec;
	}

	/**
	 * @param audioCodec the audioCodec to set
	 */
	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	/**
	 * @return the audioBitRate
	 */
	public int getAudioBitRate() {
		return audioBitRate;
	}

	/**
	 * @param audioBitRate the audioBitRate to set
	 */
	public void setAudioBitRate(int audioBitRate) {
		this.audioBitRate = audioBitRate;
	}

	/**
	 * @return the audioSamplingRate
	 */
	public int getAudioSamplingRate() {
		return audioSamplingRate;
	}

	/**
	 * @param audioSamplingRate the audioSamplingRate to set
	 */
	public void setAudioSamplingRate(int audioSamplingRate) {
		this.audioSamplingRate = audioSamplingRate;
	}

	/**
	 * @return the audioDuration
	 */
	public int getAudioDuration() {
		return audioDuration;
	}

	/**
	 * @param audioDuration the audioDuration to set
	 */
	public void setAudioDuration(int audioDuration) {
		this.audioDuration = audioDuration;
	}
}
