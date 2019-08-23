package com.jufan.platform.http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.cyss.android.lib.service.ISendPacket;
import com.cyss.android.lib.util.SystemUtil;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.DateUtil;

public class ClientHttpPacket extends ISendPacket {

	@Override
	public AbstractCommonData sendHttpPost(AbstractCommonData sendData)
			throws Exception {
		AbstractCommonData head = sendData.getDataValue("head");
		head.putStringValue("source_channel", "M");
		head.putStringValue("target_channel", "P");
		head.putStringValue("message_type", "0");
		head.putStringValue("client_time", DateUtil.detaledFormat(new Date()));
		head.putStringValue("username", head.getStringValue("phone_num"));
		String url = getCommonUrl(sendData);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		String sendXml = DataConvertFactory.praseBase64String(sendData);
		Log.i(SystemUtil.LOG_MSG, "sendData==>" + sendData);
		BasicNameValuePair xml = new BasicNameValuePair("xml", sendXml);
		nameValuePairs.add(xml);
		return DataConvertFactory.getInstanceByJson(post(url, nameValuePairs));
	}

}
