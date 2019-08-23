package wyf.ytl.entity;
import static wyf.ytl.tool.ConstantUtil.*;

import java.io.Serializable;

/*
 * 本类表示将领，封装了将领的信息，如名称、职位、忠诚度，以及包括
 * 统御、武力、智力、敏捷、体力值等个人属性，还有等级和经验值、战绩等
 */
public class General implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2114236481246768478L;
	
	public General(){}
	
	private String name;//将领名称
	private int rank;//将领职位
	int loyalty;//将领忠诚度

	public int defend;//将领统御力
	public int power;//将领武力
	public int intelligence;//将领智力
	public int agility;//将领敏捷度
	private int strength;//将领的体力，打仗会减少
	public int maxStrength = 100;//将领体力的上限,随着升级会增加

	int level;//将领的等级
	int exp;//将领经验值
	
	public CityDrawable cityDrawable = null;//将领当前所在地城，如果跟随英雄则为NULL
	
	public General(String name, int rank, int loyalty, int defend, 
			int power, int intelligence,int agility,int strength,int level){
		this.name = name;
		this.rank = rank;
		this.loyalty = loyalty;
		this.defend = defend;
		this.power = power;
		this.intelligence = intelligence;
		this.agility = agility;
		this.strength = strength;
		this.level = level;
	}
	public int getMaxStrength() {
		return maxStrength;
	}
	public void setMaxStrength(int maxStrength) {
		this.maxStrength = maxStrength;
	}
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public int getDefend() {
		return defend;
	}
	public void setDefend(int defend) {
		this.defend = defend;
	}
	public int getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(int loyalty) {
		this.loyalty = loyalty;
	}
	public int getAgility() {
		return agility;
	}
	public void setAgility(int agility) {
		this.agility = agility;
	}
	
	public CityDrawable getCityDrawable(){//设置将领所在地城池
		return cityDrawable;
	}

	public String getRank() {
		return GENERAL_TITLE[rank];
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}