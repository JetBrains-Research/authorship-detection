package com.newweb.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.newweb.model.base.Bxg;

@Entity
@Table(name="t_order_bxgfdws")
public class OrderBxgFdw {
	
	private int ID;
	private Order order;
	private double fdwHeight;
	private double fdwWidth;
	private int fdwCount;
	private Bxg fdwFgType;
	private int fdwFgCount;
	private Bxg fdwYgType;
	private int fdwYgCount;
	private String remark;
	private String operation; //数据被操作的方式(create:第一次的订单提交创建,add:订单修改时添加的,update:订单修改时被更新的)
	
	@Column
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column
	public int getFdwCount() {
		return fdwCount;
	}
	public void setFdwCount(int fdwCount) {
		this.fdwCount = fdwCount;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	@ManyToOne
	@JoinColumn(name="orderID")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Column
	public double getFdwHeight() {
		return fdwHeight;
	}
	public void setFdwHeight(double fdwHeight) {
		this.fdwHeight = fdwHeight;
	}
	
	@Column
	public double getFdwWidth() {
		return fdwWidth;
	}
	public void setFdwWidth(double fdwWidth) {
		this.fdwWidth = fdwWidth;
	}
	
	@ManyToOne
	@JoinColumn(name="fgbxgid")
	public Bxg getFdwFgType() {
		return fdwFgType;
	}
	public void setFdwFgType(Bxg fdwFgType) {
		this.fdwFgType = fdwFgType;
	}
	
	@Column
	public int getFdwFgCount() {
		return fdwFgCount;
	}
	public void setFdwFgCount(int fdwFgCount) {
		this.fdwFgCount = fdwFgCount;
	}
	
	@ManyToOne
	@JoinColumn(name="ygbxgid")
	public Bxg getFdwYgType() {
		return fdwYgType;
	}
	public void setFdwYgType(Bxg fdwYgType) {
		this.fdwYgType = fdwYgType;
	}
	
	@Column
	public int getFdwYgCount() {
		return fdwYgCount;
	}
	public void setFdwYgCount(int fdwYgCount) {
		this.fdwYgCount = fdwYgCount;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
