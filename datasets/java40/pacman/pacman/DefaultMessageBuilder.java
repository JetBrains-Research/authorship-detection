/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: DefaultMessageBuilder.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.impl
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月2日 上午1:17:52
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.jms.impl;

import com.yeshj.pacman.jms.IMessageBuilder;
import com.yeshj.pacman.jms.message.AudioTranscodingMessage;
import com.yeshj.pacman.jms.message.BaseMessage;
import com.yeshj.pacman.jms.message.FeedbackMessage;
import com.yeshj.pacman.jms.message.VideoTranscodingMessage;
import com.yeshj.pacman.utils.IdGenerator;

/**
 * @ClassName: DefaultMessageBuilder
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月2日 上午1:17:52
 */
public class DefaultMessageBuilder implements IMessageBuilder {

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageBuilder#buildAudioTaskMessage()
	 */
	@Override
	public BaseMessage buildAudioTaskMessage(
			int taskId,
			int classId,
			int lessonId,
			int type,
			String guid,
			String codec,
			int bitrate, 
			int sample, 
			int duration,
			String media) {

		AudioTranscodingMessage message = new AudioTranscodingMessage();
		message.setClassId(classId);
		message.setLessonId(lessonId);
		message.setAudioBitRate(bitrate);
		message.setAudioSamplingRate(sample);
		message.setAudioDuration(duration);
		message.setCommandId(taskId);
		message.setAudioCodec(codec);
		message.setMediaFile(media);
		message.setGuid(guid);
		message.setCommandType(type);
		
		return message;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageBuilder#buildVideoTaskMessage()
	 */
	@Override
	public BaseMessage buildVideoTaskMessage(
			int taskId,
			int classId,
			int lessonId,
			int type,
			String guid,
			String acodec,
			String vcodec,
			int audioBitrate,
			int audioSample,
			int videoBitrate,
			int videoWidth,
			int videoHeight,
			int duration,
			String media) {

		VideoTranscodingMessage message = new VideoTranscodingMessage();
		message.setCommandId(taskId);
		message.setClassId(classId);
		message.setLessonId(lessonId);
		message.setAudioBitRate(audioBitrate);
		message.setAudioSamplingRate(audioSample);
		message.setVideoBitRate(videoBitrate);
		message.setVideoWidth(videoWidth);
		message.setVideoHeight(videoHeight);
		message.setAudioCodec(acodec);
		message.setVideoCodec(vcodec);
		message.setMediaFile(media);
		message.setGuid(guid);
		message.setCommandType(type);
		
		return message;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageBuilder#buildFeedbackMessage()
	 */
	@Override
	public BaseMessage buildFeedbackMessage(int cmdId, boolean success, String msg) {

		FeedbackMessage message = new FeedbackMessage();
		
		message.setCommandId(cmdId);
		message.setSuccess(success);
		message.setMsg(msg);
		
		return message;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageBuilder#buildCommandMessage()
	 */
	@Override
	public BaseMessage buildCommandMessage(String msg) {

		FeedbackMessage message = new FeedbackMessage();
		message.setCommandId(IdGenerator.nextInt());
		message.setMsg(msg);
		
		return message;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageBuilder#buildHeartbeatMessage()
	 */
	@Override
	public BaseMessage buildHeartbeatMessage() {

		FeedbackMessage message = new FeedbackMessage();
		message.setCommandId(IdGenerator.nextInt());
		
		return message;
	}

}
