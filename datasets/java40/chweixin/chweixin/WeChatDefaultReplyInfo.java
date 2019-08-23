package ipower.wechat.modal;

import java.util.Date;

import ipower.model.Paging;

/**
 * 默认回复消息。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class WeChatDefaultReplyInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,content,accountId,accountName;
	private Integer status;
	private Date createTime;
	/**
	 * 构造函数。
	 * */
	public WeChatDefaultReplyInfo(){
		this.setCreateTime(new Date());
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
	 * 获取回复ID。
	 * @return
	 * 回复ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置回复ID。
	 * @param id
	 * 回复ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取回复内容。
	 * @return
	 * 回复内容。
	 * */
	public String getContent() {
		return content;
	}
	/**
	 * 设置回复内容。
	 * @param content
	 * 回复内容。
	 * */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取回复状态。
	 * @return
	 * 回复状态(1-启用,0-停用)。
	 * */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置回复状态。
	 * @param status
	 * 回复状态(1-启用,0-停用)。
	 * */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取创建时间。
	 *  @return 创建时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}