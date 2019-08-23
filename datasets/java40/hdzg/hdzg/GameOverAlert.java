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
			"��ûǮ����·˰���Ѿ���Ϊ�سǽ���Ľ������ˣ�ͳһ��ҵ�Ѿ����ø�ǳ�ˣ�ֻ���´������ˣ�",
			"���Ȼ��ǧ��δ������ţ����Ѿ��ɹ�ͳһ��ȫ������ᱻ�ϰ�����Զ��ס�ģ�",
	};
	int winOrLose;//0��ʾ��Ϸʧ�ܣ�1��ʾ��Ϸʤ��
	//������������0��ʾ��Ϸʧ�ܣ�����1��ʾͳһ�й�
	public GameOverAlert(GameView gameView,int winOrLose,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		super(gameView,bmpDialogBack, bmpDialogButton);
		this.winOrLose = winOrLose;
	}
	@Override
	public void drawDialog(Canvas canvas) {
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);		      
		drawString(canvas, alertMessage[winOrLose]);     
		//����ťȷ����ť
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);       
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
		paint.setTextSize(18);
		canvas.drawText("ȷ��",
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
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȷ����
				gameView.stopGame();
				gameView.activity.myHandler.sendEmptyMessage(14);//��Activity����Ϣ�ز˵�
			}			
		}
		return true;
	}
	
}