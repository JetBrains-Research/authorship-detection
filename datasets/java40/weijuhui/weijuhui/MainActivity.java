package com.weparty.views;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.weparty.R;
import com.weparty.WePartyApplication;
import com.weparty.domain.SourceData;
import com.weparty.domain.UpdateResult;
import com.weparty.fragment.FragScence;
import com.weparty.utils.APKDownloader;
import com.weparty.utils.Output;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.LinearLayout.LayoutParams;

/**
 * 
 * <ul>
 * <li><b>name : </b>		WXEntryActivity		</li>
 * <li><b>description :</b>	活动首页				</li>
 * <li><b>author : </b>		yelingh			    </li>
 * <li><b>date : </b>		2013-8-29 上午2:58:05		</li>
 * </ul>
 */
@EActivity(R.layout.act_main)
public class MainActivity extends Activity {

	/** IWXAPI 是第三方app和微信通信的openapi接口 */
	 IWXAPI api;
	 
	 private boolean flag = true;

	@AfterViews
	void initConfig(){
		
		/** IWXAPI 是第三方app和微信通信的openapi接口 */
		api = WXAPIFactory.createWXAPI(this, WePartyApplication.APP_ID, false);

		/** app注册至微信 */
		api.registerApp(WePartyApplication.APP_ID);
		
		buildContent();
		
		/*LayoutParams params = getWH();
		
		WePartyApplication.width = params.width;
		WePartyApplication.height = params.height;*/
		
		updateVersion();
	}
	
	private LayoutParams getWH(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeigh = dm.heightPixels;
		LayoutParams params = new LayoutParams(screenWidth/2, screenHeigh/3);
		return params;
	}
	
	private void buildContent() {

		Fragment details = (Fragment) getFragmentManager()
				.findFragmentById(R.id.main_content);

		details = new FragScence();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.main_content, details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void updateVersion(){
		
		UpdateResult result = SourceData.getUpdateResult();
		
		if(result != null && result.isUpdate()){
			
			Output.showInfoDialog(this,"出新版本"+ 
					SourceData.getUpdateResult().getVersion() + "啦,赶快体验吧",
					"注意注意","我要体验","先等会吧",
					new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							apkDownload();
						}
					}, new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
		}
		
	}
	
	private void apkDownload(){
		Output.toast(this,"开始下载");
		APKDownloader downloader = new APKDownloader(this);
		downloader.setAppDownLoadUrl(SourceData.DOWNLOAD + SourceData.getUpdateResult().getAkpUrl());
		downloader.downLoadNewVersion();
	}

}
