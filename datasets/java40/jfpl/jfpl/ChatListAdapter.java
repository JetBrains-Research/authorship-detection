package com.jufan.platform.adapter;

import java.util.Date;
import java.util.List;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.service.ChatLocationService;
import com.jufan.platform.service.ChatTextService;
import com.jufan.platform.service.ChatUploadService;
import com.jufan.platform.ui.R;
import com.jufan.platform.util.ChatMessageUtil;
import com.jufan.platform.util.GlobalVariables;
import com.jufan.platform.util.ImageUtil;
import com.jufan.platform.view.VoicePlayItem;
import com.jufan.platform.webview.JFChatWebChromeClient;
import com.jufan.platform.webview.JFChatWebViewClient;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class ChatListAdapter extends BaseAdapter {

	private List<AbstractCommonData> dataSource;
	private AutoActivity ctx;
	private String busiType;
	private ListView chatList;

	public ChatListAdapter(AutoActivity ctx,
			List<AbstractCommonData> dataSource, String busiType,
			ListView chatList) {
		this.ctx = ctx;
		this.dataSource = dataSource;
		this.busiType = busiType;
		this.chatList = chatList;
	}

	public void addItem(AbstractCommonData item) {
		this.dataSource.add(item);
		this.notifyDataSetChanged();
	}

	public void addList(List<AbstractCommonData> list) {
		this.dataSource.addAll(list);
		this.notifyDataSetChanged();
	}

	public void removeItem(AbstractCommonData acd) {
		this.dataSource.remove(acd);
		this.notifyDataSetChanged();
	}

	@Override
	public int getViewTypeCount() {
		return 7;
	}

	@Override
	public int getItemViewType(int position) {
		AbstractCommonData acd = dataSource.get(position);
		int type = Integer.parseInt(acd.getStringValue("msg_type"));
		return type;
	}

	@Override
	public int getCount() {
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatViewHolder holder = null;
		int type = getItemViewType(position);
		int layoutId = 0;
		switch (type) {
		case 1:
			layoutId = R.layout.chat_list_item;
			break;
		case 2:
			layoutId = R.layout.chat_image_item;
			break;
		case 3:
			layoutId = R.layout.chat_voice_item;
			break;
		case 4:
			layoutId = R.layout.chat_location_item;
			break;
		case 5:
			layoutId = R.layout.chat_list_item;
			break;
		case 6:
			layoutId = R.layout.chat_form_item;
			break;
		default:
			layoutId = R.layout.chat_list_item;
			break;
		}
		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(layoutId, null);
			holder = new ChatViewHolder();
			holder.chatLayout = (LinearLayout) convertView
					.findViewById(R.id.chat_type);
			holder.chatBgLayout = (LinearLayout) convertView
					.findViewById(R.id.chat_bg_layout);
			holder.sendState = (ProgressBar) convertView
					.findViewById(R.id.send_state);
			holder.sendFinishState = (ImageView) convertView
					.findViewById(R.id.send_finish_state);
			holder.chatTextView = (TextView) convertView
					.findViewById(R.id.chat_msg);
			holder.chatImage = (ImageView) convertView
					.findViewById(R.id.chat_image);
			holder.chatVoice = (VoicePlayItem) convertView
					.findViewById(R.id.voice_play_msg);
			holder.chatLoaction = (TextView) convertView
					.findViewById(R.id.chat_location);
			holder.chatWebview = (WebView) convertView
					.findViewById(R.id.chat_web_view);
			convertView.setTag(holder);
		} else {
			holder = (ChatViewHolder) convertView.getTag();
		}

		AbstractCommonData acd = dataSource.get(position);
		String direction = acd.getStringValue("type");
		String sendStateFlag = acd.getStringValue("send_state");
		if (holder.sendState != null && holder.sendFinishState != null) {
			if (sendStateFlag != null) { // send_state 0:发送成功 1:发送失败 2:正在发送
				// 3:上传成功并未发送
				holder.sendState.setVisibility(View.GONE);
				holder.sendFinishState.setVisibility(View.VISIBLE);
				if ("0".equals(sendStateFlag)) {
					holder.sendFinishState
							.setImageResource(R.drawable.done_holo);
				} else if ("1".equals(sendStateFlag)
						|| "3".equals(sendStateFlag)) {
					holder.sendFinishState
							.setImageResource(R.drawable.holo_undone);
				} else if ("2".equals(sendStateFlag)) {
					Log.d(SystemUtil.LOG_MSG, "init again list item");
					holder.sendState.setVisibility(View.VISIBLE);
					holder.sendFinishState.setVisibility(View.GONE);
				}
			} else {
				if ("0".equals(type)) {
					holder.sendState.setVisibility(View.GONE);
					holder.sendFinishState.setVisibility(View.VISIBLE);
					holder.sendFinishState
							.setImageResource(R.drawable.done_holo);
				}
			}
		}
		if ("0".equals(direction)) {
			holder.chatLayout.setGravity(Gravity.LEFT);
			holder.chatBgLayout.setBackgroundDrawable(ctx.getResources()
					.getDrawable(R.drawable.chat_from_bg_normal));
			holder.sendState.setVisibility(View.GONE);
			if (type == 1 && holder.chatTextView != null) { // 文本
				String chatMsg = acd.getStringValue("chat_msg");
				chatMsg = ChatMessageUtil.convertToClientMessage(chatMsg);
				holder.chatTextView.setText(chatMsg);
			} else if (type == 2 && holder.chatImage != null) { // 图片
				Bitmap bm = ImageLoader.getInstance().loadImageSync(
						"file://" + acd.getStringValue("image_path"));
				holder.chatImage.setImageBitmap(bm);
				holder.chatImage.setLayoutParams(new LinearLayout.LayoutParams(
						bm.getWidth() + 40, bm.getHeight() + 25));
			} else if (type == 3 && holder.chatVoice != null) { // 语音
				holder.chatVoice.setPlaySource(acd.getStringValue("audio_url"));
				holder.chatVoice.setBindEventView(convertView);
			} else if (type == 4 && holder.chatLoaction != null) {
				holder.chatLoaction
						.setText(acd.getStringValue("chat_location"));
			} else if (type == 5) {

			} else if (type == 6 && holder.chatWebview != null) {
				if (StringUtil.isNull(holder.chatWebview.getUrl())) {
					WebSettings ws = holder.chatWebview.getSettings();
					ws.setJavaScriptEnabled(true);
					ws.setJavaScriptCanOpenWindowsAutomatically(true);
					ws.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
					holder.chatWebview.requestFocus();
					holder.chatWebview
							.setWebViewClient(new JFChatWebViewClient(
									holder.sendState));
					holder.chatWebview
							.setWebChromeClient(new JFChatWebChromeClient(ctx));
					holder.chatWebview.loadUrl(acd.getStringValue("url"));
				}
			}
		} else if ("1".equals(direction)) {
			holder.chatLayout.setGravity(Gravity.RIGHT);
			holder.chatBgLayout.setBackgroundDrawable(ctx.getResources()
					.getDrawable(R.drawable.chat_img_to_bg_mask));
			String msgId = acd.getStringValue("id");
			if (StringUtil.isNull(msgId)) {
				msgId = SystemUtil.getUniqueId();
				acd.putStringValue("id", msgId);
			}
			if (type == 1 && holder.chatTextView != null) { // 文本
				holder.chatTextView.setPadding(12, 10, 25, 0);
				String chatMsg = acd.getStringValue("chat_msg");

				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					chatMsg = ChatMessageUtil.convertToServerMessage(chatMsg);
					ctx.update("add_chat_message", msgId, "1", "1",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, chatMsg, new Date(),
							this.busiType, "2", "", "");
					acd.putStringValue("send_state", "2");

					ChatMessageUtil.sendChatText(ctx,
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, "to group", chatMsg,
							new ChatTextService(ctx, chatList,
									holder.sendState, holder.sendFinishState,
									acd));
				}
				chatMsg = ChatMessageUtil.convertToClientMessage(chatMsg);
				holder.chatTextView.setText(chatMsg);
			} else if (type == 2 && holder.chatImage != null) { // 图片

				String imagePath = acd.getStringValue("image_path");
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					ctx.update("add_chat_message", msgId, "1", "2",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, imagePath, new Date(),
							this.busiType, "2", "", "");
					acd.putStringValue("send_state", "2");
					ChatMessageUtil.uploadFile(ctx, imagePath,
							new ChatUploadService(ctx, chatList,
									holder.sendState, holder.sendFinishState,
									acd));
				}
				String imageUrl = "file://" + imagePath;
				Bitmap bm = ImageLoader.getInstance().loadImageSync(imageUrl,
						ImageUtil.getChatImageSize(imagePath));
				holder.chatImage.setImageBitmap(bm);
				holder.chatImage.setPadding(5, 3, 22, 3);
				holder.chatImage.setLayoutParams(new LinearLayout.LayoutParams(
						bm.getWidth() + 25, bm.getHeight() + 10));

			} else if (type == 3 && holder.chatVoice != null) {// 语音
				String audioUrl = acd.getStringValue("audio_url");
				holder.chatVoice.setPlaySource(audioUrl);
				holder.chatVoice.setBindEventView(holder.chatVoice);
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					ctx.update("add_chat_message", msgId, "1", "3",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, audioUrl, new Date(),
							this.busiType, "2", "", "");
					acd.putStringValue("send_state", "2");
					ChatMessageUtil.uploadFile(ctx, audioUrl,
							new ChatUploadService(ctx, chatList,
									holder.sendState, holder.sendFinishState,
									acd));
				}

			} else if (type == 4 && holder.chatLoaction != null) {
				holder.chatBgLayout.setPadding(5, 5, 22, 5);
				if ("0".equals(acd.getStringValue("load_data_type"))
						&& acd.getStringValue("send_state") == null) {
					String[] args = (String[]) acd.getObjectValue("tag");
					ctx.update("add_chat_message", msgId, "1", "4",
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, args[0] + "\n"
									+ args[1], new Date(), this.busiType, "2",
							args[2], args[3]);
					acd.putStringValue("send_state", "2");
					ChatMessageUtil.sendChatLocation(ctx,
							GlobalVariables.loginUsername,
							GlobalVariables.chatToUser, "toGroup", type + "",
							args[2], args[3], new ChatLocationService(ctx,
									chatList, holder.sendState,
									holder.sendFinishState, acd));

				}
				holder.chatLoaction
						.setText(acd.getStringValue("chat_location"));
			}
		}
		return convertView;
	}

	public static class ChatViewHolder {
		public LinearLayout chatLayout;
		public LinearLayout chatBgLayout;
		public ImageView sendFinishState;
		public ProgressBar sendState;
		public TextView chatTextView;
		public ImageView chatImage;
		public VoicePlayItem chatVoice;
		public TextView chatLoaction;
		public WebView chatWebview;
	}

}
