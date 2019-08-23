package wyf.ytl.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.entity.Skill;
import wyf.ytl.entity.SuiXinBuSkill;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class UseSkillView {
	GameView gameView;
	int yeSpan = 9;//每页显示的个数
	int selectI = 0;
	int startY = 90;
	int startX = 25;
	
	ArrayList<Skill> items = new ArrayList<Skill>();//需要列出的所有技能
	
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	static Bitmap menuTitle;//表格的标题背景
	static Bitmap logo;
	static Bitmap addBitmap;//小加号
	static Bitmap cutBitmap;//小减号
	
	HashMap<Integer,Skill> skills;
	int number = 1;
	Paint paint;
	public UseSkillView(GameView gameView) {
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
		//初始化技能
		skills = gameView.hero.getHeroSkill();
		items.clear();
		Set<Integer> keySet = skills.keySet();
		Iterator<Integer> ii = keySet.iterator();
		
		while(ii.hasNext()){
			Skill s = (Skill)skills.get((Integer)ii.next());
			if(s.getSkillType() == 1){
				items.add(s);
			}
		}
	}
	public void onDraw(Canvas canvas){//绘制的方法 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(23);//设置文字大小
		canvas.drawText("使用计谋", 50, 40, paint);
		paint.setTextSize(18);//设置文字大小
		
		canvas.drawBitmap(menuTitle, 10, 70, paint);
		canvas.drawText("技能名", 15, 90, paint);
		canvas.drawText("等级", 120, 90, paint);
		canvas.drawText("消耗", 200, 90, paint);
		
		
		for(int i=0; i<items.size(); i++){
			canvas.drawText(items.get(i).getName(), 15, startY +35 + 30*i, paint);
			canvas.drawText(items.get(i).getProficiencyLevel()+"", 120, startY +35 + 30*i, paint);
			canvas.drawText(items.get(i).getStrengthCost()+"", 200, startY +35+ 30*i, paint);		
			if(items.get(i) instanceof SuiXinBuSkill){//是随心步时
				canvas.drawText("("+number+")", 75, startY +35 + 30*i, paint);
				canvas.drawBitmap(addBitmap, 250, startY+35+30*i-15, paint);
				canvas.drawBitmap(cutBitmap, 270, startY+35+30*i-15, paint);				
			}
		}

		canvas.drawBitmap(selectBackground, 10, startY + 35 + 30*selectI - 22, paint);
		canvas.drawBitmap(buttonBackGround, 130, 430, paint);//绘制详细按钮背景
		canvas.drawText("使用", 142, 452, paint);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>212 && x<242 && y>15 && y<45){//点击了向下翻页按钮
				if(items.size() == 0){
					return true;
				}			
				selectI++;
				if(selectI > items.size()-1){ 
					selectI = items.size()-1;
				}	
			}
			else if(x>243 && x<273 && y>15 && y<45){//点击了向上翻页按钮
				if(items.size() == 0){
					return true;
				}
				selectI--;
				if(selectI < 0){
					selectI = 0;
				}
			}
			else if(x>274 && x<304&& y>15 && y<45){//点击了关闭按钮
				gameView.setStatus(0);
			}
			if(x>130 && x<190 && y>430 && y<460){//使用按钮
				if(items.size() == 0){
					return true;
				}
				Skill skill = items.get(selectI);
				
				if(skill.calculateResult() == -1){//不能用
				}
				else {//可以使用时
					gameView.suiXinBu = number;
					skill.useSkill(0);//使用技能
				}
				selectI = 0;
				gameView.setStatus(0);
			}
			else if(x>252 && x<267 && y>111 && y<126){//点击小加号
				this.number++;
				if(number > 6){
					number = 6;
				}
			}
			else if(x>271 && x<286 && y>111 && y<126){//点击了小减号
				this.number--;
				if(number <1 ){
					number = 1;
				}
			}
		}
		return true;
	}
}
