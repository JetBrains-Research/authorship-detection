package wyf.ytl.view;

import wyf.ytl.HDZGActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundView extends SurfaceView implements SurfaceHolder.Callback{



	HDZGActivity activity;
	Paint paint;
	public SoundView(HDZGActivity activity) {
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);//�����
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		paint.setTextSize(16);//�������ִ�С
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawText("�Ƿ񲥷�����?", 110, 230, paint);
		canvas.drawText("��", 5, 460, paint); 
		canvas.drawText("��", 300, 460, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>3 && x<20 && y>440 && y<460){//����ǰ�ť
				activity.isStartSound = true;
				activity.isBackSound = true;
				activity.isEnvironmentSound = true;
				activity.isBattleSound = true;
				activity.myHandler.sendEmptyMessage(7);
			}
			else if(x>300 && x<320 && y>440 && y<460){//�����ť
				activity.isStartSound = false;
				activity.isBackSound = false;
				activity.isEnvironmentSound = false;
				activity.isBattleSound = false;
				activity.myHandler.sendEmptyMessage(7);
			}
		}
		return super.onTouchEvent(event);
	}


	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		new Thread(){
			public void run(){
				Canvas c = SoundView.this.getHolder().lockCanvas(null);
		        synchronized (SoundView.this.getHolder()) {
		        	onDraw(c);
		        }
		        SoundView.this.getHolder().unlockCanvasAndPost(c);			
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
