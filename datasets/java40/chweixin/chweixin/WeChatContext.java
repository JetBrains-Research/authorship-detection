package ipower.wechat.message;

import java.io.Serializable;
import java.util.Date;
/**
 * 微信用户消息上下文。
 * @author yangyong.
 * @since 2014-04-10.
 * */
public class WeChatContext implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 事件消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	/**
	 * 文本消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	/**
	 * 图片消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	/**
	 * 语音消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 视频消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	/**
	 * 地理位置信息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	/**
	 * 链接消息请求。
	 * */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	/**
	 * 关注事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_SUBSCRIBE = "subscribe";
	/**
	 * 取消关注事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 扫描事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_SCAN = "SCAN";
	/**
	 * 上报地理位置事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_LOCATION = "LOCATION";
	/**
	 * 自定义菜单事件消息。
	 * */
	public static final String EVENT_MESSAGE_TYPE_CLICK = "CLICK";
	
	private String accountId,userId,openId,lastMenuKey;
	private Date lastActiveTime;
	/**
	 * 构造函数。
	 * @param account
	 * 所属公众号。
	 * @param openId
	 * 微信用户OpenId
	 * */
	public WeChatContext(String accountId, String openId){
		this.accountId = accountId;
		this.openId = openId;
	}
	/**
	 *  获取所属公众号ID。
	 *  @return 所属公众号ID。
	 * */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * 获取用户ID。
	 * @return
	 * 用户ID(身份验证后获得)。
	 * */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId
	 * 用户ID(身份验证后获得)。
	 * */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取微信用户OpenId.
	 *@return 微信用户OpenId.
	 * */
	public String getOpenId() {
		return this.openId;
	}
	/**
	 * 获取最新按钮键值。
	 * @return 最新按钮键值。
	 * */
	public String getLastMenuKey() {
		return lastMenuKey;
	}
	/**
	 * 设置最新按钮键值。
	 * @param lastMenuKey
	 * 最新按钮键值。
	 * */
	public void setLastMenuKey(String lastMenuKey) {
		this.lastMenuKey = lastMenuKey;
	}
	/**
	 * 获取最后一次活动时间。
	 * @return 最后一次活动时间。
	 * */
	public Date getLastActiveTime() {
		return lastActiveTime;
	}
	/**
	 * 设置最后一次活动时间。
	 * @param lastActiveTime
	 * 最后一次活动时间。
	 * */
	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	/**
	 * 清空上下文。
	 * */
	public synchronized void clear(){
		this.lastMenuKey = null;
		this.setUserId(null);
	}
	/**
	 * 是否已身份认证。
	 * @return
	 * 已身份认证为true，否则返回false.
	 * */
	public boolean isAuthen(){
		return !(this.getUserId() == null || this.getUserId().trim().isEmpty());
	}
}