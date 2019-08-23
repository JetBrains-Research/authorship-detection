package wyf.ytl.tool;

import wyf.ytl.entity.CityDrawable;
import wyf.ytl.entity.General;
import wyf.ytl.entity.Hero;

/*
 * 该类提供一系列的静态方法，主要是一些公式的计算，如果伐木所得，攻城输赢计算
 * 体力增加等等
 */
public class GameFormula{
	public static final int BASIC_STRENGTH_INCREMENT = 18;//英雄的体力基础增加值
	public static final int EXTRA_STRENGTH_INCREMENT = 2;//英雄的体力额外增加值，和等级有关
	public static final int FOOD_DECREASE_EACH = 8;//每个人消耗的粮食
	//计算并返回英雄应该增加的体力
	public static int getHeroStrengthIncrement(Hero hero){//根据英雄的现状计算体力该增加多少
		int level = hero.level;
		int increment = BASIC_STRENGTH_INCREMENT+level*EXTRA_STRENGTH_INCREMENT;
		if(hero.strength+increment >hero.maxStrength){
			increment = hero.maxStrength-hero.strength;
		}
		return increment;
	}
	//计算并返回将领应该增加的体力,基本算法就是体力越低,回复越快,等级越高,恢复越慢,但暂时先不这样
	public static int getGeneralStrengthIncrement(General general){
		//int level = general.getLevel();//获得将军等级
		int strength = general.getStrength();//获得将军体力
		int increment = BASIC_STRENGTH_INCREMENT;//计算出增量
		if(strength + increment > general.maxStrength){//如果不用加那么多就已经完全恢复
			increment = general.maxStrength - strength;
		}
		return increment;
	}
	//计算并返回粮草的衰减数
	public static int getFoodDecreaseEach(Hero hero){
		int level = hero.level;
		return FOOD_DECREASE_EACH+level;
	}
	//计算并返回技能的收益值，如该获得多少麦子等等
	public static int getSkillEearning(int proficiency,int basicEarning){
		return basicEarning*(proficiency);
	}
	
	//计算并返回一座城池的防御力
	public static int getCityDefence(CityDrawable city){
		int defend =0;
		General g = city.guardGeneral.get(0);
		float gDefend = (g.defend*0.8f+g.intelligence*0.2f)/100.0f;
		defend = (int)(city.baseDefend*city.army*(1+gDefend)) + city.warTower*100;
		return defend;
	}
	//计算并返回一座城池的攻击力
	public static int getCityAttack(CityDrawable city){
		int attack = 0;//攻击力
		General g = city.getGuardGeneral().get(0);//获得守将对象
		float gAttack = (g.power*0.7f+g.agility*0.2f+g.intelligence*0.1f)/100.0f;
		attack = (int)(city.baseAttack*city.getArmy()*(1+gAttack)) + city.warTank*100;
		return (int)attack;
	}
	//计算并返回英雄的攻击力
	public static int getHeroAttack(Hero hero,General general){
		int attack = 0;//攻击力
		float gAttack = (general.power*0.7f+general.agility*0.2f+general.intelligence*0.1f)/100.0f;//英雄的攻击加成
		attack = (int)((hero.basicAttack*hero.armyWithMe)*(1+gAttack));
		return attack;
	}
	//计算并返回英雄的防御力
	public static int getHeroDefence(Hero hero,General general){
		int defence = 0;
		float gDefend = (general.defend*0.8f+general.intelligence*0.2f)/100.0f;//英雄的防御加成
		defence = (int)((hero.basicDefend*hero.armyWithMe)*(1+gDefend));
		return defence;
	}
}