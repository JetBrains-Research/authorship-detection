/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: DefaultMQManager.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.impl
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月17日 下午4:53:37
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.util.InetAddressUtil;

import com.yeshj.pacman.jms.IMQListener;
import com.yeshj.pacman.jms.IMQFactory;
import com.yeshj.pacman.jms.IMQReceiver;
import com.yeshj.pacman.jms.IMQSender;
import com.yeshj.pacman.jms.MQException;
import com.yeshj.pacman.jms.QueueType;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.utils.StringHelper;

/**
 * TODO
 * @Class: DefaultMQManager
 * @author: zhangxinyu
 * @date: 2014年12月17日 下午4:53:37
 */
public class DefaultMQFactory implements IMQFactory{

	private final static ILog logger = LogFactory.getLog(DefaultMQFactory.class);
	private final static String PREFIX = "[MQFactory]";
	
	private ActiveMQConnectionFactory	mFactory;
	private Map<QueueType, String> 		map;
	private static String 				clientID = null; 
	
	private TopicConnection 	topicConnection;
	private TopicSession		topicSession;
	private TopicSubscriber 	subscriber;
	/**
	 * @Title:DefaultMQManager
	 */
	public DefaultMQFactory(String addr, Map<QueueType, String> map) {
		
		if (clientID == null) {
			try {
				clientID = InetAddressUtil.getLocalHostName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.map = map;
		
		mFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				addr);
		
		logger.info(PREFIX + " CREATED, addr:" + addr);
	}
	
	/** (non Javadoc)
	 * TODO
	 * @Title: getSender
	 * @param qtype
	 * @return
	 * @see com.yeshj.pacman.jms.IMQFactory#getSender(com.yeshj.pacman.jms.QueueType)
	 */
	@Override
	public IMQSender getSender(QueueType qtype) throws MQException{

		String destName = map.get(qtype);
		
		try {
			
			Connection con = mFactory.createConnection();
			con.start();
			IMQSender sender = null;
			
			if (qtype == QueueType.TOPIC_COMMAND)
				sender = new DefaultMQSender(con, destName, false); //topic sender.
			else
				sender = new DefaultMQSender(con, destName);
			
			logger.info(PREFIX + " Sender created![" + destName + "]"); 
			
			return sender;
		} catch (JMSException e) {
			
			throw new MQException("Can not getSender.", e);
		} //try			
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: getReceiver
	 * @param qtype
	 * @return
	 * @see com.yeshj.pacman.jms.IMQFactory#getReceiver(com.yeshj.pacman.jms.QueueType)
	 */
	@Override
	public IMQReceiver getReceiver(QueueType qtype) throws MQException{

		String destName = map.get(qtype);
		
		try {
			
			Connection con = mFactory.createConnection();
			con.start();			
			IMQReceiver receiver = new DefaultMQReceiver(con, destName);

			return receiver;
		} catch (JMSException e) {
			
			throw new MQException("Fail to get receiver!", e);
		}
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: setTopicListener
	 * @param queue type
	 * @param listener
	 * @throws MQException
	 * @see com.yeshj.pacman.jms.IMQFactory#setTopicListener(com.yeshj.pacman.jms.IMQListener)
	 */
	@Override
	public void setTopicListener(QueueType qtype, IMQListener listener) throws MQException {
		
		String destName = map.get(qtype);
		
		if (topicConnection != null) {
			closeTopicListener();
		}
		
		try {
			
			topicConnection = mFactory.createTopicConnection();

			topicConnection.setClientID(clientID + "_" + StringHelper.random());
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = topicSession.createTopic(destName);
			subscriber = topicSession.createDurableSubscriber(topic, "sub");
			subscriber.setMessageListener(listener);
			
			topicConnection.start();			
		} catch (JMSException e) {
			
			throw new MQException("Fail to listen to topic!", e);
		}
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: closeTopicListener
	 * @throws MQException
	 * @see com.yeshj.pacman.jms.IMQFactory#closeTopicListener()
	 */
	@Override
	public void closeTopicListener() throws MQException {

		if (subscriber != null) {
			try {
				
				subscriber.close();
				subscriber = null;
			} catch (JMSException e) {
				throw new MQException("fail to close topic subscriber.", e);
			}
		}
		
		if (topicSession != null) {
			try {
				
				topicSession.close();
				topicSession = null;
			} catch (JMSException e) {
				
				throw new MQException("fail to close topic session.", e);
			}
		}
		
		if (topicConnection != null) {
			try {

				topicConnection.close();
				topicConnection = null;
			} catch (JMSException e) {

				throw new MQException("fail to close topic connection.", e);
			}
		}
	}

	
}
