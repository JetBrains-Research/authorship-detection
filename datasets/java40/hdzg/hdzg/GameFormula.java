package wyf.ytl.tool;

import wyf.ytl.entity.CityDrawable;
import wyf.ytl.entity.General;
import wyf.ytl.entity.Hero;

/*
 * �����ṩһϵ�еľ�̬��������Ҫ��һЩ��ʽ�ļ��㣬�����ľ���ã�������Ӯ����
 * �������ӵȵ�
 */
public class GameFormula{
	public static final int BASIC_STRENGTH_INCREMENT = 18;//Ӣ�۵�������������ֵ
	public static final int EXTRA_STRENGTH_INCREMENT = 2;//Ӣ�۵�������������ֵ���͵ȼ��й�
	public static final int FOOD_DECREASE_EACH = 8;//ÿ�������ĵ���ʳ
	//���㲢����Ӣ��Ӧ�����ӵ�����
	public static int getHeroStrengthIncrement(Hero hero){//����Ӣ�۵���״�������������Ӷ���
		int level = hero.level;
		int increment = BASIC_STRENGTH_INCREMENT+level*EXTRA_STRENGTH_INCREMENT;
		if(hero.strength+increment >hero.maxStrength){
			increment = hero.maxStrength-hero.strength;
		}
		return increment;
	}
	//���㲢���ؽ���Ӧ�����ӵ�����,�����㷨��������Խ��,�ظ�Խ��,�ȼ�Խ��,�ָ�Խ��,����ʱ�Ȳ�����
	public static int getGeneralStrengthIncrement(General general){
		//int level = general.getLevel();//��ý����ȼ�
		int strength = general.getStrength();//��ý�������
		int increment = BASIC_STRENGTH_INCREMENT;//���������
		if(strength + increment > general.maxStrength){//������ü���ô����Ѿ���ȫ�ָ�
			increment = general.maxStrength - strength;
		}
		return increment;
	}
	//���㲢�������ݵ�˥����
	public static int getFoodDecreaseEach(Hero hero){
		int level = hero.level;
		return FOOD_DECREASE_EACH+level;
	}
	//���㲢���ؼ��ܵ�����ֵ����û�ö������ӵȵ�
	public static int getSkillEearning(int proficiency,int basicEarning){
		return basicEarning*(proficiency);
	}
	
	//���㲢����һ���ǳصķ�����
	public static int getCityDefence(CityDrawable city){
		int defend =0;
		General g = city.guardGeneral.get(0);
		float gDefend = (g.defend*0.8f+g.intelligence*0.2f)/100.0f;
		defend = (int)(city.baseDefend*city.army*(1+gDefend)) + city.warTower*100;
		return defend;
	}
	//���㲢����һ���ǳصĹ�����
	public static int getCityAttack(CityDrawable city){
		int attack = 0;//������
		General g = city.getGuardGeneral().get(0);//����ؽ�����
		float gAttack = (g.power*0.7f+g.agility*0.2f+g.intelligence*0.1f)/100.0f;
		attack = (int)(city.baseAttack*city.getArmy()*(1+gAttack)) + city.warTank*100;
		return (int)attack;
	}
	//���㲢����Ӣ�۵Ĺ�����
	public static int getHeroAttack(Hero hero,General general){
		int attack = 0;//������
		float gAttack = (general.power*0.7f+general.agility*0.2f+general.intelligence*0.1f)/100.0f;//Ӣ�۵Ĺ����ӳ�
		attack = (int)((hero.basicAttack*hero.armyWithMe)*(1+gAttack));
		return attack;
	}
	//���㲢����Ӣ�۵ķ�����
	public static int getHeroDefence(Hero hero,General general){
		int defence = 0;
		float gDefend = (general.defend*0.8f+general.intelligence*0.2f)/100.0f;//Ӣ�۵ķ����ӳ�
		defence = (int)((hero.basicDefend*hero.armyWithMe)*(1+gDefend));
		return defence;
	}
}