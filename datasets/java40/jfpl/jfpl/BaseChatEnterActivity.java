package com.jufan.platform.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.util.SystemUtil;
import com.lianzt.util.StringUtil;

public abstract class BaseChatEnterActivity extends AutoActivity {
	protected String busiType;
	protected String busiSerial;
	protected String chatTitle;

	private ImageView chatIcon;
	private ImageView backIcon;
	private TextView titleTv;

	@Override
	public void onCreate(Bundle savedInstanceState, int layoutId, String prefix) {
		super.onCreate(savedInstanceState, layoutId, prefix);
		Intent intent = getIntent();
		this.busiType = intent.getStringExtra("busi_type");
		this.busiSerial = intent.getStringExtra("busi_serial");
		this.chatTitle = intent.getStringExtra("title");
		this.chatIcon = (ImageView) findViewById(R.id.chat_icon_iv);
		this.backIcon = (ImageView) findViewById(R.id.title_back_btn);
		this.titleTv = (TextView) findViewById(R.id.frame_title_text);
		if (this.chatIcon != null) {
			this.chatIcon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					chatIconClick(v);
				}
			});
		}
		if (this.backIcon != null) {
			this.backIcon.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	public void chatIconClick(View v) {
		Intent intent = new Intent(this, Chat2Activity.class);
		intent.putExtra("busi_type", busiType);
		intent.putExtra("busi_serial", busiSerial);
		intent.putExtra("title", chatTitle);
		startActivity(intent);
	}

	public Intent getCustomIntent(Class clazz) {
		Intent intent = new Intent(this, clazz);
		intent.putExtra("busi_type", busiType);
		intent.putExtra("busi_serial", busiSerial);
		intent.putExtra("title", chatTitle);
		return intent;
	}

	public void setTitle(String title) {
		if (this.titleTv != null) {
			if (StringUtil.isNull(title)) {
				Log.w(SystemUtil.LOG_MSG, "title is null");
			} else {
				this.titleTv.setText(title);
			}
		} else {
			Log.w(SystemUtil.LOG_MSG, "not found R.id.frame_title_text");
		}
	}
}
