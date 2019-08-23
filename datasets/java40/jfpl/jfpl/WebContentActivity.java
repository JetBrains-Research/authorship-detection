package com.jufan.platform.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.webview.JFWebChromeClient;
import com.jufan.platform.webview.JFWebViewClient;
import com.jufan.platform.webview.PluginManager;
import com.lianzt.util.StringUtil;

@SuppressLint("JavascriptInterface")
public class WebContentActivity extends RightMenuActivity {
	@InjectionView(id = R.id.web_view)
	private WebView webView;
	@InjectionView(id = R.id.frame_title_text)
	private TextView titleTv;
	@InjectionView(id = R.id.loading_bar)
	private android.widget.ImageView loadingBar;
	@InjectionView(id = R.id.right_menu)
	private android.widget.ImageView rightMenu;
	@InjectionView(id = R.id.chat_icon_iv, click = "chatIconClick")
	private android.widget.ImageView chatIcon;
	@InjectionView(id = R.id.webview_refresh, click = "refreshClick")
	private android.widget.ImageView refreshBtn;
	@InjectionView(id = R.id.webview_back, click = "backClick")
	private android.widget.ImageView backBtn;
	@InjectionView(id = R.id.title_back_btn, click = "exitBtnClick")
	private android.widget.ImageView exitBtn;

	private JFWebViewClient webViewClient;
	private boolean refreshState = false;
	private boolean goBackState = false;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.web_content, "");
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		String url = intent.getStringExtra("url");
		String appId = intent.getStringExtra("app_id");
		if (!StringUtil.isNull(appId)) {
			super.setChatBusiType(appId);
		}
		if (!StringUtil.isNull(title)) {
			titleTv.setText(title);
			super.setChatTitle(title);
		}
		if (StringUtil.isNull(url)) {
			url = "file:///android_asset/www/index.html";
		}
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		// ws.setSavePassword(false);
		// ws.setSaveFormData(false);
		// ws.setAppCacheEnabled(false);
		// ws.setAllowFileAccess(true);
		// ws.setRenderPriority(RenderPriority.LOW);
		webViewClient = new JFWebViewClient(this);
		webView.requestFocus();
		webView.setWebViewClient(webViewClient);
		webView.setWebChromeClient(new JFWebChromeClient(this, loadingBar));
		webView.addJavascriptInterface(new PluginManager(this), "jufan");
		webView.loadUrl(url);

		super.setRightMenuBtn(rightMenu);

	}

	public void setRefreshState(boolean flag) {
		this.refreshState = flag;
		if (flag) {
			refreshBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.webviewtab_refresh_normal));
		} else {
			refreshBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.webviewtab_refresh_disable));
		}
	}

	public void setGoBackState(boolean flag) {
		this.goBackState = flag;
		if (flag) {
			backBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.webviewtab_back_normal));
		} else {
			backBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.webviewtab_back_disable));
		}
	}

	public void refreshClick(View v) {
		String url = webViewClient.getRefreshUrl();
		if (!StringUtil.isNull(url) && this.refreshState) {
			webView.loadUrl(url);
		}
	}

	public void backClick(View v) {
		if (webView.canGoBack() && goBackState) {
			webView.goBack();
		}
	}

	public void exitBtnClick(View v) {
		finish();
	}

	public void chatIconClick(View v) {
		Intent intent = new Intent(this, Chat2Activity.class);
		intent.putExtra("busi_type", busiType);
		intent.putExtra("busi_serial", busiSerial);
		intent.putExtra("title", chatTitle);
		startActivity(intent);
	}

	public void runJavascript(String js) {
		if (!js.startsWith("javascript:")) {
			js = "javascript:" + js + "";
		}
		webView.loadUrl(js);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destroyWebView(webView);
		System.gc();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()
				&& goBackState) {
			webView.goBack();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void destroyWebView(WebView wv) {
		try {
			// wv.stopLoading();
			// wv.pauseTimers();
			// wv.clearFormData();
			// wv.clearAnimation();
			// wv.clearDisappearingChildren();
			// wv.clearView();
			// wv.clearCache(true);
			// wv.clearHistory();
			// wv.clearMatches();
			// wv.clearSslPreferences();
			// wv.destroyDrawingCache();
			wv.freeMemory();
			wv.destroy();
			// wv.removeAllViews();
		} catch (Exception ex) {

		}
	}
}
