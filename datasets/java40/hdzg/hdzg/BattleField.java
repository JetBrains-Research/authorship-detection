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
	
	static Bitmap [] heroGeneral;//�ҷ�����
	static Bitmap [] enemyGeneral;//�з�����
	static Bitmap [] heroSoldier;//�ҷ�ʿ��
	static Bitmap [] enemySoldier;//�з�ʿ��
	static Bitmap bmpBack;//����
	static Bitmap bmpCityBorder;//�������
	static int generalWidth = 80;
	static int generalHeight = 100;
	
	
	int borderX = 102;
	int borderY = 10;	
	int soldierWidth = 80;
	int soldierHeight = 80;
	int heroGeneralIndex;//�ҷ�����֡���
	int enemyGeneralIndex;//�з�����֡���
	int heroSoldierIndex;//�ҷ�ʿ��֡���
	int enemySoldierIndex;//�з�ʿ��������
	int heroGeneralStartX = 220;//�ҷ�������ʼx����
	int heroGeneralStartY = 360;//�ҷ�������ʼy����
	int enemyGeneralStartX = 20;//�з�������ʼx����
	int enemyGeneralStartY = 20;//�з�������ʼy����
	int heroSoldierStartX = 160;//�ҷ�ʿ����ʼx����
	int heroSoldierStartY = 180;//�ҷ�ʿ����ʼy����
	int enemySoldierStartX = 130;//�з�ʿ����ʼx����
	int enemySoldierStartY = 110;//�з�ʿ����ʼy����
	int soldierSpanX = 40;
	int soldierSpany = 40;
	int soldierNumber = 4;
	String city;//�����Է�������
	
	public BattleField(GameView gameView){
		this.gameView = gameView;
		startAnimation();
	}
	//��������ʼ��ͼƬ
	public static void initBitmap(Resources r){
		heroGeneral = new Bitmap[4];//��ʼ���ҷ�Ӣ��
		Bitmap temp = BitmapFactory.decodeResource(r, R.drawable.battle_hero);
		for(int i=0;i<4;i++){
			heroGeneral[i] = Bitmap.createBitmap(temp, i*generalWidth, 0, generalWidth, generalHeight);
		}
		//�з�Ӣ��
		enemyGeneral = new Bitmap[4];//��ʼ���з�Ӣ��
		temp = BitmapFactory.decodeResource(r, R.drawable.battle_enemy_general);
		for(int i=0;i<4;i++){
			enemyGeneral[i] = Bitmap.createBitmap(temp, i*generalWidth, 0, generalWidth, generalHeight);
		}
		//����ʿ��
		heroSoldier = new Bitmap[4];//��ʼ���з�Ӣ��
		heroSoldier[0] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_1);
		heroSoldier[1] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_2);
		heroSoldier[2] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_3);
		heroSoldier[3] = BitmapFactory.decodeResource(r, R.drawable.hero_soldier_4);
		//�з�ʿ��
		enemySoldier = new Bitmap[4];//��ʼ���з�Ӣ��
		enemySoldier[0] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_1);
		enemySoldier[1] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_2);
		enemySoldier[2] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_3);
		enemySoldier[3] = BitmapFactory.decodeResource(r, R.drawable.enemy_soldier_4); 
		//����
		bmpBack = BitmapFactory.decodeResource(r, R.drawable.battle_field);     
		bmpCityBorder = BitmapFactory.decodeResource(r, R.drawable.board); 
	}
	//���Լ�
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(bmpBack, 0, 0, null);
		canvas.drawBitmap(heroGeneral[heroGeneralIndex], heroGeneralStartX, heroGeneralStartY, null);//�ҷ�Ӣ��
		canvas.drawBitmap(enemyGeneral[enemyGeneralIndex], enemyGeneralStartX, enemyGeneralStartY, null);//�з�Ӣ��
		for(int i=0;i<soldierNumber;i++){//�ҷ�ʿ��
			canvas.drawBitmap(heroSoldier[heroSoldierIndex], heroSoldierStartX-i*soldierSpanX, heroSoldierStartY+i*soldierSpanX, null);
		}
		for(int i=0;i<soldierNumber;i++){//�ҷ�ʿ��
			canvas.drawBitmap(enemySoldier[enemySoldierIndex], enemySoldierStartX-i*soldierSpanX, enemySoldierStartY+i*soldierSpanX, null);
		}
		//�����б߿�
		canvas.drawBitmap(bmpCityBorder, borderX, borderY, null);
		//����������
		if(city!=null){
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setTextSize(38);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(city, 160, borderY+38, paint);
		}
	}
	//�����������̶߳���
	public void startAnimation(){
		new BattleThread().start();
	}
	//�̣߳�����֡
	class BattleThread extends Thread{
		boolean flag=true;
		int sleepSpan = 300;
		int counter = 0;//20�ξͽ���
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