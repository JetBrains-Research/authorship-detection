package com.weico.core.baseUi;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: duyuan
 * Date: 13-8-4
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseViewPager extends ViewPager {
    private float subX;
    private float subY;
    private float mTouchX;
    private float mTouchY;
    boolean enableParent = false;

    public gestureType getmGestureType() {
        return mGestureType;
    }

    public void setmGestureType(gestureType mGestureType) {
        this.mGestureType = mGestureType;
    }

    private gestureType mGestureType;

    public BaseViewPager(Context context) {
        super(context);
        mGestureType = gestureType.All;
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureType = gestureType.All;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        float Y = event.getY();
        float X = event.getX();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = Y;
                mTouchX = X;
                enableParent = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("BaseViewPager","onInterceptTouchEvent action:ACTION_MOVE");
                subX = X - mTouchX;
                subY = Y - mTouchY;
                float dY = Math.abs(subY);
                float dX = Math.abs(subX);
                Log.d("onInterceptTouchEventYY",subX+"");
                switch (mGestureType) {
                    case All:
                        enableParent = false;
                        break;
                    case Vertical:
                        if (Math.abs(subY) > Math.abs(subX)) {
                            enableParent = true;
                        }
                        break;
                    case Horizon:
                        if (Math.abs(subY) < Math.abs(subX)) {
                            enableParent = true;
                        }
                        break;
                    case LeftOnly:
                        if (subX < 0) {
                            enableParent = true;
                        }
                        break;
                    case RightOnly:
                        if (subX > 0) {
                            enableParent = true;
                        }
                        break;
                    case BottomOnly:
                        if (subY < 0) {
                            enableParent = true;
                        }
                        break;
                    case TopOnly:
                        if (subY < 0) {
                            enableParent = true;
                        }
                        break;
                    default:

                        break;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(!enableParent);
        return super.dispatchTouchEvent(ev);
    }

    public enum gestureType {
        All, Vertical, Horizon, LeftOnly, RightOnly, TopOnly, BottomOnly;
    }
}
