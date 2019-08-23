package wyf.ytl.view;

import static wyf.ytl.tool.ConstantUtil.*;
import wyf.ytl.entity.CityDrawable;
import wyf.ytl.tool.ConstantUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class WarAlert extends GameAlert{
	CityDrawable enemy;//�����Լ����Ǹ��ǳ�
	CityDrawable self;//���������Լ����Ǹ��ǳ�
	String [] alertMessage = {
			"��̽��������פ��xxyy�ǳص�zz��������������Ϯ��ĳ���aa����ѡ��",
			"������ʿ�������ֿ������˾ù����£����ڳ����ˡ�",
			"�ؾ��������أ��������ڱ�������̫����ĳǳر������ˣ�xx����Ҳ����Ͷ���˵��ˡ�"
	};
	int status = 0;//0��ʾѯ�ʣ�1��ʾ��ʾ���Ϊ��ס��2��ʾ������
	public WarAlert(GameView gameView, Bitmap bmpDialogBack,
			Bitmap bmpDialogButton,CityDrawable enemy,CityDrawable self) {
		super(gameView, bmpDialogBack, bmpDialogButton);
		this.enemy = enemy;
		this.self = self;
	}

	@Override
	public void drawDialog(Canvas canvas) {
		String showString = null;
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		showString = alertMessage[status];
		if(status == 0){//ѯ��״̬
			showString = showString.replaceFirst("xx", ConstantUtil.COUNTRY_NAME[enemy.getCountry()]);
			showString = showString.replaceFirst("yy", enemy.getCityName());
			showString = showString.replaceFirst("zz", enemy.getGuardGeneral().get(0).getName());
			showString = showString.replaceFirst("aa", self.getCityName());
		}
		if(status == 2){
			showString = showString.replaceFirst("xx", self.getGuardGeneral().get(0).getName());
		}
		drawString(canvas, showString);
		//����ťȷ����ť
		canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
		paint.setTextSize(18);
		if(status == 0){
			canvas.drawText("��ս",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);			
			//�鿴�Ƿ���Ҫ���ڶ���ȡ����ť//��ȡ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("����", 
					DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);						
		}
		else {//����״ֻ̬����ȷ����ť������
			canvas.drawText("ȷ��",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);	
		}
	}

	public boolean onTouch(View view, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȷ����
				switch(status){
				case 0://�����Ҫ��ս���е�����ȥ
					gameView.setStatus(4);//��ʾս������
					gameView.battleField.setCity(self.getCityName());
					gameView.battleField.startAnimation();//����ս������
					status = calculateWinOrLose(enemy,self);
					if(status == 2){//��������
						gameView.hero.getCityList().remove(self);
						self.setBackToInit();
						self.setCountry(enemy.getCountry());
						gameView.allCityDrawable.add(self);
					}
					break;
				case 1://û�����°���ȷ��
				case 2://�������˰���ȷ��
					recoverGame();
					break;
				}			
			}
			else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
					&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȡ����
				status = calculateWinOrLose(enemy,self);
				if(status == 2){//��������
					gameView.hero.getCityList().remove(self);
				}
			}			
		}
		return true;
	}
	//������Ӯ
	public int calculateWinOrLose(CityDrawable enemy,CityDrawable self){
		if(enemy.army > self.army){
			if(Math.random()>0.3){
				return 2;
			}
			else{
				return 1;
			}
		}
		else{
			if(Math.random()>0.3){
				return 1;
			}
			else {
				return 2;
			}
		}
	}
	//�������������������ظ���Ϸ״̬
	public void recoverGame(){		
		gameView.setCurrentGameAlert(null);//�ÿռ�¼���õı���		
		gameView.setStatus(0);//��������GameViewΪ����״̬
		gameView.gvt.setChanging(true);//����ת����
		gameView.setOnTouchListener(gameView);//����������
	}
	
}