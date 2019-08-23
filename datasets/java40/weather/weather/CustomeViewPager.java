package com.weico.core.emotion.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomeViewPager extends ViewPager {

	public static final String TAG = "CustomeViewPager";
	private static final int minSwipeDistance = 30;
	private float mTouchX;

    public CustomeViewPager(Context context) {
        super(context);
    }
	public CustomeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			mTouchX = x;
			break;
		case MotionEvent.ACTION_MOVE:
			float dX = Math.abs(x - mTouchX);
			if (dX > minSwipeDistance){
				return true;
            }
			break;
		}
		return super.onInterceptTouchEvent(event);
	}

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);//这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
        return super.dispatchTouchEvent(ev);
    }
}
