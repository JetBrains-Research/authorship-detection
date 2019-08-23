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


import java.io.InputStream;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.chacha.config.Config;
import org.chacha.log.LogPrn;

import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

/**
 * 连接池实现类
 *	@author: Fully
 */
public class AbstractDAO {
    protected final LogPrn log = new LogPrn(getClass());
    protected static String dbServerIP="127.0.0.1";
    protected static int dbServerPort=27017;
    protected static String dbName="chacha";
    protected static String dbUserName="chacha";
    protected static String dbPassword="cha123";
    protected static String connectionDescriptor=null;
    private static int connectionsPerHost=90;
    private static int threadsAllowedToBlockForConnectionMultiplier=20;
    private static int connectTimeout=30000;
    private static boolean autoConnectRetry=true;
    protected static MongoConnection connection=new MongoConnection();
    
	static{
		try {
			Properties prop = new Properties();
			InputStream is = AbstractDAO.class.getClassLoader().getResourceAsStream("conf\\chacha.properties");
			prop.load(is);
			dbServerIP=Config.getDbServerIP();
			dbServerPort=Config.getDbServerPort();
			dbName=Config.getDbName();
			dbUserName=Config.getDbUserName();
			dbPassword=Config.getDbPassword();
			connectionDescriptor=Config.getConnectionDescriptor();
			connectionsPerHost=Config.getConnectionsPerHost();
			threadsAllowedToBlockForConnectionMultiplier=Config.getThreadsAllowedToBlockForConnectionMultiplier();
			connectTimeout=Config.getConnectTimeout();
			autoConnectRetry=Config.isAutoConnectRetry();
			
	        connection.setConnectionDatabase(dbName);
	        connection.setConnectionHost(dbServerIP);
	        connection.setConectionPort(dbServerPort);
	        connection.setConnectionDescriptor(connectionDescriptor);
	        connection.setConnectionPerHost(connectionsPerHost);
	        connection.setThreadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
	        connection.setConnectTimeout(connectTimeout);
	        connection.setAutoConnectRetry(autoConnectRetry);
	        connection.connect();
		} catch (Exception e) {
			throw new ExceptionInInitializerError("mongodb argument init error!");
		}
	}

	public AbstractDAO() {
		super();
	}

	public Connection getConnection() {
		return connection;
	}
	
    protected DBObject idQuery(String id) {
        return QueryBuilder.start("_id").is(id).get();
    }
    
    protected String generateID(){
    	return new ObjectId().toStringBabble();
    }
	
}
