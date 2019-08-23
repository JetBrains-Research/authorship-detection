package com.newweb.model.base;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.newweb.model.business.OrderSmall;
import com.newweb.model.business.SmallPriceCut;

@Entity
@Table(name="t_small")
public class Small implements Cloneable{

	private int ID;
	private String name;
	private String type;
	private double lsprice;
	private double pfprice;
	private Supplier supplier;
	private String norms;
	private String unit;
	private int buycount;
	private Set<OrderSmall> orderSmalls;
	private Set<SmallPriceCut> smallPriceCuts;
	private boolean valid;
	
	@Column(columnDefinition="boolean default true")
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	@OneToMany(mappedBy = "small", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<SmallPriceCut> getSmallPriceCuts() {
		return smallPriceCuts;
	}
	public void setSmallPriceCuts(Set<SmallPriceCut> smallPriceCuts) {
		this.smallPriceCuts = smallPriceCuts;
	}
	@OneToMany(mappedBy = "small", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<OrderSmall> getOrderSmalls() {
		return orderSmalls;
	}
	public void setOrderSmalls(Set<OrderSmall> orderSmalls) {
		this.orderSmalls = orderSmalls;
	}
	@Column(columnDefinition="int default 0 ")
	public int getBuycount() {
		return buycount;
	}
	public void setBuycount(int buycount) {
		this.buycount = buycount;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(columnDefinition="int default 0 ")
	public double getLsprice() {
		return lsprice;
	}
	public void setLsprice(double lsprice) {
		this.lsprice = lsprice;
	}
	@Column(columnDefinition="int default 0 ")
	public double getPfprice() {
		return pfprice;
	}
	public void setPfprice(double pfprice) {
		this.pfprice = pfprice;
	}
	@ManyToOne
	@JoinColumn(name="supplierID")
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@Column
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	
	@Column
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Object clone(){
		try {
			Small small = (Small) super.clone();
			small.supplier = (Supplier) supplier.clone();
			return small;
		} catch (Exception e) {
			return null;
		}
	}
	
}
