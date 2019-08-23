/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: TranscodingStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 上午10:01:40
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import java.io.FileNotFoundException;

import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.schedule.StepExecuteException;
import com.yeshj.pacman.utils.ExternalTool;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.ProcessExecutor;

/**
 * TODO
 * @Class: TranscodingStep
 * @author: zhangxinyu
 * @date: 2015年1月5日 上午10:01:40
 */
public class TranscodingStep extends StepBase {

	private final static String PREFIX = "[TranscodingStep] ";
	/** (non Javadoc)
	 * TODO
	 * @Title: execute
	 * @param context
	 * @return
	 * @throws Exception
	 * @see com.yeshj.pacman.schedule.IStep#execute(com.yeshj.pacman.schedule.ExecuteContext)
	 */
	@Override
	public Object execute(ExecuteContext context) throws Exception {

		boolean audionly = context.getAttribute(Constants.CONTEXT_AUDIONLY, true);
		
		if (audionly) {
			decodeAudio(context);
		} else {
			decodeVideo(context);
		}
		
		return null;
	}

	/**
	 * TODO
	 * @Title: decodeVideo
	 * @param context
	 * @return: void
	 * @throws Exception 
	 */
	private void decodeVideo(ExecuteContext context) throws Exception {
		
		String videoCodec = context.getAttribute(Constants.CONTEXT_VIDEO_CODEC);
		String audioCodec = context.getAttribute(Constants.CONTEXT_AUDIO_CODEC);
		int videoBitrate = context.getAttribute(Constants.CONTEXT_VIDEO_BITRATE);
		int audioBitrate = context.getAttribute(Constants.CONTEXT_AUDIO_BITRATE);
		int audioSamping = context.getAttribute(Constants.CONTEXT_SAMPLING, 0);
		
		String videoSrc = context.getAttribute(Constants.CONTEXT_MEDIA_PATH);
		String videoDest = context.getAttribute(Constants.CONTEXT_MEDIA_FULLPATH);
		
		if (!FileHelper.exists(videoSrc)) {
			throw new FileNotFoundException(videoSrc);
		}
		
		FileHelper.ensureExists(videoDest, true);
		
		String command = ExternalTool.buildVideoCommand(
				videoCodec, audioCodec, videoBitrate, audioBitrate, audioSamping, videoSrc, videoDest);
		
		logger.info(PREFIX + " CMD:" + command);
		ProcessExecutor pe = new ProcessExecutor();
		if (pe.runShell(command))
		{
			
			logger.info(PREFIX + " execute successfully!");
		} else {

			logger.error(PREFIX + " execute fail!\n");
			throw new StepExecuteException();
		}
	}

	/**
	 * TODO
	 * @Title: decodeAudio
	 * @param context
	 * @return: void
	 * @throws Exception 
	 */
	private void decodeAudio(ExecuteContext context) throws Exception {
		
		String audioCodec = context.getAttribute(Constants.CONTEXT_AUDIO_CODEC);
		int audioBitrate = context.getAttribute(Constants.CONTEXT_AUDIO_BITRATE, 0);
		int audioSamping = context.getAttribute(Constants.CONTEXT_SAMPLING, 0);
		int audioDuration = context.getAttribute(Constants.CONTEXT_DURATION, 0);
		
		String audioSrc = context.getAttribute(Constants.CONTEXT_MEDIA_PATH);
		String audioDest = context.getAttribute(Constants.CONTEXT_MEDIA_FULLPATH);
		
		if (!FileHelper.exists(audioSrc)) {
			throw new FileNotFoundException(audioSrc);
		}
		
		FileHelper.ensureExists(audioDest, true);

		String command = ExternalTool.buildAudioCommand(
				audioCodec, audioBitrate, audioSamping, audioDuration, audioSrc, audioDest);
		
		logger.info(PREFIX + " CMD:" + command);
		
		ProcessExecutor pe = new ProcessExecutor();
		if (pe.runShell(command))
		{
			
			logger.info(PREFIX + " execute successfully!");
		} else {

			logger.error(PREFIX + " execute fail!\n");
			throw new StepExecuteException();
		}
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: name
	 * @return
	 * @see com.yeshj.pacman.schedule.IStep#name()
	 */
	@Override
	public String name() {

		return "media.transcoding.step";
	}

}
