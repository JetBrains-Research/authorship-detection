/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: SysMonitorTest.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.test
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月20日 上午9:11:51
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.task.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.SystemInfo;
import com.yeshj.pacman.schedule.step.SystemMonitorStep;

/**
 * @ClassName: SysMonitorTest
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月20日 上午9:11:51
 */
public class SysMonitorTest {

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
	public void testSysInfo() throws Exception {
		
		SystemMonitorStep monitor = new SystemMonitorStep();
		ExecuteContext context = new ExecuteContext();
		SystemInfo info = (SystemInfo) monitor.execute(context);
		assertNotNull(info);
		
		System.out.println("user:" + info.getCpuUser() + " sys:" + info.getCpuSys() +
				" nice:" + info.getCpuNice() + " idle:" + info.getCpuIdle());
		System.out.println("total:" + info.getMemTotal() + " used:" + info.getMemUsed() +
				" free:" + info.getMemFree());
		
		assertTrue(info.getCpuIdle() > 1.0d);
		
		assertTrue(info.getMemTotal() > 0);
		assertTrue(info.getMemFree() > 0);

	}
}
