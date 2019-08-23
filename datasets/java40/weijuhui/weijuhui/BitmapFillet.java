package com.weparty.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date    : 2012-11-8
 * 图片切角工具
 */
public class BitmapFillet {

	public static final int ALL = 347120;
    public static final int TOP = 547120;
    public static final int LEFT = 647120;
    public static final int RIGHT = 747120;
    public static final int BOTTOM = 847120;
    
    /**
     * 
     * 指定图片的切边，对图片进行圆角处理
     * @param type 具体参见：{@link BitmapFillet#ALL} , {@link BitmapFillet#TOP} ,
     * 				{@link BitmapFillet#LEFT} , {@link BitmapFillet#RIGHT} , {@link BitmapFillet#BOTTOM}
     * @param bitmap 需要被切圆角的图片
     * @param roundPx 要切的像素大小
     * @return 处理后的图片对象
     *
     */
    public static Bitmap fillet(int type,Bitmap bitmap,int roundPx) {
        try {
        	// 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
        	// 然后在画板上画出一个想要的形状的区域。
        	// 最后把源图片帖上。
        	final int width = bitmap.getWidth();
        	final int height = bitmap.getHeight();
        	
            Bitmap paintingBoard = Bitmap.createBitmap(width,height, Config.ARGB_8888);
            Canvas canvas = new Canvas(paintingBoard);
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
            
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);   
            
            if( TOP == type ){
            	clipTop(canvas,paint,roundPx,width,height);
            }else if( LEFT == type ){
            	 clipLeft(canvas,paint,roundPx,width,height);
            }else if( RIGHT == type ){
            	clipRight(canvas,paint,roundPx,width,height);
            }else if( BOTTOM == type ){
            	clipBottom(canvas,paint,roundPx,width,height);
            }else{
            	clipAll(canvas,paint,roundPx,width,height);
            }
            
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
            //帖子图
            final Rect src = new Rect(0, 0, width, height);
            canvas.drawBitmap(bitmap, src, src, paint);
            return paintingBoard;
        } catch (Exception exp) {        
            return bitmap;
        }
    }
    
    private static void clipLeft(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(offset,0,width,height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, 0, offset * 2 , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
    
    private static void clipRight(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(0, 0, width-offset, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(width - offset * 2, 0, width , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
    
    private static void clipTop(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(0, offset, width, height);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, 0, width , offset * 2);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
    
    private static void clipBottom(final Canvas canvas,final Paint paint,int offset,int width,int height){
        final Rect block = new Rect(0, 0, width, height - offset);
        canvas.drawRect(block, paint);
        final RectF rectF = new RectF(0, height - offset * 2 , width , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
    
    private static void clipAll(final Canvas canvas,final Paint paint,int offset,int width,int height){
    	final RectF rectF = new RectF(0, 0, width , height);
        canvas.drawRoundRect(rectF, offset, offset, paint);
    }
}
