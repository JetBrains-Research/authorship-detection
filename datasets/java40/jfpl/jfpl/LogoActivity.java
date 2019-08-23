package com.jufan.platform.ui;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.FrameContext;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.receiver.PushMessageReceiver;
import com.jufan.platform.service.LoginService;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.util.SSLConnection;

public class LogoActivity extends AutoActivity {

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler();

	private Runnable runThread = new Runnable() {

		@Override
		public void run() {
			if (PushMessageReceiver.hasBind(LogoActivity.this)) {
				AbstractCommonData loginPara = GlobalVariables
						.getAutoLoginParam(LogoActivity.this);
				if (loginPara != null) {
					if (loginPara.getBooleanValue("is_auto_login")) {
						loginService(loginPara);
					} else {
						gotoLogin();
					}
				} else {
					gotoLogin();
				}

			} else {
				handler.postDelayed(runThread, 1000);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.logo, "");
		if (!PushMessageReceiver.hasBind(this)) {
			Log.d(SystemUtil.LOG_MSG, "has no bind");
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					ConstVariables.BAIDU_API_KEY);
			PushManager.enableLbs(getApplicationContext());

		} else {
			Log.d(SystemUtil.LOG_MSG, "has bind");
		}

		GlobalVariables.initVariables(this);
		// PushManager.startWork(getApplicationContext(),
		// PushConstants.LOGIN_TYPE_API_KEY, ConstVariables.BAIDU_API_KEY);
		// PushManager.enableLbs(getApplicationContext());
		FrameContext.startBaseService(this);
		handler.postDelayed(runThread, 2000);
	}

	private void gotoLogin() {
		Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	private void loginService(AbstractCommonData loginPara) {
		final String username = loginPara.getStringValue("username");
		final String pwd = loginPara.getStringValue("pwd");
		AbstractCommonData acd = ConstVariables.getCommPacket(username);
		acd.putStringValue("username", username);
		acd.putStringValue("password",
				SSLConnection.Md5(pwd + ConstVariables.NODE_MD5_CHECK_KEY));
		String[] args = PushMessageReceiver.getBindInfo(this);
		acd.putStringValue("user_id", args[2]);
		acd.putStringValue("channel_id", args[1]);
		acd.putStringValue("_url", ConstVariables.CHAT_BIND_INFO_URL);
		acd.putBooleanValue("is_show_loading", false);
		acd.putObjectValue("iservice", new LoginService(this, username, pwd,
				true));
		acd.putObjectValue("ipacket",
				HttpSendFactory.getChatPlatformSendMethod());
		ServiceController.addService(acd, this);
	}
}
