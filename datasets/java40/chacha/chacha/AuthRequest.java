/*
 * Copyright 2012 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.chacha.entity;

import org.chacha.util.JsonPojoMapper;


public class AuthRequest extends Request{
	public static String CMD_AUTH="auth" ; //密码认证命令，命令号
	public static String CMD_TOKEN="token";//令牌认证命令号
	public static String CMD_LOGOUT="logout";//令牌注销命令号
	
	public static String KEY_USERNAME="name";	//用户名key
	public static String KEY_PASSWD="passwd";	//密码key
	public static String KEY_ACTION="action";	//认证回应行为
	public static int NEEDTOKEN=0;	//回应消息返回令牌
	public static int NOTOKEN=1;	//回应消息不需要令牌
	
	public static String KEY_TOKEN="ticket";//令牌字串
	
	public static void main(String[] args){
		//密码认证请求
		AuthRequest auth=new AuthRequest();
		auth.setOpt(CMD_AUTH);
		auth.put(KEY_USERNAME, "chacha");
		auth.put(KEY_PASSWD, "dddddddd");
		auth.put(KEY_ACTION, NEEDTOKEN);
		try{
			String json=JsonPojoMapper.toJson(auth, true);
			System.out.println(json);
			
			AuthRequest req=(AuthRequest)JsonPojoMapper.fromJson(json, AuthRequest.class);
			System.out.println(req.get(KEY_USERNAME));
			System.out.println(req.get(KEY_PASSWD));
			System.out.println(req.get(KEY_ACTION));
			//令牌认证请求
			 auth=new AuthRequest();
			 auth.setOpt(CMD_TOKEN);
			 auth.put(KEY_TOKEN, "f706c0e77064d8c127ea45397d72778942685880218da7e8e005137104b65df8");
			 json=JsonPojoMapper.toJson(auth, true);
			 System.out.println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
