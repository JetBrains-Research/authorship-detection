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

import java.util.LinkedList;
import java.util.List;

import net.karmafiles.ff.core.tool.dbutil.converter.Converter;

import org.bson.types.ObjectId;
import org.chacha.dao.AccountDAO;
import org.chacha.dao.Connection;
import org.chacha.mapper.dao.AccountMapperDAO;
import org.chacha.model.Account;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

public class PerformanceAccountDAOTest {
	AccountDAO dao;
	Connection connection;
	
	@Before
	public void setUP(){
		dao=new AccountDAO();
		connection=dao.getConnection();
	}
	/**
	 * 单条插入性能测试
	 */
	@Test
	public void performanceTest(){
		DBCollection coll=connection.getCollection(dao.getCollectionName());
		for(int i=0;i<1;i++){
			//coll.drop();
			long seq=coll.count();
			long begin=System.currentTimeMillis();
			for(long j=seq;j<seq+1000000;j++){
				Account entity=new Account();
				entity.setUserName("chacha"+String.valueOf(j));
				entity.setPasswd("dddddddd");
				dao.add(entity);
			}
			long end=System.currentTimeMillis();
			System.out.println("performanceTest costs="+(end-begin));
			
		}
		long begin=System.currentTimeMillis();
		Account acct=dao.getAccountByName("chacha500000");
		long end=System.currentTimeMillis();
		System.out.println("getAccountByName costs="+(end-begin));
	}
	
	/**
	 * 批量插入性能测试
	 */
	//@Test
	public void performanceBatchTest(){
		DBCollection coll=connection.getCollection(dao.getCollectionName());
		coll.setWriteConcern(WriteConcern.NONE);
		for(int i=0;i<5;i++){
			coll.drop();
			List<DBObject> accounts=new LinkedList<DBObject>();
			long begin=System.currentTimeMillis();
			for(long j=0;j<1000000;j++){
				BasicDBObject entity=new BasicDBObject();
				entity.put("userName", "chacha"+String.valueOf(j));
				entity.put("passwd", "dddddddd");
				accounts.add(entity);
				if(accounts.size()%50000==0){
					coll.insert(accounts);
					accounts.clear();
				}
			}
			long end=System.currentTimeMillis();
			System.out.println("performanceBatchTest costs="+(end-begin));
		}
	}
	/**
	 * 基于mongomapper插入性能测试
	 */
	//@Test
	public void performanceMapperTest(){
		DBCollection coll=connection.getCollection(dao.getCollectionName());
		coll.setWriteConcern(WriteConcern.NONE);
		for(int i=0;i<2;i++){
			coll.drop();
			long begin=System.currentTimeMillis();
			for(long j=0;j<1000000;j++){
				Account entity=new Account();
				entity.setUserName("chacha"+String.valueOf(j));
				entity.setPasswd("dddddddd");
				entity.set_id(new ObjectId().toStringBabble());
				DBObject obj=Converter.toDBObject(entity);
				coll.insert(obj);
			}
			long end=System.currentTimeMillis();
			System.out.println("performanceMapperTest costs="+(end-begin));
		}
	}
	/**
	 * 通过mapperdao插入性能测试
	 */
	//@Test
	public void performanceMapperDaoTest(){
		DBCollection coll=connection.getCollection(dao.getCollectionName());
		AccountMapperDAO mapperDao=new AccountMapperDAO();
		for(int i=0;i<2;i++){
			coll.drop();
			long begin=System.currentTimeMillis();
			for(long j=0;j<1000000;j++){
				Account entity=new Account();
				entity.setUserName("chacha"+String.valueOf(j));
				entity.setPasswd("dddddddd");
				//entity.setCreated(new Date());
				//entity.setModified(new Date());
				mapperDao.add(entity);
			}
			long end=System.currentTimeMillis();
			System.out.println("performanceMapperDaoTest costs="+(end-begin));
		}
	}
}
