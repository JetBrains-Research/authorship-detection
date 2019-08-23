package wyf.ytl.entity;
/*
 * ��ׯ��������ļ����
 */
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_HEIGHT;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_SPAN;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_START_X;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_START_Y;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_WIDTH;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_WORD_LEFT;
import static wyf.ytl.tool.ConstantUtil.DIALOG_BTN_WORD_UP;
import static wyf.ytl.tool.ConstantUtil.DIALOG_START_Y;
import static wyf.ytl.tool.ConstantUtil.DIALOG_WORD_SIZE;

import java.io.Serializable;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class VillageDrawable extends MyMeetableDrawable implements Serializable{
	String [] dialogMessage={//�Ի�����ʾ����
			"ǰ����һ����ׯ���Ƿ���ļ������Ԥ�����Ľ�Ǯ15000��",
			"ǰ����һ����ׯ��������������ͷ�Ľ�ʿ�Ƕ�û�þ��÷��ˣ��ͱ���������ļ�����˰ɡ�",
			"�ɹ���ļ������xx�ˡ�",
			"������ԶԶ���������ţ�������������һ����Ӱ��û���š�"
		};
	int cost = 15000;//����
	int status=-1;//״̬λ��Ϊ0��ʾ��ʾ�Ƿ��б���Ϊ1��ʾ��Ǯ�б�,Ϊ2��ʾ��ʾ�б��ɹ���Ϊ3��ʾ�б�ʧ��
	int maxNumber = 8000;//ÿ�������ļ8000
	float failOdd = 0.3f;//������֮һ�ĸ����в��ϱ�
	
	public VillageDrawable(){}
	
	public VillageDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,
			int [][] meetableMatrix) {
		super(bmpSelf, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, bmpDialogBack, bmpDialogButton);
	}
	@Override
	public void drawDialog(Canvas canvas, Hero hero) {
		String showString = null;//��Ҫ��ʾ���Ի����е��ַ���
		tempHero = hero;
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		//���ó�ʼ״̬λ
		if(status == -1){
			if(tempHero.getTotalMoney() < cost){//��Ǯ������
				status = 1;
			}
			else{//�����Ǯ����
				status = 0;
			}			
		}
		showString = dialogMessage[status];//ȷ��Ҫ��ʾ���ַ���
		drawString(canvas, showString);
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
		//��ȡ����ť
		if(status == 0){//�鿴�Ƿ���Ҫ���ڶ���ȡ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("ȡ��", 
					DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȷ����
				switch(status){
				case 0://��Ǯ����ׯʱ��ȷ�������ׯ
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//��Ǯ
					recruitTroops();//��ļ����
					break;
				case 1://��Ǯ�б�ʱ��ҵ�ȷ�Ϸ���
				case 2://�б� �ɹ�
				case 3://�б�ʧ��
					recoverGame();
					break;
				}				
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȡ����
				recoverGame();
			}			
		}
		return true;
	}
	//�������������������ظ���Ϸ״̬
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//����������
		tempHero.father.setCurrentDrawable(null);//�ÿռ�¼���õı���
		tempHero.father.setStatus(0);//��������GameViewΪ����״̬
		tempHero.father.gvt.setChanging(true);//����ת����
		status = -1;
	}
	//��������ļ����
	public void recruitTroops(){
		if(Math.random()<failOdd){//��һ�������в��ϱ�
			status = 3;
		} 
		else{//�����˱�
			status = 2;
			int result = 0;
			if(Math.random()<0.4){//�м����в�����ô��
				result = 5000;
			}
			else{
				result = maxNumber;
			}
			tempHero.setArmyWithMe(tempHero.getArmyWithMe()+result);
			dialogMessage[2] = dialogMessage[2].replaceFirst("xx", result+"");
		}
	}	
}