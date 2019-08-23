/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: MediaPackStepTest.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月6日 下午4:24:42
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.task.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.step.MediaPackStep;
import com.yeshj.pacman.utils.FileHelper;

/**
 * TODO
 * @Class: MediaPackStepTest
 * @author: zhangxinyu
 * @date: 2015年1月6日 下午4:24:42
 */
public class MediaPackStepTest {

	private List<String> files;
	/**
	 * TODO
	 * @Title: setUp
	 * @throws java.lang.Exception
	 * @return: void
	 */
	@Before
	public void setUp() throws Exception {
		
		FileHelper.copyFile("e:\\mp3\\mww.mp3", "e:\\temp\\packtemp\\index.dat");
		files = FileHelper.getAllFileInDir("e:/temp/resource", true);
		String xml = FileHelper.combinePath(this.getClass().getResource("/").toString(), "log4j.xml").replace("file:\\", "");
		FileHelper.copyFile(xml, "e:/temp/packtemp/index.xml");
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
	public void testNormal() throws Exception {

		ExecuteContext context = new ExecuteContext();
		context.setAttribute(Constants.CONTEXT_TEMP_DIR, "e:/temp/packtemp");
		context.setAttribute(Constants.CONTEXT_RESOURCE_DIR, "e:/temp/resource");
		context.setAttribute(Constants.CONTEXT_HJPACK, "e:/temp/123456789.hjp");
		MediaPackStep step = new MediaPackStep();
		step.execute(context);
		
		assertTrue(FileHelper.exists("e:/temp/123456789.hjp"));
	}

}
