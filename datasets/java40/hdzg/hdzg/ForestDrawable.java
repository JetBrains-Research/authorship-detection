package wyf.ytl.entity;							//声明包语句
import static wyf.ytl.tool.ConstantUtil.*;

import java.io.Serializable;				//引入相关类

import android.graphics.Bitmap;				//引入相关类
import android.graphics.Canvas;				//引入相关类
import android.graphics.Paint;				//引入相关类
import android.graphics.Typeface;			//引入相关类
import android.view.MotionEvent;			//引入相关类
import android.view.View;					//引入相关类

public class ForestDrawable extends MyMeetableDrawable implements View.OnTouchListener, Serializable{
	public ForestDrawable(){}
	private static final long serialVersionUID = 1173248855453483373L;
	String dialogMessage[] = {//对话框的提示信息，第一个是可以狩猎，第二个是不可以狩猎显示的
		"此处树林阴翳，林中必然有许多参天大树。是否伐木？预计消耗体力xx，收获yy金。",
		"此处是个伐木的好地方，怎奈人困马乏，体力不足，只好改日再来！"
	};
	int result;//记录英雄可以获得的收益
	int status;//状态，0：显示是否伐木的选择对话框，1：显示不能伐木的提示对话框
	//构造器
	public ForestDrawable
			(
			Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,
			int [][] meetableMatrix
			){
		super(bmpSelf, col, row, width, height, refCol, refRow, 
				noThrough, meetable, meetableMatrix,bmpDialogBack,bmpDialogButton);
	}
	@Override//绘制对话框的方法
	public void drawDialog(Canvas canvas,Hero hero) {
		String showString = null;//需要显示到对话框中的字符串
		tempHero = hero;
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		Skill skill = hero.heroSkill.get(LUMBER);//获得英雄的伐木技能
		result = skill.calculateResult();	//计算英雄的收益
		if(result == -1){//体力值不够了
			status = 1;								//设置对话框状态
			showString = dialogMessage[status];		//设置对话框显示的文本信息
		}
		else{//如果体力值够
			status = 0;								//设置对话框状态
			showString = dialogMessage[status];		//设置对话框显示的文本信息
			showString = showString.replaceAll("xx", skill.strengthCost+"");//替换消耗体力的字符串
			showString = showString.replaceFirst("yy", result+"");//替换掉预计收获金钱数的字符串
		}
		drawString(canvas, showString);				//在对话框中绘制信息
		//画按钮确定按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();				//创建画笔对象
		paint.setARGB(255, 42, 48, 103);		//设置画笔颜色
		paint.setAntiAlias(true);				//设置抗锯齿
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));	//设置字体
		paint.setTextSize(18);				//设置字号
		canvas.drawText("确定",				//绘制“确定”文字
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);
		//画取消按钮
		if(status == 0){//查看是否需要画第二个取消按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X
					+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("取消", 			//绘制“取消”文字
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
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
				switch(status){
				case 0://有木可伐时玩家确认伐木
					tempHero.heroSkill.get(LUMBER).useSkill(result);
					break;
				case 1://无木可伐时玩家确认放弃
					break;
				}
				recoverGame();	//恢复游戏状态
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是取消键
				recoverGame();
			}			
		}
		return true;
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//返还监听器
		tempHero.father.setCurrentDrawable(null);//置空记录引用的变量
		tempHero.father.setStatus(0);//重新设置GameView为待命状态
		tempHero.father.gvt.setChanging(true);//骰子转起来
	}
}