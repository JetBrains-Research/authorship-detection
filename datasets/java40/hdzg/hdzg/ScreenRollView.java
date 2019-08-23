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
	Bitmap bmpScroll;//���ͼƬ
	Bitmap bmpBack;//����ͼƬ
	DrawThread drawThread;//��̨���ػ��߳�
	ScreenRollThread screenRollThread;//�������ĺ�̨�߳�
	float textSize = 23f;//����Ĵ�С
	int scrollStartX = 320;//���ʼ��x����
	int scrollStartY = 60;//���ʼ��y���� 
	int characterEachLine = 10;//ÿ����ʾ�ĺ��ָ���
	int characterSpanX = 36;//����x������
	int characterSpanY = 25;//����y������
	int characterNumber = 1;//�Ѿ���ʾ�˵�����
	int textStartX=237;//������ʼ��x����
	int textStartY=135;//������ʼ��y����
	int startChar=0;//���ַ����е����￪ʼ��������ڹ���ʹ��
	int maxChar = 60;//������������ʾ�����ָ���
	int alpha = 255;//͸����
	
	int status;//״̬λ��1��ʾ����룬2��ʾ������ʾ
	
	String msg = "����ĩ�꣬�����Ѿ�����ʵ��������ֻ���������Ϲ���һ�ҡ���������֮���ս�������Ƿ�����죬�����̲�"
		+"�ͽ����ż����ʡ���֪���ܷ��������������Լ��ĲŸ�ı��Ϊ�Լ���סһ�����أ������Ǻ���������һ������¹"
		+"��ԭ�����̰˻ģ�";
	
	public ScreenRollView(HDZGActivity activity) {
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);
		drawThread = new DrawThread(getHolder());
		screenRollThread = new ScreenRollThread(this);
		initBitmap(getResources());
		status = 1;
	}
	//��ʼ��ͼƬ
	public void initBitmap(Resources r){
		bmpScroll = BitmapFactory.decodeResource(r, R.drawable.scroll);//���ͼƬ
		
	}
	//��Ļ�Ļ��Ʒ���
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		Paint paint = new Paint();
		paint.setAlpha(alpha);
		//�����
		switch(status){
		case 3://����״̬
		case 2://������
			canvas.drawBitmap(bmpScroll, scrollStartX, scrollStartY, paint);
			paint.setTextSize(textSize);
			paint.setAntiAlias(true);
			paint.setARGB(alpha, 234, 234, 234);
			if(characterNumber > maxChar){//�����ĻҪ���Ƶ��������������ֵ
				startChar += characterEachLine;//��ʼλ�øı�
				characterNumber -= characterEachLine;
			}
			int lines = characterNumber/characterEachLine+(characterNumber%characterEachLine==0?0:1);//���� һ����Ҫ����
			for(int i=0;i<lines;i++){
				if(i == lines-1){//�������һ�У����ܲ������У�����Դ�һ��
					int n = characterNumber - characterEachLine*(lines -1);//��������һ����Ҫ���ٸ���
					for(int j=0;j<n-1;j++){
						canvas.drawText(
								msg.charAt(startChar+i*characterEachLine+j)+"",
								textStartX-i*characterSpanX,
								textStartY+j*characterSpanY,
								paint);
					}
				}
				else{//���������һ��
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
		case 1://�����
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
		if(!screenRollThread.isAlive()){//��û����������
			screenRollThread.start();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setFlag(false);
	}
	
	//��̨���ػ��߳�
	class DrawThread extends Thread{//ˢ֡�߳�
		private int sleepSpan = ConstantUtil.GAME_VIEW_SLEEP_SPAN;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder) {//������
            this.surfaceHolder = surfaceHolder;
        }
        
        public void setFlag(boolean flag) {//����ѭ�����λ
        	this.flag = flag;
        }
        
		public void run() {
			Canvas c;
			while(flag){
	                c = null;
	                try {
	                	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) {
	                    	onDraw(c);
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
	            try{
	            	Thread.sleep(1500);//˯��ָ��������
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
		}
	}
}