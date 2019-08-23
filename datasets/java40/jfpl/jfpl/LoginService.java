package com.jufan.platform.service;

import java.util.Date;

import android.content.Intent;
import android.util.Log;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.LoginActivity;
import com.jufan.platform.ui.MainActivity;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;

public class LoginService implements IServiceLogic {

	private AutoActivity ctx;
	private String username;
	private String pwd;
	private boolean toLogin;

	public LoginService(AutoActivity ctx, String username, String pwd,
			boolean toLogin) {
		this.ctx = ctx;
		this.username = username;
		this.pwd = pwd;
		this.toLogin = toLogin;
	}

	public LoginService(AutoActivity ctx, String username, String pwd) {
		this.ctx = ctx;
		this.username = username;
		this.pwd = pwd;
		this.toLogin = false;
	}

	@Override
	public AbstractCommonData doSuccess(AbstractCommonData acd) {
		Log.i(SystemUtil.LOG_MSG, "login service success");
		GlobalVariables.loginUsername = username;
		AbstractCommonData loginPara = DataConvertFactory.getInstanceEmpty();
		loginPara.putStringValue("username", username);
		loginPara.putStringValue("pwd", pwd);
		loginPara.putLongValue("last_login_time", new Date().getTime());
		loginPara.putBooleanValue("is_auto_login", true);
		GlobalVariables.setAutoLoginParam(ctx, loginPara);
		Intent intent = new Intent(ctx, MainActivity.class);
		ctx.startActivity(intent);
		ctx.finish();
		return null;
	}

	@Override
	public AbstractCommonData doError(AbstractCommonData acd) {
		Log.i(SystemUtil.LOG_MSG, "login service error");
		if (this.toLogin) {
			Intent intent = new Intent(ctx, LoginActivity.class);
			ctx.startActivity(intent);
			ctx.finish();
		}
		return null;
	}

}
