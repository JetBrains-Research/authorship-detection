/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: DefaultMQReceiver.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.impl
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月18日 下午11:08:23
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.jms.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.commons.lang3.StringUtils;

import com.yeshj.pacman.annotation.Transmit;
import com.yeshj.pacman.jms.IMQListener;
import com.yeshj.pacman.jms.IMQReceiver;
import com.yeshj.pacman.jms.MQException;
import com.yeshj.pacman.jms.message.BaseMessage;

/**
 * @ClassName: DefaultMQReceiver
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月18日 下午11:08:23
 */
public class DefaultMQReceiver implements IMQReceiver {
	
	private Connection			mConnection;
	private Session 			mSession;
	private MessageConsumer		mConsumer;
	private Destination			mDestination;
	
	private boolean			mIsQueue;
	private MQEventSource		mSource;
	
	protected DefaultMQReceiver(Connection 	connection,
							       String		destName) 
			throws JMSException {
		
		this(connection, destName, true);
	}
	
	protected DefaultMQReceiver(Connection	connection,
							       String 		destName,
							       boolean		isQueue) 
			throws JMSException {

		mConnection		= connection;
		mIsQueue		= isQueue;
		mSource			= new MQEventSource();
		
		mSession		= mConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		if (mIsQueue)
			mDestination 	= mSession.createQueue(destName);
		else
			mDestination	= mSession.createTopic(destName);
		
		mConsumer		= mSession.createConsumer(mDestination);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#tryGetMessage()
	 */
	@Override
	public boolean tryGetMessage() throws MQException {
		
		return tryGetMessage(0);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#tryGetMessage(int)
	 */
	@Override
	public boolean tryGetMessage(int milliseconds) throws MQException {
		
		boolean result 	= false;
		Message message 	= null;
		try {
			
			if (milliseconds < 10) {
				message = mConsumer.receive(10);
			} else {
				
				message = mConsumer.receive(milliseconds);
			}
			
			result = message == null ? false : true;
		} catch (JMSException e) {
			
			throw new MQException("Fail to receive message!", e);
		}
		
		if (result)
			mSource.fireMessageEvent(message);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#setListener(com.yeshj.pacman.jms.IMQListener)
	 */
	@Override
	public void addListener(IMQListener listener) {
		
		mSource.addListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#getDestName()
	 */
	@Override
	public String getDestName() {
	
		try {
			if (mIsQueue)
				return ((Queue)mDestination).getQueueName();
			else
				return ((Topic)mDestination).getTopicName();
		} catch (JMSException e) {
			
			e.printStackTrace();
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#isQueue()
	 */
	@Override
	public boolean isQueue() {
		
		return mIsQueue;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#Close()
	 */
	@Override
	public void Close() throws MQException {
		
		try {
			
			if (mSession != null) {
				mSession.close();
			}
			
			if (mConnection != null) {
				mConnection.close();
			}
		} catch (JMSException e) {
			
			throw new MQException("Fail to close receiver!", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#getMessage()
	 */
	@Override
	public Message getMessage() throws MQException {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQReceiver#getMessage(int)
	 */
	@Override
	public Message getMessage(int milliseconds)
			throws MQException {
		
		Message message 	= null;
		try {
			
			if (milliseconds < 10) {
				
				message = mConsumer.receive();
			} else {
				
				message = mConsumer.receive(milliseconds);
			}
			
		} catch (JMSException e) {
			
			throw new MQException("Fail to receive message!", e);
		}
		
		return message;
	}
}

class MQEventSource {

	private Vector<IMQListener> repository;
	
	public MQEventSource() {
		
		repository = new Vector<IMQListener>();
	}
	
	public void addListener(IMQListener listener) {
		
		repository.add(listener);
	}
	
	public void fireMessageEvent(Message message) throws MQException {
		
		int type 	= 0;
		String txt 	= null;
		byte[] buf	= null;
		Map<String, Object> map = null;
		String className = null;
		
		try {
			if (message instanceof TextMessage) {
				
				type 	= 1;
				txt 	= ((TextMessage)message).getText();
			} else if (message instanceof BytesMessage) {
				
				type  		= 2;
				BytesMessage bm = (BytesMessage)message; 
				ByteBuffer bb	= ByteBuffer.allocate((int)bm.getBodyLength());
				buf = bb.array();
				bm.readBytes(buf);
				
			} else if (message instanceof MapMessage) {
				
				className = message.getStringProperty("mq.clazz");
				
				if (StringUtils.isEmpty(className))
					type	= 3;
				else
					type	= 4;
				
				map 	= new HashMap<String, Object>();
				MapMessage mm = ((MapMessage)message);
				
				@SuppressWarnings("rawtypes")
				Enumeration en = mm.getMapNames();
				while(en.hasMoreElements()) {
					
					String key = (String) en.nextElement();
					map.put(key, mm.getObject(key));
				}
			}
		} catch (JMSException e) {
			
			throw new MQException("Fail to recognize the message type!", e);
		}
		
		for(IMQListener listener : repository) {
			switch(type) {
			case 1:
				listener.onTextMessage(txt);
				break;
			case 2:
				listener.onStreamMessage(buf);
				break;
			case 3:
				listener.onMapMessage(map);
				break;
			case 4:
				
				try {
					
					Class<?> clazz = Class.forName(className);
					Field[] fields = clazz.getDeclaredFields();
					Object obj = clazz.newInstance();
					
					for(Field field : fields) {
						
						Transmit transmit = field.getAnnotation(Transmit.class);
						if (transmit != null) {
							field.setAccessible(true);
							field.set(obj, map.get(transmit.key()));
						}
					}
					listener.onObjectMessage(obj);
				} catch (Exception e) {
					
					throw new MQException("Fail to cast map to object!", e);
				}
				break;
			default:
				//fall over
			}
		}
	}
}
