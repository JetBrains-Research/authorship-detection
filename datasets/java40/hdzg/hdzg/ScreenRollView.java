package wyf.ytl.view;

import wyf.ytl.HDZGActivity;
import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.tool.ConstantUtil;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ScreenRollView extends SurfaceView implements SurfaceHolder.Callback{
	
	HDZGActivity activity;
	Bitmap bmpScroll;//竹简图片
	Bitmap bmpBack;//背景图片
	DrawThread drawThread;//后台的重绘线程
	ScreenRollThread screenRollThread;//竹简滚屏的后台线程
	float textSize = 23f;//字体的大小
	int scrollStartX = 320;//竹简开始的x坐标
	int scrollStartY = 60;//竹简开始的y坐标 
	int characterEachLine = 10;//每行显示的汉字个数
	int characterSpanX = 36;//文字x方向间距
	int characterSpanY = 25;//文字y方向间距
	int characterNumber = 1;//已经显示了的字数
	int textStartX=237;//文字起始的x坐标
	int textStartY=135;//文字起始的y坐标
	int startChar=0;//从字符串中的哪里开始输出，用于滚屏使用
	int maxChar = 60;//竹简上最大能显示的文字个数
	int alpha = 255;//透明度
	
	int status;//状态位，1表示竹简划入，2表示文字显示
	
	String msg = "东周末年，周室已经名存实亡，天下只是在名义上归于一家。各个诸侯国之间的战争早已是烽火连天，各种吞并"
		+"和结盟屡见不鲜。不知你能否在这乱世靠着自己的才干谋略为自己守住一方土地，甚至是和其他诸侯国一样，逐鹿"
		+"中原，并吞八荒？";
	
	public ScreenRollView(HDZGActivity activity) {
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);
		drawThread = new DrawThread(getHolder());
		screenRollThread = new ScreenRollThread(this);
		initBitmap(getResources());
		status = 1;
	}
	//初始化图片
	public void initBitmap(Resources r){
		bmpScroll = BitmapFactory.decodeResource(r, R.drawable.scroll);//竹简图片
		
	}
	//屏幕的绘制方法
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		Paint paint = new Paint();
		paint.setAlpha(alpha);
		//画竹简
		switch(status){
		case 3://渐隐状态
		case 2://画文字
			canvas.drawBitmap(bmpScroll, scrollStartX, scrollStartY, paint);
			paint.setTextSize(textSize);
			paint.setAntiAlias(true);
			paint.setARGB(alpha, 234, 234, 234);
			if(characterNumber > maxChar){//如果屏幕要绘制的文字数大于最大值
				startChar += characterEachLine;//起始位置改变
				characterNumber -= characterEachLine;
			}
			int lines = characterNumber/characterEachLine+(characterNumber%characterEachLine==0?0:1);//计算 一共需要几行
			for(int i=0;i<lines;i++){
				if(i == lines-1){//到了最后一行，可能不是整行，区别对待一下
					int n = characterNumber - characterEachLine*(lines -1);//求出最后这一样需要多少个字
					for(int j=0;j<n-1;j++){
						canvas.drawText(
								msg.charAt(startChar+i*characterEachLine+j)+"",
								textStartX-i*characterSpanX,
								textStartY+j*characterSpanY,
								paint);
					}
				}
				else{//还不是最后一排
					for(int j=0;j<characterEachLine;j++){
						canvas.drawText(
								msg.charAt(startChar+i*characterEachLine+j)+"",
								textStartX-i*characterSpanX,
								textStartY+j*characterSpanY,
								paint);
					}
				}
			}
			break;
		case 1://画竹简
			canvas.drawBitmap(bmpScroll, scrollStartX, scrollStartY, paint);
			break;
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(!drawThread.isAlive()){
			drawThread.start();
		}
		screenRollThread.flag = true;
		if(!screenRollThread.isAlive()){//还没启动就启动
			screenRollThread.start();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setFlag(false);
	}
	
	//后台的重绘线程
	class DrawThread extends Thread{//刷帧线程
		private int sleepSpan = ConstantUtil.GAME_VIEW_SLEEP_SPAN;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder) {//构造器
            this.surfaceHolder = surfaceHolder;
        }
        
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
        
		public void run() {
			Canvas c;
			while(flag){
	                c = null;
	                try {
	                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) {
	                    	onDraw(c);
	                    }
	                } finally {
	                    if (c != null) {
	                    	//更新屏幕显示内容
	                        this.surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	                try{
	                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	                }
	                catch(Exception e){
	                	e.printStackTrace();
	                }
	            }
	            try{
	            	Thread.sleep(1500);//睡眠指定毫秒数
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
		}
	}
}