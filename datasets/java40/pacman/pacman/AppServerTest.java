/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: AppServerTest.java
 * @Prject: AppPackSever
 * @Package: com.yeshj.pacman.startup.test
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月29日 上午12:43:44
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.startup.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.schedule.SystemInfo;
import com.yeshj.pacman.startup.AppInitException;
import com.yeshj.pacman.startup.AppServer;
/**
 * @ClassName: AppServerTest
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月29日 上午12:43:44
 */
public class AppServerTest {

	private BeanFactory factory;
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

	@Test(expected = AppInitException.class)
	public void testErrorConfig() throws AppInitException {
		
		factory = new ClassPathXmlApplicationContext("empty_config.xml");
		AppServer server = new AppServer();
		server.initialize(factory);
	}

	@Test(expected = AppInitException.class)
	public void testNotFoundConfig() throws AppInitException {

		AppServer server = new AppServer();
		server.initialize(null);
	}
	
	@Test
	public void testNormal() throws AppInitException {
		
		factory = new ClassPathXmlApplicationContext("config.xml");
		AppServer server = new AppServer();
		server.initialize(factory);
		
		assertEquals(40, AppConfig.getInstance().getVideoWeight());
		assertEquals("tcp://localhost:61616", AppConfig.getInstance().getJmsAddress());
		
		server.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SystemInfo info = server.touch();
		assertTrue(info.getCpuIdle() > 0);
		assertTrue(info.getMemFree() > 0);
		assertTrue(server.isWorking());
		
		server.stop();
		server.await();
		
		assertFalse(server.isWorking());
	}
}
