package wyf.ytl.view;

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BattleField{
	GameView gameView;
	
	static Bitmap [] heroGeneral;//我方将领
	static Bitmap [] enemyGeneral;//敌方将领
	static Bitmap [] heroSoldier;//我方士兵
	static Bitmap [] enemySoldier;//敌方士兵
	static Bitmap bmpBack;//背景
	static Bitmap bmpCityBorder;//城名外框
	static int generalWidth = 80;
	static int generalHeight = 100;
	
	
	int borderX = 102;
	int borderY = 10;	
	int soldierWidth = 80;
	int soldierHeight = 80;
	int heroGeneralIndex;//我方将领帧序号
	int enemyGeneralIndex;//敌方将领帧序号
	int heroSoldierIndex;//我方士兵帧序号
	int enemySoldierIndex;//敌方士兵真序列
	int heroGeneralStartX = 220;//我方将领起始x坐标
	int heroGeneralStartY = 360;//我方将领起始y坐标
	int enemyGeneralStartX = 20;//敌方将领起始x坐标
	int enemyGeneralStartY = 20;//敌方将领起始y坐标
	int heroSoldierStartX = 160;//我方士兵起始x坐标
	int heroSoldierStartY = 180;//我方士兵起始y坐标
	int enemySoldierStartX = 130;//敌方士兵起始x坐标
	int enemySoldierStartY = 110;//敌方士兵起始y坐标
	int soldierSpanX = 40;
	int soldierSpany = 40;
	int soldierNumber = 4;
	String city;//画出对方的来处
	
	public BattleField(GameView gameView){
		this.gameView = gameView;
		startAnimation();
	}
	//方法：初始化图片
	public static void initBitmap(Resources r){
		heroGeneral = new Bitmap[4];//初始化我方英雄
		Bitmap temp = BitmapFactory.decodeResource(r, R.drawable.battle_hero);
		for(int i=0;i<4;i++){
			heroGeneral[i] = Bitmap.createBitmap(temp, i*generalWidth, 0, generalWidth, generalHeight);
		}
		//敌方英雄
		enemyGeneral = new Bitmap[4];//初始化敌方英雄
		temp = BitmapFactory.decodeResource(r, R.drawable.battle_enemy_general);
		for(int i=0;i<4;i++){
			enemyGeneral[i] = Bitmap.createBitmap(temp, i*generalWidth, 0, generalWidth, generalHeight);
		}
		//己方士兵
		heroSoldier = new Bitmap[4];//初始化敌方英雄
		heroSoldier[0] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_1);
		heroSoldier[1] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_2);
		heroSoldier[2] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_3);
		heroSoldier[3] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_4);
		//敌方士兵
		enemySoldier = new Bitmap[4];//初始化敌方英雄
		enemySoldier[0] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_1);
		enemySoldier[1] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_2);
		enemySoldier[2] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_3);
		enemySoldier[3] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_4); 
		//背景
		bmpBack = BitmapFactory.decodeResource(r, R.drawable.battle_field);     
		bmpCityBorder = BitmapFactory.decodeResource(r, R.drawable.board); 
	}
	//画自己
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(bmpBack, 0, 0, null);
		canvas.drawBitmap(heroGeneral[heroGeneralIndex], heroGeneralStartX, heroGeneralStartY, null);//我方英雄
		canvas.drawBitmap(enemyGeneral[enemyGeneralIndex], enemyGeneralStartX, enemyGeneralStartY, null);//敌方英雄
		for(int i=0;i<soldierNumber;i++){//我方士兵
			canvas.drawBitmap(heroSoldier[heroSoldierIndex], heroSoldierStartX-i*soldierSpanX, heroSoldierStartY+i*soldierSpanX, null);
		}
		for(int i=0;i<soldierNumber;i++){//我方士兵
			canvas.drawBitmap(enemySoldier[enemySoldierIndex], enemySoldierStartX-i*soldierSpanX, enemySoldierStartY+i*soldierSpanX, null);
		}
		//画城市边框
		canvas.drawBitmap(bmpCityBorder, borderX, borderY, null);
		//画城市名称
		if(city!=null){
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setTextSize(38);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(city, 160, borderY+38, paint);
		}
	}
	//方法：创建线程动画
	public void startAnimation(){
		new BattleThread().start();
	}
	//线程，负责换帧
	class BattleThread extends Thread{
		boolean flag=true;
		int sleepSpan = 300;
		int counter = 0;//20次就结束
		public void run(){
			while(flag){
				heroGeneralIndex = (heroGeneralIndex+1)%4;
				enemyGeneralIndex = (enemyGeneralIndex+1)%4;
				heroSoldierIndex = (heroSoldierIndex+1)%4;
				enemySoldierIndex = (enemySoldierIndex+1)%4;
				counter++;
				if(counter == 20){
					flag = false;
					gameView.setStatus(0);
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
	public void setCity(String city) {
		this.city = city;
	}
}