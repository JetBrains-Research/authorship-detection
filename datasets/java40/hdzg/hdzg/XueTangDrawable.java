package wyf.ytl.entity;

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

public class XueTangDrawable extends MyMeetableDrawable implements Serializable{
	String [] dialogMessage={
			"ǰ����һ��ѧ�ã��Ƿ��ý������ڴ�ѧϰһ����Ԥ�����Ľ�Ǯ1500��",
			"�������һ��ѧ�ã�������Ľ�Ǯ̫���ˣ������˲������ȥ��",
			"xx����ͨ����ѧ��ѧϰһ�������������yy�㡣",
			"xx������ѧ������һ�죬ʲôҲûѧ�ᡣ"
		};
	int cost = 1500;//����
	int status=-1;//״̬λ��Ϊ0��ʾ��ʾ�Ƿ��ȥ��Ϊ1��ʾ��ʾ���ܽ�ȥ,Ϊ2��ʾ��ʾ��ѵ���
	int intelligenceIncrement = 3;//ÿ���������3��
	
	public XueTangDrawable(){}
	
	public XueTangDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
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
				case 0://��Ǯ����ʱ���ȷ�Ͻ���
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//��Ǯ
					upgradeGeneralAgility();//�����ѡһ��������������
					break;
				case 1://��Ǯ��ѧ��ʱ��ҵ�ȷ�Ϸ���
				case 2://�Ѿ�����ѧ��
				case 3://ѧϰʧ��
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
	//�������������������ݶ�
	public void upgradeGeneralAgility(){
		int size = tempHero.generalList.size();
		int r = (int)(Math.random()*100)%size;//��������λ�����������
		General g = tempHero.generalList.get(r);		
		int increment = (int)(Math.random()*(intelligenceIncrement+1));//�����ȡ����
		if(increment == 0){//���ʲô��ûѧ��
			status = 3;
			dialogMessage[3] = dialogMessage[3].replaceFirst("xx", g.getName());//�滻�Ի��������
		}
		else{//���ѧ������
			status = 2;
			g.setIntelligence(g.getIntelligence()+increment);//����Ӣ������
			dialogMessage[2] = dialogMessage[2].replaceFirst("xx", g.getName());//�滻�Ի��������
			dialogMessage[2] = dialogMessage[2].replaceFirst("yy", increment+"");//�滻�Ի��������			
		}

	}
	//�������������������ظ���Ϸ״̬
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//����������
		tempHero.father.setCurrentDrawable(null);//�ÿռ�¼���õı���
		tempHero.father.setStatus(0);//��������GameViewΪ����״̬
		tempHero.father.gvt.setChanging(true);//����ת����
		status = -1;//״̬��λ
	}
}