/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: MessagePoller.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.impl
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月13日 上午10:52:04
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.yeshj.pacman.annotation.Transmit;
import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.jms.IMQListener;
import com.yeshj.pacman.jms.IMQReceiver;
import com.yeshj.pacman.jms.IMessageManager;
import com.yeshj.pacman.jms.MQException;
import com.yeshj.pacman.jms.QueueType;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * TODO
 * 
 * @Class: MessagePoller
 * @author: zhangxinyu
 * @date: 2015年1月13日 上午10:52:04
 */
public class MessagePoller extends TimerTask {

	private final static ILog logger = LogFactory.getLog(MessagePoller.class);
	private final static String PREFIX = "[MessagePoller]";
	
	private static List<IMQListener> gListeners = new ArrayList<IMQListener>();

	public static void addListener(IMQListener listener) {

		synchronized (gListeners) {
			gListeners.add(listener);
		}
	}

	private static IMessageManager gMsgManager = null;

	public static void setMessageManager(IMessageManager mgr) {
		gMsgManager = mgr;
	}

	public static IMessageManager getMessageManager() {
		
		return gMsgManager;
	}
	
	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: run
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {

		if (gMsgManager == null) {
			
			//logger.warn(PREFIX + " Empty manager, ignored!");
			return;
		}
		Message msg = null;
		try {

			msg = gMsgManager.receiveMessage(QueueType.QUEUE_FEEDBACK);
			if (msg == null) {
				
				//logger.warn(PREFIX + " No message feedback, ignored!");
				return;
			}
			
			fireMessageEvent(msg);
		} catch (MQException e) {
			logger.error("receiver message error!", e);
		}
	}

	private void fireMessageEvent(Message message) throws MQException {

		int type = 0;
		String txt = null;
		byte[] buf = null;
		Map<String, Object> map = null;
		String className = null;

		try {
			if (message instanceof TextMessage) {

				type = 1;
				txt = ((TextMessage) message).getText();
				logger.info(PREFIX + " Got text message![" + txt + "]");
			} else if (message instanceof BytesMessage) {

				type = 2;
				BytesMessage bm = (BytesMessage) message;
				ByteBuffer bb = ByteBuffer.allocate((int) bm.getBodyLength());
				buf = bb.array();
				bm.readBytes(buf);
				logger.info(PREFIX + " Got stream message![" + bm.getJMSMessageID() + "]");
			} else if (message instanceof MapMessage) {

				className = message.getStringProperty("mq.clazz");

				if (StringUtils.isEmpty(className))
					type = 3;
				else
					type = 4;

				map = new HashMap<String, Object>();
				MapMessage mm = ((MapMessage) message);

				@SuppressWarnings("rawtypes")
				Enumeration en = mm.getMapNames();
				while (en.hasMoreElements()) {

					String key = (String) en.nextElement();
					map.put(key, mm.getObject(key));
				}
			}
		} catch (JMSException e) {

			throw new MQException("Fail to recognize the message type!", e);
		}

		synchronized (gListeners) {
			for (IMQListener listener : gListeners) {
				switch (type) {
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
						if (Object.class != clazz.getSuperclass()) {
							fields = ArrayUtils.addAll(fields, clazz.getSuperclass().getDeclaredFields());
						}
						Object obj = clazz.newInstance();

						for (Field field : fields) {

							Transmit transmit = field
									.getAnnotation(Transmit.class);
							if (transmit != null) {
								field.setAccessible(true);
								field.set(obj, map.get(transmit.key()));
							}
						}
						logger.info(PREFIX + " Got object message![" + obj.toString() + "]");
						listener.onObjectMessage(obj);
					} catch (Exception e) {

						throw new MQException("Fail to cast map to object!", e);
					}
					break;
				default:
					// fall over
				}
			}
		}
	}
}
