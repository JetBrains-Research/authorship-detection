package wyf.ytl.entity;
/*
 * 村庄，可以招募兵丁
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

public class VillageDrawable extends MyMeetableDrawable implements Serializable{
	String [] dialogMessage={//对话框显示文字
			"前方是一个村庄，是否招募兵丁？预计消耗金钱15000。",
			"前方是一个村庄，不过我们连手头的将士们都没得军饷发了，就别想着再招募兵丁了吧。",
			"成功招募到兵丁xx人。",
			"村民们远远看到你的旗号，都躲了起来，一个人影都没见着。"
		};
	int cost = 15000;//花费
	int status=-1;//状态位，为0表示显示是否招兵，为1表示无钱招兵,为2表示显示招兵成功，为3表示招兵失败
	int maxNumber = 8000;//每次最多招募8000
	float failOdd = 0.3f;//有三分之一的概率招不上兵
	
	public VillageDrawable(){}
	
	public VillageDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
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
				case 0://有钱进村庄时点确定进入村庄
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//交钱
					recruitTroops();//招募军队
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
		status = -1;
	}
	//方法：招募军队
	public void recruitTroops(){
		if(Math.random()<failOdd){//有一定概率招不上兵
			status = 3;
		} 
		else{//招上了兵
			status = 2;
			int result = 0;
			if(Math.random()<0.4){//有几率招不到那么多
				result = 5000;
			}
			else{
				result = maxNumber;
			}
			tempHero.setArmyWithMe(tempHero.getArmyWithMe()+result);
			dialogMessage[2] = dialogMessage[2].replaceFirst("xx", result+"");
		}
	}	
}