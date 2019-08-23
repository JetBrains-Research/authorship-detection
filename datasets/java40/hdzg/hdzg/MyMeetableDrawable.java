package wyf.ytl.entity;				//声明包语句
import static wyf.ytl.tool.ConstantUtil.*;

import java.io.Externalizable;			//引入相关类
import java.io.IOException;				//引入相关类
import java.io.ObjectInput;				//引入相关类
import java.io.ObjectOutput;				//引入相关类

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.view.GameView;
import android.graphics.Bitmap;				//引入相关类
import android.graphics.BitmapFactory;		//引入相关类
import android.graphics.Canvas;				//引入相关类
import android.graphics.Paint;				//引入相关类
import android.view.MotionEvent;			//引入相关类
import android.view.View;					//引入相关类

public abstract class MyMeetableDrawable extends MyDrawable implements View.OnTouchListener, Externalizable {
	public int [][] meetableMatrix;//可遇矩阵，相对于本MyDrawable所占的块数
	public Bitmap bmpDialogBack;//对话框背景图片
	public Bitmap bmpDialogButton;//对话框的按钮背景图片
	Hero tempHero;//英雄的引用,用于拷贝数据防止污染

	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(meetableMatrix);//可遇矩阵，相对于本MyDrawable所占的块数

	}
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		meetableMatrix = (int[][]) in.readObject();
		Bitmap b = BitmapFactory.decodeResource(GameView.resources, R.drawable.buttons);
		this.bmpDialogButton  = Bitmap.createBitmap(b, 0, 0, 60, 30);
		b = null;
		bmpDialogBack = BitmapFactory.decodeResource(GameView.resources, R.drawable.dialog_back);
	}
	//构造器
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
	//在游戏屏幕上绘制对话框的方法
	public abstract void drawDialog(Canvas canvas,Hero hero);
	//绘制给定的字符串到对话框上
	public void drawString(Canvas canvas,String string){
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTextSize(DIALOG_WORD_SIZE);//设置文字大小
		int lines = string.length()/DIALOG_WORD_EACH_LINE+(string.length()%DIALOG_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//如果是最后一行那个不太整的汉字
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
