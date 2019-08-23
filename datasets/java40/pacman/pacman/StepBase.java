/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: StepBase.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月24日 上午12:25:08
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * @ClassName: StepBase
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月24日 上午12:25:08
 */
public abstract class StepBase implements IStep {

	protected final static ILog logger = LogFactory.getLog(StepBase.class);

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.schedule.IStep#beforeExecute()
	 */
	@Override
	public void beforeExecute(ExecuteContext context) {
		
		logger.info(String.format("step[%s] started!", name()));
		context.timeOn();
	}
	
	/* (non-Javadoc)
	 * @see com.yeshj.pacman.schedule.IStep#afterExecute(com.yeshj.pacman.schedule.ExecuteContext)
	 */
	@Override
	public void afterExecute(ExecuteContext context) {
		
		context.setLastExecuteDuration(context.timeOff() / 1000);
		logger.info(String.format("step[%s] executed[%d ms]", name(), context.getLastExecuteDuration() / 1000));
	}
}
