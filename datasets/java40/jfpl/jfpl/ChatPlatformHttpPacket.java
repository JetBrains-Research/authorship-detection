package com.jufan.platform.http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.cyss.android.lib.service.ISendPacket;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.util.ConstVariables;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.DateUtil;
import com.lianzt.util.SSLConnection;

public class ChatPlatformHttpPacket extends ISendPacket {

	@Override
	public AbstractCommonData sendHttpPost(AbstractCommonData sendData)
			throws Exception {
		String url = getCommonUrl(sendData);
		AbstractCommonData head = sendData.getDataValue("head");
		head.putStringValue("device", "android:" + android.os.Build.MODEL);
		if (!head.containsKey("username") && head.containsKey("phone_num")) {
			head.putStringValue("username", head.getStringValue("phone_num"));
		}
		if (!head.containsKey("message_type")) {
			head.putStringValue("message_type", "0");
		}
		head.putStringValue("client_time", DateUtil.detaledFormat(new Date()));
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		String sendDataStr = DataConvertFactory.praseNormJson(sendData);
		String sendMd5 = SSLConnection.Md5(sendDataStr
				+ ConstVariables.NODE_MD5_CHECK_KEY);
		Log.i(SystemUtil.LOG_MSG, sendMd5 + sendDataStr);
		BasicNameValuePair xml = new BasicNameValuePair("json", sendMd5
				+ sendDataStr);
		nameValuePairs.add(xml);
		AbstractCommonData back_acd = DataConvertFactory
				.getInstanceByJson(post(url, nameValuePairs));
		return back_acd;
	}

}
