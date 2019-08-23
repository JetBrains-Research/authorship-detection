package com.jufan.platform.service;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.IServiceLogic;
import com.jufan.platform.ui.MyVioActivity;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;

public class BindVehService implements IServiceLogic {

	private AutoActivity ctx;
	private AbstractCommonData sendData;

	public BindVehService(AutoActivity ctx, AbstractCommonData sendData) {
		this.ctx = ctx;
		this.sendData = sendData;
	}

	@Override
	public AbstractCommonData doSuccess(AbstractCommonData acd) {
		MyVioActivity mva = (MyVioActivity) ctx;
		String bindUname = acd.getStringValue("jtgzfw_user");
		String hphm = sendData.getStringValue("hphm");
		String hpzl = sendData.getStringValue("hpzl");
		String clsbdh = sendData.getStringValue("clsbdh");
		try {
			mva.update("add_bind_veh", bindUname,
					GlobalVariables.loginUsername, hphm, hpzl, clsbdh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mva.refreshBindVeh(bindUname);
		return null;
	}

	@Override
	public AbstractCommonData doError(AbstractCommonData acd) {
		return null;
	}

}
