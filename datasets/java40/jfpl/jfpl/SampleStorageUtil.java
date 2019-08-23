package com.jufan.platform.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SampleStorageUtil {

	public static void saveKeyValue(Context ctx, String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getValue(Context ctx, String key) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		String value = sp.getString(key, "");
		return value;
	}

	public static void saveKeyValue(Context ctx, String[] keys, String[] values) {
		if (keys.length != values.length) {
			return;
		}
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Editor editor = sp.edit();
		for (int i = 0; i < keys.length; i++) {
			editor.putString(keys[i], values[i]);
		}
		editor.commit();
	}
}
