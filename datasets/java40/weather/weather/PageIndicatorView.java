package com.weico.core.emotion.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class PageIndicatorView extends View {
    private int mCurrentPage = -1;
    private int mTotalPage = 0;

    public PageIndicatorView(Context context) {
        super(context);
    }

    public PageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置频道页数，包括广告页，计算页面表示点的数量
     *
     * @param nPageNum
     */
    public void setTotalPage(int nPageNum) {
        mTotalPage = nPageNum;
        if (mCurrentPage >= mTotalPage)
            mCurrentPage = mTotalPage - 1;
    }

    /**
     * 设置当前页显示标识
     * @param nPageIndex
     */
    /**
     * change indicator after viewpager selected
     *
     * @param nPageIndex
     */
    public void setCurrentPage(int nPageIndex) {
        if (mCurrentPage != nPageIndex) {
            mCurrentPage = nPageIndex;
            this.invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), com.weico.core.R.drawable.emotion_key_page2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.FILL);

        Rect r = new Rect();
        this.getDrawingRect(r);
        int iconWidth = bitmap.getWidth();
        int iconHeight = bitmap.getHeight();
        int space = iconWidth / 2;

        int x = (r.width() - (iconWidth * mTotalPage + space * (mTotalPage - 1))) / 2;
        int y = (r.height() - iconHeight) / 2;

        Bitmap drawBitmap = null;
        for (int i = 0; i < mTotalPage; i++) {
//			paint.setColor(Color.LTGRAY);
            drawBitmap = bitmap;
            if (i == mCurrentPage) {
                drawBitmap = BitmapFactory.decodeResource(getResources(), com.weico.core.R.drawable.emotion_key_page1);
            }
//			canvas.drawCircle(x, y, getHeight()/6, paint);
            canvas.drawBitmap(drawBitmap, x, y, paint);
            x += iconWidth + space;

        }

        if (drawBitmap != null && !drawBitmap.isRecycled()) {
            drawBitmap.recycle();
        }


    }


}