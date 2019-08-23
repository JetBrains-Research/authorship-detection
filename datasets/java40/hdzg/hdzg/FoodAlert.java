package wyf.ytl.view;			//声明包语句

import static wyf.ytl.tool.ConstantUtil.*;
import wyf.ytl.entity.CityDrawable;
import android.graphics.Bitmap;				//引入相关类
import android.graphics.Canvas;				//引入相关类
import android.graphics.Paint;				//引入相关类
import android.view.MotionEvent;			//引入相关类
import android.view.View;					//引入相关类

public class FoodAlert extends GameAlert{
	 CityDrawable city;//记录是哪个城池粮草危机
	 String alertMessage = "刚才xx城守将派人来报,说粮草已经不多了,是否划拨粮草?";
	
	
	//构造器
	public FoodAlert(GameView gameView,CityDrawable city,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		super(gameView,bmpDialogBack, bmpDialogButton);
		this.city = city;
	}
	@Override
	public void drawDialog(Canvas canvas) {
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);//画对话框背景\\
		alertMessage=alertMessage.replaceFirst("xx", city.getCityName());//替换成实际的城市名称
		drawString(canvas, alertMessage);//画提示文本
		Paint paint = new Paint();//创建画笔
		paint.setTextSize(DIALOG_WORD_SIZE);//设置字体大小
		paint.setAntiAlias(true);//设置抗锯齿
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		//画划拨按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);//画按钮图片
		canvas.drawText("划拨", //画按钮上的文字
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint);
		//画忽略按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
		canvas.drawText("忽略",//画按钮上的文字
				DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint);
	}
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//事件为点下屏幕
			int x = (int)event.getX();//获取屏幕按下的x坐标
			int y = (int)event.getY();//获取屏幕按下的y坐标
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
				gameView.setStatus(97);//弹出自己城池管理界面
				gameView.setCurrentGameAlert(null);
				gameView.setOnTouchListener(gameView);
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是取消键
				gameView.setCurrentGameAlert(null);
				gameView.setStatus(0);//改为待命界面
				gameView.setOnTouchListener(gameView);
			}
		}
		return true;
	}
	
}