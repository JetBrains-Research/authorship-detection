package org.chacha.context;

import org.chacha.dao.impl.AccountDAO;
import org.chacha.dao.impl.LoginTokenDAO;
import org.chacha.entity.AuthRequest;
import org.chacha.entity.AuthResponse;
import org.chacha.entity.ResponseCode;
import org.chacha.model.Account;
import org.chacha.model.LoginToken;

/**
 * ��֤ҵ��ʵ����
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
	 * ������֤
	 * 
	 * @param request
	 *            ������֤����
	 * @return ��Ӧ��Ϣ����֤�ɹ���ʧ�ܣ�����֤�ɹ�����action�����Ƿ񷵻�����
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
				//�־û����ƣ����ں���sso����У��
				loginTokenDAO.addToken(token);
			}
		}
		return response;
	}

	/**
	 * ������֤
	 * 
	 * @param request
	 *            ������֤����
	 * @return ������֤�Ƿ�ɹ�
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
				response.setResult(ResponseCode.ERROR_TOKEN);//���������
			else{
				if(life>=System.currentTimeMillis()){
					response.setResult(ResponseCode.SUCCESS);//����У��ɹ�
					response.setToken(userName);	//�����û���
				}else{
					response.setResult(ResponseCode.ERROR_TOKENDISABLE);
				}
			}
		}
		return response;
	}

	/**
	 * ����ע����ȫ���˳�
	 * 
	 * @param request
	 *            ����ע������
	 * @return ���
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
