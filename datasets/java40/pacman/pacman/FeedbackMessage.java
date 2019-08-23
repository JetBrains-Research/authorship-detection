/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: FeedbackMessage.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.message
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月2日 上午3:11:30
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.jms.message;

import com.yeshj.pacman.annotation.Transmit;

/**
 * @ClassName: FeedbackMessage
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月2日 上午3:11:30
 */
public class FeedbackMessage extends BaseMessage {

	@Transmit(key = "success")
	private boolean success = false;
	
	@Transmit(key = "msg")
	private String msg;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
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
