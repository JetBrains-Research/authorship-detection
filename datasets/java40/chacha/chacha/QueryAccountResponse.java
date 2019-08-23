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
import org.chacha.client.command.Response;
import org.chacha.util.JsonPojoMapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 帐号查询回应消息
 *	@author: Fully
 */
public class QueryAccountResponse extends Response implements JsonConvert{
	private QueryAccountResponseBody body;	//查询回来的帐号数组

	public QueryAccountResponse() {
		super();
	}

	public QueryAccountResponse(int result) {
		super(result);
	}

	public QueryAccountResponse(QueryAccountResponseBody body) {
		super();
		this.body = body;
	}
	
	public QueryAccountResponse(int result,QueryAccountResponseBody body) {
		super(result);
		this.body = body;
	}

	public QueryAccountResponseBody getBody() {
		return body;
	}

	public void setBody(QueryAccountResponseBody body) {
		this.body = body;
	}
	@Override
	public void fromJson(String json) throws JsonMappingException,
			JsonGenerationException, IOException {
		QueryAccountResponse r=(QueryAccountResponse)JsonPojoMapper.fromJson(json, QueryAccountResponse.class);
		this.result=r.getResult();
		this.setBody(r.getBody());
	}
	public static void main(String[] args){
		LinkedList<AccountEntity> acctArray=new LinkedList<AccountEntity>();
		for(int i=0;i<3;i++){
			AccountEntity entity=new AccountEntity("hejing"+i,"ddddddd","");
			acctArray.add(entity);
		}
		AccountEntity[] acct=(AccountEntity[])acctArray.toArray(new AccountEntity[0]);
		QueryAccountResponseBody body=new QueryAccountResponseBody(acctArray);
		QueryAccountResponse r=new QueryAccountResponse(Response.SUCCESS,body);
		try {
			System.out.println(r.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		QueryAccountResponse from=new QueryAccountResponse();
		try {
			from.fromJson(r.toJson());
			System.out.println(from.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
