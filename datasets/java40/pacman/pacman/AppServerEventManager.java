/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: AppServerEventManager.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.event
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月30日 上午11:31:08
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.event;

/**
 * TODO
 * @Class: AppServerEventManager
 * @author: zhangxinyu
 * @date: 2014年12月30日 上午11:31:08
 */
public class AppServerEventManager extends EventManager<IAppSvrEventListener> {

	public final static int EVENT_SERVER_START = 0;
	public final static int EVENT_SERVER_SHUTDOWN = 1;
	public final static int EVENT_SERVER_HEARTBEAT = 2;
	
	private final static AppServerEventManager gInstance = new AppServerEventManager();
	/**
	 * @Title:AppServerEventManager
	 */
	private AppServerEventManager() {}
	
	public static AppServerEventManager getInstance() {
		
		return gInstance;
	}
	
	/** (non Javadoc)
	 * TODO
	 * @Title: fireEvent
	 * @param source
	 * @param params
	 * @see com.yeshj.pacman.event.EventManager#fireEvent(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public void fireEvent(Object source, Object... params) {
		
		if (params.length > 0 && params[0] != null) {
			
			notifyListeners(source, (Integer)params[0]);
		}
	}
	
	/**
	 * 
	 * 
	 * @Title: notifyListeners
	 * @param source
	 * @param typeCode
	 * @return: void
	 */
	private void notifyListeners(Object source, int typeCode) {
		
		for(IAppSvrEventListener listener : listeners) {
			
			switch (typeCode) {
			case EVENT_SERVER_START:
				listener.onAppSvrStarted(source);
				break;
			case EVENT_SERVER_SHUTDOWN:
				listener.onAppSvrShutdown(source);
				break;
			case EVENT_SERVER_HEARTBEAT:
				listener.onAppSvrHeartBeat(source);
				break;
			}
		}
	}
}
