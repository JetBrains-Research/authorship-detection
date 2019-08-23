/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: IMQReceiver.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月17日 下午2:50:11
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms;

import javax.jms.Message;

import com.yeshj.pacman.jms.message.BaseMessage;

/**
 * @Description: TODO
 * @Class: IMQReceiver
 * @author: zhangxinyu
 * @date: 2014年12月17日 下午2:50:11
 */
public interface IMQReceiver {

	/**
	 * trys to get the message inside queue or topic.
	 * 
	 * @return
	 * @throws MQException
	 */
	boolean tryGetMessage() throws MQException;
	
	/**
	 * trys to get the message inside queue or topic with timeout.
	 * 
	 * @param milliseconds
	 * @return
	 * @throws MQException
	 */
	boolean tryGetMessage(int milliseconds) throws MQException;

	/**
	 * Gets the message inside queue or topic immediately.
	 * 
	 * @return
	 * @throws MQException
	 */
	Message getMessage() throws MQException;
	
	/**
	 * Gets the message inside queue or topic immediately with timeout.
	 * 
	 * @param milliseconds
	 * @return
	 * @throws MQException
	 */
	Message getMessage(int milliseconds) throws MQException;
	
	/**
	 * Sets the message listener.
	 * 
	 * @param listener
	 */
	void addListener(IMQListener listener);
	
	/**
	 * Gets the destination(Queue name or Topic name)
	 * 
	 * @return
	 */
	String getDestName();
	
	/**
	 * Determines sending to queue or topic.
	 * 
	 * @return Yes or no
	 */
	boolean isQueue();
	
	/**
	 * Close the connection.
	 * 
	 * @Title: Close
	 * @return: void
	 */
	void Close() throws MQException;
}
