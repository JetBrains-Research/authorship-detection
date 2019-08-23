package com.jufan.platform.webview;

import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.R;
import com.jufan.platform.ui.WebContentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class JFWebChromeClient extends WebChromeClient {

	private WebContentActivity webActivity;
	private ImageView loadingBar;

	public JFWebChromeClient(WebContentActivity webActivity,
			ImageView loadingBar) {
		this.webActivity = webActivity;
		this.loadingBar = loadingBar;
	}

	/**
	 * Tell the client to display a javascript alert dialog.
	 * 
	 * @param view
	 * @param url
	 * @param message
	 * @param result
	 */
	@Override
	public boolean onJsAlert(WebView view, String url, String message,
			final JsResult result) {
		AlertDialog.Builder dlg = new AlertDialog.Builder(this.webActivity);
		dlg.setMessage(message);
		dlg.setTitle("来自 " + url);
		dlg.setCancelable(false);
		dlg.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
		dlg.create();
		dlg.show();
		return true;
	}

	/**
	 * Tell the client to display a confirm dialog to the user.
	 * 
	 * @param view
	 * @param url
	 * @param message
	 * @param result
	 */
	@Override
	public boolean onJsConfirm(WebView view, String url, String message,
			final JsResult result) {
		AlertDialog.Builder dlg = new AlertDialog.Builder(this.webActivity);
		dlg.setMessage(message);
		dlg.setTitle("来自 " + url);
		dlg.setCancelable(false);
		dlg.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
		dlg.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}
				});
		dlg.create();
		dlg.show();
		return true;
	}

	/**
	 * Tell the client to display a prompt dialog to the user. If the client
	 * returns true, WebView will assume that the client will handle the prompt
	 * dialog and call the appropriate JsPromptResult method.
	 * 
	 * @param view
	 * @param url
	 * @param message
	 * @param defaultValue
	 * @param result
	 */
	@Override
	public boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, JsPromptResult result) {

		return true;
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {

		if (newProgress != 100 && newProgress != 0) {
			// loadingBar.setBackgroundColor(ctx.getResources().getColor(
			// R.color.loading_bar_visible));
			loadingBar.setVisibility(View.VISIBLE);
			int progress = SystemUtil.SCREEN_WIDTH * newProgress / 100;
			// Log.i(SystemUtil.LOG_MSG, "process: " + newProgress + ","
			// + progress);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					progress, 6);
			layoutParams.addRule(RelativeLayout.ABOVE, R.id.bottom_bar);

			loadingBar.setLayoutParams(layoutParams);
		} else if (newProgress == 100) {
			loadingBar.setVisibility(View.GONE);
			view.destroyDrawingCache();
			view.clearCache(true);
		}
		super.onProgressChanged(view, newProgress);
	}

	@Override
	public void onReceivedTitle(WebView view, String title) {
		super.onReceivedTitle(view, title);
	}
}
