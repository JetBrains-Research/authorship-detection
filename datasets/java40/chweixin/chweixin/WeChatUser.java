package ipower.wechat.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 微信用户信息。
 * @author yangyong.
 * 
 * */
public class WeChatUser implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static int USER_STATUS_SUBSCRIBE = 1, USER_STATUS_UNSUBSCRIBE = -1;
	private WeChatAccount account;
	private String openId,userId,userSign,userName;
	private Date createTime,lastTime;
	private Integer status;
	/**
	 * 构造函数。
	 * */
	public WeChatUser(){
		this.lastTime = this.createTime = new Date();
		this.status = WeChatUser.USER_STATUS_SUBSCRIBE;
	}
	/**
	 * 获取所关注的微信公众号。
	 * @return 所关注的微信公众号。
	 * */
	public WeChatAccount getAccount() {
		return account;
	}
	/**
	 * 设置所关注的微信公众号。
	 * @param account
	 *	所关注的微信公众号。
	 * */
	public void setAccount(WeChatAccount account) {
		this.account = account;
	}
	/**
	 * 获取微信ID。
	 * @return 微信ID。
	 * */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置微信ID。
	 * @param openId
	 * 微信ID。
	 * */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 *  获取用户ID。
	 *  @return 用户ID。
	 * */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId
	 * 	用户ID。
	 * */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取登录帐号。
	 * @return 登录帐号。
	 * */
	public String getUserSign() {
		return userSign;
	}
	/**
	 * 设置登录帐号。
	 * @param userSign
	 * 登录帐号。
	 * */
	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}
	/**
	 * 获取用户名。
	 * @return userName
	 * */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置用户名。
	 * @param userName
	 * 	用户名。
	 * */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取用户状态(1-关注，－1-取消关注)。
	 * @return 用户状态(1-关注，－1-取消关注)。
	 * */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态(1-关注，－1-取消关注)。
	 * @param status
	 * 用户状态(1-关注，－1-取消关注)。
	 * */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取首次关注时间。
	 * @return 首次关注时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置首次关注时间。
	 * @param createTime
	 * 首次关注时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最近一次交互时间。
	 * @return 最近一次交互时间。
	 * */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最近一次交互时间。
	 * @param lastTime
	 * 	最近一次交互时间。
	 * */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}