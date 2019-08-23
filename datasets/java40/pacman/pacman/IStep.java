/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: IStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月23日 下午10:58:15
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

/**
 * @ClassName: IStep
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月23日 下午10:58:15
 */
public interface IStep{

	/**
	 * Before execution.
	 * 
	 * @param context
	 */
	void beforeExecute(ExecuteContext context);
	
	/**
	 * After execution.
	 * 
	 * @param context
	 */
	void afterExecute(ExecuteContext context);
	
	/**
	 * Execution.
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	Object execute(ExecuteContext context) throws Exception;
	
	/**
	 * Step's name
	 * TODO
	 * @Title: name
	 * @return
	 * @return: String
	 */
	String name();
}
