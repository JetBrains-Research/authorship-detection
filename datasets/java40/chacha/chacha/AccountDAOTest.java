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
package org.chacha;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.chacha.dao.AccountDAO;
import org.chacha.model.Account;
import org.chacha.model.AcctAttr;
import org.chacha.util.JsonPojoMapper;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


public class AccountDAOTest{
	AccountDAO dao;
	
	@Before
	public void setUP(){
		dao=new AccountDAO();
	}
	
	@Test
	public void crudTest() throws JsonMappingException, JsonGenerationException, IOException{
		Account entity=new Account();
		entity.setUserName("chacha_one");
		entity.setPasswd("dddddddd");
		AcctAttr acctAttr=new AcctAttr();
		acctAttr.setAddr("china");
		acctAttr.setAge(32);
		acctAttr.setSex(1);
		acctAttr.setAddr("chengdu china");
		entity.setAcctAttr(JsonPojoMapper.toJson(acctAttr, true));
		//增加一个帐号
		Account r=dao.add(entity);
		assertNotNull(r);
		assertNotNull(r.get_id());
		//修改帐号密码
		entity.setPasswd("aaaaaaaa");
		dao.updateAccount(entity);
		//查询帐号密码
		Account a=dao.getAccountByName("chacha_one");
		String newPass=a.getPasswd();
		assertTrue(newPass.equals("aaaaaaaa"));
		//删除帐号
		dao.deleteAccount("chacha_one");
		a=dao.getAccountByName("chacha_one");
		assertNull(a);
	}
	
	//@Test
	public void dupAddTest(){
		Account entity=new Account();
		entity.setUserName("chacha_one");
		entity.setPasswd("dddddddd");
		 
		Account r=dao.add(entity);
		assertNotNull(r.get_id());
		
		Account dup=null;
		try{
			dup=dao.add(entity);
		}catch(Exception e){
			assertNotNull(e);
		}
		assertNull(dup);
		dao.deleteAccount("chacha_one");
	}
}
