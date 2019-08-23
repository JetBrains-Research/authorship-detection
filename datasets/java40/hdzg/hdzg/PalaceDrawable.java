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

public class PalaceDrawable extends MyMeetableDrawable implements Serializable{
	String [] dialogMessage={//对话框显示文字
			"前方是皇宫，是否朝见天子，进贡讨取一个封号？预计消耗金钱100000。",
			"前方是皇宫，不过你的钱太少了，还是不要把钱花在这种沽名上吧。",
			"皇帝见到你的贡品，龙颜大悦，封你为xx。",
		};
	int cost = 100000;
	int status = -1;
	
	public PalaceDrawable(){}
	
	public PalaceDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
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
				case 0://有钱进贡玩家确认进贡
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//交钱
					upgradeHeroTitle();//为玩家升级封号
					cost += 100000;//下次封号所需的金钱增加
					break;
				case 1://无钱进贡时玩家确认放弃
				case 2://已经收到新封号
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
	//方法：为英雄加封号
	public void upgradeHeroTitle(){
		status = 2;
		tempHero.setTitle(tempHero.title+1);
	}
}
