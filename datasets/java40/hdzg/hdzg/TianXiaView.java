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
	int yeSpan = 11;//主页显示城池的个数
	int currentI = 0;//当前绘制的屏幕最上边的元素下标
	int selectI = 0;
	
	int startY = 120;
	int startX = 17;
	
	String[][] items1;//城池列表 
	
	static Bitmap menuTitle;//表格的标题背景
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	static Bitmap addBitmap;//小加号
	static Bitmap cutBitmap;//小减号
	static Bitmap logo;
	
	Paint paint;
	
	public TianXiaView(GameView gameView) {//构造器
		this.gameView = gameView;
		paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		initData();//初始化数据
	}
	
	public static void initBitmap(Resources r){//初始化图片资源   
		Bitmap menu_item = BitmapFactory.decodeResource(r, R.drawable.buttons); 
		threeBitmap = Bitmap.createBitmap(menu_item, 60, 0, 90, 30);
		buttonBackGround = Bitmap.createBitmap(menu_item, 0, 0, 60, 30);
		upBitmap = Bitmap.createBitmap(menu_item, 210, 15, 15, 15);
		downBitmap = Bitmap.createBitmap(menu_item, 210, 0, 15, 15);
		addBitmap = Bitmap.createBitmap(menu_item, 225, 15, 15, 15);
		cutBitmap = Bitmap.createBitmap(menu_item, 225, 0, 15, 15);
		menu_item = null;//释放掉大图
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);
	}
	public void initData(){//初始化数据		
		items1 = new String[gameView.allCityDrawable.size()+gameView.hero.cityList.size()][4];
		//初始化需要显示的城池信息
		int t = gameView.allCityDrawable.size();
		for(int i = 0; i<gameView.allCityDrawable.size(); i++){
			items1[i][0] = gameView.allCityDrawable.get(i).getCityName();//城池名称
			items1[i][1] = COUNTRY_NAME[gameView.allCityDrawable.get(i).getCountry()];//归属国
			items1[i][2] = gameView.allCityDrawable.get(i).getGuardGeneral().get(0).getName();//一个将领名
			items1[i][3] = gameView.allCityDrawable.get(i).getArmy()+"人";//兵力
		}
		
		for(int i = 0; i<gameView.hero.cityList.size(); i++,t++){
			items1[t][0] = gameView.hero.cityList.get(i).getCityName();//城池名称
			items1[t][1] = COUNTRY_NAME[gameView.hero.cityList.get(i).getCountry()];//归属国
			items1[t][2] = gameView.hero.cityList.get(i).getGuardGeneral().get(0).getName();//一个将领名
			items1[t][3] = gameView.hero.cityList.get(i).getArmy()+"人";//兵力
		}
	}
	public void onDraw(Canvas canvas){//绘制的方法 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(23);//设置文字大小
		canvas.drawText("天下局势", 50, 40, paint);
		paint.setTextSize(18);//设置文字大小
		canvas.drawBitmap(menuTitle, 10, 70, paint);
		canvas.drawText("城池名", 15, 90, paint);
		canvas.drawText("归属国", 85, 90, paint);
		canvas.drawText("主将", 170, 90, paint);
		canvas.drawText("兵力", 235, 90, paint);	
		
		int tempCurrentI = currentI;
		if(items1.length < yeSpan){//一个屏幕可以显示下
			for(int i=0; i<items1.length; i++){
				canvas.drawText(items1[i][0], 15, startY + 5 + 30*i, paint);
				canvas.drawText(items1[i][1], 85, startY + 5 + 30*i, paint);
				canvas.drawText(items1[i][2], 170, startY + 5 + 30*i, paint);
				canvas.drawText(items1[i][3], 235, startY + 5 + 30*i, paint);
			}
		}
		else{//一个屏幕显示不下时
			for(int i=tempCurrentI; i<tempCurrentI+yeSpan; i++){
				canvas.drawText(items1[i][0], 15, startY + 5+ 30*(i-tempCurrentI), paint);
				canvas.drawText(items1[i][1], 85, startY + 5+ 30*(i-tempCurrentI), paint);
				canvas.drawText(items1[i][2], 170, startY + 5+ 30*(i-tempCurrentI), paint);
				canvas.drawText(items1[i][3], 235, startY + 5+ 30*(i-tempCurrentI), paint);
			}
		}
		canvas.drawBitmap(selectBackground, 10, startY + 5 + 30*selectI - 22, paint);//绘制选择效果
		if(currentI != 0){//绘制小的向上箭头
			canvas.drawBitmap(upBitmap, 150, 55, paint);
		}
		if(items1.length>yeSpan && (currentI+yeSpan) < items1.length){//绘制小的向下箭头
			canvas.drawBitmap(downBitmap, 150, 444, paint);
		}
		
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>274 && x<304 && y>15 && y<45){//点击了关闭按钮
				this.selectI = 0;
				this.currentI = 0;
				gameView.setStatus(0);
			}
			else if(x>212 && x<242 && y>15 && y<45){//点击了向下翻页按钮
				selectI++;
				if(items1.length < yeSpan){//当一个屏幕可以全部显示时，即不需要滚屏 
					if(selectI > items1.length-1){ 
						selectI = items1.length-1;
					}
				}
				else {//当一屏显示不全，需要滚屏时
					if(selectI > yeSpan-1){ 
						selectI = yeSpan-1;
						currentI++;
						if((currentI+yeSpan) > items1.length){
							currentI--;
						}
					}						
				}
			}
			else if(x>243 && x<273 && y>15 && y<45){//点击了向上翻页按钮
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
