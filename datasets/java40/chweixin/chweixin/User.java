package ipower.wechat.domain;

import java.io.Serializable;

/**
 * 用户管理。
 * @author yangyong.
 * @since 2014-04-04.
 * */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,account,password;
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置用户ID。
	 * @param id
	 * 用户ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户姓名。
	 * @return 用户姓名。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户姓名。
	 * @param name
	 * 用户姓名。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取用户帐号。
	 * @return 用户帐号。
	 * */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置用户帐号。
	 * @param account
	 * 用户帐号。
	 * */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取密码。
	 * @return 密码。
	 * */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置密码。
	 * @param password
	 * 密码。
	 * */
	public void setPassword(String password) {
		this.password = password;
	}
}