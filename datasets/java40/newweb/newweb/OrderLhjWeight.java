package com.newweb.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.newweb.model.base.MaterialBrand;

@Entity
@Table(name="t_order_lhjweight")
public class OrderLhjWeight {
	
	private int ID;
	private Order order;
	private MaterialBrand materialBrand;
	private double weight;//重量
	
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
	@JoinColumn(name="materialbrandid")
	public MaterialBrand getMaterialBrand() {
		return materialBrand;
	}
	public void setMaterialBrand(MaterialBrand materialBrand) {
		this.materialBrand = materialBrand;
	}
	@Column
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public int hashCode() {
		return materialBrand.getID();
	}
	@Override
	public boolean equals(Object obj) {
		OrderLhjWeight weight = (OrderLhjWeight) obj;
		if(materialBrand.getID() == weight.getMaterialBrand().getID())
			return true;
		return false;
	}
	
	
	
	
}
