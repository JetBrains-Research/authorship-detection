/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: IMQClient.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月17日 下午2:46:57
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms;

import java.util.Map;


/**
 * JMS Sender interface.
 * 
 * @Class: IMQClient
 * @author: zhangxinyu
 * @date: 2014年12月17日 下午2:46:57
 */
public interface IMQSender {

	/**
	 * Sends the text format message.
	 * 
	 * @Title: sendTextMessage
	 * @param msg
	 * @return: void
	 */
	void sendTextMessage(String msg) throws MQException;
	
	/**
	 * Sends the text format messages one by one.
	 * 
	 * @Title: sendTextMessage
	 * @param msgs
	 * @throws MQException
	 * @return: void
	 */
	void sendTextMessage(String[] msgs) throws MQException;
	
	/**
	 * Sends the key-value data.
	 * 
	 * @Title: sendMapMessage
	 * @param map
	 * @throws MQException
	 * @return: void
	 */
	void sendMapMessage(Map<String, Object> map) throws MQException;

	/**
	 * Sends object via the class of object.
	 * 
	 * @Title: sendObject
	 * @param t
	 * @throws MQException
	 * @return: void
	 */
	<T> void sendObject(T t) throws MQException;
	
	/**
	 * Sends the bytes stream
	 * 
	 * @Title: sendStream
	 * @param buffer
	 * @return: void
	 */
	void sendStream(byte[] buffer) throws MQException;
	
	/**
	 * Gets the destination(Queue name or Topic name)
	 * 
	 * @Title: getDestName
	 * @return
	 * @return: String
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
