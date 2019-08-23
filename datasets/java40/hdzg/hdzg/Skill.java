package wyf.ytl.entity;						//���������

import java.io.Serializable;			//���������


/*
 * ����Ϊ�����࣬ÿ������������һ�����ܣ����а������ܵ������ȣ�
 * ÿ���ͷ����ĵ���������ʱ�������ȣ��������ͼ�����ID����������
 * ��һ��use������ÿ���ͷŶ�����ã����ڼ������ú����������ȵȲ���
 */
public abstract class Skill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5878577464788782434L;
	int id;//����id��Ωһ��ʶһ������
	String name;//���ܵ����� 
	int proficiencyLevel;//����������
	int strengthCost;//ÿ���ͷ����ĵ�����
	int basicEarning;//����ĵ�λ�����ڼ���Ӣ�۵�������
	int tempProficiency;//��ʱ�����ȣ���һ���̶Ȼ����Ӽ���������
	int proficiencyToUpgrade;//����������������Ҫ����ʱ������
	int skillType;//�������ͣ�����0:����ܡ�1:ս�����ܡ�2:��ͨ����
	Hero hero;//����Ӣ�۵�����
	
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
	//������ÿ��ʹ��ǰ���ã����ݷ���Ӣ��Ӧ�õ����棬����������򷵻�-1;
	public abstract int calculateResult();
	
	//�������ͷż��ܣ������ĵ����ģ�ɨβ����
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