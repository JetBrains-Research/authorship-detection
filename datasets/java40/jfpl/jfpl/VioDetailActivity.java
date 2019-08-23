package com.jufan.platform.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.cyss.android.lib.view.FrameAlertDialog;
import com.jufan.platform.service.WfxwSearchService;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.exception.InstanceDataException;

public class VioDetailActivity extends BaseChatEnterActivity {
	@InjectionView(id = R.id.title_back_btn, click = "titleBackBtnClick")
	private ImageButton titleBackBtn;
	// @InjectionView(id = R.id.show_vio_pic, click = "showVioPicClick")
	private android.widget.Button showVioPicBtn;
	@InjectionView(id = R.id.wfxw, click = "wfxwBtnClick")
	private android.widget.TextView wfxwTv;

	private AbstractCommonData acd;

	private String wfxw;

	private FrameAlertDialog fad;
	private String bindUsername;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.vio_detail, "");
		Intent intent = getIntent();
		String json = intent.getStringExtra("json");
		this.bindUsername = intent.getStringExtra("bind_name");
		String title = intent.getStringExtra("wfdz");
		this.setTitle(title);
		fad = new FrameAlertDialog(this);
		try {
			acd = DataConvertFactory.getInstanceByJson(json);
			acd.putStringValue("fkje", acd.getStringValue("fkje") + " 元");
			fillData(acd);
			wfxw = wfxwTv.getText().toString();
			wfxwTv.setText(Html.fromHtml("<u>" + wfxw + "</u>"));
		} catch (InstanceDataException e) {
			e.printStackTrace();
		}

	}

	public void wfxwBtnClick(View v) {
		AbstractCommonData acd = SystemUtil.getCommonData("S23023",
				bindUsername);
		acd.putStringValue("wfxw", wfxw);
		acd.putObjectValue("iservice", new WfxwSearchService(this, wfxw, fad));
		ServiceController.addService(acd, this);
	}

	public void showVioPicClick(View v) {
		Intent intent = new Intent(this, ShowImageActivity.class);
		intent.putExtra("xh", acd.getStringValue("xh"));
		startActivity(intent);
	}

	public void titleBackBtnClick(View v) {
		finish();
	}
}
