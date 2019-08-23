/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: ShellCommand.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 上午10:25:04
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.utils;

/**
 * TODO
 * @Class: ShellCommand
 * @author: zhangxinyu
 * @date: 2015年1月5日 上午10:25:04
 */
public class ShellCommand {

	private StringBuffer cmdBuffer;
	
	private ShellCommand(String cmd) {
		cmdBuffer = new StringBuffer(cmd);
	}
	
	public static ShellCommand create(String cmd) {
		
		return new ShellCommand(cmd);
	}
	
	public <T> ShellCommand add(T opt) {
		
		cmdBuffer.append(" " + String.valueOf(opt));
		return this;
	}
	
	public <T> ShellCommand add(String key, T opt) {
		
		cmdBuffer.append(String.format(" %s %s", key, String.valueOf(opt)));
		return this;
	}
	
	/** (non Javadoc)
	 * TODO
	 * @Title: toString
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return cmdBuffer.toString();
	}
}
