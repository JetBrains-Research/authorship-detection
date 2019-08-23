/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: MockModel.java
 * @Prject: libMsgLayer
 * @Package: com.yeshj.pacman.jms.test
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月19日 上午10:40:02
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.jms.test;

import com.yeshj.pacman.annotation.Transmit;

/**
 * TODO
 * @Class: MockModel
 * @author: zhangxinyu
 * @date: 2014年12月19日 上午10:40:02
 */
public class MockModel {
	@Transmit(key="id")
	private int	id;
	@Transmit(key="name")
	private String	name;
	private double salary;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
}