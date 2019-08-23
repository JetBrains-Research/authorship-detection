/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: ExecuteResult.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月24日 上午1:07:27
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

/**
 * @ClassName: ExecuteResult
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月24日 上午1:07:27
 */
public class ExecuteResult {

	private Object result;
	private Exception exception = null;
	private int commandId;
		
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the exception
	 */
	public Exception getException() {
		
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(Exception exception) {
		
		this.exception = exception;
	}

	/**
	 * @return the commandId
	 */
	public int getCommandId() {
		return commandId;
	}

	/**
	 * @param commandId the commandId to set
	 */
	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}
	
	
}
