package com.picasso;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * Created by zhoukai on 13-9-18.
 */
public class RoundCornerTransformation implements Transformation {
    int roundPixels;

    public RoundCornerTransformation(int roundPixels) {
        this.roundPixels = roundPixels;
    }

    public Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        int color;
        Paint paint;
        Rect rect;
        RectF rectF;
        Bitmap result;
        Canvas canvas;
        float roundPx;
        result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(result);
        color = 0xff424242;
        paint = new Paint();
        rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rectF = new RectF(rect);
        roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return result;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap roundBitMap = getRoundedRectBitmap(source,this.roundPixels);
       if (roundBitMap!=source){
           source.recycle();
       }
        return roundBitMap;
    }

    @Override
    public String key() {
        return "RoundCorner()";
    }
}