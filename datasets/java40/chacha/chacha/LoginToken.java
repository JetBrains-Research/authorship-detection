package org.chacha.model;

import java.util.Random;

import org.chacha.util.DigestUtil;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 64位字符串的登录令牌，可用于sso
 *	@author: Fully
 */
public class LoginToken {
	private String _id;
	private String userName;//用户名
	private String token;	//令牌
	private long tokenLifeTime=INVALID;//令牌有效期
	public static long INVALID=-1;
	private static Random r=new Random();
	private static final int HOUR=60*60*1000;
	
	public LoginToken(){}
	/**
	 * 默认有效期1小时的令牌构造方法
	 * @param userName
	 */
	public LoginToken(String userName){
		this(userName,HOUR);
	}
	/**
	 * 
	 * @param userName 用户名
	 * @param tokenLifeTime 有效期，单位毫秒
	 */
	public LoginToken(String userName,long tokenLifeTime){
		super();
		this.userName=userName;
		long now=System.currentTimeMillis();
		String src=userName+(r.nextLong()+now);
		this.token=DigestUtil.digestPassword(src);
		//令牌有效期
		this.tokenLifeTime=now+tokenLifeTime;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getTokenLifeTime() {
		return tokenLifeTime;
	}

	public void setTokenLifeTime(long tokenLifeTime) {
		this.tokenLifeTime = tokenLifeTime;
	}
	
	public DBObject toDbObject(){
		BasicDBObject entity=new BasicDBObject();
		entity.put("_id", _id);
		entity.put("userName", userName);
		entity.put("token", token);
		entity.put("tokenLifeTime", tokenLifeTime);
		return entity;
	}
	
	public void fromDbObject (DBObject obj){
		this._id=(String)obj.get("_id");
		this.userName=(String)obj.get("userName");
		this.token=(String)obj.get("token");
		this.tokenLifeTime=obj.get("tokenLifeTime")==null?-1:(Long)obj.get("tokenLifeTime");
		
	}
	
	public static void main(String[] args){
		LoginToken token=new LoginToken("chacha");
		System.out.println(token.getToken());
		
		token=new LoginToken("chacha",3600*1000*2);
		System.out.println(token.getToken());
	}
}
