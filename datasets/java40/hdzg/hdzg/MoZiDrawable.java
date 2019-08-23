package wyf.ytl.entity;
/*
 * 墨子，遇到后会有几率教会英雄建造箭垛
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
	String [] dialogMessage={//对话框显示文字
			"前方是墨子的住所，是否前去拜访？预计消耗金钱3500。",
			"前方是墨子的住所，不过你没钱准备礼品，下次再来拜访吧。",
			"墨子被你的诚意感动，于是乎，他答应帮你建造箭垛增加你的城池防御力。",
			"你去拜访墨子，不过碰巧他出门远游了。",			
			"墨子说，我上次帮你建造的箭垛还没完工呢，干嘛催这么急？"
		};
	int cost = 5000;//花费
	int status=-1;//状态位，为0表示显示是否拜访，为1表示显示不能去拜访,为2表示拜访成功，为3表示拜访失败,为4表示之前的战车还没造好呢
	int projectNumber = 3;//建造的数量
	
	
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
				case 0://有钱进时玩家确认进
					tempHero.setTotalMoney(tempHero.getTotalMoney() - cost);//交钱
					buildArrowTower();//有几率为英雄建造箭垛
					break;
				case 1://无钱进时玩家确认放弃拜访
				case 2://拜访成功
				case 3://拜访失败
				case 4://拜访成功，但是建造失败，原因是之前的还正在建造
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
	//方法：为英雄建造箭垛
	public void buildArrowTower(){
		if(Math.random()<0.4){//有一定几率不在家，不帮英雄建造
			status=3; //拜访失败
		}
		else{
			boolean isUnderConstruction = false;
			if(tempHero.researchList.size() != 0){
				for(Research research:tempHero.researchList){
					if(research.researchProject == 1){//如果目前正在建造箭垛，0是战车，1是箭垛
						isUnderConstruction = true;
						break;
					}
				}
			}
			if(isUnderConstruction){//如果正在建造箭垛
				status = 4;
			}
			else{//准备建造箭垛
				status = 2;
				Research r = new Research("墨子", 1, projectNumber);
				tempHero.getResearchList().add(r);
			}
		}
	}

}
