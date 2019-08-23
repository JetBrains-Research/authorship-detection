package com.jufan.platform.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class JFChatWebChromeClient extends WebChromeClient {
	private Context ctx;

	public JFChatWebChromeClient(Context ctx) {
		this.ctx = ctx;
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
		AlertDialog.Builder dlg = new AlertDialog.Builder(this.ctx);
		dlg.setMessage(message);
		dlg.setTitle("提示");
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
		AlertDialog.Builder dlg = new AlertDialog.Builder(this.ctx);
		dlg.setMessage(message);
		dlg.setTitle("提示");
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

}
