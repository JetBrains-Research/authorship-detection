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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.newweb.model.business.Lhj;
import com.newweb.model.business.OrderLhjWeight;

@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="t_materialbrand")
public class MaterialBrand implements Cloneable{

	private int ID;
	private String name;
	private Supplier supplier;
	private String type;
	private String py;
	private Set<TypeForBrand> typeForBrands;
	private Set<Bxg> bxgs;
	private Set<Lhj> lhjs;
	private Set<OrderLhjWeight> orderLhjs;
	private Set<LhjPrice> lhjPrices;
	private boolean valid;
	
	@Column(columnDefinition="boolean default true")
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@OneToMany(mappedBy = "materialBrand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<LhjPrice> getLhjPrices() {
		return lhjPrices;
	}
	public void setLhjPrices(Set<LhjPrice> lhjPrices) {
		this.lhjPrices = lhjPrices;
	}
	@OneToMany(mappedBy = "materialBrand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<OrderLhjWeight> getOrderLhjs() {
		return orderLhjs;
	}
	public void setOrderLhjs(Set<OrderLhjWeight> orderLhjs) {
		this.orderLhjs = orderLhjs;
	}
	@OneToMany(mappedBy = "materialBrand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<Lhj> getLhjs() {
		return lhjs;
	}
	public void setLhjs(Set<Lhj> lhjs) {
		this.lhjs = lhjs;
	}
	@OneToMany(mappedBy = "materialBrand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<Bxg> getBxgs() {
		return bxgs;
	}
	public void setBxgs(Set<Bxg> bxgs) {
		this.bxgs = bxgs;
	}
	@OneToMany(mappedBy = "materialBrand", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<TypeForBrand> getTypeForBrands() {
		return typeForBrands;
	}
	public void setTypeForBrands(Set<TypeForBrand> typeForBrands) {
		this.typeForBrands = typeForBrands;
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
	
	@ManyToOne
	@JoinColumn(name="supplierID")
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	
	@Override
	public Object clone(){
		try {
			MaterialBrand m = (MaterialBrand) super.clone();
			m.supplier = (Supplier) supplier.clone();
			return m;
		} catch (Exception e) {
			return null;
		}
	}
}
