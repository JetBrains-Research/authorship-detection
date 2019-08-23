package com.jufan.platform.service;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.R;
import com.lianzt.commondata.AbstractCommonData;

public class ChatBaseService implements IServiceLogic {

	protected ProgressBar progressBar;
	protected ImageView imageView;
	protected AbstractCommonData itemData;
	protected AutoActivity ctx;
	protected ListView chatList;

	public ChatBaseService(AutoActivity ctx, ListView chatList,
			ProgressBar progressBar, ImageView imageView, AbstractCommonData acd) {

		this.progressBar = progressBar;
		this.imageView = imageView;
		this.itemData = acd;
		this.ctx = ctx;
		this.chatList = chatList;
	}

	protected void doChatSuccess(AbstractCommonData acd) {

	}

	protected void doChatError(AbstractCommonData acd) {

	}

	@Override
	public AbstractCommonData doSuccess(AbstractCommonData acd) {
		String msgZt = acd.getStringValue("message_zt");
		if ("1".equals(msgZt)) {
			progressBar.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(R.drawable.done_holo);
			itemData.putStringValue("send_state", "0");
			BaseAdapter ba = (BaseAdapter) chatList.getAdapter();
			ba.notifyDataSetChanged();
			doChatSuccess(acd);
		} else {
			doError(acd);

		}

		return null;
	}

	@Override
	public AbstractCommonData doError(AbstractCommonData acd) {
		progressBar.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageResource(R.drawable.holo_undone);
		itemData.putStringValue("send_state", "1");
		BaseAdapter ba = (BaseAdapter) chatList.getAdapter();
		ba.notifyDataSetChanged();
		doChatError(acd);
		return null;
	}

}
