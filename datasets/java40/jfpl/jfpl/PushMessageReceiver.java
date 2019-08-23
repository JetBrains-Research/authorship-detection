package com.jufan.platform.receiver;

import java.util.Date;
import java.util.List;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.cyss.android.lib.service.BaseService;
import com.cyss.android.lib.service.IServiceLogic;
import com.cyss.android.lib.service.ServiceController;
import com.cyss.android.lib.util.DBHelper;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.ChatActivity;
import com.jufan.platform.ui.MainActivity;
import com.jufan.platform.ui.R;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.exception.InstanceDataException;
import com.lianzt.util.DateUtil;
import com.lianzt.util.StringUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PushMessageReceiver extends FrontiaPushMessageReceiver {

	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		if (errorCode == 0) {
			Log.i(SystemUtil.LOG_MSG, "userId: " + userId + ", channelId:"
					+ channelId + ", appid:" + appid);
			setBind(context, true, new String[] { appid, channelId, userId });
		}
	}

	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
	}

	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		Log.d(SystemUtil.LOG_MSG, "jf-pl---message:" + message
				+ ", customContent:" + customContentString);
		AbstractCommonData backAcd = null;
		try {
			backAcd = DataConvertFactory.getInstanceByJson(message);
		} catch (InstanceDataException ex) {
			SystemUtil.printException(ex, "w");
		}
		if (backAcd != null
				&& !StringUtil.isNull(GlobalVariables.loginUsername)) {

			AbstractCommonData head = backAcd.getDataValue("head");
			String msgType = head.getStringValue("message_type");
			AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
			item.putStringValue("type", "0");
			item.putStringValue("msg_type", msgType);
			String fromUser = backAcd.getStringValue("from_user");
			String fromGroup = backAcd.getStringValue("from_group");

			String notifyTitle = context.getString(R.string.app_name);
			String notifyContent = "客服有新的消息回复";
			String recentlyTitle = "";
			String recentlyContent = null;
			String recentlyUrl = null;
			String recentlyAppId = null;

			DBHelper dbHelper = new DBHelper(context);
			SQLiteDatabase con = dbHelper.getWritableDatabase();
			Date serverDate = null;
			try {
				serverDate = DateUtil.parseDate(head
						.getStringValue("server_time"));
			} catch (Exception ex) {
			}
			int defaultType = 0;
			recentlyAppId = backAcd.getStringValue("busi_type");
			if ("1".equals(msgType)) {
				String chatMsg = backAcd.getStringValue("message_text");
				item.putStringValue("chat_msg", chatMsg);
				con.execSQL(
						SystemUtil.SQL_MAP.get("add_chat_message"),
						new Object[] { SystemUtil.getUniqueId(), "0", msgType,
								fromUser, GlobalVariables.loginUsername,
								chatMsg, serverDate, recentlyAppId, "0", "", "" });
				recentlyContent = chatMsg;
			} else if ("2".equals(msgType)) {
				recentlyContent = "【图片】";
			} else if ("3".equals(msgType)) {
				recentlyContent = "【语音】";
			} else if ("4".equals(msgType)) {
				recentlyContent = "【位置】";
			} else if ("5".equals(msgType)) {
				recentlyContent = "【视频】";
			} else if ("6".equals(msgType)) {
				recentlyContent = "【表单】" + backAcd.getStringValue("busi_desc");
			} else if ("9".equals(msgType)) {
				defaultType = 1;
				notifyTitle = backAcd.getStringValue("title");
				notifyContent = backAcd.getStringValue("content");
				recentlyTitle = notifyTitle;
				recentlyContent = notifyContent;
				recentlyAppId = backAcd.getStringValue("busi_type");
				recentlyUrl = backAcd.getStringValue("url");
			}

			if (SystemUtil.TOP_ACTIVITY != null
					&& "chatactivity".equals(SystemUtil.TOP_ACTIVITY.getClass()
							.getSimpleName().toLowerCase())
					&& SystemUtil.TOP_ACTIVITY_ACTIVE
					&& fromUser.equals(GlobalVariables.chatToUser)
					&& defaultType == 0) {
				ChatActivity ca = (ChatActivity) SystemUtil.TOP_ACTIVITY;
				ca.appendChatItem(item);
				ca.chatListToBottom();
				addRecently(con, recentlyTitle, recentlyContent, recentlyAppId,
						recentlyUrl, false);
			} else if (SystemUtil.TOP_ACTIVITY != null
					&& "mainactivity".equals(SystemUtil.TOP_ACTIVITY.getClass()
							.getSimpleName().toLowerCase())
					&& SystemUtil.TOP_ACTIVITY_ACTIVE) {
				addRecently(con, recentlyTitle, recentlyContent, recentlyAppId,
						recentlyUrl, true);
				sendNotify(context, backAcd, notifyTitle, notifyContent);
				MainActivity ma = (MainActivity) SystemUtil.TOP_ACTIVITY;
				ma.refreshRecently();

			} else {
				sendNotify(context, backAcd, notifyTitle, notifyContent);
				addRecently(con, recentlyTitle, recentlyContent, recentlyAppId,
						recentlyUrl, true);
			}

			con.close();
		}
	}

	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		Log.d(SystemUtil.LOG_MSG, "title：" + title);
		Log.d(SystemUtil.LOG_MSG, "description：" + description);
		Log.d(SystemUtil.LOG_MSG, "customContentString：" + customContentString);
	}

	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {

	}

	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		if (errorCode == 0) {
			setBind(context, false, null);
		}
	}

	public static boolean hasBind(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		String flag = sp.getString("bind_flag", "");
		if ("ok".equalsIgnoreCase(flag)) {
			return true;
		}
		return false;
	}

	public static String[] getBindInfo(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		String flag = sp.getString("bind_flag", "");
		if ("not".equalsIgnoreCase(flag)) {
			return null;
		}
		String[] args = new String[3];
		args[0] = sp.getString("app_id", "");
		args[1] = sp.getString("channel_id", "");
		args[2] = sp.getString("user_id", "");
		return args;
	}

	public static void setBind(Context context, boolean flag, String[] args) {
		String flagStr = "not";
		if (flag) {
			flagStr = "ok";
		}
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("bind_flag", flagStr);
		if (flag) {
			editor.putString("app_id", args[0]);
			editor.putString("channel_id", args[1]);
			editor.putString("user_id", args[2]);
		} else {
			editor.remove("app_id");
			editor.remove("channel_id");
			editor.remove("user_id");
		}
		editor.commit();
	}

	private void sendNotify(Context context, AbstractCommonData backAcd,
			String title, String content) {
		NotificationManager notifyManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
		CharSequence tickerText = context.getString(R.string.app_name);
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("new_msg", DataConvertFactory.praseNormJson(backAcd));
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				(int) System.currentTimeMillis(), intent, 0);
		notification.setLatestEventInfo(context, title, content, contentIntent);
		notifyManager.notify(0, notification);
	}

	private void addRecently(SQLiteDatabase con, String title, String content,
			String appId, String url, boolean flag) {

		Cursor cursor = con.rawQuery(
				SystemUtil.SQL_MAP.get("get_one_chat_recently"),
				new String[] { appId });

		if (cursor.moveToNext()) {
			int num = cursor.getInt(0);
			if (flag) {
				num++;
			}
			con.execSQL(SystemUtil.SQL_MAP.get("update_chat_recently"),
					new Object[] { title, new Date().getTime(), content, url,
							num, appId });
		} else {
			int num = flag ? 1 : 0;
			con.execSQL(SystemUtil.SQL_MAP.get("add_chat_recently"),
					new Object[] { appId, title, new Date().getTime(), content,
							url, num });
		}
		cursor.close();
	}
}
