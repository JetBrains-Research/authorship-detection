/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: SystemInfo.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月19日 下午12:54:49
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule;

import com.yeshj.pacman.annotation.Transmit;

/**
 * Defines the system info model.
 * 
 * @Class: SystemInfo
 * @author: zhangxinyu
 * @date: 2014年12月19日 下午12:54:49
 */
public class SystemInfo {

	@Transmit(key = "ip")
	private String ip;
	
	@Transmit(key = "user")
	private double cpuUser;
	
	@Transmit(key = "sys")
	private double cpuSys;
	
	@Transmit(key = "nice")
	private double cpuNice;
	
	@Transmit(key = "idle")
	private double cpuIdle;
	
	@Transmit(key = "total")
	private long memTotal;
	
	@Transmit(key = "used")
	private long memUsed;
	
	@Transmit(key = "free")
	private long memFree;
	
	@Transmit(key = "usage")
	private double memUsage;
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * @return the cpuUser
	 */
	public double getCpuUser() {
		return cpuUser;
	}
	/**
	 * @param cpuUser the cpuUser to set
	 */
	public void setCpuUser(double cpuUser) {
		this.cpuUser = cpuUser;
	}
	/**
	 * @return the cpuSys
	 */
	public double getCpuSys() {
		return cpuSys;
	}
	/**
	 * @param cpuSys the cpuSys to set
	 */
	public void setCpuSys(double cpuSys) {
		this.cpuSys = cpuSys;
	}
	/**
	 * @return the cpuNice
	 */
	public double getCpuNice() {
		return cpuNice;
	}
	/**
	 * @param cpuNice the cpuNice to set
	 */
	public void setCpuNice(double cpuNice) {
		this.cpuNice = cpuNice;
	}
	/**
	 * @return the cpuIdle
	 */
	public double getCpuIdle() {
		return cpuIdle;
	}
	/**
	 * @param cpuIdle the cpuIdle to set
	 */
	public void setCpuIdle(double cpuIdle) {
		this.cpuIdle = cpuIdle;
	}
	/**
	 * @return the memTotal
	 */
	public long getMemTotal() {
		return memTotal;
	}
	/**
	 * @param memTotal the memTotal to set
	 */
	public void setMemTotal(long memTotal) {
		this.memTotal = memTotal;
	}
	/**
	 * @return the memUsed
	 */
	public long getMemUsed() {
		return memUsed;
	}
	/**
	 * @param memUsed the memUsed to set
	 */
	public void setMemUsed(long memUsed) {
		this.memUsed = memUsed;
	}
	/**
	 * @return the memFree
	 */
	public long getMemFree() {
		return memFree;
	}
	/**
	 * @param memFree the memFree to set
	 */
	public void setMemFree(long memFree) {
		this.memFree = memFree;
	}
	/**
	 * @return the memUsage
	 */
	public double getMemUsage() {
		return memUsage;
	}
	/**
	 * @param memUsage the memUsage to set
	 */
	public void setMemUsage(double memUsage) {
		this.memUsage = memUsage;
	}
	
}
