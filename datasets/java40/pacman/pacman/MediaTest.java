/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: MediaTest.java
 * @Prject: libMedia
 * @Package: com.yeshj.pacman
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月22日 下午3:33:38
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO
 * @Class: MediaTest
 * @author: zhangxinyu
 * @date: 2014年12月22日 下午3:33:38
 */
public class MediaTest {

	
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
	public void testMp3() {
		MediaInfo mi = new MediaInfo();
		mi.analyzeMedia("e:\\mp3\\mww.mp3");
		
		assertEquals("mpeg audio", mi.getMediaTypeKey());
		System.out.println("bitrate:" + mi.getAudioBitrate());
		System.out.println("duration:" + mi.getAudioDuration());
		System.out.println("codec:" + mi.getAudioFormat());
		System.out.println("sample:" + mi.getAudioSamplingRate());
		assertTrue(mi.getAudioBitrate() > 0);
		assertEquals(44100, mi.getAudioSamplingRate());
	}

	@Test
	public void testMp4() {
		
		MediaInfo mi = new MediaInfo();
		mi.analyzeMedia("E:\\video\\2.mp4");
		String mt = mi.getMediaTypeKey();
		String acodec = mi.getAudioFormat();
		String vcodec = mi.getVideoFormat();
		int abr = mi.getAudioBitrate();
		int vbr = mi.getVideoBitrate();
		
		assertEquals("mpeg audio", mt);
		assertNotEquals("", acodec);
		assertNotEquals("", vcodec);
		assertTrue(mi.getAudioBitrate() > 0);
		assertTrue(mi.getVideoBitrate() > 0);
	}
}
