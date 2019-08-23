package com.jufan.platform.webview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;

import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.WebContentActivity;
import com.lianzt.util.StringUtil;

public class PluginManager {

	private WebContentActivity ctx;

	public PluginManager(WebContentActivity ctx) {
		this.ctx = ctx;
	}

	public void exec(String json) {

	}

	public boolean setChatBusiType(String tag) {
		ctx.setChatBusiType(tag);
		return true;
	}

	public boolean setChatBusiSerial(String str) {
		ctx.setChatBusiSerial(str);
		return true;
	}

	public String getChatBusiType() {
		return ctx.getChatBusiType();
	}

	public boolean initMenu(String json) {
		Log.d(SystemUtil.LOG_MSG, json);
		try {
			JSONObject jobj = new JSONObject(json);
			String _flag = jobj.getString("is_default");

			if (!StringUtil.isNull(_flag)) {
				boolean flag = Boolean.parseBoolean(_flag);
				ctx.setDefaultMenuItem(flag);
			}

			JSONArray buttons = jobj.getJSONArray("buttons");
			Log.d(SystemUtil.LOG_MSG, "---->" + buttons.length());
			for (int i = 0; i < buttons.length(); i++) {
				JSONObject button = (JSONObject) buttons.get(i);
				Log.d(SystemUtil.LOG_MSG, "---->" + button.toString());
				String name = button.getString("name");

				ctx.addMenuItem(name, button.toString(),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								String json = v.getTag().toString();
								try {
									JSONObject jobj = new JSONObject(json);
									String tag = jobj.getString("tag");
									String click = jobj.getString("click");
									String js = "javascript:(" + click + ")('"
											+ tag + "')";
									Log.d(SystemUtil.LOG_MSG, js);
									ctx.runJavascript(js);
								} catch (JSONException e) {
									SystemUtil.printException(e, "w");
								}
							}
						});
			}
		} catch (JSONException e) {
			SystemUtil.printException(e, "w");
		}
		return true;
	}
}
