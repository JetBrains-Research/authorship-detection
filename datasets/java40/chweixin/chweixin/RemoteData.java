package ipower.wechat.modal;

import java.io.Serializable;

/**
 * 提供给第三方调用数据。
 * @author yangyong.
 * @since 2014-04-12.
 */
public class RemoteData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userId,menu,type,data;
	/**
	 * 构造函数。
	 */
	public RemoteData(){}
	/**
	 * 构造函数。
	 * @param userId
	 * 微信用户ID。
	 * @param menu
	 * 微信用户最后点击菜单代码。
	 * @param type
	 * 微信请求消息类型。
	 * @param data
	 * 微信请求数据。
	 */
	public RemoteData(String userId,String menu,String type,String data){
		this.setUserId(userId);
		this.setMenu(menu);
		this.setType(type);
		this.setData(data);
	}
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID。
	 * @param userId
	 * 用户ID。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取菜单代码。
	 * @return 菜单代码。
	 */
	public String getMenu() {
		return menu;
	}
	/**
	 * 设置菜单代码。
	 * @param menu
	 * 菜单代码。
	 */
	public void setMenu(String menu) {
		this.menu = menu;
	}
	/**
	 * 获取微信请求类型。
	 * @return 微信请求类型。
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置微信请求类型。
	 * @param type
	 * 微信请求类型。
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取微信请求数据。
	 * @return 微信请求数据。
	 */
	public String getData() {
		return data;
	}
	/**
	 * 设置微信请求数据。
	 * @param data
	 * 微信请求数据。
	 */
	public void setData(String data) {
		this.data = data;
	}
	
}