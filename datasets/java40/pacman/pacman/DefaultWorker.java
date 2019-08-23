/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: DefaultWorker.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.impl
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 下午3:17:10
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.yeshj.pacman.jms.message.BaseMessage;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.IStep;
import com.yeshj.pacman.schedule.IWorker;
import com.yeshj.pacman.schedule.InnerWorkerPool;
import com.yeshj.pacman.schedule.WorkerType;
import com.yeshj.pacman.utils.StringHelper;

/**
 * TODO
 * 
 * @Class: DefaultWorker
 * @author: zhangxinyu
 * @date: 2014年12月24日 下午3:17:10
 */
public class DefaultWorker implements IWorker {

	private final static ILog logger 		= LogFactory.getLog(DefaultWorker.class);
	private final static String PREFIX 	= "[DefaultWorker]";
	private final ExecuteContext context 	= new ExecuteContext();

	private AtomicBoolean signalStop = new AtomicBoolean(false);
	private List<IStep> steps;
	private WorkerType type;

	public DefaultWorker() {

		steps = new ArrayList<IStep>();
	}

	/**
	 * @return the context
	 */
	@Override
	public ExecuteContext getContext() {

		return context;
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: run
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		int cmdId = 0;
		InnerWorkerPool pool = context
				.getAttribute(InnerWorkerPool.WORKER_POOL);
		BaseMessage msg = context.getAttribute("msg");
		
		if (msg != null)
			cmdId = msg.getCommandId();
		
		for (IStep step : steps) {

			if (!signalStop.get()) {

				step.beforeExecute(context);
				Object obj = null;
				try {

					obj = step.execute(context);
					context.setResult(cmdId, obj, null);
					if (context.isRollbackFlag()) {
						logger.error(PREFIX + step.name() + " ERROR ROLLBACK.");
						break;
					}
				} catch (Exception e) {

					logger.error(PREFIX + step.name() + " ERROR OCCURS.", e);
					context.setResult(cmdId, obj, e);
					break;
				} finally {

					step.afterExecute(context);
				}
			} else {

				logger.info(String.format(
						"%s[%s] stopped by user cancelling.", PREFIX, step.name()));
				break;
			}
		}

		if (pool != null)
			pool.callback(context);
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: getExecutedDuration
	 * @return
	 * @see com.yeshj.pacman.schedule.IWorker#getExecutedDuration()
	 */
	@Override
	public long getExecutedDuration() {

		return context.getLastExecuteDuration();
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: setAttribute
	 * @param key
	 * @param value
	 * @see com.yeshj.pacman.schedule.IWorker#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public <T> void setAttribute(String key, T value) {

		context.setAttribute(key, value);
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: getAttribute
	 * @param key
	 * @return
	 * @see com.yeshj.pacman.schedule.IWorker#getAttribute(java.lang.String)
	 */
	@Override
	public <T> T getAttribute(String key) {

		return context.getAttribute(key);
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: canStop
	 * @return
	 * @see com.yeshj.pacman.schedule.IWorker#canStop()
	 */
	@Override
	public boolean canStop() {

		return true;
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: stop
	 * @see com.yeshj.pacman.schedule.IWorker#stop()
	 */
	@Override
	public void stop() {

		signalStop.set(true);
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: getSteps
	 * @return
	 * @see com.yeshj.pacman.schedule.IWorker#getSteps()
	 */
	@Override
	public List<IStep> getSteps() {

		return steps;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	public void setSteps(List<IStep> steps) {

		this.steps.clear();
		this.steps.addAll(steps);
	}

	/**
	 * @return the type
	 */
	@Override
	public WorkerType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	@Override
	public void setType(WorkerType type) {
		this.type = type;
	}

}
