package ipower.wechat.modal;

import java.util.Date;
import ipower.model.Paging;
/**
 * 微信关注用户信息。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class WeChatUserInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String openId,userId,userSign,userName,accountId,accountName;
	private Date createTime,lastTime;
	private Integer status; 
	/**
	 * 构造函数。
	 * */
	public WeChatUserInfo(){
		this.setCreateTime(new Date());
	}
	/**
	 * 构造函数。
	 * @param accountId
	 * 微信公众号ID。
	 * @param openId
	 * 微信关注用户ID。
	 * */
	public WeChatUserInfo(String accountId,String openId){
		this();
		this.setAccountId(accountId);
		this.setOpenId(openId);
	}
	/**
	 * 获取所属公众号ID。
	 * @return 所属公众号ID。
	 * */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * 设置所属公众号ID。
	 * @param accountId
	 * 所属公众号ID。
	 * */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取所属公众号名称。
	 * @return 所属公众号名称。
	 * */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置所属公众号名称。
	 * @param accountName
	 * 所属公众号名称。
	 * */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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