package ipower.wechat.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 微信公众号菜单。
 * @author yangyong.
 * @since 2014-04-01.
 * */
public class WeChatMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static int MENU_TYPE_CLICK = 1, MENU_TYPE_VIEW = 2;
	private String id,name,code,url;
	private Integer type,orderNo;
	private WeChatAccount account;
	private WeChatMenu parent;
	private Set<WeChatMenu> children = new LinkedHashSet<WeChatMenu>();
	/**
	 * 获取所属公众号。
	 * @return 所属公众号。
	 * */
	public WeChatAccount getAccount() {
		return account;
	}
	/**
	 * 设置所属公众号。
	 * @param account
	 * 	所属公众号。
	 * */
	public void setAccount(WeChatAccount account) {
		this.account = account;
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
	 * 	菜单ID。
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
	 * 	菜单名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取菜单代码。
	 * @return 菜单代码。
	 * */
	public String getCode() {
		return code;
	}
	/**
	 * 设置菜单代码。
	 * @param code
	 * 	菜单代码。
	 * */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取菜单类型。
	 * @return 
	 * 	菜单类型。
	 * 1-Click，2-View
	 * */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置菜单类型。
	 * @param type
	 * 	菜单类型。
	 * 1-Click，2-View
	 * */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 *  获取上级菜单。
	 *  @return 上级菜单。
	 * */
	public WeChatMenu getParent() {
		return parent;
	}
	/**
	 * 设置上级菜单。
	 * @param parent
	 * 上级菜单。
	 * */
	public void setParent(WeChatMenu parent) {
		this.parent = parent;
	}
	/**
	 * 获取URL
	 * 类型为View时为URL跳转地址，类型为Click时不为空则为业务接口地址。
	 * */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置URL
	 * 类型为View时为URL跳转地址，类型为Click时不为空则为业务接口地址。
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取子菜单集合。
	 * @return 子菜单集合。
	 * */
	public Set<WeChatMenu> getChildren() {
		return children;
	}
	/**
	 * 设置子菜单集合。
	 * @param children
	 * 	子菜单集合。
	 * */
	public void setChildren(Set<WeChatMenu> children) {
		this.children = children;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 * */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 排序号。
	 * */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}