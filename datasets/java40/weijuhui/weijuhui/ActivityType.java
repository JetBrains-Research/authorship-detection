package com.weparty.view;

import com.umeng.analytics.MobclickAgent;
import com.weparty.R;
import com.weparty.domain.ActType;
import com.weparty.tools.BitmapFillet;
import com.weparty.tools.Constant;
import com.weparty.tools.ResourceReader;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityType extends Activity {

	ActType[] actType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_act_type);

		TextView title = (TextView) findViewById(R.id.title_center);
		title.setText("选择聚会");

		findViewById(R.id.title_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityType.this.finish();
			}
		});

		findViewById(R.id.title_pos).setVisibility(View.INVISIBLE);
		GridView gridView = (GridView) findViewById(R.id.act_type);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setAdapter(new ActTypeAdapter(this, buildType()));
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
					long arg3) {
				Intent i;
				if(postion != 5){
					 i = new Intent(ActivityType.this, BuildActActivity.class);
					 i.putExtra("type",String.valueOf(actType[postion].getId()));
					 startActivity(i);
				}else{
					 i = new Intent(ActivityType.this, TypeListActivity.class);
					 startActivityForResult(i,0);
				}
				
				
			}

		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			Intent i = new Intent(ActivityType.this, BuildActActivity.class);
			 i.putExtra("type",data.getStringExtra("value"));
			 startActivity(i);
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private ActType[] buildType() {
		actType = new ActType[6];
		actType[0] = new ActType(Constant.TYPE_COFFEE,R.drawable.type_coffee_selector, "喝咖啡");
		actType[1] = new ActType(Constant.TYPE_MOVIE,R.drawable.type_movie_selector, "看电影");
		actType[2] = new ActType(Constant.TYPE_KTV,R.drawable.type_ktv_selector, "去唱K");
		actType[3] = new ActType(Constant.TYPE_PLAY,R.drawable.type_play_selector, "去桌游");
		actType[4] = new ActType(Constant.TYPE_TRAVEL,R.drawable.type_travel_selector, "去旅游");
		actType[5] = new ActType(Constant.TYPE_MORE,R.drawable.type_more_selector, "更多");
		return actType;
	}

	class ActTypeAdapter extends BaseAdapter {

		private int mLayoutId;
		private ActType[] array;
		private Activity mContext;

		public ActTypeAdapter(Activity context, ActType[] arrays) {
			mLayoutId = R.layout.ite_act_type;
			this.array = arrays;
			this.mContext = context;
		}

		@Override
		public int getCount() {
			return array.length;
		}

		@Override
		public Object getItem(int index) {
			return array[index];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;

			ViewHolder holder;

			if (row == null) {
				LayoutInflater inflater = mContext.getLayoutInflater();
				row = inflater.inflate(mLayoutId, null);

				holder = new ViewHolder();
				holder.act_type_img = (ImageView) row
						.findViewById(R.id.act_type_img);
				/*
				 * holder.act_type_explain = (TextView) row
				 * .findViewById(R.id.act_type_explain);
				 */
				row.setTag(holder);
			} else {
				holder = (ViewHolder) row.getTag();
			}

			int resId = array[position].getImg();
			// String explain = array[position].getTitle();

			// Bitmap iconBitmap = ResourceReader.readAsBitmap(mContext, resId);

			holder.act_type_img.setImageResource(resId);

			/*
			 * holder.act_type_img.setImageBitmap(BitmapFillet.fillet(
			 * BitmapFillet.ALL, iconBitmap, 20));
			 */

			// holder.act_type_explain.setText(explain);

			return row;
		}

		class ViewHolder {
			ImageView act_type_img;
			// TextView act_type_explain;
		}
	}
}
