package com.jufan.platform.ui;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.receiver.PushMessageReceiver;
import com.jufan.platform.service.LoginService;
import com.jufan.platform.util.ChatMessageUtil;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.SSLConnection;
import com.lianzt.util.StringUtil;

import android.widget.EditText;

public class LoginActivity extends AutoActivity {
	@InjectionView(id = R.id.register_btn, click = "registerBtnClick")
	private Button registerBtn;

	@InjectionView(id = R.id.login_btn, click = "loginBtnClick")
	private Button loginBtn;

	@InjectionView(id = R.id.login_username)
	private EditText usernameEt;

	@InjectionView(id = R.id.login_pwd)
	private EditText pwdEt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.login, "");
	}

	public void registerBtnClick(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	public void loginBtnClick(View v) {
		final String username = usernameEt.getText().toString();
		final String pwd = pwdEt.getText().toString();
		if (StringUtil.isNull(username)) {
			Toast.makeText(this, "用户名不可为空", Toast.LENGTH_LONG).show();
		} else {

			AbstractCommonData acd = ConstVariables.getCommPacket(username);
			acd.putStringValue("username", username);
			acd.putStringValue("password",
					SSLConnection.Md5(pwd + ConstVariables.NODE_MD5_CHECK_KEY));
			String[] args = PushMessageReceiver.getBindInfo(this);
			acd.putStringValue("user_id", args[2]);
			acd.putStringValue("channel_id", args[1]);
			acd.putStringValue("_url", ConstVariables.CHAT_BIND_INFO_URL);
			acd.putObjectValue("iservice",
					new LoginService(this, username, pwd));
			acd.putObjectValue("ipacket",
					HttpSendFactory.getChatPlatformSendMethod());
			ServiceController.addService(acd, this);
		}

	}
}
