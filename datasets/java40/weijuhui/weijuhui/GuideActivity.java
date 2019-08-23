package com.weparty.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.weparty.R;

/**
 * 
 * <ul>
 * <li><b>name : </b> GuideActivity</li>
 * <li><b>description :</b> 引导页</li>
 * <li><b>author : </b> yelingh</li>
 * <li><b>date : </b> 2013-8-31 上午8:45:55</li>
 * </ul>
 */

@EActivity(R.layout.act_guide)
public class GuideActivity extends Activity {

	@ViewById(R.id.guide_activity_viewpager)
	ViewPager mPager;

	@ViewById(R.id.guide_activity_btn)
	ImageButton mButton;

	View mPage1, mPage2, mPage3;

	/**
	 * 存放显示内容的View
	 */
	private List<View> mViews = new ArrayList<View>();

	@AfterViews
	void afterViews() {
		initViewPager();
		mButton = (ImageButton) mPage3.findViewById(R.id.guide_activity_btn);
		mButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到主界面
				startActivity(new Intent(GuideActivity.this, MainActivity_.class));
				finish();
			}
		});

	}

	private void initViewPager() {
		mPage1 = LayoutInflater.from(this).inflate(R.layout.act_guide_page1,
				null);
		mPage2 = LayoutInflater.from(this).inflate(R.layout.act_guide_page2,
				null);
		mPage3 = LayoutInflater.from(this).inflate(R.layout.act_guide_page3,
				null);
		mViews.add(mPage1);
		mViews.add(mPage2);
		mViews.add(mPage3);
		mPager.setAdapter(new ViewPagerAdapter());
	}

	/**
	 * ViewPager适配器
	 * 
	 * @author rendongwei
	 * 
	 */
	private class ViewPagerAdapter extends PagerAdapter {

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mViews.get(arg1));
		}

		public void finishUpdate(View arg0) {

		}

		public int getCount() {

			return mViews.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mViews.get(arg1));
			return mViews.get(arg1);

		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}

	}
}
