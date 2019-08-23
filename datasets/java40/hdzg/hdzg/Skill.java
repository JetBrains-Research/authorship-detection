package wyf.ytl.entity;						//声明包语句

import java.io.Serializable;			//引入相关类


/*
 * 该类为技能类，每个该类对象代表一个技能，其中包含技能的熟练度，
 * 每次释放消耗的体力，临时的熟练度，技能类型及技能ID，技能类中
 * 有一个use方法，每次释放都会调用，用于计算所得和增加熟练度等操作
 */
public abstract class Skill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5878577464788782434L;
	int id;//技能id，惟一标识一个技能
	String name;//技能的名字 
	int proficiencyLevel;//技能熟练度
	int strengthCost;//每次释放消耗的体力
	int basicEarning;//收入的单位，用于计算英雄的总收入
	int tempProficiency;//临时熟练度，到一定程度会增加技能熟练度
	int proficiencyToUpgrade;//技能熟练度升级需要的临时熟练度
	int skillType;//技能类型，包括0:生活技能、1:战斗技能、2:普通技能
	Hero hero;//所属英雄的引用
	
	public Skill(){}
	
	public Skill(int id,String name,int basicEarning,int skillType,Hero hero){
		this.id = id;
		this.name = name;
		this.basicEarning = basicEarning;
		this.skillType = skillType;
		this.hero = hero;
		proficiencyLevel = 1;
		tempProficiency = 0;
		proficiencyToUpgrade = 100;
	}
	//方法：每次使用前调用，根据返回英雄应得的收益，如果无收益则返回-1;
	public abstract int calculateResult();
	
	//方法：释放技能，该消耗的消耗，扫尾工作
	public abstract void useSkill(int skillEarning);
		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProficiencyLevel() {
		return proficiencyLevel;
	}

	public void setProficiencyLevel(int proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

	public int getStrengthCost() {
		return strengthCost;
	}

	public void setStrengthCost(int strengthCost) {
		this.strengthCost = strengthCost;
	}

	public int getTempProficiency() {
		return tempProficiency;
	}

	public void setTempProficiency(int tempProficiency) {
		this.tempProficiency = tempProficiency;
	}

	public int getSkillType() {
		return skillType;
	}

	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}
}