/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: BootStrapper.java
 * @Prject: AppPackSever
 * @Package: com.yeshj.pacman.startup
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月28日 下午9:23:59
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.startup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * @ClassName: BootStrapper
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月28日 下午9:23:59
 */
public class BootStrapper {

	private final static ILog logger = LogFactory.getLog(BootStrapper.class);
	
	private static BeanFactory gFactory;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.info("============App Server Starting...=========");
		AppServer server = new AppServer();
		gFactory = new ClassPathXmlApplicationContext("appserver.xml");
		try {

			if (Environment.precheck()) {
				
				server.initialize(gFactory);
				Runtime.getRuntime().addShutdownHook(server.createShutdownHook());
				
				server.start();
			}
		} catch (AppInitException e) {
			
			logger.error("============App Server Stopped=========", e);
			return ; //exit app directly.
		}
	}
}