/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: LessonParserTest.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月25日 上午9:46:20
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.task.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yeshj.pacman.schedule.dom.LessonInfo;
import com.yeshj.pacman.schedule.dom.LessonInfoParser;
import com.yeshj.pacman.utils.FileHelper;

/**
 * TODO
 * @Class: LessonParserTest
 * @author: zhangxinyu
 * @date: 2014年12月25日 上午9:46:20
 */
public class LessonParserTest {

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
	public void testLessonParser() throws Exception {
		
		String clazzpath = this.getClass().getResource("/").toString();
		LessonInfo info = new LessonInfoParser().parseAndSave(clazzpath + "/lesson.xml", "e:/lesson.xml");
		
		assertNotNull(info);
		assertEquals(140118, info.getClassID());
		assertEquals(253772, info.getLessonID());
		assertEquals("100010040.mp3", info.getMedia());
		assertEquals(3, info.getMediaType());
		assertEquals("LessonName", info.getLessonName());
		assertTrue(info.getAllFiles().size() > 0);
		assertTrue(FileHelper.exists("e:/lesson.xml"));
	}

	@Test
	public void testTrimToBareName() throws Exception {
		
		String url = "http://www.hujiang.com/abc/132/3kjd/1.mp3";
		LessonInfoParser parser = new LessonInfoParser();
		assertEquals("1", parser.trimToBareName(url));
		
		url = "hi$$so__i23.mp4";
		assertEquals("hi$$so__i23", parser.trimToBareName(url));
	}
}
