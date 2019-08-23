/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: MessageManager.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.impl
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月30日 下午3:02:19
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;

import org.springframework.context.ApplicationContextException;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.jms.IMQFactory;
import com.yeshj.pacman.jms.IMQListener;
import com.yeshj.pacman.jms.IMQReceiver;
import com.yeshj.pacman.jms.IMQSender;
import com.yeshj.pacman.jms.IMessageBuilder;
import com.yeshj.pacman.jms.IMessageManager;
import com.yeshj.pacman.jms.MQException;
import com.yeshj.pacman.jms.QueueType;
import com.yeshj.pacman.jms.message.BaseMessage;

/**
 * TODO
 * @Class: MessageManager
 * @author: zhangxinyu
 * @date: 2014年12月30日 下午3:02:19
 */
public class MessageManager implements IMessageManager {

	private IMQFactory mqFactory = null;

	public MessageManager() {

		this.mqFactory = createFactory();
	}
	
	/** (non Javadoc)
	 * TODO
	 * @Title: sendMessage
	 * @param msg
	 * @param dest
	 * @throws MQException 
	 * @see com.yeshj.pacman.jms.IMessageManager#sendMessage(com.yeshj.pacman.jms.message.BaseMessage, com.yeshj.pacman.jms.QueueType)
	 */
	@Override
	public <T extends BaseMessage> void sendMessage(T msg, QueueType dest) throws MQException {

		IMQSender sender = null;
		try {
			
			sender = mqFactory.getSender(dest);
			sender.sendObject(msg);
		} finally {
			
			sender.Close();
		}
		
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: receiveMessage
	 * @param dest
	 * @return
	 * @see com.yeshj.pacman.jms.IMessageManager#receiveMessage(com.yeshj.pacman.jms.QueueType)
	 */
	@Override
	public Message receiveMessage(QueueType dest) throws MQException{

		Message result = null;
		
		IMQReceiver receiver = null;
		try {
			
			receiver = mqFactory.getReceiver(dest);
			result = receiver.getMessage(AppConfig.getInstance().getJmsLoadTimeout());
		} finally {
			if (receiver != null)
				receiver.Close();
		}
		
		return result;
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: createBuilder
	 * @return
	 * @see com.yeshj.pacman.jms.IMessageManager#createBuilder()
	 */
	@Override
	public IMessageBuilder createBuilder() {
		
		return new DefaultMessageBuilder();
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageManager#createFactory()
	 */
	@Override
	public IMQFactory createFactory() throws ApplicationContextException{
		
		if (AppConfig.getInstance() == null) {
			throw new ApplicationContextException("AppConfig is null!");
			//AppConfig.getFactory()
		}

		Map<QueueType, String> map = new HashMap<QueueType, String>();
		map.put(QueueType.QUEUE_AUDIO, 		AppConfig.getInstance().getAudioQueueName());
		map.put(QueueType.QUEUE_VIDEO, 		AppConfig.getInstance().getVideoQueueName());
		map.put(QueueType.QUEUE_FEEDBACK, 		AppConfig.getInstance().getFeedbackQueueName());
		map.put(QueueType.QUEUE_HEARTBEAT, 	AppConfig.getInstance().getHeartbeatQueueName());
		map.put(QueueType.TOPIC_COMMAND, 		AppConfig.getInstance().getCommandTopicName());
		
		return new DefaultMQFactory(AppConfig.getInstance().getJmsAddress(), map);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMessageManager#setMessageListener(com.yeshj.pacman.jms.IMQListener, com.yeshj.pacman.jms.QueueType)
	 */
	@Override
	public void setMessageListener(IMQListener listener, QueueType dest)
			throws MQException {

		if (mqFactory != null) {
			
			if (dest == QueueType.TOPIC_COMMAND) {
				
				mqFactory.closeTopicListener();
				mqFactory.setTopicListener(dest, listener);
			} else {
				
				if (MessagePoller.getMessageManager() == null)
					MessagePoller.setMessageManager(this);
				MessagePoller.addListener(listener);
			}
		}
	}

}
