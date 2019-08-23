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
package org.chacha.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 *	@author: Fully
 *	初始化连接池参数并且进行封装
 */
public class Config {
	private static String dbServerIP="127.0.0.1";
    private static int dbServerPort=27017;
    private static String dbName="chacha";
    private static String dbUserName="chacha";
    private static String dbPassword="cha123";
    private static String connectionDescriptor=null;
    private static int connectionsPerHost=90;
    private static int threadsAllowedToBlockForConnectionMultiplier=20;
    private static int connectTimeout=30000;
    private static boolean autoConnectRetry=true;
    private static int serverPort=9001;
    private static int tokenLifeTime=3600;
    
	static{
		try{
			Properties prop = new Properties();
			InputStream is = Config.class.getClassLoader().getResourceAsStream("conf\\chacha.properties");
			prop.load(is);
			dbServerIP=prop.getProperty("dbServerIP");
			dbServerPort=Integer.parseInt(prop.getProperty("dbServerPort"));
			dbName=prop.getProperty("dbName");
			dbUserName=prop.getProperty("dbUserName");
			dbPassword=prop.getProperty("dbPassword");
			connectionDescriptor=prop.getProperty("connectionDescriptor");
			connectionsPerHost=Integer.parseInt((String)prop.getProperty("poolSize"));
			threadsAllowedToBlockForConnectionMultiplier=Integer.parseInt((String)prop.getProperty("threadsAllowedToBlockForConnectionMultiplier"));
			connectTimeout=Integer.parseInt((String)prop.getProperty("connectTimeout"));
			autoConnectRetry=Boolean.parseBoolean((String)prop.getProperty("autoConnectRetry"));
			//serverPort=Integer.parseInt(prop.getProperty("ServerPort"));
			//tokenLifeTime=Integer.parseInt(prop.getProperty("tokenLifeTime"));
		}catch (Exception e) {
			throw new ExceptionInInitializerError("chacha server argument init error!");
		}
	}

	public static String getDbServerIP() {
		return dbServerIP;
	}

	public static void setDbServerIP(String dbServerIP) {
		Config.dbServerIP = dbServerIP;
	}

	public static int getDbServerPort() {
		return dbServerPort;
	}

	public static void setDbServerPort(int dbServerPort) {
		Config.dbServerPort = dbServerPort;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		Config.dbName = dbName;
	}

	public static String getDbUserName() {
		return dbUserName;
	}

	public static void setDbUserName(String dbUserName) {
		Config.dbUserName = dbUserName;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		Config.dbPassword = dbPassword;
	}

	public static String getConnectionDescriptor() {
		return connectionDescriptor;
	}

	public static void setConnectionDescriptor(String connectionDescriptor) {
		Config.connectionDescriptor = connectionDescriptor;
	}

	public static int getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public static void setConnectionsPerHost(int connectionsPerHost) {
		Config.connectionsPerHost = connectionsPerHost;
	}

	public static int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public static void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		Config.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public static int getConnectTimeout() {
		return connectTimeout;
	}

	public static void setConnectTimeout(int connectTimeout) {
		Config.connectTimeout = connectTimeout;
	}

	public static boolean isAutoConnectRetry() {
		return autoConnectRetry;
	}

	public static void setAutoConnectRetry(boolean autoConnectRetry) {
		Config.autoConnectRetry = autoConnectRetry;
	}

	public static int getServerPort() {
		return serverPort;
	}

	public static void setServerPort(int serverPort) {
		Config.serverPort = serverPort;
	}

	public static int getTokenLifeTime() {
		return tokenLifeTime;
	}

	public static void setTokenLifeTime(int tokenLifeTime) {
		Config.tokenLifeTime = tokenLifeTime;
	}
	
	
}
