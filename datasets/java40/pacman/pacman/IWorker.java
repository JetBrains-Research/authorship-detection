/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: IWorker.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月23日 下午3:04:56
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

import java.util.List;
import java.util.Map;


/**
 * @ClassName: IWorker
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月23日 下午3:04:56
 */
public interface IWorker extends Runnable{

	/**
	 * Getter and setter the duration of executed.
	 * 
	 * @return
	 */
	long	getExecutedDuration(); 
	
	/**
	 * Getter and Setter for worker type.
	 * 
	 * @Title: getType
	 * @return
	 * @return: WorkerType
	 */
	WorkerType getType();
	void setType(WorkerType type);
	
	/**
	 * Getter and setter for parameter.
	 * 
	 * @param key
	 * @param value
	 */
	<T> void setAttribute(String key, T value);
	<T> T 	getAttribute(String key);
	
	/**
	 * 
	 * @return
	 */
	ExecuteContext getContext();
	
	/**
	 * Whether the task can be stopped.
	 * 
	 * @return
	 */
	boolean canStop();
	
	/**
	 * Stop the running task
	 */
	void stop();
	
	/**
	 * Gets the steps inside worker.
	 * 
	 * @return
	 */
	List<IStep> getSteps();
}
