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
package org.chacha.client.command;

import java.io.IOException;

import org.chacha.util.JsonPojoMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 回应消息基类
 *	@author: Fully
 */
public class Response implements JsonConvert{
	protected int result;				//返回码
	public static int ERROR_COMMAND=-2; //系统不能识别的非法命令
	public static int ERROR_USERPASS=1; //用户名或密码错误
	public static int EXCEPTOIN=-1;     //认证服务器异常
	public static int SUCCESS=0; 		//成功
	public static int ERROR_TOKENNOTEXIST=21;//令牌不存在
	public static int ERROR_DISABLE=22;		//令牌已失效
	public static int ERROR_TOKEN=23;		//错误的令牌
	public static int ERROR_TOKENLOGOUTFAIL=24;//令牌注销失败
	public static int ERROR_ACCOUNT_ADD=31;		//帐号增加失败
	public static int ERROR_ACCOUNT_MODIFY=32;		//帐号修改失败
	
	public Response() {
		super();
	}

	
	public Response(int result) {
		super();
		this.result = result;
	}


	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
	}


	@Override
	public String toJson() throws JsonMappingException,
			JsonGenerationException, IOException {
		return JsonPojoMapper.toJson(this,true);
	}

	@Override
	public void fromJson(String json) throws JsonMappingException,
			JsonGenerationException, IOException {
		Response r=(Response)JsonPojoMapper.fromJson(json, Response.class);
		this.result=r.getResult();
		
	}

}
