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
package org.chacha.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.chacha.exception.ChachaRuntimeException;
import org.chacha.log.LogPrn;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**
 * 
 *	@author: Fully
 *	初始化连接池
 */
public class MongoConnection implements Connection{
	private LogPrn log=new LogPrn(MongoConnection.class);
	private DB db;
	//db的ip
	private String connectionHost;
	//db的端口
	private int conectionPort;
	//db的名称
	private String connectionDatabase;
	//连接串，适用于多db server的情况
	private String connectionDescriptor;
	//连接池大小
	private int connectionPerHost=90;
	//连接池的乘数
	private int threadsAllowedToBlockForConnectionMultiplier=20;
	//连接超时时长
	private int connectTimeout=30000;
	//是否自动重连
	private boolean autoConnectRetry=true;
	
	
	public MongoConnection() {
		super();
	}

	@Override
	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}

	@Override
	public void connect() {
		try{
			Mongo mongo;
			if(connectionDescriptor!=null){
				String[] hosts=connectionDescriptor.split(",");
				List<ServerAddress> addr=new ArrayList<ServerAddress>();
				for(String host:hosts){
					String[] hostPortPair=host.split(":");
					int port=27017;
					if(hostPortPair.length>1){
						try{
							port=Integer.parseInt(hostPortPair[1]);
						}catch(Exception e){
							
						}
						addr.add(new ServerAddress(hostPortPair[0],port));
					}
				}
				mongo=new Mongo(addr);
			}else{
				mongo=new Mongo(connectionHost,conectionPort);
			}
			//连接池参数
			MongoOptions opt=mongo.getMongoOptions();
			opt.setConnectionsPerHost(connectionPerHost);
			opt.setThreadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
			opt.setAutoConnectRetry(autoConnectRetry);
			opt.setConnectTimeout(connectTimeout);
			db=mongo.getDB(connectionDatabase);
			log.info("MongoConection init success!");
		}catch(UnknownHostException e){
			log.error("init exception:",e);
			throw new ChachaRuntimeException(e);
		}
		
	}

	public void setConnectionHost(String connectionHost) {
		this.connectionHost = connectionHost;
	}

	public void setConectionPort(int conectionPort) {
		this.conectionPort = conectionPort;
	}

	public void setConnectionDatabase(String connectionDatabase) {
		this.connectionDatabase = connectionDatabase;
	}

	public void setConnectionDescriptor(String connectionDescriptor) {
		this.connectionDescriptor = connectionDescriptor;
	}

	public void setConnectionPerHost(int connectionPerHost) {
		this.connectionPerHost = connectionPerHost;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}

}
