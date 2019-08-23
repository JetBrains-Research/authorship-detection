/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: BaseCommand.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.model
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月18日 下午6:20:15
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.message;

import com.yeshj.pacman.annotation.Transmit;
import com.yeshj.pacman.jms.CommandType;

/**
 * Defines base command.
 * 
 * @Class: BaseCommand
 * @author: zhangxinyu
 * @date: 2014年12月18日 下午6:20:15
 */
public abstract class BaseMessage {

	@Transmit(key = "id")
	private int commandId;
	@Transmit(key = "free")
	private boolean free;	
	/**
	 * @return the commandType
	 */
	public int getCommandType() {
		return commandType;
	}

	/**
	 * @param commandType the commandType to set
	 */
	public void setCommandType(int commandType) {
		this.commandType = commandType;
	}

	@Transmit(key = "type")
	private int commandType;
	
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
	
	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free) {
		this.free = free;
	}
}
