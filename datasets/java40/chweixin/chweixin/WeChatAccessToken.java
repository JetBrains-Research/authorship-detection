package ipower.wechat.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 微信公众号全局唯一票据。
 * @author yangyong.
 * @since 2014-04-03.
 * */
public class WeChatAccessToken implements Serializable {
	private static final long serialVersionUID = 1L;
	private WeChatAccount account;
	private String id,accessToken;
	private Date createTime,failureTime;
	/**
	 * 构造函数。
	 * */
	public WeChatAccessToken(){
		this.createTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.createTime);
		cal.add(Calendar.SECOND, 7200);
		this.failureTime = cal.getTime();
	}
	/**
	 * 获取票据ID。
	 * @return 票据ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置票据ID。
	 * @param id
	 * 票据ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取公众号。
	 * @return 公众号。
	 * */
	public WeChatAccount getAccount() {
		return account;
	}
	/**
	 * 设置公众号。
	 * @param account
	 * 公众号。
	 * */
	public void setAccount(WeChatAccount account) {
		this.account = account;
	}
	/**
	 * 获取全局唯一票据。
	 * @return 全局唯一票据。
	 * */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * 设置全局唯一票据。
	 * @param accessToken
	 * 全局唯一票据。
	 * */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * 获取票据创建时间。
	 * @return 票据创建时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置票据创建时间。
	 * @param createTime
	 * 票据创建时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取票据失效时间。
	 * @return 票据失效时间。
	 * */
	public Date getFailureTime() {
		return failureTime;
	}
	/**
	 * 设置票据失效时间。
	 * @param failureTime
	 *  票据失效时间。
	 * */
	public void setFailureTime(Date failureTime) {
		this.failureTime = failureTime;
	}
	/**
	 * 票据是否有效。
	 * @return 
	 * 有效返回true，失效返回false。
	 * */
	public boolean isEffective(){
		return System.currentTimeMillis() < this.failureTime.getTime();
	}
}