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
	String [] dialogMessage={//对话框显示文字
			"前方是孙武的住所，是否前去拜访？预计消耗金钱3500。",
			"前方是孙武的住所，不过你现在没什么钱买礼品，下次再来拜访吧。",
			"你去拜访孙武，不过碰巧他不在家。",
			"孙武被你的诚意感动，于是乎，他传授了你一些xx的兵法。",
			"老夫已经平生所学尽数传授与你，剩下的就需要靠你自己去领悟了。"
		};
	int cost = 3500;//花费
	int status=-1;//状态位，为0表示显示是否进去，为1表示显示不能进去,为2表示拜访成功，为3表示拜访失败
	String skillName;//暂时存放新学到的技能
	
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
		String showString = null;//需要显示到对话框中的字符串
		tempHero = hero;
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		//设置初始状态位
		if(status == -1){
			if(tempHero.getTotalMoney() < cost){//没钱买什么礼品
				status = 1;
			}
			else{//孙武在家
				status = 0;
			}			
		}		
		showString = dialogMessage[status];//确定要显示的字符串
		if(status == 3){//当前状态时向玩家提示学到了什么技能
			showString = showString.replaceFirst("xx",skillName);
		}
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
				case 0://有钱进时玩家确认进
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//交钱					
					teachSkill();//随机挑选技能教会英雄
					break;
				case 1://无钱进时玩家确认放弃
				case 2://已经进要出来
				case 3://学习失败
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
		status = -1;//状态复位
	}
	//方法：教会英雄一项技能
	public void teachSkill(){
		if(Math.random()<0.4){//有一定几率不教英雄
			status=2; //拜访失败
		}
		else{
			status = 3;//拜访成功
			int size = tempHero.father.skillToLearn.size();
			if(size == 0){
				status = 4;//拜访成功，但是无技能可学了
			}
			else{//还有技能可学
				int index = (int)(Math.random()*size);//随机选取个技能来学习
				Skill skill = tempHero.father.skillToLearn.get(index);
				this.skillName = skill.getName();
				tempHero.heroSkill.put(skill.id, skill);
				tempHero.father.skillToLearn.remove(skill);
			}
		}
	}
}
