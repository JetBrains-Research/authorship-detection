/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: MessageManagerTest.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月30日 下午3:19:59
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.jms.IMessageManager;
import com.yeshj.pacman.jms.impl.MessageManager;

/**
 * TODO
 * @Class: MessageManagerTest
 * @author: zhangxinyu
 * @date: 2014年12月30日 下午3:19:59
 */
public class MessageManagerTest {

	BeanFactory factory;
	/**
	 * TODO
	 * @Title: setUp
	 * @throws java.lang.Exception
	 * @return: void
	 */
	@Before
	public void setUp() throws Exception {
		
		try {
			factory = new ClassPathXmlApplicationContext("config.xml");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
	public void testManager() {

		MessageManager manager = (MessageManager) factory.getBean("mq.manager");
		if (manager == null) {
			System.out.println("manager is null");
		}
		assertNotNull(manager);

	}

}
