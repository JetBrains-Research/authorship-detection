package com.jufan.platform.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ant.liao.GifView;
import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;

public class RegisterTipActivity extends AutoActivity {

	@InjectionView(id = R.id.first_step_icon)
	private GifView stIcon1;
	@InjectionView(id = R.id.first_done_icon)
	private ImageView sdIcon1;
	@InjectionView(id = R.id.second_step_icon)
	private GifView stIcon2;
	@InjectionView(id = R.id.second_done_icon)
	private ImageView sdIcon2;
	@InjectionView(id = R.id.third_step_icon)
	private GifView stIcon3;
	@InjectionView(id = R.id.third_done_icon)
	private ImageView sdIcon3;
	@InjectionView(id = R.id.check_yzm, click = "showYzmClick")
	private Button checYzmBtn;

	private AlertDialog.Builder alertDialogBuilder;
	private AlertDialog alertDialog;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 1:
					sdIcon1.setVisibility(View.VISIBLE);
					stIcon1.setVisibility(View.GONE);
					break;
				case 2:
					sdIcon2.setVisibility(View.VISIBLE);
					stIcon2.setVisibility(View.GONE);
					checYzmBtn.setEnabled(true);
					alertDialog = alertDialogBuilder.show();
					break;
				case 3:
					sdIcon3.setVisibility(View.VISIBLE);
					stIcon3.setVisibility(View.GONE);
					Intent intent = new Intent(RegisterTipActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
					break;
				default:
					break;
				}
			} catch (Exception e) {

			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.register2, "");
		stIcon1.setGifImage(R.drawable.small_loading3);
		stIcon2.setGifImage(R.drawable.small_loading3);
		stIcon3.setGifImage(R.drawable.small_loading3);
		EditText et = new EditText(this);
		et.setText("456234");
		alertDialogBuilder = new AlertDialog.Builder(this).setTitle("请输入短信验证码")
				.setIcon(android.R.drawable.ic_dialog_info).setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								handler.sendEmptyMessage(3);

							}
						}).start();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(1);
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						handler.sendEmptyMessage(2);

					}
				}).start();
			}
		}).start();
	}

	public void showYzmClick(View v) {
		if (alertDialog == null) {
			alertDialog = alertDialogBuilder.show();
		} else if (alertDialog != null && !alertDialog.isShowing()) {
			alertDialog.show();
		}
	}
}
