package com.weparty.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.weparty.R;
import com.weparty.domain.Share;
import com.weparty.tools.Constant;

public class ShareDialog extends Activity {

	Context context;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_share);

		context = getApplicationContext();
		
		findViewById(R.id.launch_pos_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ShareDialog.this,LaunchActivity.class);
				setResult(Constant.ITEM_VALUE_SHARE, intent);
				finish();
			}
		});
		
		findViewById(R.id.launch_canle_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShareDialog.this.finish();
			}
		});

		/*GridView gridView = (GridView) findViewById(R.id.gird_share);

		gridView.setAdapter(new ShareAdapter(this, buildShareList()));

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				switch (arg2) {
				case 0:
					break;
				case 1:
					break;
				default:
					Toast.makeText(context, "我们正在完善噢,期待哦", 0).show();
					break;
				}
			}

		});*/
	}

	/*Share[] buildShareList() {
		Share[] share = new Share[2];
		share[0] = new Share(R.drawable.ico_share_weixin, "微信朋友");
		share[1] = new Share(R.drawable.ico_share_circle, "微信朋友圈");
		
		 * share[2] = new Share(R.drawable.ico_share_album,"通信录朋友"); share[3] =
		 * new Share(R.drawable.ico_share_qweibo,"腾讯微博"); share[4] = new
		 * Share(R.drawable.ico_share_qzone,"腾讯空间"); share[5] = new
		 * Share(R.drawable.ico_share_weixin,"新浪微博");
		 
		return share;
	}*/

	class ShareAdapter extends BaseAdapter {

		private int mLayoutId;
		private Share[] array;
		private Activity mContext;

		public ShareAdapter(Activity context, Share[] arrays) {
			mLayoutId = R.layout.ite_share;
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

			holder.act_type_img.setImageResource(resId);

			holder.act_type_explain.setText(explain);

			return row;
		}

		class ViewHolder {
			ImageView act_type_img;
			TextView act_type_explain;
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
}
