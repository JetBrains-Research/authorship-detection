/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: Wrapper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月4日 下午11:49:26
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

/**
 * @ClassName: Wrapper
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月4日 下午11:49:26
 */
public class Wrapper<T> {
	
	private T data;
	
	private boolean empty;
	
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		
		this.data = data;
		empty = this.data == null;
	}

	/**
	 * @return the empty
	 */
	public boolean isEmpty() {
		
		return empty;
	}
}
