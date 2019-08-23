package wyf.ytl.entity;							//���������
import static wyf.ytl.tool.ConstantUtil.*;

import java.io.Serializable;				//���������

import android.graphics.Bitmap;				//���������
import android.graphics.Canvas;				//���������
import android.graphics.Paint;				//���������
import android.graphics.Typeface;			//���������
import android.view.MotionEvent;			//���������
import android.view.View;					//���������

public class ForestDrawable extends MyMeetableDrawable implements View.OnTouchListener, Serializable{
	public ForestDrawable(){}
	private static final long serialVersionUID = 1173248855453483373L;
	String dialogMessage[] = {//�Ի������ʾ��Ϣ����һ���ǿ������ԣ��ڶ����ǲ�����������ʾ��
		"�˴��������裬���б�Ȼ��������������Ƿ�ľ��Ԥ����������xx���ջ�yy��",
		"�˴��Ǹ���ľ�ĺõط����������������������㣬ֻ�ø���������"
	};
	int result;//��¼Ӣ�ۿ��Ի�õ�����
	int status;//״̬��0����ʾ�Ƿ�ľ��ѡ��Ի���1����ʾ���ܷ�ľ����ʾ�Ի���
	//������
	public ForestDrawable
			(
			Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,
			int [][] meetableMatrix
			){
		super(bmpSelf, col, row, width, height, refCol, refRow, 
				noThrough, meetable, meetableMatrix,bmpDialogBack,bmpDialogButton);
	}
	@Override//���ƶԻ���ķ���
	public void drawDialog(Canvas canvas,Hero hero) {
		String showString = null;//��Ҫ��ʾ���Ի����е��ַ���
		tempHero = hero;
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		Skill skill = hero.heroSkill.get(LUMBER);//���Ӣ�۵ķ�ľ����
		result = skill.calculateResult();	//����Ӣ�۵�����
		if(result == -1){//����ֵ������
			status = 1;								//���öԻ���״̬
			showString = dialogMessage[status];		//���öԻ�����ʾ���ı���Ϣ
		}
		else{//�������ֵ��
			status = 0;								//���öԻ���״̬
			showString = dialogMessage[status];		//���öԻ�����ʾ���ı���Ϣ
			showString = showString.replaceAll("xx", skill.strengthCost+"");//�滻�����������ַ���
			showString = showString.replaceFirst("yy", result+"");//�滻��Ԥ���ջ��Ǯ�����ַ���
		}
		drawString(canvas, showString);				//�ڶԻ����л�����Ϣ
		//����ťȷ����ť
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();				//�������ʶ���
		paint.setARGB(255, 42, 48, 103);		//���û�����ɫ
		paint.setAntiAlias(true);				//���ÿ����
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));	//��������
		paint.setTextSize(18);				//�����ֺ�
		canvas.drawText("ȷ��",				//���ơ�ȷ��������
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);
		//��ȡ����ť
		if(status == 0){//�鿴�Ƿ���Ҫ���ڶ���ȡ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X
					+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("ȡ��", 			//���ơ�ȡ��������
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
				case 0://��ľ�ɷ�ʱ���ȷ�Ϸ�ľ
					tempHero.heroSkill.get(LUMBER).useSkill(result);
					break;
				case 1://��ľ�ɷ�ʱ���ȷ�Ϸ���
					break;
				}
				recoverGame();	//�ָ���Ϸ״̬
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
	}
}