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
package org.chacha.client.command.auth;

import java.io.IOException;

import org.chacha.client.command.JsonConvert;
import org.chacha.client.command.Request;
import org.chacha.util.JsonPojoMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 密码认证请求
 *	@author: Fully
 */
public class LoginRequest extends Request implements JsonConvert{
	private static String CMDLOGIN="auth";
	private LoginBody body;
	
	
	public LoginRequest() {
		super.setOpt(CMDLOGIN);
	}
	
	public LoginRequest(LoginBody body) {
		super.setOpt(CMDLOGIN);
		this.body = body;
	}

	public LoginBody getBody() {
		return body;
	}

	public void setBody(LoginBody body) {
		this.body = body;
	}

	@Override
	public String toJson() throws JsonMappingException,
			JsonGenerationException, IOException {
		return JsonPojoMapper.toJson(this,true);
	}

	@Override
	public void fromJson(String json) throws JsonMappingException,
			JsonGenerationException, IOException {
		LoginRequest lr=(LoginRequest)JsonPojoMapper.fromJson(json, LoginRequest.class);
		this.setOpt(lr.getOpt());
	}

	public static void main(String[] args){
		LoginRequest login=new LoginRequest();
		LoginBody body=new LoginBody("chacha1","fdafdgwqtgsadfadf",LoginBody.ACTION_TOKEN);
		login.setBody(body);
		try{
			System.out.println(login.toJson());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
