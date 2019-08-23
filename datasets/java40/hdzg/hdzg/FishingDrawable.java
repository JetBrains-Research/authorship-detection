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
import static wyf.ytl.tool.ConstantUtil.FISHING;

import java.io.Serializable;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class FishingDrawable extends MyMeetableDrawable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8963531040983837253L;
	String dialogMessage[] = {//�Ի������ʾ��Ϣ����һ���ǿ��Բ��㣬�ڶ����ǲ����Բ�����ʾ��
			"�˴�ˮ�����֣����б�Ȼ������㱴���Ƿ��㣿Ԥ����������xx���ջ�yy��",
			"�˴��Ǹ�����ĺõط����������������������㣬ֻ�ø���������"
		};
	int result;//��¼Ӣ�ۿ��Ի�õ�����
	Hero tempHero;//��ʱ��¼Ӣ��
	int status;//״̬��0����ʾ�Ƿ�ľ��ѡ��Ի���1����ʾ���ܷ�ľ����ʾ�Ի���
	
	public FishingDrawable(){}
	
	public FishingDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
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
		Skill skill = hero.heroSkill.get(FISHING);//���Ӣ�۵ķ�ľ����
		result = skill.calculateResult();
		if(result == -1){//����ֵ������
			status = 1;//����״̬λΪ���ܲ���
			showString = dialogMessage[status];
		}
		else{//�������ֵ��
			status = 0;
			showString = dialogMessage[status];
			showString = showString.replaceAll("xx", skill.strengthCost+"");//�滻�����������ַ���
			showString = showString.replaceFirst("yy", result+"");//�滻��Ԥ���ջ��Ǯ�����ַ���
		}
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
				case 0://��ľ�ɷ�ʱ���ȷ�ϲ���
					tempHero.heroSkill.get(FISHING).useSkill(result);
					break;
				case 1://��ľ�ɷ�ʱ���ȷ�Ϸ���
					break;
				}
				recoverGame();
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȡ����
				recoverGame();
			}			
		}
		return true;
	}
	//�������������������ָ���Ϸ״̬
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//����������
		tempHero.father.setCurrentDrawable(null);//�ÿռ�¼���õı���
		tempHero.father.setStatus(0);//��������GameViewΪ����״̬
		tempHero.father.gvt.setChanging(true);//����ת����
	}
}
