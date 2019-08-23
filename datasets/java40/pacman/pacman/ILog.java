/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: ILog.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.log
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月28日 下午9:43:17
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.log;

/**
 * @ClassName: ILog
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月28日 下午9:43:17
 */
public interface ILog {

	/**
	 * info.
	 * 
	 * @param msg
	 */
	void info(String msg);
	
	/**
	 * info.
	 * 
	 * @param msg
	 * @param t
	 */
	void info(String msg, Throwable t);
	
	/**
	 * warning.
	 * 
	 * @param msg
	 */
	void warn(String msg);
	
	/**
	 * warning.
	 * 
	 * @param msg
	 * @param t
	 */
	void warn(String msg, Throwable t);
	
	/**
	 * error.
	 * 
	 * @param msg
	 */
	void error(String msg);
	
	/**
	 * error.
	 * 
	 * @param msg
	 * @param t
	 */
	void error(String msg, Throwable t);
}
