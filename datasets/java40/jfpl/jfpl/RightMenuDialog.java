package com.jufan.platform.view;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class RightMenuDialog extends Dialog {

	private Context ctx;
	private Window window;
	private int height = 0;
	private int itemHeight;

	private LinearLayout menuLayout;

	private Map<String, TextView> itemMap = new LinkedHashMap<String, TextView>();

	public RightMenuDialog(Context context) {
		super(context, R.style.left_menu_dialog);
		this.ctx = context;
		itemHeight = SystemUtil.dip2px(ctx, 48);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.right_menu);
		this.setCanceledOnTouchOutside(true);
		window = getWindow();
		window.setWindowAnimations(R.style.left_menu_anim);
		WindowManager.LayoutParams wl = window.getAttributes();
		int itemWidth = (int) (SystemUtil.SCREEN_WIDTH / 3 * 1.75f);
		wl.width = itemWidth;
		wl.height = height;
		wl.x = SystemUtil.SCREEN_WIDTH - itemWidth * 3 / 2;
		wl.y = -(SystemUtil.SCREEN_HEIGHT - height) / 2
				+ SystemUtil.dip2px(ctx, 48) * 3 / 2 + 2;
		window.setAttributes(wl);
		menuLayout = (LinearLayout) findViewById(R.id.menu_collection);
		for (String key : itemMap.keySet()) {
			menuLayout.addView(itemMap.get(key));
		}
	}

	public void addMenuItem(String name, String tag, final View.OnClickListener click) {
		height += itemHeight;

		TextView tv = new TextView(ctx);
		tv.setText(name);
		tv.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, itemHeight));
		tv.setTextSize(15.0f);
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundDrawable(ctx.getResources().getDrawable(
				R.drawable.menu_item));
		tv.setGravity(Gravity.CENTER);
		tv.setTag(tag);
		tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				click.onClick(v);
				dismiss();
			}
		});
		itemMap.put(name, tv);
	}
	
	public int getMenuCount() {
		return itemMap.size();
	}

	public void removeMenuItem(String name) {
		if (itemMap.containsKey(name)) {
			itemMap.remove(name);
		}
	}

}
