/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: JsonResult.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.controller
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月9日 上午1:58:01
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.controller;

/**
 * @ClassName: JsonResult
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月9日 上午1:58:01
 */
public class JsonResult {

	private int success;
	
	private String msg;

	/**
	 * @return the success
	 */
	public int getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(int success) {
		this.success = success;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
