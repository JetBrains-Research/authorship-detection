/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: ExecuteContext.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月23日 下午11:34:51
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * @ClassName: ExecuteContext
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月23日 下午11:34:51
 */
public class ExecuteContext implements Cloneable{

	private final static ILog logger = LogFactory.getLog(ExecuteContext.class);
	
	//execute time of each step.
	private final ThreadLocal<Long> executeTimer = new ThreadLocal<Long>();;
	
	//total execute time of the whole task.
	private final AtomicLong totalTimer = new AtomicLong();
	
	//last executed duration.
	private long lastExecuteTime = 0;
	
	private ExecuteResult executeResult = new ExecuteResult();
	
	//Sets the return result.
	public void setResult(int cmdId, Object result, Exception e) {
		
		executeResult.setCommandId(cmdId);
		executeResult.setResult(result);
		executeResult.setException(e);
	}
	
	//Gets the return result.
	public ExecuteResult getResult() {
		
		return executeResult;
	}
	
	//attributes
	private Map<String, Object> storage;
	
	private boolean rollbackFlag;
	
	/**
	 * @return the rollbackFlag
	 */
	public boolean isRollbackFlag() {
		return rollbackFlag;
	}

	/**
	 * @param rollbackFlag the rollbackFlag to set
	 */
	public void setRollbackFlag(boolean rollbackFlag) {
		this.rollbackFlag = rollbackFlag;
	}

	/**
	 * non parameters
	 */
	public ExecuteContext() {
		
		storage = new HashMap<String, Object>();

		logger.info("ExecuteContext[paramless] created!");
	}
	
	/**
	 * with the specified parameters.
	 * 
	 * @param map
	 */
	public ExecuteContext(Map<String, Object> map) {
		
		if (map != null) {
			storage = map;
		} else {
			storage = new HashMap<String, Object>();
		}
		logger.info("ExecuteContext[param] created!");
	}
	
	/**
	 * time-on when step ready to run
	 */
	public void timeOn() {
		
		lastExecuteTime = 0;
		executeTimer.set(System.nanoTime());
	}
	
	/**
	 * time-off when step done.
	 * 
	 * @return
	 */
	public long timeOff() {
		
		lastExecuteTime = System.nanoTime() - executeTimer.get();
		
		totalTimer.addAndGet(lastExecuteTime);

		return lastExecuteTime;
	}
	
	/**
	 * Saves the attribute
	 * 
	 * @Title: setAttribute
	 * @param key
	 * @param t
	 * @return: void
	 */
	public <T> void setAttribute(String key, T t) {
		
		if (storage != null) {
			storage.put(key, t);	
		}
	}
	
	/**
	 * Unsafe!!!
	 * 
	 * @Title: getAttribute
	 * @param key
	 * @return
	 * @return: T
	 */
	public <T> T getAttribute(String key) {
		
		return getAttribute(key, null);
	}
	
	/**
	 * Gets the attribute
	 * 
	 * @Title: getAttribute
	 * @param key
	 * @param defaultValue
	 * @return
	 * @return: T
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, T defaultValue) {
		
		T t = defaultValue;
		
		if (storage != null && storage.containsKey(key)) {
			
			t = (T) storage.get(key);
		}
		
		return t;
	}
	
	/**
	 * Gets the last executed duration.
	 * 
	 * @return duration
	 */
	public long getLastExecuteDuration() {
		
		return lastExecuteTime;
	}
	
	/**
	 * Sets the last executed duration.
	 * 
	 * @Title: setLastExecuteDuration
	 * @param duration
	 * @return: void
	 */
	public void setLastExecuteDuration(long duration) {
		
		lastExecuteTime = duration;
	}
	
	/**
	 * resets the context.
	 * 
	 * @param map
	 */
	public void reset(Map<String, Object> map) {
		
		logger.info("ExecuteContext.reset()");
		storage.clear();
		if (map != null)
			storage.putAll(map);
		
		totalTimer.set(0);
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {

		ExecuteContext clone = (ExecuteContext) super.clone();
		clone.storage.putAll(this.storage);
		clone.executeTimer.set(0L);
		clone.totalTimer.set(0L);
		
		return clone;
	}

}
