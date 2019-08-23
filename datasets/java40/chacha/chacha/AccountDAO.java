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
		//�����������޾ʹ������оͺ���
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
	 * ͨ��ID��ѯ�ʺ�
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
	 * ͨ���û�����ѯ�ʺ�
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
	 * ����һ���ʺ�
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
	 * ���������ʺ�
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
	 * ͨ��IDɾ���ʺ�
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
	 * ͨ���û���ɾ���ʺ�
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
	 * �����û��������ʺ�����
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
	 * ��ȡ����˺�
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

