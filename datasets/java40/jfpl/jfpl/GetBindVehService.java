package com.jufan.platform.service;

import java.util.Date;
import java.util.List;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.IServiceLogic;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.util.SampleStorageUtil;
import com.lianzt.commondata.AbstractCommonData;

public class GetBindVehService implements IServiceLogic {

	private AutoActivity ctx;

	public GetBindVehService(AutoActivity ctx) {
		this.ctx = ctx;
	}

	@Override
	public AbstractCommonData doSuccess(AbstractCommonData acd) {
		ctx.update("delete_bind_veh_byuname", GlobalVariables.loginUsername);
		List<AbstractCommonData> vehs = acd.getArrayValue("vehs");
		for (AbstractCommonData item : vehs) {
			String bindName = item.getStringValue("jtgzfw_user");
			String hpzl = item.getStringValue("hpzl");
			String hphm = item.getStringValue("hphm");
			try {
				ctx.update("add_bind_veh", bindName,
						GlobalVariables.loginUsername, hphm, hpzl, "");
			} catch (Exception ex) {

			}
		}
		SampleStorageUtil.saveKeyValue(ctx, ConstVariables.SYNC_VEH_TIME,
				new Date().getTime() + "");
		return null;
	}

	@Override
	public AbstractCommonData doError(AbstractCommonData acd) {
		return null;
	}

}
