package ipower.wechat.message;

import java.io.Serializable;

/**
 * 消息基类。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public abstract class BaseMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ToUserName,FromUserName,MsgType;
	private int CreateTime;
	/**
	 * 构造函数。
	 * */
	public BaseMessage(){
		this.setCreateTime((int)(System.currentTimeMillis()/1000));
	}
	/**
	 * 获取目标方帐号。
	 * @return 目标帐号。
	 * */
	public String getToUserName() {
		return ToUserName;
	}
	/**
	 * 设置目标方帐号。
	 * @param toUserName
	 * 	目标方帐号。
	 * */
	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}
	/**
	 * 获取发送方帐号。
	 * @return 发送方帐号。
	 * */
	public String getFromUserName() {
		return FromUserName;
	}
	/**
	 * 设置发送方帐号。
	 * @param fromUserName
	 *  发送方帐号。
	 * */
	public void setFromUserName(String fromUserName) {
		this.FromUserName = fromUserName;
	}
	/**
	 * 获取消息类型(text/image/voice/video/location/link)。
	 * @return 消息类型(text/image/voice/video/location/link)。
	 * */
	public String getMsgType() {
		return MsgType;
	}
	/**
	 * 设置消息类型。
	 * @param msgType
	 * 消息类型(text/image/voice/video/location/link)。
	 * */
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}
	/**
	 * 获取消息创建时间。
	 * @return 消息创建时间。
	 * */
	public int getCreateTime() {
		return CreateTime;
	}
	/**
	 * 设置消息创建时间。
	 * @param createTime
	 * 	消息创建时间。
	 * */
	public void setCreateTime(int createTime) {
		this.CreateTime = createTime;
	}
}