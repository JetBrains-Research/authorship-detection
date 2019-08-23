/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: TaskParamModel.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.model
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月8日 下午4:33:43
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.model;

import java.io.Serializable;

/**
 * TaskparamModel TODO:Insert POJO class of Comment in here.
 * @author CodingMouse's POJOGenerator
 * @version v1.0.0.1 2015-01-08 04:32:12
 */
public class TaskParamModel implements Serializable {

	/*
	 * Private Fields
	 */
	private static final long serialVersionUID = -1177954821L;
	private Integer pid;
	private Integer tid;
	private String pname;
	private String pvalue;

	/**
	 * Default Constructor
	 */
	public TaskParamModel() {
		super();
	}

	/**
	 * Full Constructor
	 * @param pid TODO:Insert Constructor method parameter pid of Comment in here.
	 * @param tid TODO:Insert Constructor method parameter tid of Comment in here.
	 * @param pname TODO:Insert Constructor method parameter pname of Comment in here.
	 * @param pvalue TODO:Insert Constructor method parameter pvalue of Comment in here.
	 */
	public TaskParamModel(
		Integer pid, 
		Integer tid, 
		String pname, 
		String pvalue) {
		super();
		this.pid = pid;
		this.tid = tid;
		this.pname = pname;
		this.pvalue = pvalue;
	}

	/**
	 * TODO:Insert Private field pid Getter method of Comment in here.
	 * @return pid TODO:Insert method return of Comment in here.
	 */
	public Integer getPid() {
		return this.pid;
	}

	/**
	 * TODO:Insert Private field pid Setter method of Comment in here.
	 * @param pid TODO:Insert method parameter pid of Comment in here.
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * TODO:Insert Private field tid Getter method of Comment in here.
	 * @return tid TODO:Insert method return of Comment in here.
	 */
	public Integer getTid() {
		return this.tid;
	}

	/**
	 * TODO:Insert Private field tid Setter method of Comment in here.
	 * @param tid TODO:Insert method parameter tid of Comment in here.
	 */
	public void setTid(Integer tid) {
		this.tid = tid;
	}

	/**
	 * TODO:Insert Private field pname Getter method of Comment in here.
	 * @return pname TODO:Insert method return of Comment in here.
	 */
	public String getPname() {
		return this.pname;
	}

	/**
	 * TODO:Insert Private field pname Setter method of Comment in here.
	 * @param pname TODO:Insert method parameter pname of Comment in here.
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * TODO:Insert Private field pvalue Getter method of Comment in here.
	 * @return pvalue TODO:Insert method return of Comment in here.
	 */
	public String getPvalue() {
		return this.pvalue;
	}

	/**
	 * TODO:Insert Private field pvalue Setter method of Comment in here.
	 * @param pvalue TODO:Insert method parameter pvalue of Comment in here.
	 */
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	/**
	 * The object's equivalence test method.
	 * @param otherObject Another object
	 * @return Whether the current object equal to another object.
	 */
	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (this.getClass() != otherObject.getClass()) return false;
		TaskParamModel other = (TaskParamModel) otherObject;
		return this.pid.equals(other.pid)
			&& this.tid.equals(other.tid)
			&& this.pname.equals(other.pname)
			&& this.pvalue.equals(other.pvalue);
	}

	/**
	 * The object's hash code generation method.
	 * @return TaskparamModel hash code
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.pid != null ? this.pid.hashCode() : 0);
		hash += (this.tid != null ? this.tid.hashCode() : 0);
		hash += (this.pname != null ? this.pname.hashCode() : 0);
		hash += (this.pvalue != null ? this.pvalue.hashCode() : 0);
		return hash;
	}

	/**
	 * The object's string representation method.
	 * @return TaskparamModel string representation
	 */
	@Override
	public String toString() {
		String content = "";
		content += "[ pid = " + this.pid;
		content += ", tid = " + this.tid;
		content += ", pname = " + this.pname;
		content += ", pvalue = " + this.pvalue + " ]";
		return content;
	}

}