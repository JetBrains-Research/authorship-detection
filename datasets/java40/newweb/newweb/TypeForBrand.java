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

import com.newweb.model.business.OrderLhjWinProp;

@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="t_typeforbrand")
public class TypeForBrand {

	private int ID;
	private String name;
	private MaterialBrand materialBrand;
	private boolean valid;
	private Set<OrderLhjWinProp> orderLhjWinProps;
	
	@OneToMany(mappedBy = "typeForBrand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<OrderLhjWinProp> getOrderLhjWinProps() {
		return orderLhjWinProps;
	}
	public void setOrderLhjWinProps(Set<OrderLhjWinProp> orderLhjWinProps) {
		this.orderLhjWinProps = orderLhjWinProps;
	}
	@Column(columnDefinition="boolean default true")
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
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
	@JoinColumn(name="materialbrandID")
	public MaterialBrand getMaterialBrand() {
		return materialBrand;
	}
	public void setMaterialBrand(MaterialBrand materialBrand) {
		this.materialBrand = materialBrand;
	}
	
}
