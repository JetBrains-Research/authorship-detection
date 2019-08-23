package com.weparty.views;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umeng.analytics.MobclickAgent;
import com.weparty.R;
import com.weparty.domain.SourceData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.act_edit_phone)
public class EditNumberActivity extends Activity {
	
	@Extra
	int flag;
	
	@ViewById(R.id.number_dt)
	EditText number_dt;
	
	@Extra("value")
	String value;
	
	@ViewById(R.id.title_phone_title)
	RelativeLayout title_phone_title;
	
	
	@AfterViews
	void afterViews(){
		if (flag != -1) {
			number_dt = (EditText) findViewById(R.id.number_dt);
			if(value.equals("number")){
				value = "";
			}
			number_dt.setText(value);
			TextView title = (TextView) findViewById(R.id.title_center);
			title.setText("编辑手机号");
			title_phone_title.setBackgroundResource(SourceData.getTheme());
		}
	}
		
	@Click(R.id.title_back)
	void back(){
		EditNumberActivity.this.finish();
	}

	@Click(R.id.title_pos) 
	void pos(){
		Intent intent = new Intent(EditNumberActivity.this,
				LaunchActivity.class);
		intent.putExtra("value", number_dt.getText()
				.toString());
		setResult(flag, intent);
		finish();
		
	}
	
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onResume(this);
	}
	
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	    hideKeyboard();
	}
	
	
	private void hideKeyboard(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		imm.hideSoftInputFromWindow(number_dt.getWindowToken(), 0); 
	}
}
