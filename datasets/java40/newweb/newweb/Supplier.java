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

@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="t_supplier")
public class Supplier implements Cloneable{

	private int ID;
	private String name;
	private String place;
	private String contact;
	private Set<MaterialBrand> materialBrands;
	private Set<Bxg> bxgs;
	private Set<Small> smalls;
	private boolean valid;
	
	@Column(columnDefinition="boolean default true")
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<Bxg> getBxgs() {
		return bxgs;
	}
	public void setBxgs(Set<Bxg> bxgs) {
		this.bxgs = bxgs;
	}
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<Small> getSmalls() {
		return smalls;
	}
	public void setSmalls(Set<Small> smalls) {
		this.smalls = smalls;
	}
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public Set<MaterialBrand> getMaterialBrands() {
		return materialBrands;
	}
	public void setMaterialBrands(Set<MaterialBrand> materialBrands) {
		this.materialBrands = materialBrands;
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	@Column
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
