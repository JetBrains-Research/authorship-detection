package ipower.wechat.message.resp;

import ipower.wechat.message.BaseMessage;
/**
 * 响应消息基类。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public abstract class BaseRespMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 * */
	public BaseRespMessage(){
		super();
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public BaseRespMessage(BaseMessage req){
		super();
		if(req != null){
			this.setToUserName(req.getFromUserName());
			this.setFromUserName(req.getToUserName());
		}
	}
	/**
	 * 获取接收方帐号(收到的OpenID)。
	 * @return 接收方帐号(收到的OpenID)。
	 * */
	@Override
	public String getToUserName() {
		return super.getToUserName();
	}
	/**
	 * 设置接收方帐号(收到的OpenID)。
	 * @param toUserName
	 * 	接收方帐号(收到的OpenID)。
	 * */
	@Override
	public void setToUserName(String toUserName) {
		super.setToUserName(toUserName);
	}
	/**
	 * 获取开发者微信号。
	 * @return 开发者微信号。
	 * */
	@Override
	public String getFromUserName() {
		return super.getFromUserName();
	}
	/**
	 * 设置开发者微信号。
	 * @param fromUserName
	 *  开发者微信号。
	 * */
	@Override
	public void setFromUserName(String fromUserName) {
		super.setFromUserName(fromUserName);
	}
}