package com.jufan.platform.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushManager;
import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.adapter.MainPagerAdapter;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.service.GetBindVehService;
import com.jufan.platform.util.ChatMessageUtil;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.util.SampleStorageUtil;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("ResourceAsColor")
public class MainActivity extends AutoActivity implements OnPageChangeListener {

	@InjectionView(id = R.id.main_pager)
	private ViewPager viewPager;
	@InjectionView(id = R.id.page_tab_cursor)
	private android.widget.ImageView pageCursor;
	@InjectionView(id = R.id.title_recently, click = "pageTabClick")
	private TextView recentTv;
	@InjectionView(id = R.id.title_app, click = "pageTabClick")
	private TextView appTv;
	@InjectionView(id = R.id.title_more, click = "pageTabClick")
	private TextView settingTv;

	private View recentlyPage, appPage, settingPage;
	private int pageBarWidth = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.main, "");

		// 同步绑定车辆
		String syncVehTimeStr = SampleStorageUtil.getValue(this,
				ConstVariables.SYNC_VEH_TIME);
		if (!StringUtil.isNull(syncVehTimeStr)) {
			long syncVehTime = Long.parseLong(syncVehTimeStr);
			if (new Date().getTime() - syncVehTime > ConstVariables.DEFAULT_SYNC_TIME) {
				AbstractCommonData sendData = SystemUtil.getCommonData("",
						GlobalVariables.loginUsername);
				sendData.putStringValue("_url", ConstVariables.GET_BIND_VEH_URL);
				sendData.putObjectValue("ipacket",
						HttpSendFactory.getChatPlatformSendMethod());
				sendData.putObjectValue("iservice", new GetBindVehService(this));
				ServiceController.addService(sendData);
			}
		}
		recentTv.setTag(0);
		appTv.setTag(1);
		settingTv.setTag(2);

		LayoutInflater lf = getLayoutInflater();
		recentlyPage = lf.inflate(R.layout.recently, null);
		appPage = lf.inflate(R.layout.application, null);
		settingPage = lf.inflate(R.layout.more, null);
		List<View> viewList = new ArrayList<View>();
		viewList.add(recentlyPage);
		viewList.add(appPage);
		viewList.add(settingPage);
		List<String> titleList = new ArrayList<String>();
		titleList.add(getString(R.string.recently));
		titleList.add(getString(R.string.application));
		titleList.add(getString(R.string.more));
		viewPager.setAdapter(new MainPagerAdapter(this, viewList, titleList));
		viewPager.setOnPageChangeListener(this);
		pageBarWidth = SystemUtil.SCREEN_WIDTH / 3;
		pageCursor.setLayoutParams(new LinearLayout.LayoutParams(pageBarWidth,
				4));
		onPageSelected(0);

		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
				this, R.layout.notification, R.id.notification_icon,
				R.id.notification_title, R.id.notification_text);
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		PushManager.setNotificationBuilder(this, 1, cBuilder);

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int index, float per, int width) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				pageBarWidth, 4);
		lp.setMargins(index * pageBarWidth + width / 3, 0, 0, 0);
		pageCursor.setLayoutParams(lp);
	}

	@Override
	public void onPageSelected(int index) {
		int black = getResources().getColor(R.color.black);
		int green = getResources().getColor(R.color.tab_cursor);
		switch (index) {
		case 0:
			recentTv.setTextColor(green);
			appTv.setTextColor(black);
			settingTv.setTextColor(black);
			break;
		case 1:
			recentTv.setTextColor(black);
			appTv.setTextColor(green);
			settingTv.setTextColor(black);
			break;
		case 2:
			recentTv.setTextColor(black);
			appTv.setTextColor(black);
			settingTv.setTextColor(green);
			break;

		default:
			break;
		}
	}

	public void pageTabClick(View v) {
		int index = Integer.parseInt(v.getTag().toString());
		viewPager.setCurrentItem(index);
	}

	@Override
	protected void onGridItemClick(String prxName, android.widget.GridView g,
			View v, int position, long id) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		if (g.getId() == R.id.app_grid) {
			String url = acd.getStringValue("url");
			Log.d(SystemUtil.LOG_MSG, url);
			Intent intent = null;
			if (url.startsWith("http://") || url.startsWith("file://")) {
				intent = new Intent(this, WebContentActivity.class);
				intent.putExtra("title", acd.getStringValue("title"));
				intent.putExtra("app_id", acd.getStringValue("app_id"));
				intent.putExtra("url", url);
			} else if (url.startsWith("intent://")) {
				intent = new Intent();
				intent.putExtra("title", acd.getStringValue("title"));
				intent.putExtra("busi_type", acd.getStringValue("app_id"));
				url = url.replace("intent://", "");
				int index = url.lastIndexOf(".");
				intent.setClassName(url.substring(0, index), url);
			} else {
				GlobalVariables.showToast(this, "非法路径");
				return;
			}
			startActivity(intent);
		}
	}

	@Override
	protected void handleListItem(View v, int position) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		String uncheckNum = acd.getStringValue("nocheck_num");
		if ("0".equals(uncheckNum)) {
			v.findViewById(R.id.nocheck_num).setVisibility(View.GONE);
		}
		String icon = acd.getStringValue("icon");
		if (!StringUtil.isNull(icon)) {
			ImageView iv = (ImageView) v.findViewById(R.id.recently_icon);
			iv.setImageBitmap(ImageLoader.getInstance().loadImageSync(icon));
		}
		String desc = acd.getStringValue("recently_desc");
		acd.putStringValue("recently_desc",
				ChatMessageUtil.convertToClientMessage(desc));
	}

	@Override
	protected void handleGridItem(android.widget.GridView gv, View v,
			int position) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		if (gv.getId() == R.id.app_grid) {
			String icon = acd.getStringValue("icon");
			ImageView iconIv = (ImageView) v.findViewById(R.id.app_icon);
			iconIv.setImageBitmap(ImageLoader.getInstance().loadImageSync(icon));
		}
	}

	@Override
	protected void onListItemClick(String prxName, ListView l, View v,
			int position, long id) {
		if (l.getId() == R.id.recently_list) {
			AbstractCommonData acd = (AbstractCommonData) v.getTag();
			String url = acd.getStringValue("url");
			String title = acd.getStringValue("title");
			String appId = acd.getStringValue("app_id");
			Intent intent = null;
			if (StringUtil.isNull(url)) {
				intent = new Intent(this, Chat2Activity.class);
				intent.putExtra("title", title);
				intent.putExtra("busi_type", appId);
				intent.putExtra("busi_serial", "");
			} else {
				intent = new Intent(this, WebContentActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("url", url);
			}
			// http://www.jtgzfw.com/jtgzfw/_pc/wx/tcc_list.do?xzqh=5oOg5rWO5Yy6
			// http://www.jtgzfw.com/jtgzfw/_pc/wx/jgcs_list.do?type_id=JC02
			// file:///android_asset/www/test.html
			update("clear_app_nocheck_num", appId);
			refreshRecently();
			startActivity(intent);
		}
	}

	public void refreshRecently() {
		AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
		List<AbstractCommonData> list = query("get_chat_recently_list");
		for (AbstractCommonData item : list) {
			String title = item.getStringValue("title");
			String content = item.getStringValue("content");
			String icon = item.getStringValue("icon");
			item.putStringValue("recently_app", title);
			item.putStringValue("recently_desc", content);
		}
		acd.putArrayValue("recently_list", list);
		fillData(acd, recentlyPage, "");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notifyManager.cancel(0);
		viewPager.setCurrentItem(0);
		refreshRecently();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addCategory(Intent.CATEGORY_HOME);
			startActivity(i);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
