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

import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;


public class MongodbOperationTest {
	private final String SERVERIP="192.168.0.13";
	private final int PORT=27017;
	private final String DBNAME="chacha";
	
	DB db;
	Mongo m;
	
	@Before
	public void setUP(){
		try {
			m=new Mongo(SERVERIP,PORT);
			db=m.getDB(DBNAME);
			Set<String> colls=db.getCollectionNames();
			for(String s:colls){
				System.out.println(s);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void insertTest(){
		System.out.println("insertTest");
		DBCollection coll=db.getCollection("test");
		BasicDBObject doc=new BasicDBObject();
		doc.put("name", "MongoDB");
		doc.put("type", "database");
		doc.put("count", 1);
		BasicDBObject info=new BasicDBObject();
		info.put("x",200);
		info.put("y", 102);
		doc.put("info",info);
		WriteResult w=coll.insert(doc);
		assertTrue(w.getLastError().ok());
		
		DBObject myDoc=coll.findOne();
		System.out.println(myDoc);
		
		long begin=System.currentTimeMillis();
		List<DBObject> list=new ArrayList<DBObject>();
		for(int i=0;i<10000;i++){
			BasicDBObject bo=new BasicDBObject().append("i",i);
			list.add(bo);
		}
		coll.insert(list);
		long end=System.currentTimeMillis();
		System.out.println("batchinsert cost="+(end-begin));
		
		begin=System.currentTimeMillis();
		coll.setWriteConcern(WriteConcern.NONE);
		for(int i=0;i<10000;i++){
			BasicDBObject bo=new BasicDBObject().append("i",i);
			coll.insert(bo);
		}
		end=System.currentTimeMillis();
		System.out.println("singleinsert cost="+(end-begin));
		
	}
	
	@Test 
	public void queryTest(){
		System.out.println("queryTest");
		DBCollection coll=db.getCollection("test");
		DBCursor cursor=coll.find();
		try{
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}finally{
			System.out.println(cursor.count());
			cursor.close();
		}
		
		BasicDBObject query=new BasicDBObject();
		query.put("i",71);
		cursor=coll.find(query);
		try{
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}finally{
			System.out.println(cursor.count());
			cursor.close();
		}
		
		query=new BasicDBObject();
		query.put("i",new BasicDBObject("$gt",9000));
		cursor=coll.find(query);
		try{
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}finally{
			System.out.println(cursor.count());
			cursor.close();
		}
		
		query=new BasicDBObject();
		query.put("i",new BasicDBObject("$gt",9000).append("$lte", 9010));
		cursor=coll.find(query);
		try{
			while(cursor.hasNext()){
				System.out.println(cursor.next());
			}
		}finally{
			System.out.println(cursor.count());
			cursor.close();
		}
	}
}
