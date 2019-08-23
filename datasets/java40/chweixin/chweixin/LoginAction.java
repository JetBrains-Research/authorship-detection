package ipower.wechat.action;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ipower.action.BaseAction;
import ipower.model.Json;
import ipower.model.UserIdentity;
import ipower.wechat.service.IUserAuthenticationService;
/**
 * 登录Action。
 * @author yangyong.
 * @since 2014-03-15.
 * */
public class LoginAction extends BaseAction implements SessionAware {
	private Map<String, Object> session;
	private IUserAuthenticationService userAuthentication;
	private String account,password;
	/**
	 * 设置用户认证服务接口。
	 * @param userAuthentication
	 * 	用户认证服务接口。
	 * */
	public void setUserAuthentication(IUserAuthenticationService userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
	/**
	 * 设置Session。
	 * @param session
	 * 	Session.
	 * */
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	/**
	 * 设置用户账号。
	 * @param account
	 * 	用户账号。
	 * */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 设置用户密码。
	 * @param password
	 * 	用户密码。
	 * */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 登录。
	 * @throws IOException 
	 * */
	public void login() throws IOException{
		Json result = new Json();
		try{
			UserIdentity identity = this.userAuthentication.authen(this.account, this.password);
			if(identity != null && this.session != null){
				this.session.put(BaseAction.CURRENT_USER_SESSION_KEY, identity);
				result.setSuccess(true);
			}
		}catch(Exception e){
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}finally{
			this.writeJson(result);
		}
	}
	/**
	 * 初始化账号密码。
	 * @throws IOException 
	 * */
	public void init() throws IOException{
		try{
			this.userAuthentication.init("admin", "123");
			this.writeJson("初始化账号［admin］完成！");
		}catch(Exception e){
			this.writeJson(e.getMessage());
		}
	}
}