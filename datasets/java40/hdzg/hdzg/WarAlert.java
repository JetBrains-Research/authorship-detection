package wyf.ytl.view;

import static wyf.ytl.tool.ConstantUtil.*;
import wyf.ytl.entity.CityDrawable;
import wyf.ytl.tool.ConstantUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class WarAlert extends GameAlert{
	CityDrawable enemy;//攻击自己的那个城池
	CityDrawable self;//被攻击的自己的那个城池
	String [] alertMessage = {
			"后方探子来报，驻守xxyy城池的zz将军率领大军，奇袭你的城市aa，请选择：",
			"经过将士们殊死抵抗，敌人久攻不下，终于撤军了。",
			"守军积极防守，但是由于兵力悬殊太大，你的城池被攻破了，xx将军也被迫投降了敌人。"
	};
	int status = 0;//0表示询问，1表示显示结果为守住，2表示被攻下
	public WarAlert(GameView gameView, Bitmap bmpDialogBack,
			Bitmap bmpDialogButton,CityDrawable enemy,CityDrawable self) {
		super(gameView, bmpDialogBack, bmpDialogButton);
		this.enemy = enemy;
		this.self = self;
	}

	@Override
	public void drawDialog(Canvas canvas) {
		String showString = null;
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		showString = alertMessage[status];
		if(status == 0){//询问状态
			showString = showString.replaceFirst("xx", ConstantUtil.COUNTRY_NAME[enemy.getCountry()]);
			showString = showString.replaceFirst("yy", enemy.getCityName());
			showString = showString.replaceFirst("zz", enemy.getGuardGeneral().get(0).getName());
			showString = showString.replaceFirst("aa", self.getCityName());
		}
		if(status == 2){
			showString = showString.replaceFirst("xx", self.getGuardGeneral().get(0).getName());
		}
		drawString(canvas, showString);
		//画按钮确定按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
		paint.setTextSize(18);
		if(status == 0){
			canvas.drawText("观战",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);			
			//查看是否需要画第二个取消按钮//画取消按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("忽略", 
					DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);						
		}
		else {//其他状态只画个确定按钮就行了
			canvas.drawText("确定",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);	
		}
	}

	public boolean onTouch(View view, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
				switch(status){
				case 0://玩家需要观战，切到哪里去
					gameView.setStatus(4);//显示战争动画
					gameView.battleField.setCity(self.getCityName());
					gameView.battleField.startAnimation();//启动战争动画
					status = calculateWinOrLose(enemy,self);
					if(status == 2){//被攻下了
						gameView.hero.getCityList().remove(self);
						self.setBackToInit();
						self.setCountry(enemy.getCountry());
						gameView.allCityDrawable.add(self);
					}
					break;
				case 1://没被攻下按下确认
				case 2://被攻下了按下确认
					recoverGame();
					break;
				}			
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是取消键
				status = calculateWinOrLose(enemy,self);
				if(status == 2){//被攻下了
					gameView.hero.getCityList().remove(self);
				}
			}			
		}
		return true;
	}
	//计算输赢
	public int calculateWinOrLose(CityDrawable enemy,CityDrawable self){
		if(enemy.army > self.army){
			if(Math.random()>0.3){
				return 2;
			}
			else{
				return 1;
			}
		}
		else{
			if(Math.random()>0.3){
				return 1;
			}
			else {
				return 2;
			}
		}
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame(){		
		gameView.setCurrentGameAlert(null);//置空记录引用的变量		
		gameView.setStatus(0);//重新设置GameView为待命状态
		gameView.gvt.setChanging(true);//骰子转起来
		gameView.setOnTouchListener(gameView);//返还监听器
	}
	
}