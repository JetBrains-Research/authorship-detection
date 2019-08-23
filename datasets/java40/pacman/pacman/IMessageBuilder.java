/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: IMessageBuilder.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月30日 下午2:48:09
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms;

import com.yeshj.pacman.jms.message.BaseMessage;

/**
 * TODO
 * @Class: IMessageBuilder
 * @author: zhangxinyu
 * @date: 2014年12月30日 下午2:48:09
 */
public interface IMessageBuilder {

	/**
	 * 
	 * @param classId
	 * @param lessonId
	 * @param bitrate
	 * @param sample
	 * @param duration
	 * @return
	 */
	BaseMessage buildAudioTaskMessage(
			int taskId,
			int classId,
			int lessonId,
			int type,
			String guid,
			String codec,
			int bitrate, 
			int sample, 
			int duration,
			String media);
	
	/**
	 * 
	 * @param classId
	 * @param lessonId
	 * @param audioBitrate
	 * @param audioSample
	 * @param videoBitrate
	 * @param videoWidth
	 * @param videoHeight
	 * @param duration
	 * @return
	 */
	BaseMessage buildVideoTaskMessage(
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
			String media);
	
	/**
	 * 
	 * @param cmdId
	 * @param success
	 * @param msg
	 * @return
	 */
	BaseMessage buildFeedbackMessage(int cmdId, boolean success, String msg);
	
	/**
	 * 
	 * @param cmd
	 * @return
	 */
	BaseMessage buildCommandMessage(String cmd);
	
	/**
	 * 
	 * @return
	 */
	BaseMessage buildHeartbeatMessage();
}
