package com.jufan.platform.util;

import java.util.Date;

import com.jufan.platform.ui.R;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;
import com.lianzt.util.DateUtil;

public class ConstVariables {

	public static final String[] IMAGE_TYPE = new String[] { "png", "jpg",
			"jpeg", "gif" };

	public static final int SEND_IMAGE_FLAG = 9999;
	public static final int SEND_LOCATION_FLAG = 9998;
	public static final int DEFAULT_SYNC_TIME = 1 * 1000 * 3600;
	public static final String BAIDU_API_KEY = "lGGTF2RpKuLqjHVuAs8CFB6G";

	public static final String NODE_MD5_CHECK_KEY = "sys713.jtgzfw.com";

	public static final String[] LOCATION_NEAR_BY = new String[] { "酒店", "饭店",
			"大学", "学校", "驾校" };

	public static final String CHAT_DEFAULT_BUSITYPE = "0";
	public static final String CHAT_DEFAULT_BUSISERIAL = "";
	public static final String CHAT_DEFAULT_TITLE = "客服咨询";

	public static final String SERVER_URL = "http://192.168.1.130:3000";

	public static final String VIO_SERVER_URL = "http://192.168.1.131:8084/jtgzfw/ClientServlet";

	public static final String CHAT_BIND_INFO_URL = SERVER_URL
			+ "/platform/user/login";

	public static final String BIND_VEH_URL = SERVER_URL
			+ "/platform/jtgzfw/bind";

	public static final String UNBIND_VEH_URL = SERVER_URL
			+ "/platform/jtgzfw/unbind";

	public static final String GET_BIND_VEH_URL = SERVER_URL
			+ "/platform/jtgzfw/getbind";

	public static final String RESGITER_URL = SERVER_URL + "/platform/user/reg";

	public static final String SYNC_VEH_TIME = "sync_bind_veh_time";

	public static final String[][] INIT_APP = new String[][] {
			{ "1", "违法助手", "intent://com.jufan.platform.ui.MyVioActivity", // "intent://com.jufan.sslk.ui.LogoActivity",//
					// file:///android_asset/www/test.html
					"assets://images/vio_search.png" },
			{
					"2",
					"保险理赔",
					"http://www.jtgzfw.com/jtgzfw/_pc/wx/jgcs_list.do?type_id=JC02",
					"assets://images/friend.png" } };

	public static final String[] HPZL_DESC = new String[] { "02", "小型车辆", "01",
			"大型车辆" };

	public static AbstractCommonData getCommPacket(String username) {
		AbstractCommonData acd = DataConvertFactory.getInstance();
		AbstractCommonData head = acd.getDataValue("head");
		head.putStringValue("device", "android-phone: "
				+ android.os.Build.MODEL);
		head.putStringValue("client_time", DateUtil.detaledFormat(new Date()));
		head.putStringValue("username", username);
		head.putStringValue("message_type", "");
		return acd;
	}

}
