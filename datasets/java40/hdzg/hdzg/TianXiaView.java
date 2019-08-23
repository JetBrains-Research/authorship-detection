package wyf.ytl.view;

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import static wyf.ytl.tool.ConstantUtil.*;

public class TianXiaView {
	GameView gameView;
	int yeSpan = 11;//��ҳ��ʾ�ǳصĸ���
	int currentI = 0;//��ǰ���Ƶ���Ļ���ϱߵ�Ԫ���±�
	int selectI = 0;
	
	int startY = 120;
	int startX = 17;
	
	String[][] items1;//�ǳ��б� 
	
	static Bitmap menuTitle;//���ı��ⱳ��
	static Bitmap threeBitmap;//���Ͻǵ�������ť
	static Bitmap panel_back;//����ͼ
	static Bitmap selectBackground;//ѡ�к�ı���
	static Bitmap buttonBackGround;//��ť����
	static Bitmap upBitmap;//���ϵ�С��ͷ
	static Bitmap downBitmap;//���µ�С��ͷ
	static Bitmap addBitmap;//С�Ӻ�
	static Bitmap cutBitmap;//С����
	static Bitmap logo;
	
	Paint paint;
	
	public TianXiaView(GameView gameView) {//������
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
		addBitmap = Bitmap.createBitmap(menu_item, 225, 15, 15, 15);
		cutBitmap = Bitmap.createBitmap(menu_item, 225, 0, 15, 15);
		menu_item = null;//�ͷŵ���ͼ
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);
	}
	public void initData(){//��ʼ������		
		items1 = new String[gameView.allCityDrawable.size()+gameView.hero.cityList.size()][4];
		//��ʼ����Ҫ��ʾ�ĳǳ���Ϣ
		int t = gameView.allCityDrawable.size();
		for(int i = 0; i<gameView.allCityDrawable.size(); i++){
			items1[i][0] = gameView.allCityDrawable.get(i).getCityName();//�ǳ�����
			items1[i][1] = COUNTRY_NAME[gameView.allCityDrawable.get(i).getCountry()];//������
			items1[i][2] = gameView.allCityDrawable.get(i).getGuardGeneral().get(0).getName();//һ��������
			items1[i][3] = gameView.allCityDrawable.get(i).getArmy()+"��";//����
		}
		
		for(int i = 0; i<gameView.hero.cityList.size(); i++,t++){
			items1[t][0] = gameView.hero.cityList.get(i).getCityName();//�ǳ�����
			items1[t][1] = COUNTRY_NAME[gameView.hero.cityList.get(i).getCountry()];//������
			items1[t][2] = gameView.hero.cityList.get(i).getGuardGeneral().get(0).getName();//һ��������
			items1[t][3] = gameView.hero.cityList.get(i).getArmy()+"��";//����
		}
	}
	public void onDraw(Canvas canvas){//���Ƶķ��� 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//����logo
		paint.setTextSize(23);//�������ִ�С
		canvas.drawText("���¾���", 50, 40, paint);
		paint.setTextSize(18);//�������ִ�С
		canvas.drawBitmap(menuTitle, 10, 70, paint);
		canvas.drawText("�ǳ���", 15, 90, paint);
		canvas.drawText("������", 85, 90, paint);
		canvas.drawText("����", 170, 90, paint);
		canvas.drawText("����", 235, 90, paint);	
		
		int tempCurrentI = currentI;
		if(items1.length < yeSpan){//һ����Ļ������ʾ��
			for(int i=0; i<items1.length; i++){
				canvas.drawText(items1[i][0], 15, startY + 5 + 30*i, paint);
				canvas.drawText(items1[i][1], 85, startY + 5 + 30*i, paint);
				canvas.drawText(items1[i][2], 170, startY + 5 + 30*i, paint);
				canvas.drawText(items1[i][3], 235, startY + 5 + 30*i, paint);
			}
		}
		else{//һ����Ļ��ʾ����ʱ
			for(int i=tempCurrentI; i<tempCurrentI+yeSpan; i++){
				canvas.drawText(items1[i][0], 15, startY + 5+ 30*(i-tempCurrentI), paint);
				canvas.drawText(items1[i][1], 85, startY + 5+ 30*(i-tempCurrentI), paint);
				canvas.drawText(items1[i][2], 170, startY + 5+ 30*(i-tempCurrentI), paint);
				canvas.drawText(items1[i][3], 235, startY + 5+ 30*(i-tempCurrentI), paint);
			}
		}
		canvas.drawBitmap(selectBackground, 10, startY + 5 + 30*selectI - 22, paint);//����ѡ��Ч��
		if(currentI != 0){//����С�����ϼ�ͷ
			canvas.drawBitmap(upBitmap, 150, 55, paint);
		}
		if(items1.length>yeSpan && (currentI+yeSpan) < items1.length){//����С�����¼�ͷ
			canvas.drawBitmap(downBitmap, 150, 444, paint);
		}
		
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>274 && x<304 && y>15 && y<45){//����˹رհ�ť
				this.selectI = 0;
				this.currentI = 0;
				gameView.setStatus(0);
			}
			else if(x>212 && x<242 && y>15 && y<45){//��������·�ҳ��ť
				selectI++;
				if(items1.length < yeSpan){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
					if(selectI > items1.length-1){ 
						selectI = items1.length-1;
					}
				}
				else {//��һ����ʾ��ȫ����Ҫ����ʱ
					if(selectI > yeSpan-1){ 
						selectI = yeSpan-1;
						currentI++;
						if((currentI+yeSpan) > items1.length){
							currentI--;
						}
					}						
				}
			}
			else if(x>243 && x<273 && y>15 && y<45){//��������Ϸ�ҳ��ť
				selectI--;
				if(selectI < 0){
					selectI = 0;
					currentI--;
					if(currentI < 0){
						currentI = 0;
					}
				}	
			}
		}
		return true;
	}

}
