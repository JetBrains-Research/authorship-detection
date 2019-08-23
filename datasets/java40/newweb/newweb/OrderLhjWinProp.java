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
import com.newweb.model.base.TypeForBrand;

@Entity
@Table(name="t_order_lhjwinprop")
public class OrderLhjWinProp {

	private int ID;
	private Order order;
	private MaterialBrand materialBrand;
	private TypeForBrand typeForBrand;
	private double winHeight;
	private double winWidth;
	private int winCount;
	private String shType;
	private double nmHeight;
	private int isHasZZ;
	private int isHasCircle;
	private int circleCount;
	private String winType;
	private int fourLinkPartZZCount;
	private String glassType;
	private String remark;
	private String windowsBuyType;
	private String ytDirection;
	private String operation; //数据被操作的方式(create:第一次的订单提交创建,add:订单修改时添加的,update:订单修改时被更新的)
	
	@Column
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column
	public String getYtDirection() {
		return ytDirection;
	}
	public void setYtDirection(String ytDirection) {
		this.ytDirection = ytDirection;
	}
	@Column
	public String getWindowsBuyType() {
		return windowsBuyType;
	}
	public void setWindowsBuyType(String windowsBuyType) {
		this.windowsBuyType = windowsBuyType;
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
	
	@ManyToOne
	@JoinColumn(name="orderID")
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne
	@JoinColumn(name="lhjBrandID")
	public MaterialBrand getMaterialBrand() {
		return materialBrand;
	}
	public void setMaterialBrand(MaterialBrand materialBrand) {
		this.materialBrand = materialBrand;
	}
	
	@ManyToOne
	@JoinColumn(name="lhjTypeForBrandID")
	public TypeForBrand getTypeForBrand() {
		return typeForBrand;
	}
	public void setTypeForBrand(TypeForBrand typeForBrand) {
		this.typeForBrand = typeForBrand;
	}
	
	@Column
	public double getWinHeight() {
		return winHeight;
	}
	public void setWinHeight(double winHeight) {
		this.winHeight = winHeight;
	}
	
	@Column
	public double getWinWidth() {
		return winWidth;
	}
	public void setWinWidth(double winWidth) {
		this.winWidth = winWidth;
	}
	
	@Column
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	
	@Column
	public String getShType() {
		return shType;
	}
	public void setShType(String shType) {
		this.shType = shType;
	}
	
	@Column
	public double getNmHeight() {
		return nmHeight;
	}
	public void setNmHeight(double nmHeight) {
		this.nmHeight = nmHeight;
	}
	
	@Column
	public int getIsHasZZ() {
		return isHasZZ;
	}
	public void setIsHasZZ(int isHasZZ) {
		this.isHasZZ = isHasZZ;
	}
	
	@Column
	public int getIsHasCircle() {
		return isHasCircle;
	}
	public void setIsHasCircle(int isHasCircle) {
		this.isHasCircle = isHasCircle;
	}
	
	@Column
	public int getCircleCount() {
		return circleCount;
	}
	public void setCircleCount(int circleCount) {
		this.circleCount = circleCount;
	}
	
	@Column
	public String getWinType() {
		return winType;
	}
	public void setWinType(String winType) {
		this.winType = winType;
	}
	
	@Column
	public int getFourLinkPartZZCount() {
		return fourLinkPartZZCount;
	}
	public void setFourLinkPartZZCount(int fourLinkPartZZCount) {
		this.fourLinkPartZZCount = fourLinkPartZZCount;
	}
	
	@Column
	public String getGlassType() {
		return glassType;
	}
	public void setGlassType(String glassType) {
		this.glassType = glassType;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
