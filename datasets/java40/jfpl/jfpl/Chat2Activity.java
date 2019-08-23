package com.jufan.platform.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.adapter.ChatListAdapter;
import com.jufan.platform.util.ChatMessageUtil;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.view.VoiceRecordDialog;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.exception.InstanceDataException;
import com.lianzt.util.StringUtil;

public class Chat2Activity extends AutoActivity implements OnItemClickListener,
		OnItemLongClickListener {
	@InjectionView(id = R.id.frame_title_text)
	private TextView titleTv;
	@InjectionView(id = R.id.chat_content)
	private EditText chatEt;

	@InjectionView(id = R.id.add_info, click = "addInfoClick")
	private ImageView addInfoIv;
	@InjectionView(id = R.id.send_chat, click = "sendChatClick")
	private Button sendChatBtn;
	@InjectionView(id = R.id.voice_record, click = "voiceBtnClick")
	private ImageView voiceBtn;
	@InjectionView(id = R.id.press_voice_btn, touch = "pressRecordTouch")
	private Button pressRecordBtn;
	@InjectionView(id = R.id.get_location_btn, click = "locationBtnClick")
	private Button locationBtn;
	@InjectionView(id = R.id.get_image_btn, click = "imageBtnClick")
	private Button imageBtn;
	@InjectionView(id = R.id.send_chat_btn_content)
	private LinearLayout sendChatLayout;
	@InjectionView(id = R.id.add_msg_panel)
	private LinearLayout addMsgPanel;
	@InjectionView(id = R.id.main_layout)
	private RelativeLayout mainLayout;
	@InjectionView(id = R.id.title_back_btn, click = "exitBtnClick")
	private ImageView exitBtn;

	@InjectionView(id = R.id.chat_list)
	private android.widget.ListView chatList;

	private VoiceRecordDialog vrDialog;
	private ChatListAdapter chatListAdapter;

	private boolean isAddVisible = false;
	private boolean isInputType = true;
	private int addPanelHeight = (int) (SystemUtil.SCREEN_HEIGHT * 0.504f);
	private float recordVoiceY = 0;
	private String title;
	private String id;
	private String serial;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.chat, "");

		this.vrDialog = new VoiceRecordDialog(this);

		chatEt.setMaxWidth(SystemUtil.SCREEN_WIDTH
				- SystemUtil.dip2px(this, 100));
		chatEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String content = s.toString();
				setSendState(!StringUtil.isNull(content));
			}
		});
		setListType(false);
		// 初始化参数
		Intent intent = getIntent();
		initParams(intent);

		RelativeLayout.LayoutParams para = (LayoutParams) addMsgPanel
				.getLayoutParams();
		para.height = addPanelHeight;
		addMsgPanel.setLayoutParams(para);
		this.chatList.setOnItemClickListener(this);
		this.chatList.setOnItemLongClickListener(this);
		initHistoryMsg();
		// for (int i = 0; i < 3; i++) {
		// String html = "test.html";
		// if (i == 1) {
		// html = "test1.html";
		// }
		// AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
		// item.putStringValue("type", "0");
		// item.putStringValue("msg_type", "6");
		// item.putStringValue("url", "file:///android_asset/www/" + html);
		// item.putStringValue("load_data_type", "0");
		// appendChatItem(item);
		// }
	}

	private void initHistoryMsg() {
		List<AbstractCommonData> list = query("get_chat_message_by_one",

		this.id, GlobalVariables.loginUsername, GlobalVariables.loginUsername);
		for (AbstractCommonData acd : list) {
			acd.putStringValue("load_data_type", "1");
			String content = acd.getStringValue("content");
			if ("1".equals(acd.getStringValue("msg_type"))) {
				acd.putStringValue("chat_msg", content);
			} else if ("2".equals(acd.getStringValue("msg_type"))) {
				acd.putStringValue("image_path", content);
			} else if ("3".equals(acd.getStringValue("msg_type"))) {
				acd.putStringValue("audio_url", content);
			} else if ("4".equals(acd.getStringValue("msg_type"))) {
				acd.putStringValue("chat_location", content);
				String[] arr = content.split("\n");
				if (arr.length != 2) {
					String _str = arr[0];
					arr = new String[] { _str, "" };
				}
				acd.putObjectValue(
						"tag",
						new String[] { arr[0], arr[1],
								acd.getStringValue("arg1"),
								acd.getStringValue("arg2") });
			}
		}
		this.chatListAdapter = new ChatListAdapter(this, list, this.id,
				chatList);
		chatList.setAdapter(this.chatListAdapter);
		chatList.setSelection(chatList.getCount() - 1);
	}

	public void setSendState(boolean flag) {
		if (flag) {
			addInfoIv.setVisibility(View.GONE);
			sendChatLayout.setVisibility(View.VISIBLE);
		} else {
			addInfoIv.setVisibility(View.VISIBLE);
			sendChatLayout.setVisibility(View.GONE);
		}
	}

	public void exitBtnClick(View v) {
		finish();
	}

	public void sendChatClick(View v) {
		AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
		item.putStringValue("type", "1");
		item.putStringValue("msg_type", "1");
		item.putStringValue("chat_msg", chatEt.getText().toString());
		item.putStringValue("load_data_type", "0");
		appendChatItem(item);
		chatEt.setText("");
		chatListToBottom();

	}

	public void addInfoClick(View v) {
		if (!isAddVisible) {
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(chatEt.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
			setAddPanelState(true);
			voiceBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.voice_record_button));
			pressRecordBtn.setVisibility(View.GONE);
			chatEt.setVisibility(View.VISIBLE);
		} else {
			setAddPanelState(false);
			chatEt.setFocusable(true);
		}
	}

	public void voiceBtnClick(View v) {
		if (isInputType) {
			voiceBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.chatting_button));
			pressRecordBtn.setVisibility(View.VISIBLE);
			chatEt.setVisibility(View.GONE);
			try {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(chatEt.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {
				// 无EditText焦点
			}
		} else {
			voiceBtn.setImageDrawable(getResources().getDrawable(
					R.drawable.voice_record_button));
			pressRecordBtn.setVisibility(View.GONE);
			chatEt.setVisibility(View.VISIBLE);
		}
		isInputType = !isInputType;
		setAddPanelState(false);
	}

	public boolean pressRecordTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.press_voice_btn) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				this.vrDialog.dismiss();
				if (this.vrDialog.getCancelState() == 0) {
					String path = this.vrDialog.getLastVoicePath();
					if (!StringUtil.isNull(path)) {
						AbstractCommonData item = DataConvertFactory
								.getInstanceEmpty();
						item.putStringValue("type", "1");
						item.putStringValue("msg_type", "3");
						item.putStringValue("audio_url", path);
						item.putStringValue("load_data_type", "0");
						appendChatItem(item);
						chatListToBottom();
					}
				}
			} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
				this.recordVoiceY = event.getY();
				this.vrDialog.show();
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				if (this.recordVoiceY - event.getY() > SystemUtil.dip2px(this,
						80)) {
					vrDialog.setCancelState(1);
				} else {
					vrDialog.setCancelState(0);
				}
			}
		}
		return false;
	}

	public void locationBtnClick(View v) {
		Intent intent = new Intent(this, LocationPickerActivity.class);
		startActivityForResult(intent, 0);
	}

	public void imageBtnClick(View v) {
		Intent intent = new Intent(this, ImagePickerActivity.class);
		startActivityForResult(intent, 0);
	}

	private void setAddPanelState(boolean flag) {
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		if (flag) {
			addMsgPanel.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams para = (RelativeLayout.LayoutParams) mainLayout
					.getLayoutParams();
			para.height = SystemUtil.SCREEN_HEIGHT - addPanelHeight;
			mainLayout.setLayoutParams(para);
			isAddVisible = true;
		} else {
			addMsgPanel.setVisibility(View.GONE);
			RelativeLayout.LayoutParams para = (RelativeLayout.LayoutParams) mainLayout
					.getLayoutParams();
			para.height = SystemUtil.SCREEN_HEIGHT + addPanelHeight;
			mainLayout.setLayoutParams(para);
			isAddVisible = false;
		}
		chatListToBottom();
	}

	public void chatListToBottom() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				chatList.setSelection(chatList.getCount() - 1);
			}
		}, 100);

	}

	private void initParams(Intent intent) {
		// 初始化参数
		this.title = intent.getStringExtra("title");
		this.id = intent.getStringExtra("busi_type");
		this.serial = intent.getStringExtra("busi_serial");
		GlobalVariables.chatAppId = this.id;
		GlobalVariables.chatAppSerial = this.serial;
		if (StringUtil.isNull(this.id)) {
			this.id = ConstVariables.CHAT_DEFAULT_BUSITYPE;

		}
		if (StringUtil.isNull(this.title)) {
			this.title = ConstVariables.CHAT_DEFAULT_TITLE;
		}

		titleTv.setText(this.title);

		String json = intent.getStringExtra("new_msg");

		if (!StringUtil.isNull(json)) {
			NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			notifyManager.cancel(0);
			try {
				AbstractCommonData acd = DataConvertFactory
						.getInstanceByJson(json);
				appendChatItem(acd);
			} catch (InstanceDataException e) {
				e.printStackTrace();
			}
		}
	}

	public void appendChatItem(AbstractCommonData item) {
		this.chatListAdapter.addItem(item);
	}

	public void appendChatList(List<AbstractCommonData> list) {
		this.chatListAdapter.addList(list);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		initParams(intent);
		// refresh data

		// NotificationManager 进入此activity
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == ConstVariables.SEND_IMAGE_FLAG) {
			ArrayList<String> imageList = intent
					.getStringArrayListExtra("image_list");
			List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
			for (String imagePath : imageList) {
				AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
				item.putStringValue("type", "1");
				item.putStringValue("msg_type", "2");
				item.putStringValue("image_path", imagePath);
				item.putStringValue("load_data_type", "0");
				list.add(item);
			}
			appendChatList(list);

		} else if (resultCode == ConstVariables.SEND_LOCATION_FLAG) {
			String[] addressArr = intent.getStringArrayExtra("address");
			AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
			item.putStringValue("type", "1");
			item.putStringValue("msg_type", "4");
			item.putStringValue("chat_location", addressArr[0] + "\n"
					+ addressArr[1]);
			item.putObjectValue("tag", addressArr);
			item.putStringValue("load_data_type", "0");
			appendChatItem(item);
		}
		setAddPanelState(false);
		chatListToBottom();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position,
			long arg3) {
		AbstractCommonData acd = (AbstractCommonData) this.chatListAdapter
				.getItem(position);
		final ProgressBar sendState = (ProgressBar) v
				.findViewById(R.id.send_state);
		final ImageView sendFinishState = (ImageView) v
				.findViewById(R.id.send_finish_state);
		int type = this.chatListAdapter.getItemViewType(position);
		if (type == 1) {
			ChatMessageUtil.showChatLongClickTextDialog(this, chatList,
					sendState, sendFinishState, acd);
		} else if (type == 2 || type == 3 || type == 4) {
			ChatMessageUtil.showChatLongClickMediaDialog(this, chatList,
					sendState, sendFinishState, acd);
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		AbstractCommonData acd = (AbstractCommonData) this.chatListAdapter
				.getItem(position);
		final ProgressBar sendState = (ProgressBar) v
				.findViewById(R.id.send_state);
		final ImageView sendFinishState = (ImageView) v
				.findViewById(R.id.send_finish_state);
		String msgType = acd.getStringValue("msg_type");
		String type = acd.getStringValue("type");
		if ("1".equals(msgType) && "1".equals(type)) {
			String state = acd.getStringValue("send_state");
			if (!"0".equals(state)) {
				ChatMessageUtil.showChatClickTextDialog(this, chatList,
						sendState, sendFinishState, acd);

			}
		} else if ("2".equals(msgType)) {// 图片
			Intent intent = new Intent(this, ShowImageActivity.class);
			intent.putExtra("image_path", acd.getStringValue("image_path"));
			startActivity(intent);
		} else if ("4".equals(msgType)) { // 位置
			Intent intent = new Intent(this, ShowLocationActivity.class);
			String[] arr = (String[]) acd.getObjectValue("tag");
			intent.putExtra("tag", arr);
			startActivity(intent);
		}
	}
}
