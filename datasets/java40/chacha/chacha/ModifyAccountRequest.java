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
package org.chacha.client.command.account;

import java.io.IOException;
import java.util.LinkedList;

import org.chacha.client.command.JsonConvert;
import org.chacha.client.command.Request;
import org.chacha.util.JsonPojoMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 *修改帐号请求命令
 *	@author: Fully
 */
public class ModifyAccountRequest extends Request implements JsonConvert{
	private static final String CMD="modify";
	private AccountBody body;
	
	
	public ModifyAccountRequest(AccountBody body) {
		super.setOpt(CMD);
		this.body = body;
	}

	
	public ModifyAccountRequest() {
		super.setOpt(CMD);
	}


	public AccountBody getBody() {
		return body;
	}


	public void setBody(AccountBody body) {
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
		ModifyAccountRequest r=(ModifyAccountRequest)JsonPojoMapper.fromJson(json, ModifyAccountRequest.class);
		this.setOpt(r.getOpt());
		this.setBody(r.getBody());
		
	}
	public static void main(String[] args){
		//包含多个帐号的帐号修改消息，带附加属性
		LinkedList<AccountEntity> acctArray=new LinkedList<AccountEntity>();
		for(int i=0;i<3;i++){
			AccountAttr acctAttr=new AccountAttr();
			acctAttr.setAddr("chengdu china");
			acctAttr.setAge(36);
			acctAttr.setSex(1);
			String attr="";//附加属性如果没有可以不构造
			try {
				attr=JsonPojoMapper.toJson(acctAttr, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			AccountEntity entity=new AccountEntity("hejing"+i,"ddddddd",attr);
			acctArray.add(entity);
		}
		AccountBody body=new AccountBody(acctArray);
		ModifyAccountRequest request=new ModifyAccountRequest(body);
		try {
			System.out.println(request.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//包含多个帐号的帐号增加消息，无附加属性
		acctArray=new LinkedList<AccountEntity>();
		for(int i=0;i<3;i++){
			AccountEntity entity=new AccountEntity("hejing"+i,"ddddddd","");
			acctArray.add(entity);
		}
		body=new AccountBody(acctArray);
		request=new ModifyAccountRequest(body);
		try {
			System.out.println(request.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//json转换为pojo
		ModifyAccountRequest r=new ModifyAccountRequest();
		try {
			r.fromJson(request.toJson());
			System.out.println(r.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
