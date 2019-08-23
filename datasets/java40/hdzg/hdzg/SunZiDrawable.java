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

public class SunZiDrawable extends MyMeetableDrawable implements Serializable{	
	String [] dialogMessage={//�Ի�����ʾ����
			"ǰ���������ס�����Ƿ�ǰȥ�ݷã�Ԥ�����Ľ�Ǯ3500��",
			"ǰ���������ס��������������ûʲôǮ����Ʒ���´������ݷðɡ�",
			"��ȥ�ݷ����䣬�������������ڼҡ�",
			"���䱻��ĳ���ж������Ǻ�������������һЩxx�ı�����",
			"�Ϸ��Ѿ�ƽ����ѧ�����������㣬ʣ�µľ���Ҫ�����Լ�ȥ�����ˡ�"
		};
	int cost = 3500;//����
	int status=-1;//״̬λ��Ϊ0��ʾ��ʾ�Ƿ��ȥ��Ϊ1��ʾ��ʾ���ܽ�ȥ,Ϊ2��ʾ�ݷóɹ���Ϊ3��ʾ�ݷ�ʧ��
	String skillName;//��ʱ�����ѧ���ļ���
	
	public SunZiDrawable(){}
	
	public SunZiDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,
			int [][] meetableMatrix) {
		super(bmpSelf, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, bmpDialogBack, bmpDialogButton);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void drawDialog(Canvas canvas, Hero hero) {
		String showString = null;//��Ҫ��ʾ���Ի����е��ַ���
		tempHero = hero;
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		//���ó�ʼ״̬λ
		if(status == -1){
			if(tempHero.getTotalMoney() < cost){//ûǮ��ʲô��Ʒ
				status = 1;
			}
			else{//�����ڼ�
				status = 0;
			}			
		}		
		showString = dialogMessage[status];//ȷ��Ҫ��ʾ���ַ���
		if(status == 3){//��ǰ״̬ʱ�������ʾѧ����ʲô����
			showString = showString.replaceFirst("xx",skillName);
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
				case 0://��Ǯ��ʱ���ȷ�Ͻ�
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//��Ǯ					
					teachSkill();//�����ѡ���̻ܽ�Ӣ��
					break;
				case 1://��Ǯ��ʱ���ȷ�Ϸ���
				case 2://�Ѿ���Ҫ����
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
	//�������������������ظ���Ϸ״̬
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//����������
		tempHero.father.setCurrentDrawable(null);//�ÿռ�¼���õı���
		tempHero.father.setStatus(0);//��������GameViewΪ����״̬
		tempHero.father.gvt.setChanging(true);//����ת����
		status = -1;//״̬��λ
	}
	//�������̻�Ӣ��һ���
	public void teachSkill(){
		if(Math.random()<0.4){//��һ�����ʲ���Ӣ��
			status=2; //�ݷ�ʧ��
		}
		else{
			status = 3;//�ݷóɹ�
			int size = tempHero.father.skillToLearn.size();
			if(size == 0){
				status = 4;//�ݷóɹ��������޼��ܿ�ѧ��
			}
			else{//���м��ܿ�ѧ
				int index = (int)(Math.random()*size);//���ѡȡ��������ѧϰ
				Skill skill = tempHero.father.skillToLearn.get(index);
				this.skillName = skill.getName();
				tempHero.heroSkill.put(skill.id, skill);
				tempHero.father.skillToLearn.remove(skill);
			}
		}
	}
}
