/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: VideoTranscodingMessage.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.model
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 下午5:16:34
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.message;

import com.yeshj.pacman.annotation.Transmit;

/**
 * TODO
 * @Class: VideoTranscodingMessage
 * @author: zhangxinyu
 * @date: 2014年12月24日 下午5:16:34
 */
public class VideoTranscodingMessage extends BaseMessage {

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
	
	@Transmit(key = "vibr")
	protected int videoBitRate;

	@Transmit(key = "visamr")
	protected int vedioSamplingRate;

	@Transmit(key = "vidur")
	protected int vedioDuration;

	@Transmit(key = "width")
	protected int videoWidth;

	@Transmit(key = "vcodec")
	protected String videoCodec;

	@Transmit(key = "media")
	protected String mediaFile;

	@Transmit(key = "cid")
	protected int classId;

	@Transmit(key = "lid")
	protected int lessonId;

	@Transmit(key = "height")
	protected int videoHeight;
	
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
	 * @return the audioBitRate
	 */
	public int getAudioBitRate() {
		return audioBitRate;
	}
	
	/**
	 * @return the audioCodec
	 */
	public String getAudioCodec() {
		return audioCodec;
	}
	
	/**
	 * @return the audioDuration
	 */
	public int getAudioDuration() {
		return audioDuration;
	}
	
	/**
	 * @return the audioSamplingRate
	 */
	public int getAudioSamplingRate() {
		return audioSamplingRate;
	}
	
	/**
	 * @return the vedioDuration
	 */
	public int getVedioDuration() {
		return vedioDuration;
	}
	
	/**
	 * @return the vedioSamplingRate
	 */
	public int getVedioSamplingRate() {
		return vedioSamplingRate;
	}
	
	/**
	 * @return the videoBitRate
	 */
	public int getVideoBitRate() {
		return videoBitRate;
	}
	/**
	 * @return the videoCodec
	 */
	public String getVideoCodec() {
		return videoCodec;
	}

	/**
	 * @return the videoHeight
	 */
	public int getVideoHeight() {
		return videoHeight;
	}

	/**
	 * @return the videoWidth
	 */
	public int getVideoWidth() {
		return videoWidth;
	}

	/**
	 * @param audioBitRate the audioBitRate to set
	 */
	public void setAudioBitRate(int audioBitRate) {
		this.audioBitRate = audioBitRate;
	}

	/**
	 * @param audioCodec the audioCodec to set
	 */
	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	/**
	 * @param audioDuration the audioDuration to set
	 */
	public void setAudioDuration(int audioDuration) {
		this.audioDuration = audioDuration;
	}

	/**
	 * @param audioSamplingRate the audioSamplingRate to set
	 */
	public void setAudioSamplingRate(int audioSamplingRate) {
		this.audioSamplingRate = audioSamplingRate;
	}

	/**
	 * @param vedioDuration the vedioDuration to set
	 */
	public void setVedioDuration(int vedioDuration) {
		this.vedioDuration = vedioDuration;
	}

	/**
	 * @param vedioSamplingRate the vedioSamplingRate to set
	 */
	public void setVedioSamplingRate(int vedioSamplingRate) {
		this.vedioSamplingRate = vedioSamplingRate;
	}

	/**
	 * @param videoBitRate the videoBitRate to set
	 */
	public void setVideoBitRate(int videoBitRate) {
		this.videoBitRate = videoBitRate;
	}

	/**
	 * @param videoCodec the videoCodec to set
	 */
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}

	/**
	 * @param videoHeight the videoHeight to set
	 */
	public void setVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
	}

	/**
	 * @param videoWidth the videoWidth to set
	 */
	public void setVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
	}
}
