package com.newweb.model.business;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.newweb.model.base.Customer;

@Entity
@Table(name="t_order")
public class Order {
	private String ID;
	private Customer customer;
	private String createDate;
	private String createTime;
	private String orderCreateType;
	private String acceptStatus;
	private String doneStatus;
	private Bill bill;
	private String remark;
	private Set<OrderLhjWinProp> orderLhjWinProps;
	private Set<OrderSmall> orderSmalls;
	private Set<OrderBxg> orderBxgs;
	private Set<OrderLhjWeight> orderLhjWeights;
	private Set<OrderBxgFdw> bxgFdws;
	private Set<OrderOther> orderOthers;
	private Set<OrderPrice> orderPrices;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderPrice> getOrderPrices() {
		return orderPrices;
	}
	public void setOrderPrices(Set<OrderPrice> orderPrices) {
		this.orderPrices = orderPrices;
	}
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderOther> getOrderOthers() {
		return orderOthers;
	}
	public void setOrderOthers(Set<OrderOther> orderOthers) {
		this.orderOthers = orderOthers;
	}
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderBxgFdw> getBxgFdws() {
		return bxgFdws;
	}
	public void setBxgFdws(Set<OrderBxgFdw> bxgFdws) {
		this.bxgFdws = bxgFdws;
	}
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderLhjWeight> getOrderLhjWeights() {
		return orderLhjWeights;
	}
	public void setOrderLhjWeights(Set<OrderLhjWeight> orderLhjWeights) {
		this.orderLhjWeights = orderLhjWeights;
	}
	@Column
	public String getOrderCreateType() {
		return orderCreateType;
	}
	public void setOrderCreateType(String orderCreateType) {
		this.orderCreateType = orderCreateType;
	}
	@Column
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderBxg> getOrderBxgs() {
		return orderBxgs;
	}
	public void setOrderBxgs(Set<OrderBxg> orderBxgs) {
		this.orderBxgs = orderBxgs;
	}
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderSmall> getOrderSmalls() {
		return orderSmalls;
	}
	public void setOrderSmalls(Set<OrderSmall> orderSmalls) {
		this.orderSmalls = orderSmalls;
	}
	
	@Id
	@Column(name="id")
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	@ManyToOne
	@JoinColumn(name="customerID")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderLhjWinProp> getOrderLhjWinProps() {
		return orderLhjWinProps;
	}
	public void setOrderLhjWinProps(Set<OrderLhjWinProp> orderLhjWinProps) {
		this.orderLhjWinProps = orderLhjWinProps;
	}
	
	@Column
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Column
	public String getAcceptStatus() {
		return acceptStatus;
	}
	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}
	
	@Column
	public String getDoneStatus() {
		return doneStatus;
	}
	public void setDoneStatus(String doneStatus) {
		this.doneStatus = doneStatus;
	}
	
	@ManyToOne
	@JoinColumn(name="billID")
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
