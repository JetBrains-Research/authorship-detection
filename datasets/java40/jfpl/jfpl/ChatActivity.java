package com.jufan.platform.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.EditText;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.service.ChatLocationService;
import com.jufan.platform.service.ChatTextService;
import com.jufan.platform.service.ChatUploadService;
import com.jufan.platform.util.ChatMessageUtil;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.util.ImageUtil;
import com.jufan.platform.view.VoicePlayItem;
import com.jufan.platform.view.VoiceRecordDialog;
import com.jufan.platform.webview.JFChatWebChromeClient;
import com.jufan.platform.webview.JFChatWebViewClient;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.exception.InstanceDataException;
import com.lianzt.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

@SuppressLint("SetJavaScriptEnabled")
public class ChatActivity extends AutoActivity {
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

	@InjectionView(id = R.id.chat_list)
	private android.widget.ListView chatList;

	private VoiceRecordDialog vrDialog;

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

		initHistoryMsg();

		AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
		item.putStringValue("type", "0");
		item.putStringValue("msg_type", "6");
		item.putStringValue("url", "file:///android_asset/www/test.html");
		item.putStringValue("load_data_type", "0");
		appendChatItem(item);
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
		AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
		acd.putArrayValue("chat_list", list);
		fillData(acd, getWindow().getDecorView());
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

	@Override
	protected void onListItemLongClick(String prxName, ListView l, View v,
			int position, long id) {
		final ProgressBar sendState = (ProgressBar) v
				.findViewById(R.id.send_state);
		final ImageView sendFinishState = (ImageView) v
				.findViewById(R.id.send_finish_state);
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		String msgType = acd.getStringValue("msg_type");
		if ("1".equals(msgType)) {
			ChatMessageUtil.showChatLongClickTextDialog(this, chatList,
					sendState, sendFinishState, acd);
		} else if ("2".equals(msgType) || "3".equals(msgType)
				|| "4".equals(msgType)) {
			ChatMessageUtil.showChatLongClickMediaDialog(this, chatList,
					sendState, sendFinishState, acd);
		}
	}

	@Override
	protected void onListItemClick(String prxName, ListView l, View v,
			int position, long id) {
		final ProgressBar sendState = (ProgressBar) v
				.findViewById(R.id.send_state);
		final ImageView sendFinishState = (ImageView) v
				.findViewById(R.id.send_finish_state);
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
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

	@Override
	protected int getListItemLayout(AbstractCommonData acd, View v) {
		int layout = -1;
		String msgType = acd.getStringValue("msg_type");
		if ("2".equals(msgType)) {// 图片
			layout = R.layout.chat_image_item;
		} else if ("3".equals(msgType)) {// 语音
			layout = R.layout.chat_voice_item;
		} else if ("4".equals(msgType)) { // 位置
			layout = R.layout.chat_location_item;
		} else if ("6".equals(msgType)) { //
			layout = R.layout.chat_form_item;
		}
		return layout;
	}

	@Override
	protected void handleListItem(View v, int position) {
		final AbstractCommonData acd = (AbstractCommonData) v.getTag();
		String type = acd.getStringValue("type");
		String msgType = acd.getStringValue("msg_type");
		LinearLayout chatLayout = (LinearLayout) v.findViewById(R.id.chat_type);
		LinearLayout chatBgLayout = (LinearLayout) v
				.findViewById(R.id.chat_bg_layout);
		ProgressBar sendState = (ProgressBar) v.findViewById(R.id.send_state);
		ImageView sendFinishState = (ImageView) v
				.findViewById(R.id.send_finish_state);
		String sendStateFlag = acd.getStringValue("send_state");
		if (sendFinishState != null && sendState != null) {
			if (sendStateFlag != null) { // send_state 0:发送成功 1:发送失败 2:正在发送
				// 3:上传成功并未发送
				sendState.setVisibility(View.GONE);
				sendFinishState.setVisibility(View.VISIBLE);
				if ("0".equals(sendStateFlag)) {
					sendFinishState.setImageResource(R.drawable.done_holo);
				} else if ("1".equals(sendStateFlag)
						|| "3".equals(sendStateFlag)) {
					sendFinishState.setImageResource(R.drawable.holo_undone);
				} else if ("2".equals(sendStateFlag)) {
					// sendFinishState.setImageResource(R.drawable.holo_undone);
					Log.d(SystemUtil.LOG_MSG, "init again list item");
					sendState.setVisibility(View.VISIBLE);
					sendFinishState.setVisibility(View.GONE);

				}
			} else {
				if ("0".equals(type)) {
					sendState.setVisibility(View.GONE);
					sendFinishState.setVisibility(View.VISIBLE);
					sendFinishState.setImageResource(R.drawable.done_holo);
				}
			}
		}

		if ("0".equals(type)) {
			chatLayout.setGravity(Gravity.LEFT);
			chatBgLayout.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.chat_from_bg_normal));
			sendState.setVisibility(View.GONE);
			if ("1".equals(msgType)) { // 文本
				String chatMsg = acd.getStringValue("chat_msg");
				chatMsg = ChatMessageUtil.convertToClientMessage(chatMsg);
				acd.putStringValue("chat_msg", chatMsg);

			} else if ("3".equals(msgType)) { // 语音
				VoicePlayItem vpi = (VoicePlayItem) v
						.findViewById(R.id.voice_play_msg);
				vpi.setPlaySource(acd.getStringValue("audio_url"));
				vpi.setBindEventView(v);
			} else if ("2".equals(msgType)) {
				ImageView chatImage = (ImageView) v
						.findViewById(R.id.chat_image);
				Bitmap bm = ImageLoader.getInstance().loadImageSync(
						"file://" + acd.getStringValue("image_path"));
				chatImage.setImageBitmap(bm);
				chatImage.setLayoutParams(new LinearLayout.LayoutParams(bm
						.getWidth() + 40, bm.getHeight() + 25));
			} else if ("4".equals(msgType)) {

			} else if ("5".equals(msgType)) {

			} else if ("6".equals(msgType)) {
				WebView wv = (WebView) v.findViewById(R.id.chat_web_view);
				if (StringUtil.isNull(wv.getUrl())) {
					WebSettings ws = wv.getSettings();
					ws.setJavaScriptEnabled(true);
					ws.setJavaScriptCanOpenWindowsAutomatically(true);
					ws.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
					wv.requestFocus();
					wv.setWebViewClient(new JFChatWebViewClient(sendState));
					wv.setWebChromeClient(new JFChatWebChromeClient(this));
					wv.loadUrl(acd.getStringValue("url"));
				}
			}
		} else if ("1".equals(type)) {
			chatLayout.setGravity(Gravity.RIGHT);
			chatBgLayout.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.chat_img_to_bg_mask));
			String msgId = acd.getStringValue("id");
			if (StringUtil.isNull(msgId)) {
				msgId = SystemUtil.getUniqueId();
				acd.putStringValue("id", msgId);
			}
			if ("1".equals(msgType)) { // 文本
				TextView chatTv = (TextView) v.findViewById(R.id.chat_msg);
				chatTv.setPadding(12, 10, 25, 0);
				String chatMsg = acd.getStringValue("chat_msg");
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					chatMsg = ChatMessageUtil.convertToServerMessage(chatMsg);
					update("add_chat_message", msgId, "1", "1",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, chatMsg, new Date(),
							this.id, "2", "", "");
					acd.putStringValue("send_state", "2");

					ChatMessageUtil.sendChatText(this,
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, "to group", chatMsg,
							new ChatTextService(this, chatList, sendState,
									sendFinishState, acd));
				}
				chatMsg = ChatMessageUtil.convertToClientMessage(chatMsg);
				acd.putStringValue("chat_msg", chatMsg);

			} else if ("3".equals(msgType)) { // 语音
				String audioUrl = acd.getStringValue("audio_url");
				VoicePlayItem vpi = (VoicePlayItem) v
						.findViewById(R.id.voice_play_msg);
				vpi.setPlaySource(audioUrl);
				vpi.setBindEventView(v);
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					update("add_chat_message", msgId, "1", "3",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, audioUrl, new Date(),
							this.id, "2", "", "");
					acd.putStringValue("send_state", "2");
					ChatMessageUtil.uploadFile(this, audioUrl,
							new ChatUploadService(this, chatList, sendState,

							sendFinishState, acd));
				}
			} else if ("2".equals(msgType)) {
				ImageView chatImage = (ImageView) v
						.findViewById(R.id.chat_image);
				String imagePath = acd.getStringValue("image_path");
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					update("add_chat_message", msgId, "1", "2",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, imagePath, new Date(),
							this.id, "2", "", "");
					acd.putStringValue("send_state", "2");
					ChatMessageUtil.uploadFile(this, imagePath,
							new ChatUploadService(this, chatList, sendState,

							sendFinishState, acd));
				}
				String imageUrl = "file://" + imagePath;
				Bitmap bm = ImageLoader.getInstance().loadImageSync(imageUrl,
						ImageUtil.getChatImageSize(imagePath));
				chatImage.setImageBitmap(bm);
				chatImage.setPadding(5, 3, 22, 3);
				chatImage.setLayoutParams(new LinearLayout.LayoutParams(bm
						.getWidth() + 25, bm.getHeight() + 10));

			} else if ("4".equals(msgType)) {
				chatBgLayout.setPadding(5, 5, 22, 5);
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					String[] args = (String[]) acd.getObjectValue("tag");
					update("add_chat_message", msgId, "1", "4",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, args[0] + "\n"
									+ args[1], new Date(), this.id, "2",
							args[2], args[3]);
					acd.putStringValue("send_state", "2");
					ChatMessageUtil.sendChatLocation(this,
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, "toGroup", msgType,
							args[2], args[3], new ChatLocationService(this,
									chatList, sendState, sendFinishState, acd));

				}
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && isAddVisible) {
			setAddPanelState(false);
			return false;
		}
		return super.onKeyDown(keyCode, event);
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
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
			for (String imagePath : imageList) {
				AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
				item.putStringValue("type", "1");
				item.putStringValue("msg_type", "2");
				item.putStringValue("image_path", imagePath);
				item.putStringValue("load_data_type", "0");
				list.add(item);
			}
			acd.putArrayValue("chat_list", list);
			fillData(acd, getWindow().getDecorView(), "");

		} else if (resultCode == ConstVariables.SEND_LOCATION_FLAG) {
			String[] addressArr = intent.getStringArrayExtra("address");
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
			AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
			item.putStringValue("type", "1");
			item.putStringValue("msg_type", "4");
			item.putStringValue("chat_location", addressArr[0] + "\n"
					+ addressArr[1]);
			item.putObjectValue("tag", addressArr);
			item.putStringValue("load_data_type", "0");
			list.add(item);
			acd.putArrayValue("chat_list", list);
			fillData(acd, getWindow().getDecorView(), "");
		}
		setAddPanelState(false);
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

	public void appendChatItem(AbstractCommonData itemAcd) {
		AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
		List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
		list.add(itemAcd);
		acd.putArrayValue("chat_list", list);
		fillData(acd, getWindow().getDecorView(), "");
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

}
