package org.chacha.dao.impl;

import java.util.LinkedList;

import org.chacha.dao.AbstractDAO;
import org.chacha.dao.DAO;
import org.chacha.model.LoginToken;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteConcern;

public class LoginTokenDAO extends AbstractDAO implements DAO {
	private static final String COLLECTION_NAME = "token";
	// ��������
	static {
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		coll.ensureIndex(new BasicDBObject("userName", 1), "idx_name", true);
		coll.ensureIndex(new BasicDBObject("token", 1), "idx_token", false);
	}

	public String getCollectionName() {
		return COLLECTION_NAME;
	}

	/**
	 * �������Ʋ�ѯDBObject
	 * 
	 * @param token
	 * @return
	 */
	private DBObject queryTokenByToken(String token) {
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		QueryBuilder query = QueryBuilder.start();
		DBObject db = query.put("token").is(token).get();
		DBObject object = coll.findOne(db);
		return object;
	}

	/**
	 * �����û�����ѯ
	 * 
	 * @param userName
	 * @return
	 */
	private DBObject queryTokenByuserName(String userName) {
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		QueryBuilder Query = QueryBuilder.start();
		DBObject db = Query.put("userName").is(userName).get();
		DBObject object = coll.findOne(db);
		return object;
	}

	/**
	 * �������ƻ�ȡLoginTokenʵ��
	 * @param token
	 * @return
	 */
	public LoginToken getTokenByToken(String token) {
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		DBObject obj = queryTokenByToken(token);
		DBObject object = coll.findOne(obj);
		if (object != null) {
			LoginToken loginToken = new LoginToken();
			loginToken.fromDbObject(object);
			return loginToken;
		} else
			return null;
	}
	
	/**
	 * �����û�����ȡlogintoken
	 * @param userName
	 * @return
	 */
	public LoginToken getTokenByuserName(String userName){
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		DBObject obj = queryTokenByuserName(userName);
		DBObject object=coll.findOne(obj);
		if(object!=null){
			LoginToken loginToken = new LoginToken();
			loginToken.fromDbObject(object);
			return loginToken;
		}else
			return null;
	}
	
	/**
	 * ���ʵ��logintoken
	 * @param entity
	 * @return
	 */
	public LoginToken addToken(LoginToken entity){
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		LoginToken token=getTokenByuserName(entity.getUserName());
		if(token==null){
			entity.set_id(super.generateID());
			coll.insert(entity.toDbObject(),WriteConcern.NONE);
		}else{
			token.setToken(entity.getToken());
			token.setTokenLifeTime(entity.getTokenLifeTime());
			updateToken(token);
		}
		return entity;
	}
	
	/**
	 * �޸�����
	 * @param entity
	 */
	public void updateToken(LoginToken entity) {
		DBCollection coll = connection.getCollection(COLLECTION_NAME);
		coll.setWriteConcern(WriteConcern.NONE);
		QueryBuilder Query = QueryBuilder.start();
		DBObject object = Query.put("userName").is(entity.getUserName()).get();
		coll.update(object, entity.toDbObject());		
	}
	
	/**
	 * ��������ɾ������
	 * @param token
	 */
	public void deleteToken(String token){
		DBObject entity=this.queryTokenByToken(token);
		if(entity!=null){
			DBCollection coll=connection.getCollection(COLLECTION_NAME);
			coll.remove(entity,WriteConcern.NONE);
		}
	}
	/**
	 * ����LoginTokenʵ��ɾ������
	 * @param loginToken
	 */
	public void deleteToken(LoginToken loginToken){
		if(loginToken!=null){
			DBCollection coll=connection.getCollection(COLLECTION_NAME);
			coll.remove(loginToken.toDbObject(),WriteConcern.NONE);
		}
	}
	
	/**
	 * ��ҳ��ѯ
	 * @param lastId ��ҳ����ID
	 * @param pageNum ÿҳ��ҳ��
	 * @return
	 */
	public LinkedList<LoginToken> getPage(String lastId,int pageNum){
		DBCollection coll=connection.getCollection(COLLECTION_NAME);
		QueryBuilder queryBuilder = QueryBuilder.start();
		DBObject query=queryBuilder.put("_id").greaterThan(lastId).get();
		DBObject orderBy=new BasicDBObject("_id",1);//����_id��������
		DBCursor cursor=coll.find(query).sort(orderBy).limit(pageNum);
		LinkedList<LoginToken> result=new LinkedList<LoginToken>();
		while(cursor.hasNext()){
			DBObject obj=cursor.next();
			LoginToken token=new LoginToken();
			token.fromDbObject(obj);
			result.add(token);
		}
		return result;
	}
}
