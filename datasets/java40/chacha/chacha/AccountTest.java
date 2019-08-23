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
package org.chacha.socket;

import static org.junit.Assert.assertTrue;

import java.net.InetSocketAddress;
import java.util.LinkedList;

import org.chacha.client.CommandService;
import org.chacha.client.SocketServiceImpl;
import org.chacha.client.command.Response;
import org.chacha.client.command.account.AccountBody;
import org.chacha.client.command.account.AccountEntity;
import org.chacha.client.command.account.AccountNameBody;
import org.chacha.client.command.account.AddAccountRequest;
import org.chacha.client.command.account.DelAccountRequest;
import org.chacha.client.command.account.ModifyAccountRequest;
import org.chacha.client.command.account.QueryAccountRequest;
import org.chacha.client.command.account.QueryAccountResponse;
import org.chacha.exception.ChachaException;
import org.junit.Before;
import org.junit.Test;

/**
 * 帐号管理接口单元测试
 *	@author: Fully
 */
public class AccountTest {
	CommandService service;
	private String serverIP="127.0.0.1";
	private int port=9001;
	
	@Before
	public void setUP(){
		InetSocketAddress addr=new InetSocketAddress(serverIP,port);
		service=new SocketServiceImpl(addr);
	}
	
	/**
	 * 增删改查帐号单元测试
	 */
	@Test
	public void crudAccountTest(){
		//批量新增10个帐号
		long begin=System.currentTimeMillis();
		String[] userNames=new String[10];
		LinkedList<AccountEntity> acctArray=new LinkedList<AccountEntity>();
		for(int i=0;i<10;i++){
			userNames[i]="chacha"+i;
			AccountEntity entity=new AccountEntity(userNames[i],"dddddddd","");
			acctArray.add(entity);
		}
		AccountBody body=new AccountBody(acctArray);
		AddAccountRequest request=new AddAccountRequest(body);
		try{
			Response r=service.addAccount(request);
			assertTrue(r.getResult()==Response.SUCCESS);
		}catch(ChachaException e){
			e.printStackTrace();
		}
		System.out.println("add cost="+(System.currentTimeMillis()-begin));
		//查询新增帐号
		AccountNameBody queryBody=new AccountNameBody(userNames);
		QueryAccountRequest qRequest=new QueryAccountRequest(queryBody);
		try{
			QueryAccountResponse qResponse=service.queryAccount(qRequest);
			assertTrue(qResponse.getResult()==Response.SUCCESS);
			assertTrue(qResponse.getBody().getAcctArray().size()==10);
		}catch(ChachaException e){
			e.printStackTrace();
		}
		//修改帐号密码
		for(AccountEntity entity:acctArray){
			entity.setPasswd("ffffffff");
		}
		body=new AccountBody(acctArray);
		ModifyAccountRequest modifyRequest=new ModifyAccountRequest(body);
		try{
			Response r=service.modifyAccount(modifyRequest);
			assertTrue(r.getResult()==Response.SUCCESS);
		}catch(ChachaException e){
			e.printStackTrace();
		}
		//查询帐号是否修改成功
		queryBody=new AccountNameBody(userNames);
		qRequest=new QueryAccountRequest(queryBody);
		try{
			QueryAccountResponse qResponse=service.queryAccount(qRequest);
			assertTrue(qResponse.getResult()==Response.SUCCESS);
			assertTrue(qResponse.getBody().getAcctArray().size()==10);
			assertTrue(qResponse.getBody().getAcctArray().get(0).getPasswd().equals("ffffffff"));
		}catch(ChachaException e){
			e.printStackTrace();
		}
		//删除之前新增的帐号
		AccountNameBody delBody=new AccountNameBody(userNames);
		DelAccountRequest dRequest=new  DelAccountRequest(delBody);
		try{
			Response r=service.delAccount(dRequest);
			assertTrue(r.getResult()==Response.SUCCESS);
		}catch(ChachaException e){
			e.printStackTrace();
		}
		//查询帐号是否删除成功
		queryBody=new AccountNameBody(userNames);
		qRequest=new QueryAccountRequest(queryBody);
		try{
			QueryAccountResponse qResponse=service.queryAccount(qRequest);
			assertTrue(qResponse.getResult()==Response.SUCCESS);
			assertTrue(qResponse.getBody().getAcctArray().size()==0);
		}catch(ChachaException e){
			e.printStackTrace();
		}
	}
}
