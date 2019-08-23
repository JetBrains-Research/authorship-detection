package wyf.ytl.view;

import wyf.ytl.HDZGActivity;
import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.tool.ConstantUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * ����Ϊ��������
 *
 */

public class HelpView extends SurfaceView implements SurfaceHolder.Callback{


	HDZGActivity activity;
	private DrawThread drawThread;//ˢ֡���߳�
	
	Bitmap help1;
	Bitmap buttonBackGround;//��ť����
	int type;
	Paint paint;
	public HelpView(HDZGActivity activity, int type) {
		super(activity);
		this.activity = activity;
		this.type = type;
		getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);
        initBitmap();
	}
	
	public void initBitmap(){
		paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		paint.setAntiAlias(true);//�����
		paint.setTextSize(18);//�������ִ�С
		help1 = BitmapFactory.decodeResource(getResources(), R.drawable.help);
		Bitmap menu_item = BitmapFactory.decodeResource(getResources(), R.drawable.buttons); 
		buttonBackGround = Bitmap.createBitmap(menu_item, 0, 0, 60, 30);
		menu_item = null;//�ͷŵ���ͼ 
	}
	

	//���Ʒ���
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(help1, 0, 50, paint);
		canvas.drawBitmap(buttonBackGround, 240, 350, paint);
		
		canvas.drawText("ȷ��", 252, 372, paint);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>240 && x<300 && y>350 && y<380){//���ȷ��
				this.activity.myHandler.sendEmptyMessage(type);
			}
		}
		return super.onTouchEvent(event);
	}
	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		drawThread.setFlag(true);
		drawThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;
        drawThread.setFlag(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
            }
        }
	}

	class DrawThread extends Thread{//ˢ֡�߳�
		private int sleepSpan = ConstantUtil.MENU_VIEW_SLEEP_SPAN;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private HelpView helpView;
		private boolean flag = false;
		
        public DrawThread(SurfaceHolder surfaceHolder, HelpView helpView) {//������
        	super.setName("==HelpView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.helpView = helpView;
        }
        
        public void setFlag(boolean flag) {
        	this.flag = flag;
        }
        
		public void run() {
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	helpView.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                    	//������Ļ��ʾ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(sleepSpan);//˯��ָ��������
                }
                catch(Exception e){
                	e.printStackTrace();
                }
            }
		}
	}
}
