/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: StepExecuteException.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月15日 上午12:07:12
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule;

/**
 * @ClassName: StepExecuteException
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月15日 上午12:07:12
 */
public class StepExecuteException extends Exception {

	private static final long serialVersionUID = -1655148019297631813L;

	public StepExecuteException() { super(); }
	
	public StepExecuteException(String msg) { super(msg); }
	
	public StepExecuteException(String msg, Exception innerException) { super(msg, innerException); }
}
