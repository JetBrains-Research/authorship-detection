package com.newweb.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.newweb.model.base.Customer;
import com.newweb.model.base.LhjPrice;

@Entity
@Table(name="t_lhjpricecut")
public class LhjPriceCut {
	private int ID;
	private Customer customer;
	private LhjPrice lhjPrice;
	private double price;
	
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
	@JoinColumn(name="customerid")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@ManyToOne
	@JoinColumn(name="lhjpriceid")
	public LhjPrice getLhjPrice() {
		return lhjPrice;
	}
	public void setLhjPrice(LhjPrice lhjPrice) {
		this.lhjPrice = lhjPrice;
	}
	@Column
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
