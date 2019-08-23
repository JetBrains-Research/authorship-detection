package com.jufan.platform.util;

import java.io.File;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.adapter.ChatListAdapter;
import com.jufan.platform.http.HttpSendFactory;
import com.jufan.platform.service.ChatImageService;
import com.jufan.platform.service.ChatLocationService;
import com.jufan.platform.service.ChatTextService;
import com.jufan.platform.service.ChatUploadService;
import com.jufan.platform.service.ChatVoiceService;
import com.jufan.platform.ui.R;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.DateUtil;
import com.lianzt.util.StringUtil;

public class ChatMessageUtil {

	private static final String SEND_URL = ConstVariables.SERVER_URL
			+ "/platform/message/send";

	private static final String UPLOAD_URL = ConstVariables.SERVER_URL
			+ "/upload/image";

	private static final String[] CHAT_CHAR_CONVERT_ARRAY = new String[] { "~",
			"@1:@" };

	private static AbstractCommonData getChatCommonPacket(String username,
			String msgType, String toUser, String toGroup,
			IServiceLogic iservice) {
		AbstractCommonData acd = DataConvertFactory.getInstance();
		AbstractCommonData head = acd.getDataValue("head");
		head.putStringValue("device", "android-phone: "
				+ android.os.Build.MODEL);
		head.putStringValue("message_type", msgType);
		head.putStringValue("client_time", DateUtil.detaledFormat(new Date()));
		head.putStringValue("username", username);
		acd.putStringValue("_url", SEND_URL);
		acd.putBooleanValue("is_alert", false);
		acd.putStringValue("to_user", toUser);
		acd.putStringValue("busi_type", GlobalVariables.chatAppId);
		acd.putStringValue("busi_serial", GlobalVariables.chatAppSerial);
		acd.putObjectValue("ipacket",
				HttpSendFactory.getChatPlatformSendMethod());
		acd.putStringValue("to_group", toGroup);
		acd.putObjectValue("iservice", iservice);
		acd.putBooleanValue("is_show_loading", false);
		return acd;
	}

	public static void sendChatText(AutoActivity ctx, String username,
			String toUser, String toGroup, String text, IServiceLogic iservice) {
		AbstractCommonData acd = getChatCommonPacket(username, "1", toUser,
				toGroup, iservice);
		acd.putStringValue("message_text", text);
		ServiceController.addService(acd, ctx);
	}

	public static void sendChatImage(AutoActivity ctx, String username,
			String toUser, String toGroup, String imageId, String imageThumbId,
			IServiceLogic iservice) {
		AbstractCommonData acd = getChatCommonPacket(username, "1", toUser,
				toGroup, iservice);
		acd.putStringValue("image_id", imageId);
		acd.putStringValue("image_thumb_d", imageThumbId);
		ServiceController.addService(acd, ctx);
	}

	public static void sendChatVoice(AutoActivity ctx, String username,
			String toUser, String toGroup, String voiceId,
			IServiceLogic iservice) {
		AbstractCommonData acd = getChatCommonPacket(username, "1", toUser,
				toGroup, iservice);
		acd.putStringValue("voice_id", voiceId);
		ServiceController.addService(acd, ctx);
	}

	public static void sendChatLocation(AutoActivity ctx, String username,
			String toUser, String toGroup, String title, String lat,
			String lon, IServiceLogic iservice) {
		AbstractCommonData acd = getChatCommonPacket(username, "1", toUser,
				toGroup, iservice);
		acd.putStringValue("location_title", title);
		acd.putStringValue("location_data", lat + ", " + lon);
		ServiceController.addService(acd, ctx);
	}

	public static void uploadFile(AutoActivity ctx, String filePath,
			IServiceLogic iservice) {
		AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
		acd.putStringValue("_filepath", filePath);
		acd.putStringValue("_url", UPLOAD_URL);
		acd.putStringValue("_filekey", "file");
		acd.putBooleanValue("is_alert", false);
		acd.putObjectValue("iservice", iservice);
		acd.putBooleanValue("is_show_loading", false);
		acd.putBooleanValue("is_upload", true);
		ServiceController.addService(acd, ctx);
	}

	public static void showChatLongClickMediaDialog(final AutoActivity ctx,
			final ListView chatList, final ProgressBar pb, final ImageView iv,
			final AbstractCommonData acd) {
		int arr = R.array.chat_media_long_click_dialog_arr;
		String type = acd.getStringValue("type");
		final String sendState = acd.getStringValue("send_state");
		final String msgType = acd.getStringValue("msg_type");
		if ("1".equals(type)) {
			if (!"0".equals(sendState)) {
				arr = R.array.chat_media_long_click_err_dialog_arr;
			}
		}
		new AlertDialog.Builder(ctx).setItems(arr,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							String id = acd.getStringValue("id");
							String type = acd.getStringValue("type");

							ctx.update("delete_chat_msg", id);
							if (chatList.getAdapter().getClass() == AutoActivity.BaseListAdapter.class) {
								AutoActivity.BaseListAdapter adapter = (AutoActivity.BaseListAdapter) chatList
										.getAdapter();
								adapter.removeItem(acd);
							} else if (chatList.getAdapter().getClass() == ChatListAdapter.class) {
								ChatListAdapter adapter = (ChatListAdapter) chatList
										.getAdapter();
								adapter.removeItem(acd);
							}

							if ("0".equals(type) || "1".equals(type)) {
								String msgType = acd.getStringValue("msg_type");
								String mediaPath = null;
								if ("2".equals(msgType)) {
									mediaPath = acd
											.getStringValue("image_path");
								} else if ("3".equals(msgType)) {
									mediaPath = acd.getStringValue("audio_url");
								}
								if (!StringUtil.isNull(mediaPath)) {
									File f = new File(mediaPath);
									f.delete();
								}
							}
							break;
						case 1:
							pb.setVisibility(View.VISIBLE);
							iv.setVisibility(View.GONE);
							if ("2".equals(msgType)) {
								String path = acd.getStringValue("image_path");
								if ("1".equals(sendState)) {
									uploadFile(ctx, path,
											new ChatUploadService(ctx,
													chatList, pb, iv, acd));

								} else if ("2".equals(sendState)) {
									AbstractCommonData _acd = ctx.queryForData(
											"get_one_chat_msg",
											acd.getStringValue("id"));
									sendChatImage(ctx,
											GlobalVariables.loginUsername,
											"to_user", "to_group", _acd
													.getStringValue("arg1"),
											_acd.getStringValue("arg2"),
											new ChatImageService(ctx, chatList,
													pb, iv, acd));

								}
							} else if ("3".equals(msgType)) {
								String path = acd.getStringValue("audio_url");
								if ("1".equals(sendState)) {
									uploadFile(ctx, path,
											new ChatUploadService(ctx,
													chatList, pb, iv, acd));

								} else if ("2".equals(sendState)) {
									AbstractCommonData _acd = ctx.queryForData(
											"get_one_chat_msg",
											acd.getStringValue("id"));
									sendChatVoice(ctx,
											GlobalVariables.loginUsername,
											"to_user", "to_group", _acd
													.getStringValue("arg1"),
											new ChatVoiceService(ctx, chatList,
													pb, iv, acd));

								}
							} else if ("4".equals(msgType)) {
								String[] arr = (String[]) acd
										.getObjectValue("tag");
								sendChatLocation(ctx,
										GlobalVariables.loginUsername,
										"to_user", "to_group",
										acd.getStringValue("chat_location"),
										arr[2], arr[3],
										new ChatLocationService(ctx, chatList,
												pb, iv, acd));

							}

							break;
						default:
							break;
						}
					}
				}).show();
	}

	public static void showChatLongClickTextDialog(final AutoActivity ctx,
			final ListView chatList, final ProgressBar pb, final ImageView iv,
			final AbstractCommonData acd) {
		new AlertDialog.Builder(ctx).setItems(
				R.array.chat_text_long_click_dialog_arr,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String text = acd.getStringValue("chat_msg");
						switch (which) {
						case 1:
							ClipboardManager cmb = (ClipboardManager) ctx
									.getSystemService(Context.CLIPBOARD_SERVICE);
							cmb.setText(text);
							Toast.makeText(ctx, "复制成功", Toast.LENGTH_LONG)
									.show();
							break;
						case 0:
							String id = acd.getStringValue("id");
							ctx.update("delete_chat_msg", id);
							if (chatList.getAdapter().getClass() == AutoActivity.BaseListAdapter.class) {
								AutoActivity.BaseListAdapter adapter = (AutoActivity.BaseListAdapter) chatList
										.getAdapter();
								adapter.removeItem(acd);
							} else if (chatList.getAdapter().getClass() == ChatListAdapter.class) {
								ChatListAdapter adapter = (ChatListAdapter) chatList
										.getAdapter();
								adapter.removeItem(acd);
							}
							break;
						default:
							break;
						}
					}
				}).show();
	}

	public static void showChatClickTextDialog(final AutoActivity ctx,
			final ListView chatList, final ProgressBar pb, final ImageView iv,

			final AbstractCommonData acd) {
		new AlertDialog.Builder(ctx).setItems(
				R.array.chat_text_click_dialog_arr,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String text = acd.getStringValue("chat_msg");
						switch (which) {
						case 0:
							pb.setVisibility(View.VISIBLE);
							iv.setVisibility(View.GONE);
							sendChatText(ctx, GlobalVariables.loginUsername,
									"appid", "togroup", text,
									new ChatTextService(ctx, chatList, pb, iv,
											acd));

							break;
						default:
							break;
						}
					}
				}).show();
	}

	public static String convertToClientMessage(String backMsg) {
		String t = null;
		for (int i = 0; i < CHAT_CHAR_CONVERT_ARRAY.length; i += 2) {
			t = backMsg.replaceAll(CHAT_CHAR_CONVERT_ARRAY[i + 1],
					CHAT_CHAR_CONVERT_ARRAY[i]);
		}
		return t;
	}

	public static String convertToServerMessage(String chatMsg) {
		String t = null;
		for (int i = 0; i < CHAT_CHAR_CONVERT_ARRAY.length; i += 2) {
			t = chatMsg.replaceAll(CHAT_CHAR_CONVERT_ARRAY[i],
					CHAT_CHAR_CONVERT_ARRAY[i + 1]);
		}
		return t;
	}

}
