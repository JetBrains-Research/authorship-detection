/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: TaskModel.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.model
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月8日 下午4:32:46
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * TaskModel 
 * @author CodingMouse's POJOGenerator
 * @version v1.0.0.1 2015-01-08 05:16:24
 */
public class TaskModel implements Serializable {

	/*
	 * Private Fields
	 */
	private static final long serialVersionUID = -1759501103L;
	private Integer 	tid;
	private Integer 	cid;
	private Integer 	lid;
	private String	 	file;
	private Timestamp 	begin;
	private Timestamp 	end;
	private Integer 	type;
	private Integer 	status;
	private String 		err;
	private String 		guid;
	private boolean 	free;
	
	/**
	 * Default Constructor
	 */
	public TaskModel() {
		super();
	}

	/**
	 * Full Constructor
	 * @param tid 
	 * @param cid 
	 * @param lid 
	 * @param begin 
	 * @param end 
	 * @param type 
	 * @param status 
	 * @param err
	 * @param guid 
	 */
	public TaskModel(
		Integer tid, 
		Integer cid, 
		Integer lid,
		String guid,
		String  file,
		Timestamp begin, 
		Timestamp end, 
		Integer type, 
		Integer status, 
		String err,
		boolean free) {
		
		super();
		this.tid = tid;
		this.cid = cid;
		this.lid = lid;
		this.file = file;
		this.begin = begin;
		this.end = end;
		this.type = type;
		this.status = status;
		this.err = err;
		this.guid = guid;
		this.free = free;
	}

	/**
	 * Private field tid Getter method.
	 * @return tid 
	 */
	public Integer getTid() {
		return this.tid;
	}

	/**
	 * Private field tid Setter method.
	 * @param tid 
	 */
	public void setTid(Integer tid) {
		this.tid = tid;
	}

	/**
	 * Private field cid Getter method.
	 * @return cid 
	 */
	public Integer getCid() {
		return this.cid;
	}

	/**
	 * Private field cid Setter method.
	 * @param cid 
	 */
	public void setCid(Integer cid) {
		this.cid = cid;
	}

	/**
	 * Private field lid Getter method.
	 * @return lid 
	 */
	public Integer getLid() {
		return this.lid;
	}

	/**
	 * Private field lid Setter method.
	 * @param lid 
	 */
	public void setLid(Integer lid) {
		this.lid = lid;
	}

	/**
	 * Private field begin Getter method.
	 * @return begin 
	 */
	public Timestamp getBegin() {
		return this.begin;
	}

	/**
	 * Private field begin Setter method.
	 * @param begin 
	 */
	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	/**
	 * Private field end Getter method.
	 * @return end 
	 */
	public Timestamp getEnd() {
		return this.end;
	}

	/**
	 * Private field end Setter method.
	 * @param end 
	 */
	public void setEnd(Timestamp end) {
		this.end = end;
	}

	/**
	 * Private field type Getter method.
	 * @return isct 
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * Private field isct Setter method.
	 * @param isct 
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Private field status Getter method.
	 * @return status 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * Private field status Setter method.
	 * @param status 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Private field err Getter method.
	 * @return err 
	 */
	public String getErr() {
		return this.err;
	}

	/**
	 * Private field err Setter method.
	 * @param err 
	 */
	public void setErr(String err) {
		this.err = err;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
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
		TaskModel other = (TaskModel) otherObject;
		return this.tid.equals(other.tid)
			&& this.cid.equals(other.cid)
			&& this.lid.equals(other.lid)
			&& this.begin.equals(other.begin)
			&& this.end.equals(other.end)
			&& this.type.equals(other.type)
			&& this.status.equals(other.status)
			&& this.err.equals(other.err);
	}

	/**
	 * The object's hash code generation method.
	 * @return TaskModel hash code
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.tid != null ? this.tid.hashCode() : 0);
		hash += (this.cid != null ? this.cid.hashCode() : 0);
		hash += (this.lid != null ? this.lid.hashCode() : 0);
		hash += (this.file != null ? this.file.hashCode() : 0);
		hash += (this.begin != null ? this.begin.hashCode() : 0);
		hash += (this.end != null ? this.end.hashCode() : 0);
		hash += (this.type != null ? this.type.hashCode() : 0);
		hash += (this.status != null ? this.status.hashCode() : 0);
		hash += (this.err != null ? this.err.hashCode() : 0);
		return hash;
	}

	/**
	 * The object's string representation method.
	 * @return TaskModel string representation
	 */
	@Override
	public String toString() {
		String content = "";
		content += "[ tid = " + this.tid;
		content += ", cid = " + this.cid;
		content += ", lid = " + this.lid;
		content += ", file = " + this.file;
		content += ", begin = " + this.begin;
		content += ", end = " + this.end;
		content += ", type = " + this.type;
		content += ", status = " + this.status;
		content += ", err = " + this.err + " ]";
		return content;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * @param free the free to set
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

}