package com.jufan.platform.ui;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.receiver.PushMessageReceiver;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.SSLConnection;
import com.lianzt.util.StringUtil;

public class RegisterActivity extends AutoActivity {

	@InjectionView(id = R.id.next_step_btn, click = "nextStepClick")
	private Button nextStepBtn;
	@InjectionView(id = R.id.reg_username)
	private EditText usernameEt;
	@InjectionView(id = R.id.reg_pwd1)
	private EditText pwd1Et;
	@InjectionView(id = R.id.reg_pwd2)
	private EditText pwd2Et;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.register1, "");
	}

	public void nextStepClick(View v) {
		final String username = usernameEt.getText().toString();
		final String pwd1 = pwd1Et.getText().toString();
		String pwd2 = pwd1Et.getText().toString();
		if (StringUtil.isNull(username)) {
			GlobalVariables.showToast(this, "通行证不可为空");
		} else if (StringUtil.isNull(pwd1)) {
			GlobalVariables.showToast(this, "密码不可为空");
		} else if (!pwd1.equals(pwd2)) {
			GlobalVariables.showToast(this, "两次密码不一样");
		} else {
			AbstractCommonData acd = ConstVariables
					.getCommPacket(GlobalVariables.loginUsername);
			acd.putStringValue("username", username);
			acd.putStringValue("password",
					SSLConnection.Md5(pwd1 + ConstVariables.NODE_MD5_CHECK_KEY));
			String[] args = PushMessageReceiver.getBindInfo(this);
			acd.putStringValue("user_id", args[2]);
			acd.putStringValue("channel_id", args[1]);
			acd.putStringValue("_url", ConstVariables.RESGITER_URL);
			acd.putObjectValue("ipacket",
					HttpSendFactory.getChatPlatformSendMethod());
			acd.putObjectValue("iservice", new IServiceLogic() {

				@Override
				public AbstractCommonData doSuccess(AbstractCommonData acd) {
					GlobalVariables.loginUsername = username;
					AbstractCommonData loginPara = DataConvertFactory
							.getInstanceEmpty();
					loginPara.putStringValue("username", username);
					loginPara.putStringValue("pwd", pwd1);
					loginPara.putLongValue("last_login_time",
							new Date().getTime());
					loginPara.putBooleanValue("is_auto_login", true);
					GlobalVariables.setAutoLoginParam(RegisterActivity.this,
							loginPara);
					Intent intent = new Intent(RegisterActivity.this,
							RegisterTipActivity.class);
					startActivity(intent);
					finish();
					return null;
				}

				@Override
				public AbstractCommonData doError(AbstractCommonData acd) {
					// TODO Auto-generated method stub
					return null;
				}
			});
			ServiceController.addService(acd, this);
		}

	}

}
