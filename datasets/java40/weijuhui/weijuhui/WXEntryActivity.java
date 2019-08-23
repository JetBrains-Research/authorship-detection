package com.weparty.wxapi;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.umeng.analytics.MobclickAgent;
import com.weparty.R;
import com.weparty.WePartyApplication;
import com.weparty.domain.SourceData;
import com.weparty.domain.WXRequest;
import com.weparty.utils.Output;
import com.weparty.views.MainActivity_;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * 
 * <ul>
 * <li><b>name : </b> WXEntryActivity</li>
 * <li><b>description :</b> app请求发送与微信回调类</li>
 * <li><b>author : </b> yelingh</li>
 * <li><b>date : </b> 2013-8-29 上午2:58:05</li>
 * </ul>
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	/** IWXAPI 是第三方app和微信通信的openapi接口 */
	IWXAPI api;

	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initConfig();
	}

	void initConfig() {
		context = getApplicationContext();
		/** IWXAPI 是第三方app和微信通信的openapi接口 */
		api = WXAPIFactory.createWXAPI(this, WePartyApplication.APP_ID, false);
		/** app注册至微信 */
		api.registerApp(WePartyApplication.APP_ID);
		
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onRestart() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// 微信向微聚会发送请求后响应
		new MainActivity_.IntentBuilder_(this).start();
		this.finish();
		Output.toast(this,"欢迎回家");
	}

	@Override
	public void onResp(BaseResp resp) {
		// 微聚会向微信发送请求后响应
		int result = 0;
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			new MainActivity_.IntentBuilder_(this).start();
			this.finish();
			Output.toast(this,"回来了");
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
		//Output.toast(this, result);
		//requestWeb();

	}

	void requestWeb() {

		try {

			WXRequest request = SourceData.getWxRequest();
			if (request != null) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = request.getUrl();
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = request.getTitle();
				msg.description = request.getDescription();

				Bitmap thumb = BitmapFactory.decodeResource(getResources(),
						R.drawable.wx_msg);

				msg.thumbData = Util.bmpToByteArray(thumb, true);

				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				boolean result = api.sendReq(req);
			}

		} catch (Exception e) {

		} finally {
		}
	}
	
	String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
}
