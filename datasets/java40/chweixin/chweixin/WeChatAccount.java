package ipower.wechat.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 微信公众号管理。
 * @author yangyong.
 * @since 2014-03-31.
 * */
public class WeChatAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,account, token, openId,appId,appSecret,businessURL,description;
	private Integer status,type;
	private Date createTime;
	/**
	 * 公众号状态-停用。
	 * */
	public final static int ACCOUNT_STATUS_DISABLE = 0;
	/**
	 * 公众号状态-启用。
	 * */
	public final static int ACCOUNT_STATUS_ENABLE = 1;
	/**
	 * 公众号类型－服务号。
	 * */
	public final static int ACCOUNT_TYPE_SERVICE = 1;
	/**
	 * 公众号类型－订阅号。
	 * */
	public final static int ACCOUNT_TYPE_SUBSCRIBE = 2;
	/**
	 * 构造函数。
	 * */
	public WeChatAccount(){
		this.setCreateTime(new Date());
	}
	/**
	 * 获取公众号ID。
	 * @return 公众号ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置公众号ID。
	 * @param id
	 * 	ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取公众号名称。
	 * @return 公众号名称。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置公众号名称。
	 * @param name
	 * 	公众号名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取公众号账号。
	 * @return 公众号账号。
	 * */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置公众号账号。
	 * @param account
	 * 	公众号账号。
	 * */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取公众号类型。
	 * @return 公众号类型。
	 * 1-服务号，2-订阅号。
	 * */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置公众号类型。
	 * @param type
	 * 公众号类型。
	 * 1-服务号，2-订阅号。
	 * */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取接入令牌。
	 * @return 接入令牌。
	 * */
	public String getToken() {
		return token;
	}
	/**
	 * 设置接入令牌。
	 * @param token
	 * 	接入令牌。
	 * */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取公众号OpenId。
	 * @return 公众号OpenId。
	 * */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置公众号OpenId。
	 * @param openId
	 * 	公众号OpenId。
	 * */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取第三方用户唯一凭证。
	 * @return 第三方用户唯一凭证。
	 * */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置第三方用户唯一凭证。
	 * @param appId
	 * 	第三方用户唯一凭证
	 * */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取第三方用户唯一凭证密钥。
	 * @return 第三方用户唯一凭证密钥。
	 * */
	public String getAppSecret() {
		return appSecret;
	}
	/**
	 * 设置第三方用户唯一凭证密钥。
	 * @param appSecret
	 * 	第三方用户唯一凭证密钥。
	 * */
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	/**
	 * 获取业务接口URL。
	 * @return 业务接口URL。
	 * */
	public String getBusinessURL() {
		return businessURL;
	}
	/**
	 * 设置业务接口URL。
	 * @param businessURL
	 * 	业务接口URL。
	 * */
	public void setBusinessURL(String businessURL) {
		this.businessURL = businessURL;
	}
	/**
	 * 获取当前账号状态。
	 * @return 当前账号状态。
	 * 0-停用，1-启用。
	 * */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置当前账号状态。
	 * @param status
	 * 	当前账号状态。
	 * 0-停用，1-启用。
	 * */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取描述信息。
	 * @return 描述信息。
	 * */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description
	 * 	描述信息。
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 	创建时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}