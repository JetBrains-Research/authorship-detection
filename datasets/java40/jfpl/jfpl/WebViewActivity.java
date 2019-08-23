package com.jufan.platform.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;

public class WebViewActivity extends AutoActivity {

	@InjectionView(id = R.id.web_view)
	private WebView appView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_test);
		WebSettings ws = appView.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		appView.setWebChromeClient(new TWebChromeClient(appView));
		appView.setWebViewClient(new TWebViewClient(appView));

		appView.loadUrl("http://www.jtgzfw.com/jtgzfw/_pc/wx/jgcs_list.do?type_id=JC02");
	}

	class TWebViewClient extends WebViewClient {

		private WebView webView;

		public TWebViewClient(WebView webView) {
			this.webView = webView;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			webView.loadUrl(url);
			return true;
		}
	}

	class TWebChromeClient extends WebChromeClient {

		private WebView webView;

		public TWebChromeClient(WebView webView) {
			this.webView = webView;
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			AlertDialog.Builder dlg = new AlertDialog.Builder(
					WebViewActivity.this);
			dlg.setMessage(message);
			dlg.setTitle("Alert");
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
	}
}
