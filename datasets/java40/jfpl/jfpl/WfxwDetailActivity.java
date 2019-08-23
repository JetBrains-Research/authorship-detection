package com.jufan.platform.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.view.FrameAlertDialog;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.exception.InstanceDataException;

public class WfxwDetailActivity extends BaseChatEnterActivity{
	@InjectionView(id = R.id.title_back_btn, click = "titleBackBtnClick")
	private ImageButton titleBackBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.wfxw_detail, "");
		Intent intent = getIntent();
		String wfxw = intent.getStringExtra("wfxw");
		this.setTitle(wfxw);
		String detail = intent.getStringExtra("detail");
		try {
			AbstractCommonData acd = DataConvertFactory.getInstanceByJson(detail);
			acd.putStringValue("wfxw", wfxw);
			fillData(acd);
		} catch (InstanceDataException e) {
			e.printStackTrace();
		}
	}
	
	public void titleBackBtnClick(View v) {
		finish();
	}
}
