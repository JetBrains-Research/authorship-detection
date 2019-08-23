package com.jufan.platform.ui;

import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;

import com.cyss.android.lib.AutoActivity;
import com.lianzt.util.SSLConnection;

public class RemoteActivity extends AutoActivity {

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 100) {
				View v = (View) msg.obj;
				setContentView(v);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		final String layoutUri = "http://192.168.1.210:3001/tt.xml";// intent.getStringExtra("layout_uri");
		handler.sendEmptyMessage(SHOW_LOADING);
		new Thread(new Runnable() {

			@Override
			public void run() {
				String layoutXml = SSLConnection.getHttpString(layoutUri);
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				XmlPullParser parser = Xml.newPullParser();
				try {
					parser.setInput(new StringReader(layoutXml));
					View v = inflater.inflate(parser, null);
					Message msg = handler.obtainMessage();
					msg.what = 100;
					msg.obj = v;
					handler.sendMessage(msg);
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(HIDE_LOADING);
			}
		}).start();
	}
}
