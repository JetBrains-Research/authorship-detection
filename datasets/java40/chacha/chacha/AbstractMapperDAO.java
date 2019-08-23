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


import java.io.InputStream;
import java.util.Properties;

import net.karmafiles.ff.core.tool.dbutil.ConnectionImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chacha.log.LogPrn;

/**
 * 
 *	@author: Fully
 */
public class AbstractMapperDAO {
	protected final LogPrn log = new LogPrn(getClass());
    protected static String dbServerIP="127.0.0.1";
    protected static int dbServerPort=27017;
    protected static String dbName="chacha";
    protected static String connectionDescriptor=null;
    protected static ConnectionImpl connection=new ConnectionImpl();
    
	static{
		try {
			Properties prop = new Properties();
			InputStream is = AbstractMapperDAO.class.getClassLoader().getResourceAsStream("chacha.properties");
			prop.load(is);
			dbServerIP=prop.getProperty("dbServerIP");
			dbServerPort=Integer.parseInt(prop.getProperty("dbServerPort"));
			dbName=prop.getProperty("dbName");
			connectionDescriptor=prop.getProperty("connectionDescriptor");
			
	        connection.setConnectionDatabase(dbName);
	        connection.setConnectionHost(dbServerIP);
	        connection.setConnectionPort(dbServerPort);
	        connection.setConnectionDescriptor(connectionDescriptor);
	        connection.connect();
		} catch (Exception e) {
			throw new ExceptionInInitializerError("mongodb argument init error!");
		}
	}

	public AbstractMapperDAO() {
		super();
	}

	public static ConnectionImpl getConnection() {
		return connection;
	}
	
}
