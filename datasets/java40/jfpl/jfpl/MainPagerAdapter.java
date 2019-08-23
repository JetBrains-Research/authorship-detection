package com.jufan.platform.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.LoginActivity;
import com.jufan.platform.ui.MainActivity;
import com.jufan.platform.ui.PersonInfoActivity;
import com.jufan.platform.ui.R;
import com.jufan.platform.ui.WebContentActivity;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.ui.R;
import com.jufan.platform.ui.WebContentActivity;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainPagerAdapter extends PagerAdapter {

	private MainActivity act;
	private List<View> viewList;
	private List<String> titleList;

	public MainPagerAdapter(MainActivity act, List<View> viewList,
			List<String> titleList) {
		this.viewList = viewList;
		this.titleList = titleList;
		this.act = act;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewList.get(position));

	}

	@Override
	public int getItemPosition(Object object) {

		return super.getItemPosition(object);
	}

	@Override
	public CharSequence getPageTitle(int position) {

		return titleList.get(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v = viewList.get(position);
		container.addView(v);
		if (position == 0) {
			// AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			// List<AbstractCommonData> list = new
			// ArrayList<AbstractCommonData>();
			// AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
			// item.putIntValue("recently_icon", R.drawable.vio_search);
			// item.putStringValue("recently_app", "掌上车管所");
			// item.putStringValue("recently_desc", "新违章提醒：金水路与文化路交叉口");
			// list.add(item);
			// act.fillData(acd, viewList.get(position), "");
			act.refreshRecently();
		} else if (position == 1) {
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			List<AbstractCommonData> appList = act.query("get_app_list");
			acd.putArrayValue("app_grid", appList);
			act.fillData(acd, viewList.get(position), "");
			// View zscgs = v.findViewById(R.id.app_zscgs);
			// View bxlp = v.findViewById(R.id.app_bxlp);
			// zscgs.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent(act, WebContentActivity.class);
			// intent.putExtra("title", act.getString(R.string.app1));
			// intent.putExtra("app_id", "1");
			// intent.putExtra("url",
			// "http://www.jtgzfw.com/jtgzfw/_pc/wx/jgcs_list.do?type_id=JC02");
			// act.startActivity(intent);
			// }
			// });
			// bxlp.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent(act, WebContentActivity.class);
			// intent.putExtra("title", act.getString(R.string.app2));
			// intent.putExtra("app_id", "2");
			// intent.putExtra("url",
			// "http://www.jtgzfw.com/jtgzfw/_pc/wx/wx_allnews.do");
			// act.startActivity(intent);
			// }
			// });
		} else if (position == 2) {
			TextView logoutTv = (TextView) v.findViewById(R.id.more_logout);
			TextView piInfoTv = (TextView) v.findViewById(R.id.more_profile);
			logoutTv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AbstractCommonData loginPara = GlobalVariables
							.getAutoLoginParam(act);
					loginPara.putBooleanValue("is_auto_login", false);
					GlobalVariables.setAutoLoginParam(act, loginPara);
					Intent intent = new Intent(act, LoginActivity.class);
					act.startActivity(intent);
					act.finish();
				}
			});

			piInfoTv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(act, PersonInfoActivity.class);
					act.startActivity(intent);
				}
			});
		}
		return v;
	}

}
