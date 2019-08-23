package wyf.ytl.entity;
/*
 * ����̳����߳��࣬��Ҫ����Ӣ�ۺ�̨���ݵĶ�ʱ�޸ģ���ÿ��һ��ʱ������Ӣ�۵�����
 * ÿ��һ��ʱ�����Ӣ�۵����ݣ�Ϊ�����߳�
 */
import static wyf.ytl.tool.ConstantUtil.*;

import java.util.ArrayList;

import wyf.ytl.view.FoodAlert;
import wyf.ytl.view.GameView;
import wyf.ytl.view.PlainAlert;
import wyf.ytl.view.WarAlert;

public class HeroBackDataThread extends Thread{

	Hero hero;//Ӣ�۵�����
	public boolean flag;//�߳��Ƿ�ִ�б�־λ
	int sleepSpan = 1000000;//����ʱ��
	int foodCount = 0;//ִ��5ѭ�����޸�����
	int warCount = 0;//ִ��10��ѭ���������м��ʱ���ս��
	CityDrawable ignoredCity;//����Һ��Ե�����Σ������
	CityDrawable emptyCity;//������Σ�����б���˿ճ�
	Research completedResearch;//�����ɵ��о���Ŀ

	//������
	public HeroBackDataThread(Hero hero){
		super.setName("==HeroBackDataThread");
		this.hero = hero;
		flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		while(flag){
			//��ʳ˥��
			foodCount++;
			if(foodCount == 5){//���������ͽ�����ʳ˥��
				foodCount = 0;
				hero.setFood((int)(hero.getFood()*0.95));
				ArrayList<CityDrawable> cityList = hero.getCityList();
				for(CityDrawable city:cityList){
					city.food -= (int)(city.food*0.15f);
					if(city.food <= MIN_FOOD){//�����������С��ĳ��ֵ�ͱ���
						if(ignoredCity == city){//���֮ǰ�Ѿ��������ˣ��Ǿͳǳطֱ�������
							GameView gv = hero.father;
							city.setBackToInit();//�ָ����е���Ϣ��Ĭ��
							
							String alertMessage = "������û�м�ʱ�������ݣ�"+city.getCityName()+"��������Σ���Ѿ�" +
									"�������ƣ�������ϰ����Ѿ���˳��"+COUNTRY_NAME[city.getCountry()]+"�������������ˡ�";
							PlainAlert pa = new PlainAlert(gv, alertMessage, GameView.dialogBack, GameView.dialogButton);
							hero.father.setCurrentGameAlert(pa);
							emptyCity = city;
						}
						else{//����ǵ�һ�γ�������Σ��
							FoodAlert fa = new FoodAlert(hero.father, city, GameView.dialogBack, GameView.dialogButton);
							hero.father.setCurrentGameAlert(fa);
							ignoredCity = city;//��¼����
						}
					}
				}
				if(emptyCity != null){//ɾ��������Σ������ɵĿճ�
					hero.cityList.remove(emptyCity);
					hero.father.allCityDrawable.add(emptyCity);
					emptyCity = null;
				}				
				foodCount = 0;
			}
			//��������
			if(hero.researchList.size() != 0){
				for(Research research:hero.researchList){
					if(research.makeProgress()){//���������Ŀȫ���깤
						completedResearch = research;//��¼����Ҫɾ������Ŀ
						if(research.researchProject == 0){//ս���о��ɹ�
							hero.warTank += research.getResearchNumber();
						}
						else {//�����о��ɹ�
							hero.warTower += research.getResearchNumber();
						}
						GameView gv = hero.father;
						String alertMessage = research.getName()+"Ϊ�㽨���"+
						(research.researchProject==0?"ս��":"����")
						+"�Ѿ���ɣ������ڳǳع����в鿴�򻮲�������Ҫ�ĳǳء�";								
						PlainAlert pa = new PlainAlert(gv, alertMessage, GameView.dialogBack,GameView.dialogButton);
						hero.father.setCurrentGameAlert(pa);
					}
				}
				if(completedResearch != null){//ɾ����ɵ���Ŀ
					hero.researchList.remove(completedResearch);
				}
			}
			//�����Ϯ
			warCount++;
			if(warCount == 10){
				if(Math.random() < 0.5){//һ�����ʴ���
					if(hero.cityList.size() != 0){//�гǿɹ�����
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
			//���Ӣ�����ϵ�Ǯ/��/���ǲ���̫����
			boolean tempFlag = false;
			if(hero.armyWithMe>=800000){//���ϵľ���̫��
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
			if(hero.totalMoney >=800000){//���ϵ�Ǯ̫��
				hero.armyWithMe -= 80000*(int)(Math.random()*20000);
				if(hero.armyWithMe > 1000){
					hero.armyWithMe -= (int)(Math.random()*500);
				}	
				if(hero.food > 1000){
					hero.food -=(int)(Math.random()*500);
				}
				tempFlag = true;
			}
			if(hero.food >= 800000){//������ʳ̫��
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
				String alertMessage="��Ĵ��������һ��ɳ��������ʧ��һЩ���ݺͽ�Ǯ����һЩʿ��Ҳ�����ˣ��������ǲ�Ҫ��̫�ණ�����Լ����ϰ���";
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