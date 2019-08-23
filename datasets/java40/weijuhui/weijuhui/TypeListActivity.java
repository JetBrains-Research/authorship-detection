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
import android.os.Bundle;
import android.renderscript.Type;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TypeListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_type_list);
		initListView();

		TextView title = (TextView) findViewById(R.id.title_center);
		title.setText("选择活动");

		findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TypeListActivity.this.finish();
			}
		});
		findViewById(R.id.title_pos).setVisibility(View.INVISIBLE);

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initListView() {

		ListView commonlist = (ListView) findViewById(R.id.commonlist);

		final ActType[] actType = buildActList();

		commonlist.setAdapter(new ActTypeAdapter(this, actType));

		// commonlist.setDividerHeight(0);

		commonlist.setSelector(R.drawable.type_list_selector);

		commonlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int positon, long arg3) {

				Intent intent = new Intent(TypeListActivity.this,
						BuildActActivity.class);
				int id = actType[positon].getId();
				intent.putExtra("value",String.valueOf(id));
				setResult(Constant.ITEM_VALUE_TYPE,intent);
				finish();
			}

		});
	}

	private ActType[] buildActList() {
		ActType[] actType = new ActType[7];
		actType[0] = new ActType(Constant.TYPE_FEAST,R.drawable.icon_feast, "吃大餐");
		actType[1] = new ActType(Constant.TYPE_COFFEE,R.drawable.icon_coffee, "喝咖啡");
		actType[2] = new ActType(Constant.TYPE_WINE,R.drawable.icon_wine, "喝一杯");
		actType[3] = new ActType(Constant.TYPE_KTV,R.drawable.icon_ktv, "去唱K");
		actType[4] = new ActType(Constant.TYPE_PERFORM,R.drawable.icon_meeting, "看演出");
		actType[5] = new ActType(Constant.TYPE_MOVIE,R.drawable.icon_movie, "看电影");
		actType[6] = new ActType(Constant.TYPE_SPHERE,R.drawable.icon_sphere, "去运动");
		return actType;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			/*Intent intent = new Intent(TypeListActivity.this,
					BuildActActivity.class);
			intent.putExtra("value", "");
			setResult(Constant.ITEM_VALUE_TYPE,intent);*/
			/*startActivity(new Intent(TypeListActivity.this,ActivityType.class));*/
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	class ActTypeAdapter extends BaseAdapter {

		private int mLayoutId;
		private ActType[] array;
		private Activity mContext;

		public ActTypeAdapter(Activity context, ActType[] arrays) {
			mLayoutId = R.layout.ite_act_type_list;
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
				holder.act_type_explain = (TextView) row
						.findViewById(R.id.act_type_explain);
				row.setTag(holder);
			} else {
				holder = (ViewHolder) row.getTag();
			}

			int resId = array[position].getImg();
			String explain = array[position].getTitle();

			Bitmap iconBitmap = ResourceReader.readAsBitmap(mContext, resId);

			holder.act_type_img.setImageBitmap(BitmapFillet.fillet(
					BitmapFillet.ALL, iconBitmap, 5));

			holder.act_type_explain.setText(explain);

			return row;
		}

		class ViewHolder {
			ImageView act_type_img;
			TextView act_type_explain;
		}
	}
}
