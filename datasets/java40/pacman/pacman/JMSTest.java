/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: JMSTest.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.test
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月19日 上午2:07:26
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.jms.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yeshj.pacman.jms.IMQListener;
import com.yeshj.pacman.jms.IMQFactory;
import com.yeshj.pacman.jms.IMQReceiver;
import com.yeshj.pacman.jms.IMQSender;
import com.yeshj.pacman.jms.MQException;
import com.yeshj.pacman.jms.QueueType;
import com.yeshj.pacman.jms.impl.DefaultMQFactory;

/**
 * @ClassName: JMSTest
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月19日 上午2:07:26
 */
public class JMSTest {

	private IMQFactory mgr;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Map<QueueType, String> map = new HashMap<QueueType, String>();
		map.put(QueueType.QUEUE_AUDIO, "test_q");
		map.put(QueueType.TOPIC_COMMAND, "test_t");
		map.put(QueueType.QUEUE_FEEDBACK, "test_q");
		map.put(QueueType.QUEUE_HEARTBEAT, "test_q");
		map.put(QueueType.QUEUE_VIDEO, "test_q");
		mgr = new DefaultMQFactory("tcp://localhost:61616", map);
		
		IMQReceiver receiver = mgr.getReceiver(QueueType.QUEUE_AUDIO);
		while(receiver.tryGetMessage(100));
		receiver.Close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	String 				verifyTxt	= null;
	byte[] 				verifyBuf	= null;
	Map<String, Object> verifyMap 	= null;
	Object 				verifyObj	= null;
	Lock 				locker		= new ReentrantLock();
	Condition			condition	= locker.newCondition();
	boolean				block		= true;
	
	class MockListener implements IMQListener {

		@Override
		public void onTextMessage(String msg) {
			verifyTxt = msg;
		}

		@Override
		public void onStreamMessage(byte[] buffer) {
			verifyBuf = buffer;
		}

		@Override
		public void onMapMessage(Map<String, Object> map) {
			verifyMap = map;
		}

		@Override
		public void onObjectMessage(Object obj) {
			verifyObj = obj;
		}
		
		/** (non Javadoc)
		 * TODO
		 * @Title: onMessage
		 * @param arg0
		 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
		 */
		@Override
		public void onMessage(Message msg) {
			
			locker.lock();
			
			try {
				if (msg instanceof TextMessage) {
					try {
						
						onTextMessage(((TextMessage) msg).getText());
					} catch (JMSException e) {
						
						onTextMessage("Error occurs:" + e.getMessage());
					}
				}
				condition.signal();
			} finally {
				locker.unlock();
			}
		}
	}
	
	@Test
	public void testTransformTxt() throws MQException {
		
		verifyTxt = null;
		IMQSender sender = null;
		sender = mgr.getSender(QueueType.QUEUE_AUDIO);
		assertNotNull(sender);

		IMQReceiver receiver = mgr.getReceiver(QueueType.QUEUE_AUDIO);
		assertNotNull(receiver);
		receiver.addListener(new MockListener());
		
		sender.sendTextMessage("This is a test!");
		
		assertTrue(receiver.tryGetMessage());
		assertTrue("This is a test!".equals(verifyTxt));
		
		assertFalse(receiver.tryGetMessage());
		sender.Close();
		receiver.Close();
	}

	@Test
	public void testTransformStream() throws MQException {
		
		byte[] buf = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,};
		
		IMQSender sender = mgr.getSender(QueueType.QUEUE_AUDIO);
		assertNotNull(sender);

		IMQReceiver receiver = mgr.getReceiver(QueueType.QUEUE_AUDIO);
		assertNotNull(receiver);
		receiver.addListener(new MockListener());
		
		sender.sendStream(buf);
		sender.Close();
		
		assertTrue(receiver.tryGetMessage());
		assertTrue(Arrays.equals(buf, verifyBuf));
		receiver.Close();
	}
	
	@Test
	public void testTransformMap() throws MQException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", 1);
		map.put("key2", 1.1f);
		map.put("key3", "Morning, guys!");
		map.put("key4", new ArrayList());
		
		IMQSender sender = mgr.getSender(QueueType.QUEUE_AUDIO);
		assertNotNull(sender);
		
		IMQReceiver receiver = mgr.getReceiver(QueueType.QUEUE_AUDIO);
		assertNotNull(receiver);
		receiver.addListener(new MockListener());
		
		sender.sendMapMessage(map);

		assertTrue(receiver.tryGetMessage());
		assertNotNull(verifyMap);
		assertEquals(1, verifyMap.get("key1"));
		assertEquals(1.1f, verifyMap.get("key2"));
		assertEquals("Morning, guys!", verifyMap.get("key3"));
		assertTrue(verifyMap.get("key4") instanceof ArrayList);
		
		sender.Close();
		receiver.Close();
	}
	
	@Test
	public void testTransformObject() throws MQException {
		
		MockModel model = new MockModel();
		model.setId(123);
		model.setName("lolita");
		model.setSalary(1000.0d);
		
		IMQSender sender = mgr.getSender(QueueType.QUEUE_AUDIO);
		assertNotNull(sender);
		IMQReceiver receiver = mgr.getReceiver(QueueType.QUEUE_AUDIO);
		assertNotNull(receiver);
		receiver.addListener(new MockListener());
		
		sender.sendObject(model);
		
		assertTrue(receiver.tryGetMessage());
		
		assertNotNull(verifyObj);
		assertTrue(verifyObj instanceof MockModel);
		
		MockModel mm = (MockModel)verifyObj;
		assertEquals(123, mm.getId());
		assertEquals("lolita", mm.getName());
		assertNotEquals(1000.0d, mm.getSalary());
		
		sender.Close();
		receiver.Close();
	}
	
	@Test
	public void testTopicReceiver() throws MQException {

		IMQSender sender = mgr.getSender(QueueType.TOPIC_COMMAND);
		
		locker.lock();
		try {
			mgr.setTopicListener(QueueType.TOPIC_COMMAND, new MockListener());			
			sender.sendTextMessage("a quick fox jump over a small sheep.");
			condition.await();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			
			if (sender != null) {
				sender.Close();
			}
			
			mgr.closeTopicListener();
			
			locker.unlock();
		}
		assertEquals("a quick fox jump over a small sheep.", verifyTxt);
	}
}
