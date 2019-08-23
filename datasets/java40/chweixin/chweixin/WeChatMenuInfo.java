package ipower.wechat.modal;

import java.io.Serializable;
import java.util.List;
/**
 * 微信菜单信息。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public class WeChatMenuInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid,id,name,code,url,accountId,accountName;
	private Integer type,orderNo;
	private List<WeChatMenuInfo> children;
	/**
	 * 获取上级菜单ID。
	 * @return 上级菜单ID。
	 * */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级菜单ID。
	 * @param pid
	 * 上级菜单ID。
	 * */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取菜单ID。
	 * @return 菜单ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置菜单ID。
	 * @param id
	 * 菜单ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取菜单名称。
	 * @return 菜单名称。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置菜单名称。
	 * @param name
	 * 菜单名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取菜单类型。
	 * @return 菜单类型。
	 * */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置菜单类型。
	 * @param type
	 * 菜单类型。
	 * */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 设置菜单代码。
	 * @return 菜单代码。
	 * */
	public String getCode() {
		return code;
	}
	/**
	 * 设置菜单代码。
	 * @param code
	 * 菜单代码。
	 * */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取菜单URL。
	 * @return 菜单URL。
	 * */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置菜单URL。
	 * @param url
	 * 菜单URL。
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取菜单排序号。
	 * @return 菜单排序号。
	 * */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置菜单排序号。
	 * @param orderNo
	 * 菜单排序号。
	 * */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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
	 * 获取子菜单集合。
	 * @return 子菜单集合。
	 * */
	public List<WeChatMenuInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子菜单集合。
	 * @param children
	 * 子菜单集合。
	 * */
	public void setChildren(List<WeChatMenuInfo> children) {
		this.children = children;
	}
}