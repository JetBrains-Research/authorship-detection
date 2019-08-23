/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: IClientFactory.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月17日 下午2:48:31
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms;

/**
 * @Description: Message Queue Manager
 * @Class: IClientFactory
 * @author: zhangxinyu
 * @date: 2014年12月17日 下午2:48:31
 */
public interface IMQFactory {
	
	/**
	 * Gets the specified message queue sender.
	 * 
	 * @Title: getSender
	 * @param: qtype
	 * @return: IMQSender
	 */
	IMQSender getSender(QueueType qtype) throws MQException;
	
	/**
	 * Gets the specified message queue receiver.
	 * 
	 * @Title: getReceiver
	 * @param qtype
	 * 
	 * @return: IMQReceiver
	 */
	IMQReceiver getReceiver(QueueType qtype) throws MQException;
	
	/**
	 * Sets the specified topic subscriber.
	 * 
	 * @Title: setTopicListener
	 * @param listener
	 * @throws MQException
	 * @return: void
	 */
	void setTopicListener(QueueType qtype, IMQListener listener) throws MQException;
	
	/**
	 * Closes the topic listener's connection.
	 * 
	 * @Title: closeTopicListener
	 * @throws MQException
	 * @return: void
	 */
	void closeTopicListener() throws MQException;
}
