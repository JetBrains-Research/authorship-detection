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
package org.chacha.context;

import java.util.LinkedList;
import java.util.Vector;

import org.chacha.client.command.account.AccountBody;
import org.chacha.client.command.account.AccountEntity;
import org.chacha.client.command.account.AccountNameBody;
import org.chacha.client.command.account.AddAccountRequest;
import org.chacha.client.command.account.DelAccountRequest;
import org.chacha.client.command.account.ModifyAccountRequest;
import org.chacha.client.command.account.QueryAccountRequest;
import org.chacha.client.command.account.QueryAccountResponse;
import org.chacha.client.command.account.QueryAccountResponseBody;
import org.chacha.dao.AccountDAO;
import org.chacha.entity.Response;
import org.chacha.entity.ResponseCode;
import org.chacha.log.LogPrn;
import org.chacha.model.Account;

/**
 * 
 *	@author: Fully
 */
public class AccountManageContextImpl implements AccountManageContext{
	protected final LogPrn log = new LogPrn(getClass());
	private AccountDAO acctDAO;
	
	
	public AccountManageContextImpl() {
		super();
		acctDAO=new AccountDAO();
	}


	@Override
	public Response add(AddAccountRequest acct) {
		AccountBody body=acct.getBody();
		LinkedList<AccountEntity> acctArray=body.getAcctArray();
		Vector<Account> acctV=new  Vector<Account>();
		for(AccountEntity entity:acctArray){
			Account a=new Account();
			a.setUserName(entity.getUserName());
			a.setPasswd(entity.getPasswd());
			a.setAcctAttr(entity.getAcctAttr());
			acctV.add(a);
		}
		boolean result=true;
		if(!acctV.isEmpty()){
			Account[] array=(Account[])acctV.toArray(new Account[0]);
			result=acctDAO.addBatch(array);
		}
		Response r=new Response();
		r.setResult(result?ResponseCode.SUCCESS:ResponseCode.ERROR_ACCOUNT_ADD);
		return r;
	}


	@Override
	public Response update(ModifyAccountRequest modifyReq) {
		Response r=new Response();
		try{
			AccountBody body=modifyReq.getBody();
			LinkedList<AccountEntity> acctArray=body.getAcctArray();
			for(AccountEntity entity:acctArray){
				Account old=acctDAO.getAccountByName(entity.getUserName());
				Account a=new Account();
				a.set_id(old.get_id());
				a.setUserName(entity.getUserName());
				a.setPasswd(entity.getPasswd());
				a.setAcctAttr(entity.getAcctAttr());
				acctDAO.updateAccount(a);
			}
			r.setResult(ResponseCode.SUCCESS);
		}catch(Exception e){
			r.setResult(ResponseCode.ERROR_ACCOUNT_MODIFY);
			log.error("update excpetion:",e);
		}
		return r;
	}


	@Override
	public Response delete(DelAccountRequest delReq) {
		Response r=new Response();
		try{
			AccountNameBody body=delReq.getBody();
			String[] nameArray=body.getNameArray();
			for(String name:nameArray){
				acctDAO.deleteAccount(name);
			}
			r.setResult(ResponseCode.SUCCESS);
		}catch(Exception e){
			r.setResult(ResponseCode.ERROR_ACCOUNT_DEL);
			log.error("delete:",e);
		}
		return r;
	}


	@Override
	public QueryAccountResponse query(QueryAccountRequest queryReq) {
		QueryAccountResponse r=new QueryAccountResponse();
		try{
			AccountNameBody body=queryReq.getBody();
			String[] nameArray=body.getNameArray();
			Account[] accts=acctDAO.getAccounts(nameArray);
			LinkedList<AccountEntity> acctArray=new LinkedList<AccountEntity>();
			for(Account a:accts){
				AccountEntity entity=new AccountEntity();
				entity.setUserName(a.getUserName());
				entity.setPasswd(a.getPasswd());
				entity.setAcctAttr(a.getAcctAttr());
				acctArray.add(entity);
			}
			QueryAccountResponseBody responseBody=new QueryAccountResponseBody(acctArray);
			r.setBody(responseBody);
			r.setResult(ResponseCode.SUCCESS);
		}catch(Exception e){
			r.setResult(ResponseCode.ERROR_ACCOUNT_QUERY);
			log.error("query:",e);
		}
		return r;
	}

}
