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
package org.chacha.mapper.dao;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.chacha.model.Account;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

/**
 * 
 *	@author: Fully
 */
public class AccountMapperDAO extends AbstractMapperDAO{
	AccountDaoHelper<Account> helper=new AccountDaoHelper<Account>();
	private static final String COLLECTION_NAME="account";
	
	public AccountMapperDAO(){
		super();
		helper.setConnection(connection);
		helper.init(Account.class, COLLECTION_NAME);
	}
	
	public static String getCollectionName(){
		return COLLECTION_NAME;
	}
	/**
	 * 通过ID查询帐号
	 * @param id
	 * @return
	 */
	public Account getAccountByID(String id){
		return helper.get(id);
	}
	/**
	 * 通过用户名查询帐号
	 * @param userName
	 * @return
	 */
	public Account getAccountByname(String userName){
		DBCollection coll=connection.getCollection(COLLECTION_NAME);
		QueryBuilder queryBuilder=QueryBuilder.start();
		DBObject query=queryBuilder.put("userName").is(userName).get();
		DBObject entity=coll.findOne(query);
		if(entity!=null)
			return null;
		else
			return Converter.toObject(Account.class, entity);
	}
	/**
	 * 增加帐号
	 * @param entity
	 * @return
	 */
	public Account add(Account entity){
		return helper.add(entity, false);
	}
	/**
	 * 通过ID删除帐号
	 * @param id
	 */
	public void deleteByID(String id){
		helper.remove(id);
	}
	/**
	 * 通过用户名删除帐号
	 * @param userName
	 */
	public void deleteAccount(String userName){
		Account entity=this.getAccountByname(userName);
		this.deleteByID(entity.get_id());
	}
}
