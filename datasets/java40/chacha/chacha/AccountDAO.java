package org.chacha.dao.impl;
import java.util.ArrayList;
import java.util.List;

import org.chacha.dao.AbstractDAO;
import org.chacha.dao.DAO;
import org.chacha.model.Account;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteConcern;

public class AccountDAO extends AbstractDAO implements DAO{
	private static final String COLLECTION_ACCOUNT="account";
	
	static{
		//创建索引，无就创建，有就忽略
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		coll.ensureIndex(new BasicDBObject("userName",1), "idx_name", true);
	}
	
	public AccountDAO() {
		super();
	}

	@Override
	public String getCollectionName() {
		return COLLECTION_ACCOUNT;
	}
	/**
	 * 通过ID查询帐号
	 * @param id
	 * @return
	 */
	public Account getAccountByID(String id){
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		DBObject object=coll.findOne(super.idQuery(id));
		if(object!=null){
			Account acct=new Account();
			acct.fromDbObject(object);
			return acct;
		}else
			return null;
	}
	/**
	 * 通过用户名查询帐号
	 * @param userName
	 * @return
	 */
	public Account getAccountByName(String userName){
		DBObject entity=queryUserName(userName);
		if(entity!=null){
			Account acct=new Account();
			acct.fromDbObject(entity);
			return acct;
		}else
			return null;
	}

	private DBObject queryUserName(String userName) {
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		QueryBuilder builder=QueryBuilder.start();
		DBObject query=builder.put("userName").is(userName).get();
		DBObject entity=coll.findOne(query);
		return entity;
	}
	/**
	 * 增加一个帐号
	 * @param entity
	 * @return
	 */
	public Account add(Account entity){
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		entity.set_id(super.generateID());
		coll.insert(entity.toDbObject(), WriteConcern.SAFE);
		return entity;
	}
	/**
	 * 批量增加帐号
	 * @param entitys
	 * @return
	 */
	public boolean addBatch(Account[] entitys){
		try{
			DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
			List<DBObject> acctList=new ArrayList<DBObject>();
			for(Account entity:entitys){
				entity.set_id(super.generateID());
				DBObject o=entity.toDbObject();
				acctList.add(o);
			}
			coll.insert(acctList,WriteConcern.SAFE);
		}catch(Exception e){
			log.error("addBatch:",e);
			return false;
		}
		return true;
	}
	/**
	 * 通过ID删除帐号
	 * @param id
	 */
	public void deleteByID(String id){
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		DBObject object=coll.findOne(super.idQuery(id));
		if(object!=null){
			coll.remove(object, WriteConcern.SAFE);
		}
	}
	/**
	 * 通过用户名删除帐号
	 * @param userName
	 */
	public void deleteAccount(String userName){
		DBObject entity=queryUserName(userName);
		if(entity!=null){
			DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
			coll.remove(entity, WriteConcern.SAFE);
		}
	}
	/**
	 * 根据用户名更改帐号数据
	 * @param acct
	 */
	public void updateAccount(Account acct){
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		QueryBuilder builder=QueryBuilder.start();
		DBObject query=builder.put("userName").is(acct.getUserName()).get();
		coll.setWriteConcern(WriteConcern.SAFE);
		coll.update(query, acct.toDbObject());
	}

	/**
	 * 获取多个账号
	 * @param nameArray
	 * @return
	 */
	public Account[] getAccounts(String[] nameArray) {
		List<Account> result=new ArrayList<Account>();
		DBCollection coll=connection.getCollection(COLLECTION_ACCOUNT);
		QueryBuilder queryBuilder = QueryBuilder.start();
		DBObject query=queryBuilder.put("userName").in(nameArray).get();
		DBCursor cursor=null;
		try{
			cursor=coll.find(query);
			while(cursor.hasNext()){
				DBObject obj=cursor.next();
				Account a=new Account();
				a.fromDbObject(obj);
				result.add(a);
			}
		}finally{
			if(cursor!=null)
				cursor.close();
		}
		return (Account[])result.toArray(new Account[0]);
	}
}

