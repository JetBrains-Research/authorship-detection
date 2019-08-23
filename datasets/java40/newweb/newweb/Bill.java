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
@Table(name="t_bill")
public class Bill {

	private String ID;
	private Customer customer;
	private String createDate;
	private String createTime;
	
	private String status;
	private double account;
	private double receivableMoney;
	private double realIncomeMoney;
	private String lastModifyDate;
	private String lastModifyTime;
	private Set<Order> order;
	private String remark;
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Order> getOrder() {
		return order;
	}
	public void setOrder(Set<Order> order) {
		this.order = order;
	}
	@Column
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	
	@Column
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	@Column
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Column
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	@Column
	public double getReceivableMoney() {
		return receivableMoney;
	}
	public void setReceivableMoney(double receivableMoney) {
		this.receivableMoney = receivableMoney;
	}
	
	@Column
	public double getRealIncomeMoney() {
		return realIncomeMoney;
	}
	public void setRealIncomeMoney(double realIncomeMoney) {
		this.realIncomeMoney = realIncomeMoney;
	}
	
	@Column
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
}
