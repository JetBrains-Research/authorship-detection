/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: IMessageManager.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月30日 下午1:17:08
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms;

import javax.jms.Message;

import com.yeshj.pacman.jms.message.BaseMessage;

/**
 * TODO
 * @Class: IMessageManager
 * @author: zhangxinyu
 * @date: 2014年12月30日 下午1:17:08
 */
public interface IMessageManager {

	/**
	 * 
	 * 
	 * @Title: sendMessage
	 * @param msg
	 * @param dest
	 * @return: void
	 */
	<T extends BaseMessage> void sendMessage(T msg, QueueType dest) throws MQException;
	
	/**
	 * 
	 * 
	 * @Title: receiveMessage
	 * @param dest
	 * @return
	 * @return: T
	 */
	Message receiveMessage(QueueType dest) throws MQException;
	
	/**
	 * 
	 * 
	 * @Title: setMessageListener
	 * @param dest
	 * @return: void
	 */
	void setMessageListener(IMQListener listener, QueueType dest) throws MQException;
	
	/**
	 * 
	 * 
	 * @Title: createBuilder
	 * @return
	 * @return: IMessageBuilder
	 */
	IMessageBuilder createBuilder();
	
	/**
	 * 
	 * @return
	 */
	IMQFactory createFactory();
}
