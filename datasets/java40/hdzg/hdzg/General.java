package wyf.ytl.entity;
import static wyf.ytl.tool.ConstantUtil.*;

import java.io.Serializable;

/*
 * �����ʾ���죬��װ�˽������Ϣ�������ơ�ְλ���ҳ϶ȣ��Լ�����
 * ͳ�������������������ݡ�����ֵ�ȸ������ԣ����еȼ��;���ֵ��ս����
 */
public class General implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2114236481246768478L;
	
	public General(){}
	
	private String name;//��������
	private int rank;//����ְλ
	int loyalty;//�����ҳ϶�

	public int defend;//����ͳ����
	public int power;//��������
	public int intelligence;//��������
	public int agility;//�������ݶ�
	private int strength;//��������������̻����
	public int maxStrength = 100;//��������������,��������������

	int level;//����ĵȼ�
	int exp;//���쾭��ֵ
	
	public CityDrawable cityDrawable = null;//���쵱ǰ���ڵسǣ��������Ӣ����ΪNULL
	
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
	
	public CityDrawable getCityDrawable(){//���ý������ڵسǳ�
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