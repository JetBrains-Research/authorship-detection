package com.weparty.fragment;

import com.weparty.R;
import com.weparty.view.ActivityType;
import com.weparty.widgets.ScrollLayout;
import com.weparty.widgets.ScrollLayout.OnScreenChangeListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragRecommend extends Fragment implements Runnable,
		OnTouchListener {

	public FragRecommend() {
	}

	ImageButton nearby_ib, onsale_ib,launch_ib;

	private int[] imageIds = { R.drawable.recoms,R.drawable.food,
			R.drawable.ktv,R.drawable.moive };

	private ScrollLayout mScrollLayout;

	private int currentItem;
	private int duration = 4000; // 间隔秒数
	private boolean isBack; // 是否回滚

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frag_recommend, container, false);

		nearby_ib = (ImageButton) view.findViewById(R.id.nearby_ib);
		onsale_ib = (ImageButton) view.findViewById(R.id.onsale_ib);
		launch_ib = (ImageButton) view.findViewById(R.id.launch_ib);
		mScrollLayout = (ScrollLayout) view.findViewById(R.id.scroll_layout);
		initTitle(view);
		return view;
	}
	
	private void initTitle(View view) {
		
		TextView title = (TextView) view.findViewById(R.id.title_center);
		title.setText("微聚会");
		
		/*Button left_btn  = (Button) view.findViewById(R.id.title_back);
		left_btn.setBackgroundResource(R.drawable.icon_title);
		left_btn.setVisibility(View.VISIBLE);*/
		 
		view.findViewById(R.id.title_back).setVisibility(View.INVISIBLE);
		view.findViewById(R.id.title_pos).setVisibility(View.INVISIBLE);
		
	}

	@Override
	public void onResume() {

		nearby_ib.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "我们正在努力完善中哦,请期待", 0).show();
			}
		});
		
		onsale_ib.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "我们正在努力完善中哦,请期待", 0).show();
				
			}
		});
		
		launch_ib.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), ActivityType.class));
			}
		});

		buildTimingRecom();
		super.onResume();
	}

	private void buildTimingRecom() {
		ImageView img;
		for (int i = 0; i < imageIds.length; i++) {
			img = new ImageView(getActivity());
			img.setScaleType(ScaleType.FIT_XY);
			img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			img.setImageResource(imageIds[i]);
			mScrollLayout.addView(img);
		}

		mScrollLayout.setOnTouchListener(this);
		mScrollLayout.postDelayed(this, duration);

		/**
		 * 监听滑动到第几页
		 */
		mScrollLayout.setOnScreenChangeListener(new OnScreenChangeListener() {

			@Override
			public void onScreenChange(int currentIndex) {
				currentItem = currentIndex;
			}
		});
	}

	@Override
	public void run() {
		if (currentItem == imageIds.length - 1)
			isBack = true;
		else if (currentItem == 0)
			isBack = false;
		if (isBack) {
			currentItem--;
		} else {
			currentItem++;
		}
		mScrollLayout.snapToScreen(currentItem);
		mScrollLayout.postDelayed(this, duration);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下时停止图片轮播
			mScrollLayout.removeCallbacks(this);
			break;
		case MotionEvent.ACTION_CANCEL:

			break;
		case MotionEvent.ACTION_UP: // 抬起时图片继续轮播
			mScrollLayout.postDelayed(this, duration);
			break;
		default:
			break;
		}
		return false;
	}

}
