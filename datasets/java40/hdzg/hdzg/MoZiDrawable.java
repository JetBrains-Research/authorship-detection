package wyf.ytl.entity;
/*
 * ī�ӣ���������м��ʽ̻�Ӣ�۽������
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

public class MoZiDrawable extends MyMeetableDrawable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7466623586350851099L;
	String [] dialogMessage={//�Ի�����ʾ����
			"ǰ����ī�ӵ�ס�����Ƿ�ǰȥ�ݷã�Ԥ�����Ľ�Ǯ3500��",
			"ǰ����ī�ӵ�ס����������ûǮ׼����Ʒ���´������ݷðɡ�",
			"ī�ӱ���ĳ���ж������Ǻ�������Ӧ���㽨�����������ĳǳط�������",
			"��ȥ�ݷ�ī�ӣ���������������Զ���ˡ�",			
			"ī��˵�����ϴΰ��㽨��ļ��⻹û�깤�أ��������ô����"
		};
	int cost = 5000;//����
	int status=-1;//״̬λ��Ϊ0��ʾ��ʾ�Ƿ�ݷã�Ϊ1��ʾ��ʾ����ȥ�ݷ�,Ϊ2��ʾ�ݷóɹ���Ϊ3��ʾ�ݷ�ʧ��,Ϊ4��ʾ֮ǰ��ս����û�����
	int projectNumber = 3;//���������
	
	
	public MoZiDrawable(){}
	
	public MoZiDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
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
				case 0://��Ǯ��ʱ���ȷ�Ͻ�
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//��Ǯ
					buildArrowTower();//�м���ΪӢ�۽������
					break;
				case 1://��Ǯ��ʱ���ȷ�Ϸ����ݷ�
				case 2://�ݷóɹ�
				case 3://�ݷ�ʧ��
				case 4://�ݷóɹ������ǽ���ʧ�ܣ�ԭ����֮ǰ�Ļ����ڽ���
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
	//������ΪӢ�۽������
	public void buildArrowTower(){
		if(Math.random()<0.4){//��һ�����ʲ��ڼң�����Ӣ�۽���
			status=3; //�ݷ�ʧ��
		}
		else{
			boolean isUnderConstruction = false;
			if(tempHero.researchList.size() != 0){
				for(Research research:tempHero.researchList){
					if(research.researchProject == 1){//���Ŀǰ���ڽ�����⣬0��ս����1�Ǽ���
						isUnderConstruction = true;
						break;
					}
				}
			}
			if(isUnderConstruction){//������ڽ������
				status = 4;
			}
			else{//׼���������
				status = 2;
				Research r = new Research("ī��", 1, projectNumber);
				tempHero.getResearchList().add(r);
			}
		}
	}

}
