package com.jufan.platform.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.view.RightMenuDialog;

public class RightMenuActivity extends AutoActivity {

	private View triggerView;
	private RightMenuDialog rDia;
	private boolean defaultMenuItemFlag = true;
	private boolean pageLoadFinish = false;

	protected String busiType = ConstVariables.CHAT_DEFAULT_BUSITYPE;
	protected String busiSerial = ConstVariables.CHAT_DEFAULT_BUSISERIAL;

	protected String chatTitle = "";
	private static final String chatEnterName = "客服咨询";

	@Override
	public void onCreate(Bundle savedInstanceState, int layoutId, String prefix) {
		super.onCreate(savedInstanceState, layoutId, prefix);
		init();
	}

	@Override
	public void setContentView(View v) {
		super.setContentView(v);
		init();
	}

	private void init() {
		if (this.rDia == null)
			this.rDia = new RightMenuDialog(this);
		if (defaultMenuItemFlag) {
			initMenuItem();
		}
	}

	private void initMenuItem() {
		// rDia.addMenuItem(chatEnterName, "", new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(RightMenuActivity.this,
		// ChatActivity.class);
		// intent.putExtra("busi_type", busiType);
		// intent.putExtra("busi_serial", busiSerial);
		// intent.putExtra("title", chatTitle);
		// startActivity(intent);
		// }
		// });
	}

	public void setDefaultMenuItem(boolean flag) {
		defaultMenuItemFlag = flag;
		if (!flag && rDia != null) {
			rDia.removeMenuItem(chatEnterName);
		}
	}

	protected void setRightMenuBtn(View v) {
		this.triggerView = v;
		this.triggerView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				rightMenuClick(v);
			}
		});
	}

	public void addMenuItem(String name, String tag, View.OnClickListener click) {
		if (rDia == null) {
			init();
		}
		rDia.addMenuItem(name, tag, click);
	}

	protected void setChatTitle(String title) {
		this.chatTitle = title;
	}

	public void setChatBusiType(String tag) {
		this.busiType = tag;
	}

	public String getChatBusiType() {
		return this.busiType;
	}

	public void setChatBusiSerial(String busiSerial) {
		this.busiSerial = busiSerial;

	}

	public void setPageLoadFinish(boolean flag) {
		this.pageLoadFinish = flag;
		if (rDia.getMenuCount() > 0) {
			triggerView.setVisibility(View.VISIBLE);
		}
	}

	public void rightMenuClick(View v) {
		if (rDia != null && pageLoadFinish) {
			rDia.show();
		} else {
			Toast.makeText(this, "请等待加载页面结束..", Toast.LENGTH_LONG).show();
		}
	}

}
