package ipower.wechat.modal;

import java.util.Date;
import ipower.model.Paging;

public class WeChatAccessTokenInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,accessToken,accountId,accountName;
	private Date createTime,failureTime;
	/**
	 * 获取公众号ID。
	 * @return 
	 * 	公众号ID。
	 * */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * 设置公众号ID。
	 * @param accountId
	 * 公众号ID。
	 * */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取公众号名称。
	 * @return 公众号名称。
	 * */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置公众号名称。
	 * @param accountName
	 * 公众号名称。
	 * */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
}