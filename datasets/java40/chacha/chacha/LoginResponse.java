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
import org.chacha.client.command.Response;
import org.chacha.util.JsonPojoMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 认证回应消息
 *	@author: Fully
 */
public class LoginResponse extends Response implements JsonConvert{
	private String token;

	public LoginResponse() {
		super();
	}

	public LoginResponse(int result,String token) {
		super.result=result;
		this.token=token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public void fromJson(String json) throws JsonMappingException,
			JsonGenerationException, IOException {
		LoginResponse r=(LoginResponse)JsonPojoMapper.fromJson(json, LoginResponse.class);
		this.result=r.getResult();
		this.token=r.getToken();
	}
	
	public static void main(String[] args){
		//pojo to json
		LoginResponse response=new LoginResponse();
		response.setResult(0);
		response.setToken("f706c0e77064d8c127ea45397d72778942685880218da7e8e005137104b65df8");
		String json="";
		try{
			json=response.toJson();
			System.out.println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		//json to pojo
		LoginResponse r=new LoginResponse();
		try {
			r.fromJson(json);
			System.out.println(r.getResult()+" "+r.getToken());
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
		
		//pojo to json
		response=new LoginResponse();
		response.setResult(22);
		json="";
		try{
			json=response.toJson();
			System.out.println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
