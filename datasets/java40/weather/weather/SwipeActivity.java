package com.weico.core.vender.sliderback;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import com.weico.core.BaseActivity;
import com.weico.core.R;
import com.weico.core.utils.ActivityUtil;
import com.weico.core.utils.KeyBoardUtil;
import com.weico.core.utils.TransactionUtil;

/**
 * Created by zhoukai on 13-8-27.
 */
public class SwipeActivity extends BaseActivity {
    boolean mHasBackGround;
    boolean mDraggable;
    boolean mIsAttached;
    Bitmap mBgBitMap;
    boolean mIsTransparent;
    private SlidingPaneLayout mSlidingPaneLayout;
    private FrameLayout mContentLayout, mSliderBackGround;
    public boolean mEnableBgBitMap = true;
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        TypedArray a = this.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) this.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        mContentLayout.addView(decorChild);
        decor.addView(mSlidingPaneLayout);
        mIsAttached = true;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initResourceAndColor() {

    }

    @Override
    public View findViewById(int id) {
        if (mIsAttached) {
            return mSlidingPaneLayout.findViewById(id);
        } else {
            return super.findViewById(id);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        mSlidingPaneLayout = (SlidingPaneLayout) LayoutInflater.from(this).inflate(R.layout.activity_base_left_drawer, null);
        getWindow().getDecorView().setBackgroundDrawable(null);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mContentLayout = (FrameLayout) mSlidingPaneLayout.findViewById(R.id.container_fragment_content);
        mSliderBackGround = (FrameLayout) mSlidingPaneLayout.findViewById(R.id.container_fragment_list);
        slidingPaneConfiguration();
        mDraggable = true;
        mIsTransparent = false;
        mHasBackGround = false;
        mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
                int alpha = (int) (255 * 0.8 * (1 - v));
                if (mHasBackGround == false && mEnableBgBitMap) {
                    mSliderBackGround.setBackgroundDrawable(new BitmapDrawable(ActivityUtil.getSliderBackBitmap()));
                    mHasBackGround = true;
                }
            }

            @Override
            public void onPanelOpened(View view) {
                //KeyBoardUtil.hideSoftKeyboardNotAlways(SwipeActivity.this);
                SwipeActivity.this.finishWithAnim(TransactionUtil.Transaction.NONE);
            }

            @Override
            public void onPanelClosed(View view) {
                if (mHasBackGround == true && mEnableBgBitMap) {
                    mSliderBackGround.setBackgroundDrawable(null);
                    mHasBackGround = false;
                }
                mIsTransparent = false;
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void setDragEnable(boolean enable,int screenWidth) {
        mDraggable = enable;
        FrameLayout layout = (FrameLayout) findViewById(R.id.container_fragment_list);
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        if (enable) {
            int width = screenWidth;
            params.width = width;//(int) (width * 0.618);
        } else {
            params.width = 0;
        }
        layout.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasBackGround = false;
        mSliderBackGround.setBackgroundDrawable(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBgBitMap != null && !mBgBitMap.isRecycled()) {
            mBgBitMap.recycle();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mBgBitMap != null && !mBgBitMap.isRecycled()) {
            mBgBitMap.recycle();
        }
        unbindDrawables(mSlidingPaneLayout);
    }


    @Override
    public void onBackPressed() {
        finishWithAnim(TransactionUtil.Transaction.POP_OUT);
    }

    /**
     * Method to configure the {@see SlidingPaneLayout}
     */
    private void slidingPaneConfiguration() {
        mSlidingPaneLayout.setSliderFadeColor(Color.argb(0, 0, 0, 0));
        mSlidingPaneLayout.setCoveredFadeColor(Color.argb(100, 0, 0, 0));
        mSlidingPaneLayout.setParallaxDistance(this.getWindowManager().getDefaultDisplay().getWidth()/3);
    }

    @Override
    public void finish() {
        //KeyBoardUtil.hideSoftKeyboardNotAlways(SwipeActivity.this);
        finishWithAnim(TransactionUtil.Transaction.POP_OUT);
    }

    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

}