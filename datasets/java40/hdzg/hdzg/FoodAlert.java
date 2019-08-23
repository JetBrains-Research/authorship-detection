package wyf.ytl.view;			//���������

import static wyf.ytl.tool.ConstantUtil.*;
import wyf.ytl.entity.CityDrawable;
import android.graphics.Bitmap;				//���������
import android.graphics.Canvas;				//���������
import android.graphics.Paint;				//���������
import android.view.MotionEvent;			//���������
import android.view.View;					//���������

public class FoodAlert extends GameAlert{
	 CityDrawable city;//��¼���ĸ��ǳ�����Σ��
	 String alertMessage = "�ղ�xx���ؽ���������,˵�����Ѿ�������,�Ƿ񻮲�����?";
	
	
	//������
	public FoodAlert(GameView gameView,CityDrawable city,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		super(gameView,bmpDialogBack, bmpDialogButton);
		this.city = city;
	}
	@Override
	public void drawDialog(Canvas canvas) {
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);//���Ի��򱳾�\\
		alertMessage=alertMessage.replaceFirst("xx", city.getCityName());//�滻��ʵ�ʵĳ�������
		drawString(canvas, alertMessage);//����ʾ�ı�
		Paint paint = new Paint();//��������
		paint.setTextSize(DIALOG_WORD_SIZE);//���������С
		paint.setAntiAlias(true);//���ÿ����
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		//��������ť
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);//����ťͼƬ
		canvas.drawText("����", //����ť�ϵ�����
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint);
		//�����԰�ť
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
		canvas.drawText("����",//����ť�ϵ�����
				DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint);
	}
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//�¼�Ϊ������Ļ
			int x = (int)event.getX();//��ȡ��Ļ���µ�x����
			int y = (int)event.getY();//��ȡ��Ļ���µ�y����
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȷ����
				gameView.setStatus(97);//�����Լ��ǳع������
				gameView.setCurrentGameAlert(null);
				gameView.setOnTouchListener(gameView);
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȡ����
				gameView.setCurrentGameAlert(null);
				gameView.setStatus(0);//��Ϊ��������
				gameView.setOnTouchListener(gameView);
			}
		}
		return true;
	}
	
}