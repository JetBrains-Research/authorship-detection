/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: LogImpl.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.log
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月28日 下午9:46:56
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.log;

import org.apache.commons.logging.Log;

/**
 * @ClassName: LogImpl
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月28日 下午9:46:56
 */
public class LogImpl implements ILog {

	private Log logger = null;
	
	protected LogImpl(Log log) {
		
		this.logger = log;
	}
	
	/* (non-Javadoc)
	 * @see com.yeshj.pacman.log.ILog#Info(java.lang.String)
	 */
	@Override
	public void info(String msg) {
		
		if (logger.isInfoEnabled())
			logger.info(msg);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.log.ILog#Info(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void info(String msg, Throwable t) {
		
		if (logger.isInfoEnabled())
			logger.info(msg, t);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.log.ILog#Warning(java.lang.String)
	 */
	@Override
	public void warn(String msg) {
		
		if (logger.isWarnEnabled())
			logger.warn(msg);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.log.ILog#Warning(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String msg, Throwable t) {
		
		if (logger.isWarnEnabled())
			logger.warn(msg, t);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.log.ILog#Error(java.lang.String)
	 */
	@Override
	public void error(String msg) {
		
		if (logger.isErrorEnabled())
			logger.error(msg);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.log.ILog#Error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String msg, Throwable t) {
		
		if (logger.isErrorEnabled())
			logger.error(msg, t);
	}

}
