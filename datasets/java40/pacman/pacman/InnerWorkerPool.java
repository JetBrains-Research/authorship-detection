/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: IWorkerPoolHost.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月12日 上午12:34:27
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: InnerWorkerPool
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月12日 上午12:34:27
 */
public abstract class InnerWorkerPool extends ThreadPoolExecutor implements IWorkerPool {

	public final static String WORKER_CALLBACK = "worker.callback";
	public final static String WORKER_POOL = "worker.pool";
	
	/**
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @param workQueue
	 */
	public InnerWorkerPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param context
	 */
	public abstract void callback(ExecuteContext context);
}
