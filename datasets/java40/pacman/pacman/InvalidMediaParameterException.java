/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: InvalidMediaParameterException.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 上午11:06:43
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.utils;

/**
 * TODO
 * @Class: InvalidMediaParameterException
 * @author: zhangxinyu
 * @date: 2015年1月5日 上午11:06:43
 */
public class InvalidMediaParameterException extends Exception {

	/**
	 * TODO
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 */
	private static final long serialVersionUID = 8085282891944555364L;

	public InvalidMediaParameterException() {
		super();
	}
	
	public InvalidMediaParameterException(String msg) {
		super(msg);
	}
	
	public InvalidMediaParameterException(String msg, Exception innerException) {
		super(msg, innerException);
	}
}
