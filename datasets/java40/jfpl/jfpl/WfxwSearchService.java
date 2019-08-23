package com.jufan.platform.service;

import android.content.Intent;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.AbstractService;
import com.cyss.android.lib.view.FrameAlertDialog;
import com.jufan.platform.ui.VioDetailActivity;
import com.jufan.platform.ui.WfxwDetailActivity;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;

public class WfxwSearchService extends AbstractService {

	private String wfxw;
	private FrameAlertDialog fad;

	public WfxwSearchService(AutoActivity autoActivity, String wfxw,
			FrameAlertDialog fad) {
		super(autoActivity);
		this.wfxw = wfxw;
		this.fad = fad;
	}

	@Override
	public AbstractCommonData doSuccess(AbstractCommonData acd) {
		acd.putBooleanValue("is_alert", false);
		if (acd.getBooleanValue("success")) {
			if (autoActivity.getClass() == VioDetailActivity.class) {
				VioDetailActivity act = (VioDetailActivity) autoActivity;
				Intent intent = act.getCustomIntent(WfxwDetailActivity.class);
				intent.putExtra("wfxw", wfxw);
				intent.putExtra("detail", DataConvertFactory.praseNormJson(acd));
				act.startActivity(intent);
			} else {
				Intent intent = new Intent(autoActivity,
						WfxwDetailActivity.class);
				intent.putExtra("wfxw", wfxw);
				intent.putExtra("detail", DataConvertFactory.praseNormJson(acd));
				autoActivity.startActivity(intent);
			}
		} else {
			fad.showDialog();
			fad.setMessage("该违法代码无对应信息");
		}
		return acd;
	}

	@Override
	public AbstractCommonData doError(AbstractCommonData acd) {
		return acd;
	}

}
