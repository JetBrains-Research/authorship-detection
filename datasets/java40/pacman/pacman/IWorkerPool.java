/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: IWorkerPool.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月23日 下午2:56:25
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

import java.util.concurrent.ExecutorService;

import com.yeshj.pacman.jms.IMQReceiver;

/**
 * @ClassName: IWorkerPool
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月23日 下午2:56:25
 */
public interface IWorkerPool extends ExecutorService{

	/**
	 * whether current status can take more tasks.
	 * 
	 * @return
	 */
	boolean canTakeTask(SystemInfo info);
	
	/**
	 * 
	 * @return
	 */
	int takeNewTask(IMQReceiver receiver, IWorkerCallback callback);
}
