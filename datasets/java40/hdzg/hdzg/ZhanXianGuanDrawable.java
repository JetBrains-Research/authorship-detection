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

public class ZhanXianGuanDrawable extends MyMeetableDrawable implements Serializable{
	String [] dialogMessage={//对话框显示文字
			"前方是一个小酒馆，要不要进去坐坐？有可能结识一些英雄豪杰哟，预计花费15000。",
			"前方是一个小酒馆，不过最近手头紧，就别想着煮酒论英雄了吧。",
			"你在酒馆中遇到了xx将军，你们谈的很投机，他决定跟随你打天下。",
			"你在酒馆喝得酩酊大醉，谁也没见着。",
			"你已成大业，天下能臣良将尽数都归了你，酒馆里也碰不到更厉害的人才了。"
		};
	int cost = 15000;//花费
	int status=-1;//状态位，为0表示显示是否去酒馆，为1表示无钱去酒馆,为2表示找到了一个将军，为3表示白去了
	float failOdd = 0.3f;//有三分之一的概率招不上将军
	
	public ZhanXianGuanDrawable(){}
	
	public ZhanXianGuanDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,
			int [][] meetableMatrix) {
		super(bmpSelf, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, bmpDialogBack, bmpDialogButton);
	}
	@Override
	public void drawDialog(Canvas canvas, Hero hero) {
		String showString = null;//需要显示到对话框中的字符串
		tempHero = hero;
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		//设置初始状态位
		if(status == -1){
			if(tempHero.getTotalMoney() < cost){//金钱不够了
				status = 1;
			}
			else{//如果金钱还够
				status = 0;
			}			
		}
		showString = dialogMessage[status];//确定要显示的字符串
		drawString(canvas, showString);
		//画按钮确定按钮
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
		paint.setTextSize(18);
		canvas.drawText("确定",
				DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
				DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
				paint
				);
		//画取消按钮
		if(status == 0){//查看是否需要画第二个取消按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("取消", 
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
				case 0://有钱时确认
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//交钱
					searchGeneral();//酒馆寻将
					break;
				case 1://无钱招兵时玩家点确认放弃
				case 2://招兵 成功
				case 3://招兵失败
					recoverGame();
					break;
				}				
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
	//方法：在酒馆寻找将领
	public void searchGeneral(){
		if(Math.random() < failOdd){//有一定几率碰不到将军
			status = 3;
		}
		else{			
			int size = tempHero.father.freeGeneral.size();
			if(size == 0){//碰到了，但是无将军可遇了，都是自己的了
				status = 4;
			}
			else{//还有将军可遇
				status = 2;//设置状态位
				int index = (int)(Math.random()*size);//随机选个将军
				General g = tempHero.father.freeGeneral.get(index);
				dialogMessage[2] = dialogMessage[2].replaceFirst("xx", g.getName());
				tempHero.getGeneralList().add(g);//添加新将军
				tempHero.father.freeGeneral.remove(g);//从自由将军列表中去掉将军
			}
			
		}
	}
}
