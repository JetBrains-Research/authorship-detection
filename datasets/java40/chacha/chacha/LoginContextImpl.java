package org.chacha.context;

import org.chacha.dao.impl.AccountDAO;
import org.chacha.dao.impl.LoginTokenDAO;
import org.chacha.entity.AuthRequest;
import org.chacha.entity.AuthResponse;
import org.chacha.entity.ResponseCode;
import org.chacha.model.Account;
import org.chacha.model.LoginToken;

/**
 * 认证业务实现类
 * 
 * @author Fully
 * 
 */
public class LoginContextImpl implements LoginContext {
	private AccountDAO accoutDAO;
    private LoginTokenDAO loginTokenDAO;
    private AuthResponse response;
	public LoginContextImpl() {
		super();
		accoutDAO = new AccountDAO();
		loginTokenDAO=new LoginTokenDAO();
		response=new AuthResponse();
	}

	/**
	 * 密码认证
	 * 
	 * @param request
	 *            密码认证请求
	 * @return 回应消息，认证成功或失败，若认证成功根据action决定是否返回令牌
	 */
	public AuthResponse loginPass(AuthRequest request) {
		String userName=((String)request.getKey(AuthRequest.KEY_USERNAME)).trim();
		Account account=accoutDAO.getAccountByName(userName);
		if(account==null||account.getPasswd().equals(AuthRequest.KEY_PASSWD)){
			response.setResult(ResponseCode.ERROR_USERPASS);
		}else{
			response.setResult(ResponseCode.SUCCESS);
			if((Integer)request.getKey(AuthRequest.KEY_ACTION)==AuthRequest.NEEDTOKEN){
				LoginToken token=new LoginToken(userName);
				response.setToken(token.getToken());
				//持久化令牌，用于后续sso令牌校验
				loginTokenDAO.addToken(token);
			}
		}
		return response;
	}

	/**
	 * 令牌认证
	 * 
	 * @param request
	 *            令牌认证请求
	 * @return 令牌认证是否成功
	 */
	public AuthResponse loginToken(AuthRequest request) {
		String token=((String)request.getKey(AuthRequest.KEY_TOKEN)).trim();
		LoginToken loginToken=loginTokenDAO.getTokenByToken(token);
		if(loginToken==null){
			response.setResult(ResponseCode.ERROR_TOKENNOTEXIST);
		}else{
			String dsToken=loginToken.getToken();
			long life=loginToken.getTokenLifeTime();
			String userName=loginToken.getUserName();
			if(!token.equals(dsToken))
				response.setResult(ResponseCode.ERROR_TOKEN);//错误的令牌
			else{
				if(life>=System.currentTimeMillis()){
					response.setResult(ResponseCode.SUCCESS);//令牌校验成功
					response.setToken(userName);	//返回用户名
				}else{
					response.setResult(ResponseCode.ERROR_TOKENDISABLE);
				}
			}
		}
		return response;
	}

	/**
	 * 令牌注销，全局退出
	 * 
	 * @param request
	 *            令牌注销请求
	 * @return 结果
	 */
	public AuthResponse loginOut(AuthRequest request) {
		String token=((String)request.getKey(AuthRequest.KEY_TOKEN)).trim();
		LoginToken loginToken=loginTokenDAO.getTokenByToken(token);
		if(loginToken==null){
			response.setResult(ResponseCode.ERROR_TOKEN);
		}else{
			loginTokenDAO.deleteToken(token);
			response.setResult(ResponseCode.SUCCESS);
		}
		return response;
	}

}
