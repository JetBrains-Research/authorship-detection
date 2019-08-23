package wyf.ytl.entity;
/*
 * 该类继承自线程类，主要负责英雄后台数据的定时修改，如每隔一段时间增加英雄的体力
 * 每个一段时间减少英雄的粮草，为科研线程
 */
import static wyf.ytl.tool.ConstantUtil.*;

import java.util.ArrayList;

import wyf.ytl.view.FoodAlert;
import wyf.ytl.view.GameView;
import wyf.ytl.view.PlainAlert;
import wyf.ytl.view.WarAlert;

public class HeroBackDataThread extends Thread{

	Hero hero;//英雄的引用
	public boolean flag;//线程是否执行标志位
	int sleepSpan = 1000000;//休眠时间
	int foodCount = 0;//执行5循环才修改粮草
	int warCount = 0;//执行10次循环才增加有几率爆发战争
	CityDrawable ignoredCity;//被玩家忽略的粮草危机城市
	CityDrawable emptyCity;//从粮草危机城市变成了空城
	Research completedResearch;//存放完成的研究项目

	//构造器
	public HeroBackDataThread(Hero hero){
		super.setName("==HeroBackDataThread");
		this.hero = hero;
		flag = true;
	}
	//线程执行方法
	public void run(){
		while(flag){
			//粮食衰减
			foodCount++;
			if(foodCount == 5){//满足条件就进行粮食衰减
				foodCount = 0;
				hero.setFood((int)(hero.getFood()*0.95));
				ArrayList<CityDrawable> cityList = hero.getCityList();
				for(CityDrawable city:cityList){
					city.food -= (int)(city.food*0.15f);
					if(city.food <= MIN_FOOD){//如果城中粮草小于某个值就报警
						if(ignoredCity == city){//如果之前已经警报过了，那就城池分崩离析吧
							GameView gv = hero.father;
							city.setBackToInit();//恢复城市的信息到默认
							
							String alertMessage = "由于你没有及时输送粮草，"+city.getCityName()+"由于粮草危机已经" +
									"不攻自破，将领和老百姓已经归顺了"+COUNTRY_NAME[city.getCountry()]+"，不再属于你了。";
							PlainAlert pa = new PlainAlert(gv, alertMessage, GameView.dialogBack, GameView.dialogButton);
							hero.father.setCurrentGameAlert(pa);
							emptyCity = city;
						}
						else{//如果是第一次出现粮草危机
							FoodAlert fa = new FoodAlert(hero.father, city, GameView.dialogBack, GameView.dialogButton);
							hero.father.setCurrentGameAlert(fa);
							ignoredCity = city;//记录下来
						}
					}
				}
				if(emptyCity != null){//删掉因粮草危机而造成的空城
					hero.cityList.remove(emptyCity);
					hero.father.allCityDrawable.add(emptyCity);
					emptyCity = null;
				}				
				foodCount = 0;
			}
			//科研制造
			if(hero.researchList.size() != 0){
				for(Research research:hero.researchList){
					if(research.makeProgress()){//如果科研项目全部完工
						completedResearch = research;//记录下需要删除的项目
						if(research.researchProject == 0){//战车研究成功
							hero.warTank += research.getResearchNumber();
						}
						else {//箭垛研究成功
							hero.warTower += research.getResearchNumber();
						}
						GameView gv = hero.father;
						String alertMessage = research.getName()+"为你建造的"+
						(research.researchProject==0?"战车":"箭垛")
						+"已经完成，可以在城池管理中查看或划拨给所需要的城池。";								
						PlainAlert pa = new PlainAlert(gv, alertMessage, GameView.dialogBack,GameView.dialogButton);
						hero.father.setCurrentGameAlert(pa);
					}
				}
				if(completedResearch != null){//删除完成的项目
					hero.researchList.remove(completedResearch);
				}
			}
			//大军来袭
			warCount++;
			if(warCount == 10){
				if(Math.random() < 0.5){//一定几率触发
					if(hero.cityList.size() != 0){//有城可攻才行
						ArrayList<CityDrawable> alEnemy = hero.father.allCityDrawable;
						int index = (int)(Math.random()*alEnemy.size());
						CityDrawable  city = alEnemy.get(index);
						ArrayList<CityDrawable> alHero = hero.getCityList();
						int index2 = (int)(Math.random()*alHero.size());
						CityDrawable city2 = alHero.get(index2);
						GameView gv = hero.father;
						WarAlert wa= new WarAlert(gv, GameView.dialogBack, GameView.dialogButton, city, city2);
						gv.setCurrentGameAlert(wa);					
					}					
				}
				warCount = 0;
			}
			//检查英雄身上的钱/粮/兵是不是太多了
			boolean tempFlag = false;
			if(hero.armyWithMe>=800000){//身上的军队太多
				int a= 80000+(int)(Math.random()*20000);
				hero.armyWithMe -=a;
				
				if(hero.food > 1000){
					hero.food -= (int)(Math.random()*500);
				}	
				if(hero.totalMoney > 1000){
					hero.totalMoney -=(int)(Math.random()*500);
				}
				tempFlag = true;
			}
			if(hero.totalMoney >=800000){//身上的钱太多
				hero.armyWithMe -= 80000*(int)(Math.random()*20000);
				if(hero.armyWithMe > 1000){
					hero.armyWithMe -= (int)(Math.random()*500);
				}	
				if(hero.food > 1000){
					hero.food -=(int)(Math.random()*500);
				}
				tempFlag = true;
			}
			if(hero.food >= 800000){//身上粮食太多
				hero.food -= 80000*(int)(Math.random()*20000);
				if(hero.armyWithMe > 1000){
					hero.armyWithMe -= (int)(Math.random()*500);
				}	
				if(hero.totalMoney > 1000){
					hero.totalMoney -=(int)(Math.random()*500);
				}
				tempFlag = true;
			}
			if(tempFlag){
				String alertMessage="你的大军遭遇了一场沙尘暴，损失了一些粮草和金钱，有一些士兵也不见了，唉，还是不要带太多东西在自己身上啊！";
				PlainAlert pa = new PlainAlert(hero.father, alertMessage, GameView.dialogBack, GameView.dialogButton);
				hero.father.setCurrentGameAlert(pa);				
			}
			try{
				Thread.sleep(sleepSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}