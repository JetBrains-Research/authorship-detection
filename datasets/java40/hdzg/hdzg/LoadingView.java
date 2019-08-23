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
 * 该类为进度条界面
 * 当程序长时间加载时给用户提示
 *
 */

public class LoadingView extends SurfaceView implements SurfaceHolder.Callback {
	int startX = ConstantUtil.LOADING_VIEW_START_X;//进度条的x坐标
	int startY = ConstantUtil.LOADING_VIEW_START_Y;//进度条的y坐标
	
	int k = 0;//当前"加载中"后面的点的个数
	public int process = 0;//0到100表示当前进度 

	HDZGActivity activity;//activity的引用
	private DrawThread drawThread;//刷帧的线程
	
	Bitmap loading1;//下面的剑的图片
	Bitmap loading2;//上面的剑的图片
	Rect r1;//取上面的剑的哪部分
	Rect r2;//将取出的部分绘制到屏幕的哪部分
	
	int type;//进度条结束后去的界面
	Paint paint;//画笔
	
	public LoadingView(HDZGActivity activity, int type) {//构造器 
		super(activity);
		this.activity = activity;//activity的引用
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
        this.type = type;//进度条结束去哪个界面
        initBitmap();//初始化图片资源
	}
	
	public void initBitmap(){//初始化图片资源的方法
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);
		paint.setTextSize(ConstantUtil.LOADING_VIEW_WORD_SIZE);//设置画笔绘制字体的大小
		paint.setColor(Color.WHITE);//设置画笔的颜色
		loading1 = BitmapFactory.decodeResource(getResources(), R.drawable.loading1);//初始化下面的剑
		loading2 = BitmapFactory.decodeResource(getResources(), R.drawable.loading2);//初始化上面的剑

	}
	
	public void onDraw(Canvas canvas){//自己写的绘制方法
		canvas.drawColor(Color.BLACK);
		//画的内容是z轴的，后画的会覆盖前面画的
		canvas.drawBitmap(loading1, startX, startY, paint);//绘制后面的剑
		
		if(process > 100){
			process = 100;
		}
		
		r1 = new Rect(0, 0, loading2.getWidth()*process/100, loading2.getHeight());//取前面的剑的一部分
		r2 = new Rect(startX-10, startY-2, startX+loading2.getWidth()*process/100, startY+loading2.getHeight());//绘制到屏幕的哪个部分
		canvas.drawBitmap(loading2, r1, r2, paint);//绘制取出的上面剑的一部分
		
		canvas.drawText("战国英雄传", startX+(loading2.getWidth()/2)-20, startY, paint);
		//绘制加载中字样
		if(k == 0){
			canvas.drawText("加载中", startX+(loading2.getWidth()/2)-20, startY+loading2.getHeight()+20, paint);
		}
		else if(k == 1){
			canvas.drawText("加载中.", startX+(loading2.getWidth()/2)-20, startY+loading2.getHeight()+20, paint);
		}
		else if(k == 2){
			canvas.drawText("加载中..", startX+(loading2.getWidth()/2)-20, startY+loading2.getHeight()+20, paint);
		}
		else if(k == 3){
			canvas.drawText("加载中...", startX+(loading1.getWidth()/2)-20, startY+loading1.getHeight()+20, paint);
		}

		k = (k+1)%4;//k循环自加
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
        this.drawThread.setFlag(true);
        this.drawThread.start();//启动刷帧线程
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {//释放时被调用
        boolean retry = true;
        drawThread.setFlag(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
            }
        }
	}
	
	class DrawThread extends Thread{//刷帧线程
		private int sleepSpan = ConstantUtil.LOADING_VIEW_SLEEP_SPAN;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private LoadingView loadingView;
		private boolean flag = false;
        public DrawThread(SurfaceHolder surfaceHolder, LoadingView loadingView) {//构造器
        	super.setName("==LoadingView.DrawThrea");
            this.surfaceHolder = surfaceHolder;
            this.loadingView = loadingView;
        }
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
		
		public void run() {
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	loadingView.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                    	//更新屏幕显示内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                if(loadingView.process >= 100){//当进度条进度达到100时
        			loadingView.activity.myHandler.sendEmptyMessage(loadingView.type);//向主activity发送Handler消息
                }
                try{
                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
                }
                catch(Exception e){
                	e.printStackTrace();
                }
            }
		}
	}
}