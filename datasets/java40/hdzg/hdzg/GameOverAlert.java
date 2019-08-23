package wyf.ytl.view;

import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_HEIGHT;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_START_X;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_START_Y;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_WIDTH;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_WORD_LEFT;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_WORD_UP;
import static wyf.ytl.tool.ConstantUtil.DIALOG_START_Y;
import static wyf.ytl.tool.ConstantUtil.DIALOG_WORD_SIZE;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameOverAlert extends GameAlert{
	String [] alertMessage={
			"你没钱交过路税，已经沦为守城将领的阶下囚了，统一大业已经永久搁浅了，只好下次再来了！",
			"你果然是千古未遇的奇才，你已经成功统一了全国，你会被老百姓永远记住的！",
	};
	int winOrLose;//0表示游戏失败，1表示游戏胜利
	//构造器，传入0表示游戏失败，传入1表示统一中国
	public GameOverAlert(GameView gameView,int winOrLose,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		super(gameView,bmpDialogBack, bmpDialogButton);
		this.winOrLose = winOrLose;
	}
	@Override
	public void drawDialog(Canvas canvas) {
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);		      
		drawString(canvas, alertMessage[winOrLose]);     
		//画按钮确定按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);       
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
		paint.setTextSize(18);
		canvas.drawText("确定",
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);		
	}

	public boolean onTouch(View view, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
				gameView.stopGame();
				gameView.activity.myHandler.sendEmptyMessage(14);//向Activity发消息回菜单
			}			
		}
		return true;
	}
	
}