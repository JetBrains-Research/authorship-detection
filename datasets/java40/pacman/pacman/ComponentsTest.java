/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: ComponentsTest.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月25日 上午9:26:06
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.task.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yeshj.pacman.schedule.IStep;
import com.yeshj.pacman.schedule.impl.DefaultWorker;

/**
 * TODO
 * @Class: ComponentsTest
 * @author: zhangxinyu
 * @date: 2014年12月25日 上午9:26:06
 */
public class ComponentsTest {

	private BeanFactory factory;
	/**
	 * TODO
	 * @Title: setUp
	 * @throws java.lang.Exception
	 * @return: void
	 */
	@Before
	public void setUp() throws Exception {
		factory = new ClassPathXmlApplicationContext("schedules.xml");
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
	public void testIOCLoad() {
		
		DefaultWorker worker = (DefaultWorker) factory.getBean("complex");
		assertNotNull(worker);
		for (IStep step : worker.getSteps()) {

			assertNotNull(step);
		}
	}

}
