package com.jufan.platform.service;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.cyss.android.lib.AutoActivity;
import com.jufan.platform.util.ChatMessageUtil;
import com.jufan.platform.util.GlobalVariables;
import com.lianzt.commondata.AbstractCommonData;

public class ChatUploadService extends ChatBaseService {

	public ChatUploadService(AutoActivity ctx, ListView chatList,
			ProgressBar progressBar, ImageView imageView, AbstractCommonData acd) {
		super(ctx, chatList, progressBar, imageView, acd);
	}

	@Override
	public void doChatSuccess(AbstractCommonData acd) {

		String id = itemData.getStringValue("id");
		if (acd.containsKey("image_id")) {
			String imageId = acd.getStringValue("image_id");
			String imageThumbId = acd.getStringValue("image_thumb_id");
			ctx.update("update_chat_args", imageId, imageThumbId, id);
			ChatMessageUtil.sendChatImage(ctx, GlobalVariables.loginUsername,
					GlobalVariables.chatToUser, "to_group", imageId,
					imageThumbId, new ChatImageService(ctx, chatList,
							progressBar, imageView, itemData));
		} else if (acd.containsKey("voice_id")) {
			String voiceId = acd.getStringValue("voice_id");
			ctx.update("update_chat_args", voiceId, "", id);
			ChatMessageUtil.sendChatVoice(ctx, GlobalVariables.loginUsername,
					GlobalVariables.chatToUser, "to_group", voiceId,
					new ChatVoiceService(ctx, chatList, progressBar, imageView,
							itemData));
		}
		ctx.update("update_chat_state", "3", id);
	}

	@Override
	public void doChatError(AbstractCommonData acd) {
		ctx.update("update_chat_state", "1", itemData.getStringValue("id"));

	}

}
