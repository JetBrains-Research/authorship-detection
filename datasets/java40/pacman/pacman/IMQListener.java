/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: IMQListener.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月18日 下午10:38:22
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.jms;

import java.util.Map;

import javax.jms.MessageListener;

/**
 * @ClassName: IMQListener
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月18日 下午10:38:22
 */
public interface IMQListener extends MessageListener {

	/**
	 * Text message arrived event.
	 * 
	 * @param qtype
	 * @return
	 */
	void onTextMessage(String msg);
	
	/**
	 * Stream message arrived event.
	 * 
	 * @param buffer
	 */
	void onStreamMessage(byte[] buffer);
	
	/**
	 * Map message arrived event.
	 * 
	 * @param map
	 */
	void onMapMessage(Map<String, Object> map);
	
	/**
	 * A generic object arrived event.
	 * 
	 * @param clazz
	 * @param obj
	 */
	void onObjectMessage(Object obj);
}
