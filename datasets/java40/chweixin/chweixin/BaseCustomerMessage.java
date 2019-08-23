package ipower.wechat.message.customer;

import ipower.wechat.message.AccessToken;
import java.io.Serializable;
/**
 * 客户消息基础抽象类。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public abstract class BaseCustomerMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private AccessToken token;
	private String toUser,msgType;
	/**
	 * 获取调用接口凭证。
	 * @return 调用接口凭证。
	 * */
	public AccessToken getToken() {
		return token;
	}
	/**
	 * 设置调用接口凭证。
	 * @param token
	 * 	调用接口凭证。
	 * */
	public void setToken(AccessToken token) {
		this.token = token;
	}
	/**
	 * 获取普通用户openid。
	 *  @return 普通用户openid。
	 * */
	public String getToUser() {
		return toUser;
	}
	/**
	 * 设置普通用户openid。
	 * @param toUser
	 * 	普通用户openid。
	 * */
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	/**
	 * 获取消息类型。
	 * @return 消息类型。
	 * */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * 设置消息类型。
	 * @param msgType
	 * 	消息类型。
	 * */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}