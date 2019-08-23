package com.weparty.views;

import org.json.JSONException;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.weparty.R;
import com.weparty.domain.SourceData;
import com.weparty.http.AsyncHttpResponseHandler;
import com.weparty.http.RequestParams;
import com.weparty.http.WePartyRestClient;
import com.weparty.http.impl.UpdateResultBuild;
import com.weparty.utils.LocalStore;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


@EActivity(R.layout.act_welcome)
public class WelcomeActivity extends Activity implements Runnable {

	
	
	@AfterViews
	void initConfig(){
		//makeRequest();
		
		if(LocalStore.getInit(this)){
			new Thread(this).start();
		}else{
			new GuideActivity_.IntentBuilder_(WelcomeActivity.this).start();
			finish();
			LocalStore.setInit(WelcomeActivity.this);
		}
		
		
	}
	
	void intentBuilder(){
		new MainActivity_.IntentBuilder_(WelcomeActivity.this).start();
		//overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
		finish();
	}
	
	void makeRequest(){
		try {
			
			WePartyRestClient.updateVersion(buildRequestParams(),new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess(String content) {
					super.onSuccess(content);
					try {
						  SourceData.setUpdateResult(new UpdateResultBuild().build(content));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFinish() {
					super.onFinish();
				}
			});
			
		} catch (Exception e) {
			
		}finally{
			
		}
		
	}
	
	RequestParams buildRequestParams() throws Exception{
		RequestParams params = new RequestParams();
		params.put("version",getVersionName());
		return params; 
	}
	
	private String getVersionName() throws Exception{
       // 获取packagemanager的实例
       PackageManager packageManager = getPackageManager();
       // getPackageName()是你当前类的包名，0代表是获取版本信息
       PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
       String version = packInfo.versionName;
       return version.replace(".","");
	 }

	@Override
	public void run() {
		try {
			// 一秒后跳转到登录界面
			Thread.sleep(2000);
			intentBuilder();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
