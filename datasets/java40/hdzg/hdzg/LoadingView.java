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
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * ����Ϊ����������
 * ������ʱ�����ʱ���û���ʾ
 *
 */

public class LoadingView extends SurfaceView implements SurfaceHolder.Callback {
	int startX = ConstantUtil.LOADING_VIEW_START_X;//��������x����
	int startY = ConstantUtil.LOADING_VIEW_START_Y;//��������y����
	
	int k = 0;//��ǰ"������"����ĵ�ĸ���
	public int process = 0;//0��100��ʾ��ǰ���� 

	HDZGActivity activity;//activity������
	private DrawThread drawThread;//ˢ֡���߳�
	
	Bitmap loading1;//����Ľ���ͼƬ
	Bitmap loading2;//����Ľ���ͼƬ
	Rect r1;//ȡ����Ľ����Ĳ���
	Rect r2;//��ȡ���Ĳ��ֻ��Ƶ���Ļ���Ĳ���
	
	int type;//������������ȥ�Ľ���
	Paint paint;//����
	
	public LoadingView(HDZGActivity activity, int type) {//������ 
		super(activity);
		this.activity = activity;//activity������
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//��ʼ��ˢ֡�߳�
        this.type = type;//����������ȥ�ĸ�����
        initBitmap();//��ʼ��ͼƬ��Դ
	}
	
	public void initBitmap(){//��ʼ��ͼƬ��Դ�ķ���
		paint = new Paint();//��������
		paint.setAntiAlias(true);
		paint.setTextSize(ConstantUtil.LOADING_VIEW_WORD_SIZE);//���û��ʻ�������Ĵ�С
		paint.setColor(Color.WHITE);//���û��ʵ���ɫ
		loading1 = BitmapFactory.decodeResource(getResources(), R.drawable.loading1);//��ʼ������Ľ�
		loading2 = BitmapFactory.decodeResource(getResources(), R.drawable.loading2);//��ʼ������Ľ�

	}
	
	public void onDraw(Canvas canvas){//�Լ�д�Ļ��Ʒ���
		canvas.drawColor(Color.BLACK);
		//����������z��ģ��󻭵ĻḲ��ǰ�滭��
		canvas.drawBitmap(loading1, startX, startY, paint);//���ƺ���Ľ�
		
		if(process > 100){
			process = 100;
		}
		
		r1 = new Rect(0, 0, loading2.getWidth()*process/100, loading2.getHeight());//ȡǰ��Ľ���һ����
		r2 = new Rect(startX-10, startY-2, startX+loading2.getWidth()*process/100, startY+loading2.getHeight());//���Ƶ���Ļ���ĸ�����
		canvas.drawBitmap(loading2, r1, r2, paint);//����ȡ�������潣��һ����
		
		canvas.drawText("ս��Ӣ�۴�", startX+(loading2.getWidth()/2)-20, startY, paint);
		//���Ƽ���������
		if(k == 0){
			canvas.drawText("������", startX+(loading2.getWidth()/2)-20, startY+loading2.getHeight()+20, paint);
		}
		else if(k == 1){
			canvas.drawText("������.", startX+(loading2.getWidth()/2)-20, startY+loading2.getHeight()+20, paint);
		}
		else if(k == 2){
			canvas.drawText("������..", startX+(loading2.getWidth()/2)-20, startY+loading2.getHeight()+20, paint);
		}
		else if(k == 3){
			canvas.drawText("������...", startX+(loading1.getWidth()/2)-20, startY+loading1.getHeight()+20, paint);
		}

		k = (k+1)%4;//kѭ���Լ�
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������
        this.drawThread.setFlag(true);
        this.drawThread.start();//����ˢ֡�߳�
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {//�ͷ�ʱ������
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
		private int sleepSpan = ConstantUtil.LOADING_VIEW_SLEEP_SPAN;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private LoadingView loadingView;
		private boolean flag = false;
        public DrawThread(SurfaceHolder surfaceHolder, LoadingView loadingView) {//������
        	super.setName("==LoadingView.DrawThrea");
            this.surfaceHolder = surfaceHolder;
            this.loadingView = loadingView;
        }
        public void setFlag(boolean flag) {//����ѭ�����λ
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
                    	loadingView.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                    	//������Ļ��ʾ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                if(loadingView.process >= 100){//�����������ȴﵽ100ʱ
        			loadingView.activity.myHandler.sendEmptyMessage(loadingView.type);//����activity����Handler��Ϣ
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