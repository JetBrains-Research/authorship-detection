package com.newweb.model.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.newweb.model.base.MaterialBrand;

/**
 * 铝材表
 * @author qianlong
 *
 */
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name="t_lhj")
public class Lhj {
	
	private int ID;
	private String name;//型材名称(如边封)
	private String norms;//规格
	private MaterialBrand materialBrand;//所属材料品牌ID
	
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
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	@ManyToOne
	@JoinColumn(name="materialbrandid")
	public MaterialBrand getMaterialBrand() {
		return materialBrand;
	}
	public void setMaterialBrand(MaterialBrand materialBrand) {
		this.materialBrand = materialBrand;
	}
	
	
}
