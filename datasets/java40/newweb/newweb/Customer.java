package com.newweb.model.base;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.newweb.model.business.Bill;
import com.newweb.model.business.BxgPriceCut;
import com.newweb.model.business.LhjPriceCut;
import com.newweb.model.business.Order;
import com.newweb.model.business.SmallPriceCut;

@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="t_customer")
public class Customer {

	private int ID;
	private String name;
	private String contact;
	private String level;
	private String py;
	private Set<Order> orders;
	private Set<Bill> bills;
	private Set<BxgPriceCut> bxgPriceCuts;
	private Set<LhjPriceCut> lhjPriceCuts;
	private Set<SmallPriceCut> smallPriceCuts;
	private boolean valid;
	
	@Column(columnDefinition="boolean default true")
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<SmallPriceCut> getSmallPriceCuts() {
		return smallPriceCuts;
	}
	public void setSmallPriceCuts(Set<SmallPriceCut> smallPriceCuts) {
		this.smallPriceCuts = smallPriceCuts;
	}
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<LhjPriceCut> getLhjPriceCuts() {
		return lhjPriceCuts;
	}
	public void setLhjPriceCuts(Set<LhjPriceCut> lhjPriceCuts) {
		this.lhjPriceCuts = lhjPriceCuts;
	}
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<BxgPriceCut> getBxgPriceCuts() {
		return bxgPriceCuts;
	}
	public void setBxgPriceCuts(Set<BxgPriceCut> bxgPriceCuts) {
		this.bxgPriceCuts = bxgPriceCuts;
	}
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Bill> getBills() {
		return bills;
	}
	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}
	@OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
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
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	@Column
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	
}
