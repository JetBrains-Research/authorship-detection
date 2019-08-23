/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: MessageHelper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月3日 上午1:38:23
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

import java.lang.reflect.Field;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.lang3.ArrayUtils;

import com.yeshj.pacman.annotation.Transmit;

/**
 * @ClassName: MessageHelper
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月3日 上午1:38:23
 */
public class MessageHelper {

	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectMessage(MapMessage map) throws Exception {
		

		String className = map.getStringProperty("mq.clazz");
		Class<?> clazz = Class.forName(className);
		Field[] fields = clazz.getDeclaredFields();
		if (Object.class != clazz.getSuperclass()) {
			fields = ArrayUtils.addAll(fields, clazz.getSuperclass().getDeclaredFields());
		}
		Object obj = clazz.newInstance();
		
		for(Field field : fields) {
			
			Transmit transmit = field.getAnnotation(Transmit.class);
			if (transmit != null) {
				field.setAccessible(true);
				field.set(obj, map.getObject(transmit.key()));
			}
		}
		
		return (T) obj;
	}
	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws JMSException
	 */
	public static String getStringMessage(Message message) throws JMSException {
		
		String result = null;
		if (message instanceof TextMessage) {
			result = ((TextMessage)message).getText();
		}
		
		return result;
	}
}
