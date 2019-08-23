package com.weparty.utils;


import com.weparty.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;


public class Output {
	
	public static void toast(Context c,String msg){
		Toast.makeText(c,msg,0).show();
	}
	
	public static void toast(Context c,int resId){
		Toast.makeText(c,c.getString(resId),0).show();
	}
	
	//private static ProgressStyle defineProDialog;
	private static ProgressDialog defineProDialog;
	
	public static void showProgressDialog(Context c) {
		if (defineProDialog == null) {
			  defineProDialog = new ProgressDialog(c);
			  defineProDialog.setMessage(c.getString(R.string.sendRequest));
		}
		defineProDialog.show();
	}
	
	public static void showProgressDialog(Context c,int id) {
		if (defineProDialog == null) {
			  defineProDialog = new ProgressDialog(c,id);
		}
		defineProDialog.show();
	}
	
	public static void closeProgressDialog() {
		if (defineProDialog != null && defineProDialog.isShowing()){
			defineProDialog.dismiss();
			defineProDialog = null;
		}
	}
	
	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "提示", "知道了", null);
	}

	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr,DialogInterface.OnClickListener onClickListener) {
		
		newDialog(context, message, titleStr, positiveStr,"", onClickListener,null);
		
	}
	
	public static void showInfoDialog(Context context, String message,
									  String titleStr, String positiveStr,String negaStr,
									  DialogInterface.OnClickListener posOnClickListener,
									  DialogInterface.OnClickListener negaOnClickListener) {
		
		newDialog(context, message, titleStr, positiveStr, 
					negaStr,posOnClickListener,negaOnClickListener);
		
	}
	
	public static void newDialog(Context context, String message,
								String titleStr, String positiveStr,String negaStr,
								DialogInterface.OnClickListener posOnClickListener,
								DialogInterface.OnClickListener negaOnClickListener){
		
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		//localBuilder.setIcon(R.drawable.ic_launcher);
		
		if(titleStr != null)localBuilder.setTitle(titleStr);
		
		localBuilder.setMessage(message);
		
		if (posOnClickListener == null)
			
			posOnClickListener = new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {}
		};
			
		if(negaOnClickListener != null) localBuilder.setNegativeButton(negaStr,negaOnClickListener);
		
		localBuilder.setPositiveButton(positiveStr,posOnClickListener);
		
		localBuilder.show();
	}
	
	
	

}
