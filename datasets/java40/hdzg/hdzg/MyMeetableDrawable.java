package wyf.ytl.entity;				//���������
import static wyf.ytl.tool.ConstantUtil.*;

import java.io.Externalizable;			//���������
import java.io.IOException;				//���������
import java.io.ObjectInput;				//���������
import java.io.ObjectOutput;				//���������

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.view.GameView;
import android.graphics.Bitmap;				//���������
import android.graphics.BitmapFactory;		//���������
import android.graphics.Canvas;				//���������
import android.graphics.Paint;				//���������
import android.view.MotionEvent;			//���������
import android.view.View;					//���������

public abstract class MyMeetableDrawable extends MyDrawable implements View.OnTouchListener, Externalizable {
	public int [][] meetableMatrix;//������������ڱ�MyDrawable��ռ�Ŀ���
	public Bitmap bmpDialogBack;//�Ի��򱳾�ͼƬ
	public Bitmap bmpDialogButton;//�Ի���İ�ť����ͼƬ
	Hero tempHero;//Ӣ�۵�����,���ڿ������ݷ�ֹ��Ⱦ

	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(meetableMatrix);//������������ڱ�MyDrawable��ռ�Ŀ���

	}
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		meetableMatrix = (int[][]) in.readObject();
		Bitmap b = BitmapFactory.decodeResource(GameView.resources, R.drawable.buttons);
		this.bmpDialogButton  = Bitmap.createBitmap(b, 0, 0, 60, 30);
		b = null;
		bmpDialogBack = BitmapFactory.decodeResource(GameView.resources, R.drawable.dialog_back);
	}
	//������
	public MyMeetableDrawable(){}
	public MyMeetableDrawable(
			Bitmap bmpSelf,int col,int row,int width,int height,
			int refCol,int refRow,int [][] noThrough,boolean meetable,int [][] meetableMatrix,
			Bitmap bmpDialogBack,Bitmap bmpDialogButton
			){
		super(bmpSelf,meetable,width,height,col,row,refCol,refRow,noThrough);
		this.meetableMatrix = meetableMatrix;
		this.bmpDialogBack = bmpDialogBack;
		this.bmpDialogButton = bmpDialogButton;
	}
	//����Ϸ��Ļ�ϻ��ƶԻ���ķ���
	public abstract void drawDialog(Canvas canvas,Hero hero);
	//���Ƹ������ַ������Ի�����
	public void drawString(Canvas canvas,String string){
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		paint.setAntiAlias(true);//�����
		paint.setTextSize(DIALOG_WORD_SIZE);//�������ִ�С
		int lines = string.length()/DIALOG_WORD_EACH_LINE+(string.length()%DIALOG_WORD_EACH_LINE==0?0:1);//�����Ҫ����������
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//��������һ���Ǹ���̫���ĺ���
				str = string.substring(i*DIALOG_WORD_EACH_LINE);
			}
			else{
				str = string.substring(i*DIALOG_WORD_EACH_LINE, (i+1)*DIALOG_WORD_EACH_LINE);
			}
			canvas.drawText(str, DIALOG_WORD_START_X, DIALOG_WORD_START_Y+DIALOG_WORD_SIZE*i, paint);
		}
	}
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return false;
	}	
}
