package com.jufan.platform.util;

import java.util.Date;

import com.cyss.android.lib.AutoActivity;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.factory.AESFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class GlobalVariables {
	public static String loginUsername = null;
	public static String chatToUser = "1031";
	public static String chatAppId = null;
	public static String chatAppSerial = null;

	public static void showToast(Context ctx, String content) {
		Toast.makeText(ctx, content, Toast.LENGTH_LONG).show();
	}

	public static void initVariables(AutoActivity act) {
		try {
			for (int i = 0; i < ConstVariables.INIT_APP.length; i++) {
				Object[] args = new Object[5];
				args[0] = ConstVariables.INIT_APP[i][0];
				args[1] = ConstVariables.INIT_APP[i][1];
				args[2] = ConstVariables.INIT_APP[i][2];
				args[3] = ConstVariables.INIT_APP[i][3];
				args[4] = "" + new Date().getTime();
				act.update("add_app", args);
			}
		} catch (Exception e) {

		}
	}

	public static AbstractCommonData getAutoLoginParam(Context ctx) {
		AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		String username = sp.getString("username", "");
		String pwd = sp.getString("pwd", "");
		long lastTime = sp.getLong("last_login_time", 0);
		boolean isAutoLogin = sp.getBoolean("is_auto_login", false);
		try {
			username = AESFactory.decryptString(username);
			pwd = AESFactory.decryptString(pwd);
			acd.putStringValue("username", username);
			acd.putStringValue("pwd", pwd);
			acd.putLongValue("last_login_time", lastTime);
			acd.putBooleanValue("is_auto_login", isAutoLogin);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return acd;
	}

	public static boolean setAutoLoginParam(Context ctx, AbstractCommonData acd) {
		String username = acd.getStringValue("username");
		String pwd = acd.getStringValue("pwd");
		long lastTime = acd.getLongValue("last_login_time");
		boolean isAutoLogin = acd.getBooleanValue("is_auto_login");
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Editor editor = sp.edit();
		try {
			editor.putString("username", AESFactory.encryptString(username));
			editor.putString("pwd", AESFactory.encryptString(pwd));
			editor.putLong("last_login_time", lastTime);
			editor.putBoolean("is_auto_login", isAutoLogin);
		} catch (Exception e) {
			return false;
		}
		editor.commit();
		return true;
	}

	public static String getHpzlDesc(String hpzl) {
		for (int i = 0; i < ConstVariables.HPZL_DESC.length; i += 2) {
			if (ConstVariables.HPZL_DESC[i].equals(hpzl)) {
				return ConstVariables.HPZL_DESC[i + 1];
			}
		}
		return null;
	}
}
