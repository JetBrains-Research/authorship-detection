/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: IAppSvrEventListener.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.event
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月29日 下午4:29:37
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.event;

/**
 * TODO
 * @Class: IAppSvrEventListener
 * @author: zhangxinyu
 * @date: 2014年12月29日 下午4:29:37
 */
public interface IAppSvrEventListener extends IEventListener {

	/**
	 * Server started event.
	 * 
	 * @Title: onAppSvrStarted
	 * @param source
	 * @return: void
	 */
	void onAppSvrStarted(Object source);
	
	/**
	 * Server stopped event.
	 * 
	 * @Title: onAppSvrShutdown
	 * @param source
	 * @return: void
	 */
	void onAppSvrShutdown(Object source);
	
	/**
	 * Sever heartbeat event.
	 * 
	 * @Title: onAppSvrHeartBeat
	 * @param source
	 * @return: void
	 */
	void onAppSvrHeartBeat(Object source);
	
	/**
	 * Server crash event.
	 * 
	 * @Title: onAppSvrCrash
	 * @param source
	 * @return: void
	 */
	void onAppSvrCrash(Object source);
}
