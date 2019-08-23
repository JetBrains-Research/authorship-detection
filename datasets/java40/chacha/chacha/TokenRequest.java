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
 * 令牌认证请求
 *	@author: Fully
 */
public class TokenRequest extends Request implements JsonConvert{
	private static final String CMDTOKEN="token";
	private TokenBody body;
	
	
	public TokenRequest() {
		super.setOpt(CMDTOKEN);
	}

	
	public TokenRequest(TokenBody body) {
		super.setOpt(CMDTOKEN);
		this.body = body;
	}


	public TokenBody getBody() {
		return body;
	}


	public void setBody(TokenBody body) {
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
		TokenRequest tr=(TokenRequest)JsonPojoMapper.fromJson(json, TokenRequest.class);
		this.setOpt(tr.getOpt());
		this.setBody(tr.getBody());
	}
	
	public static void main(String[] args){
		//pojo to json
		TokenRequest login=new TokenRequest();
		TokenBody body=new TokenBody("f706c0e77064d8c127ea45397d72778942685880218da7e8e005137104b65df8");
		login.setBody(body);
		String json="";
		try{
			json=login.toJson();
			System.out.println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		//json to pojo
		TokenRequest r=new TokenRequest();
		try {
			r.fromJson(json);
			System.out.println(r.getBody().getTicket());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
