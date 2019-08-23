package wyf.ytl.view;				//���������

/*
 * ����̳��Գ�����GameAlert����Ҫ������򵥵Ľ�����ʾ
 */
import static wyf.ytl.tool.ConstantUtil.*;
import android.graphics.Bitmap;				//���������
import android.graphics.Canvas;				//���������
import android.graphics.Paint;				//���������
import android.graphics.Typeface;			//���������
import android.view.MotionEvent;			//���������
import android.view.View;					//���������

public class PlainAlert extends GameAlert{
	String alertMessage;//Ҫ��ʾ����Ϣ,��Ҫ���ò���
	//������
	public PlainAlert(GameView gameView,String alertMessage,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		super(gameView,bmpDialogBack, bmpDialogButton);
		this.alertMessage = alertMessage;
	}
	@Override
	public void drawDialog(Canvas canvas) {//�Ը�����󷽷�drawDialog��ʵ��
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);		
		drawString(canvas, alertMessage);
		//����ťȷ����ť
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();						//�������ʶ���
		paint.setARGB(255, 42, 48, 103);				//���û�����ɫ
		paint.setAntiAlias(true);						//���ÿ����
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));//��������
		paint.setTextSize(18);							//�����ֺ�
		canvas.drawText("ȷ��",							//����ȷ����ť
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);		
	}
	//������ʵ��View.OnTouchListener�ӿڵķ���
	public boolean onTouch(View view, MotionEvent event) {
		int x = (int)event.getX();			//��õ������X����
		int y = (int)event.getY();			//��õ������Y����
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȷ����
				gameView.setStatus(0);					//������Ϸ״̬Ϊ0(����̬)	
				gameView.setOnTouchListener(gameView);	//����������������ΪGameView
				gameView.currentGameAlert = null;		//�ÿյ�ǰ��Ϸ��ʾ
			}			
		}
		return true;
	}	
}