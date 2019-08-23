/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: TranscodingStepTest.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 下午1:09:29
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.task.test;

import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.step.TranscodingStep;
import com.yeshj.pacman.utils.FileHelper;

/**
 * TODO
 * @Class: TranscodingStepTest
 * @author: zhangxinyu
 * @date: 2015年1月5日 下午1:09:29
 */
public class TranscodingStepTest {

	/**
	 * TODO
	 * @Title: setUp
	 * @throws java.lang.Exception
	 * @return: void
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * TODO
	 * @Title: tearDown
	 * @throws java.lang.Exception
	 * @return: void
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCopyCodecAudio() throws Exception {
		
		ExecuteContext context = new ExecuteContext();
		context.setAttribute(Constants.CONTEXT_MEDIA_PATH, "e:\\mp3\\mww.mp3");
		context.setAttribute(Constants.CONTEXT_MEDIA_FULLPATH, "e:\\out\\class_audio_mp3\\0.flv");
		context.setAttribute(Constants.CONTEXT_AUDIONLY, true);
		context.setAttribute(Constants.CONTEXT_AUDIO_BITRATE, 128000);
		context.setAttribute(Constants.CONTEXT_AUDIO_CODEC, "mpeg audio");
		context.setAttribute(Constants.CONTEXT_SAMPLING, 44100);
		context.setAttribute(Constants.CONTEXT_DURATION, 60*60*1000);
		TranscodingStep step = new TranscodingStep();
		step.execute(context);
		
		assertTrue(FileHelper.exists("e:\\out\\class_audio_mp3\\0.flv"));
	}
	
	@Test
	public void testTransCodecAudio() throws Exception {
		
		String src = "e:\\mp3\\mww.mp3";
		String target = "e:\\out\\class_audio_mp3\\9.flv";
		ExecuteContext context = new ExecuteContext();
		context.setAttribute(Constants.CONTEXT_MEDIA_PATH, src);
		context.setAttribute(Constants.CONTEXT_MEDIA_FULLPATH, target);
		context.setAttribute(Constants.CONTEXT_AUDIONLY, true);
		context.setAttribute(Constants.CONTEXT_AUDIO_BITRATE, 128000);
		context.setAttribute(Constants.CONTEXT_AUDIO_CODEC, "mpeg");
		context.setAttribute(Constants.CONTEXT_SAMPLING, 44100);
		context.setAttribute(Constants.CONTEXT_DURATION, 60*60*1000);
		TranscodingStep step = new TranscodingStep();
		step.execute(context);
		
		assertTrue(FileHelper.exists(target));
		assertTrue(FileHelper.sizeOf(target, true) > 0);
	}

}
