package com.newweb.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.newweb.model.base.Small;

@Entity
@Table(name="t_order_smallgoods")
public class OrderSmall {
	
	private int ID;
	private Order order;
	private Small small;
	private double count;
	private String operation; //数据被操作的方式(create:第一次的订单提交创建,add:订单修改时添加的,update:订单修改时被更新的)
	
	@Column
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
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
	
	@ManyToOne
	@JoinColumn(name="smallID")
	public Small getSmall() {
		return small;
	}
	public void setSmall(Small small) {
		this.small = small;
	}
	
	@Column
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	
	@Override
	public boolean equals(Object obj) {
		OrderSmall o = (OrderSmall) obj;
		if(small.getID() == o.getSmall().getID())
			return true;
		return false;			
	}
	
	@Override
	public int hashCode() {
		return small.getID();
	}
	
	
}
