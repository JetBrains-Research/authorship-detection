/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: MediaEncrytTest.java
 * @Prject: libMedia
 * @Package: com.yeshj.pacman
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月5日 下午11:32:03
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yeshj.pacman.utils.FileHelper;

/**
 * @ClassName: MediaEncrytTest
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月5日 下午11:32:03
 */
public class MediaEncrytTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEncode() {
		MediaEncrypt encrypt = new MediaEncrypt();
		encrypt.encodeMedia("e:/1.zip", "e:/1.data");
		assertTrue(FileHelper.exists("e:/1.data"));
	}

	@Test
	public void testDecode() {
		
		MediaEncrypt encrypt = new MediaEncrypt();
		encrypt.decodeMedia("e:/1.data", "e:/11.zip");
		
		assertTrue(FileHelper.exists("e:/11.zip"));
	}
}
