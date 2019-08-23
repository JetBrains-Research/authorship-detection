package wyf.ytl.view;

import java.util.ArrayList;

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.entity.CityDrawable;
import wyf.ytl.entity.General;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class SelectGeneral {
	GameView gameView;
	int yeSpan = 9;//ÿҳ��ʾ�ĸ���
	
	int currentI = 0;//��ǰ���Ƶ���Ļ���ϱߵ�Ԫ���±�
	int selectI = 0;//��ǰ��ѡ��
	
	int startY = 120;
	int startX = 15;
	
	ArrayList<General> totalGeneral;
	String[][] items;//��Ҫ�г����佫
	
	static Bitmap menuTitle;//���ı��ⱳ��
	static Bitmap threeBitmap;//���Ͻǵ�������ť
	static Bitmap panel_back;//����ͼ
	static Bitmap selectBackground;//ѡ�к�ı���
	static Bitmap buttonBackGround;//��ť����
	static Bitmap upBitmap;//���ϵ�С��ͷ
	static Bitmap downBitmap;//���µ�С��ͷ
	static Bitmap logo;
	
	Paint paint;
	public SelectGeneral(GameView gameView) {//������
		this.gameView = gameView;
		paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		paint.setAntiAlias(true);//�����
		initData();//��ʼ������
	}
	
	public static void initBitmap(Resources r){//��ʼ��ͼƬ��Դ   
		Bitmap menu_item = BitmapFactory.decodeResource(r, R.drawable.buttons); 
		threeBitmap = Bitmap.createBitmap(menu_item, 60, 0, 90, 30);
		buttonBackGround = Bitmap.createBitmap(menu_item, 0, 0, 60, 30);
		upBitmap = Bitmap.createBitmap(menu_item, 210, 15, 15, 15);
		downBitmap = Bitmap.createBitmap(menu_item, 210, 0, 15, 15);
		menu_item = null;//�ͷŵ���ͼ 
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);//���ⱳ��
	}
	public void initData(){//��ʼ������
		totalGeneral = gameView.hero.getTotalGeneral();//�õ�Ӣ����ӵ�е����н���
		
		items = new String[totalGeneral.size()][3];
		for(int i=0; i<totalGeneral.size(); i++){
			items[i][0] = totalGeneral.get(i).getName();//����
			items[i][1] = totalGeneral.get(i).getRank();//ְλ
			items[i][2] = totalGeneral.get(i).getStrength()+"";//����
		}
	}
	
	public void onDraw(Canvas canvas){//���Ƶķ��� 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//����logo
		paint.setTextSize(23);//�������ִ�С
		canvas.drawText("ѡ������佫", 50, 40, paint);
		paint.setTextSize(18);//�������ִ�С
		
		canvas.drawBitmap(menuTitle, 10, 70, paint);
		canvas.drawText("����", 20, 90, paint);//���Ʊ���
		canvas.drawText("ְλ", 110, 90, paint);
		canvas.drawText("����", 210, 90, paint);
		
		int tempCurrentI = currentI;
		if(items.length < yeSpan){//һ����Ļ������ʾ��
			for(int i=0; i<items.length; i++){
				canvas.drawText(items[i][0], 20, startY + 5 + 30*i, paint);
				canvas.drawText(items[i][1], 110, startY + 5 + 30*i, paint);
				canvas.drawText(items[i][2], 210, startY + 5 + 30*i, paint);
			}
		}
		else{//һ����Ļ��ʾ����ʱ
			for(int i=tempCurrentI; i<tempCurrentI+yeSpan; i++){
				canvas.drawText(items[i][0], 20, startY + 5 + 30*(i-tempCurrentI), paint);
				canvas.drawText(items[i][1], 110, startY + 5 + 30*(i-tempCurrentI), paint);
				canvas.drawText(items[i][2], 210, startY + 5 + 30*(i-tempCurrentI), paint);
			}
		}
		
		canvas.drawBitmap(selectBackground, 10, startY +5 + 30*selectI - 22, paint);//����ѡ��Ч��
		
		canvas.drawBitmap(buttonBackGround, 130, 400, paint);
		canvas.drawText("ȷ��", 142, 421, paint);

		if(currentI != 0){//����С�����ϼ�ͷ
			canvas.drawBitmap(upBitmap, 150, 55, paint);
		}
		if(items.length>yeSpan && (currentI+yeSpan) < items.length){//����С�����¼�ͷ
			canvas.drawBitmap(downBitmap, 150, 444, paint);
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(x>243 && x<273 && y>15 && y<45){//��������Ϸ�ҳ��ť
			selectI--;
			if(selectI < 0){
				selectI = 0;
				currentI--;
				if(currentI < 0){
					currentI = 0;
				}
			}	
		}
		else if(x>212 && x<242 && y>15 && y<45){//��������·�ҳ��ť{		
			selectI++;
			if(items.length < yeSpan){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
				if(selectI > items.length-1){ 
					selectI = items.length-1;
				}
			}
			else {//��һ����ʾ��ȫ����Ҫ����ʱ
				if(selectI > yeSpan-1){ 
					selectI = yeSpan-1;
					currentI++;
					if((currentI+yeSpan) > items.length){
						currentI--;
					}
				}						
			}		
		}
		else if(x>274 && x<304 && y>15 && y<45){//����˹رհ�ť
			
			CityDrawable city = (CityDrawable)gameView.currentDrawable;
			city.status = 0;
			gameView.setStatus(0);
		}
		else if(x>130 && x<190 && y>400 && y<430){//�����ȷ����ť
			gameView.fightingGeneral=  totalGeneral.get((currentI+selectI));//�õ�ѡ�е��佫			
			CityDrawable cd = (CityDrawable)gameView.currentDrawable;//��óɳǳ�������
			cd.calculateWinOrLose();//������Ӯ
			gameView.battleField.setCity(cd.getCityName());
			gameView.setStatus(4);
			gameView.battleField.startAnimation();
		}
		return true;
	}
}
