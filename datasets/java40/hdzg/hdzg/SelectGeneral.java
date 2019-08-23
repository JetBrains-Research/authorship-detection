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
	int yeSpan = 9;//每页显示的个数
	
	int currentI = 0;//当前绘制的屏幕最上边的元素下标
	int selectI = 0;//当前的选中
	
	int startY = 120;
	int startX = 15;
	
	ArrayList<General> totalGeneral;
	String[][] items;//需要列出的武将
	
	static Bitmap menuTitle;//表格的标题背景
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	static Bitmap logo;
	
	Paint paint;
	public SelectGeneral(GameView gameView) {//构造器
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
		menu_item = null;//释放掉大图 
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);//标题背景
	}
	public void initData(){//初始化数据
		totalGeneral = gameView.hero.getTotalGeneral();//得到英雄所拥有的所有将领
		
		items = new String[totalGeneral.size()][3];
		for(int i=0; i<totalGeneral.size(); i++){
			items[i][0] = totalGeneral.get(i).getName();//名称
			items[i][1] = totalGeneral.get(i).getRank();//职位
			items[i][2] = totalGeneral.get(i).getStrength()+"";//体力
		}
	}
	
	public void onDraw(Canvas canvas){//绘制的方法 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(23);//设置文字大小
		canvas.drawText("选择出征武将", 50, 40, paint);
		paint.setTextSize(18);//设置文字大小
		
		canvas.drawBitmap(menuTitle, 10, 70, paint);
		canvas.drawText("名称", 20, 90, paint);//绘制标题
		canvas.drawText("职位", 110, 90, paint);
		canvas.drawText("体力", 210, 90, paint);
		
		int tempCurrentI = currentI;
		if(items.length < yeSpan){//一个屏幕可以显示下
			for(int i=0; i<items.length; i++){
				canvas.drawText(items[i][0], 20, startY + 5 + 30*i, paint);
				canvas.drawText(items[i][1], 110, startY + 5 + 30*i, paint);
				canvas.drawText(items[i][2], 210, startY + 5 + 30*i, paint);
			}
		}
		else{//一个屏幕显示不下时
			for(int i=tempCurrentI; i<tempCurrentI+yeSpan; i++){
				canvas.drawText(items[i][0], 20, startY + 5 + 30*(i-tempCurrentI), paint);
				canvas.drawText(items[i][1], 110, startY + 5 + 30*(i-tempCurrentI), paint);
				canvas.drawText(items[i][2], 210, startY + 5 + 30*(i-tempCurrentI), paint);
			}
		}
		
		canvas.drawBitmap(selectBackground, 10, startY +5 + 30*selectI - 22, paint);//绘制选择效果
		
		canvas.drawBitmap(buttonBackGround, 130, 400, paint);
		canvas.drawText("确定", 142, 421, paint);

		if(currentI != 0){//绘制小的向上箭头
			canvas.drawBitmap(upBitmap, 150, 55, paint);
		}
		if(items.length>yeSpan && (currentI+yeSpan) < items.length){//绘制小的向下箭头
			canvas.drawBitmap(downBitmap, 150, 444, paint);
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(x>243 && x<273 && y>15 && y<45){//点击了向上翻页按钮
			selectI--;
			if(selectI < 0){
				selectI = 0;
				currentI--;
				if(currentI < 0){
					currentI = 0;
				}
			}	
		}
		else if(x>212 && x<242 && y>15 && y<45){//点击了向下翻页按钮{		
			selectI++;
			if(items.length < yeSpan){//当一个屏幕可以全部显示时，即不需要滚屏 
				if(selectI > items.length-1){ 
					selectI = items.length-1;
				}
			}
			else {//当一屏显示不全，需要滚屏时
				if(selectI > yeSpan-1){ 
					selectI = yeSpan-1;
					currentI++;
					if((currentI+yeSpan) > items.length){
						currentI--;
					}
				}						
			}		
		}
		else if(x>274 && x<304 && y>15 && y<45){//点击了关闭按钮
			
			CityDrawable city = (CityDrawable)gameView.currentDrawable;
			city.status = 0;
			gameView.setStatus(0);
		}
		else if(x>130 && x<190 && y>400 && y<430){//点击了确定按钮
			gameView.fightingGeneral=  totalGeneral.get((currentI+selectI));//得到选中的武将			
			CityDrawable cd = (CityDrawable)gameView.currentDrawable;//获得成城池类引用
			cd.calculateWinOrLose();//计算输赢
			gameView.battleField.setCity(cd.getCityName());
			gameView.setStatus(4);
			gameView.battleField.startAnimation();
		}
		return true;
	}
}
