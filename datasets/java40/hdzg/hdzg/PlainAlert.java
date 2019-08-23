package wyf.ytl.view;				//声明包语句

/*
 * 该类继承自抽象类GameAlert，主要负责最简单的界面显示
 */
import static wyf.ytl.tool.ConstantUtil.*;
import android.graphics.Bitmap;				//引入相关类
import android.graphics.Canvas;				//引入相关类
import android.graphics.Paint;				//引入相关类
import android.graphics.Typeface;			//引入相关类
import android.view.MotionEvent;			//引入相关类
import android.view.View;					//引入相关类

public class PlainAlert extends GameAlert{
	String alertMessage;//要显示的消息,需要设置才行
	//构造器
	public PlainAlert(GameView gameView,String alertMessage,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		super(gameView,bmpDialogBack, bmpDialogButton);
		this.alertMessage = alertMessage;
	}
	@Override
	public void drawDialog(Canvas canvas) {//对父类抽象方法drawDialog的实现
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);		
		drawString(canvas, alertMessage);
		//画按钮确定按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();						//创建画笔对象
		paint.setARGB(255, 42, 48, 103);				//设置画笔颜色
		paint.setAntiAlias(true);						//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//设置字体
		paint.setTextSize(18);							//设置字号
		canvas.drawText("确定",							//绘制确定按钮
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);		
	}
	//方法：实现View.OnTouchListener接口的方法
	public boolean onTouch(View view, MotionEvent event) {
		int x = (int)event.getX();			//获得点击处的X坐标
		int y = (int)event.getY();			//获得点击处的Y坐标
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
				gameView.setStatus(0);					//设置游戏状态为0(待命态)	
				gameView.setOnTouchListener(gameView);	//将监听器重新设置为GameView
				gameView.currentGameAlert = null;		//置空当前游戏提示
			}			
		}
		return true;
	}	
}