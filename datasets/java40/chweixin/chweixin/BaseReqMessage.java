package ipower.wechat.message.req;

import ipower.wechat.message.BaseMessage;
/**
 * 请求消息基类(普通用户->公众帐号)。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public abstract class BaseReqMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;
	private Long msgId;
	/**
	 * 获取开发者微信号。
	 * @return 开发者微信号。
	 * */
	@Override
	public String getToUserName() {
		return super.getToUserName();
	}
	/**
	 * 设置开发者微信号。
	 * @param toUserName
	 * 	开发者微信号。
	 * */
	@Override
	public void setToUserName(String toUserName) {
		super.setToUserName(toUserName);
	}
	/**
	 * 获取发送方帐号(一个OpenID)。
	 * @return 发送方帐号(一个OpenID)。
	 * */
	@Override
	public String getFromUserName() {
		return super.getFromUserName();
	}
	/**
	 * 设置发送方帐号(一个OpenID)。
	 * @param fromUserName
	 *  发送方帐号(一个OpenID)。
	 * */
	@Override
	public void setFromUserName(String fromUserName) {
		super.setFromUserName(fromUserName);
	}
	/**
	 * 获取消息ID。
	 * @return 消息ID。
	 * */
	public Long getMsgId() {
		return msgId;
	}
	/**
	 * 设置消息ID。
	 * @param msgId
	 * 	消息ID。
	 * */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
}