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
package org.chacha.socket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.chacha.client.CommandService;
import org.chacha.client.SocketServiceImpl;
import org.chacha.client.command.auth.LoginBody;
import org.chacha.client.command.auth.LoginRequest;
import org.chacha.client.command.auth.LoginResponse;
import org.chacha.client.command.auth.TokenBody;
import org.chacha.client.command.auth.TokenLogout;
import org.chacha.client.command.auth.TokenRequest;
import org.chacha.exception.ChachaException;

/**
 * 
 *	@author: Fully
 */
public class DriverThread implements Runnable{
	private String serverIP="127.0.0.1";
	private int port=9001;
	private String userName="chacha499999";
	private String passwd="dddddddd";
	private CountDownLatch count;
	
	
	public DriverThread(CountDownLatch count) {
		super();
		this.count = count;
	}


	public String getServerIP() {
		return serverIP;
	}


	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public CountDownLatch getCount() {
		return count;
	}


	public void setCount(CountDownLatch count) {
		this.count = count;
	}


	@Override
	public void run() {
		List<InetSocketAddress> addList=new ArrayList<InetSocketAddress>();
		InetSocketAddress addr=new InetSocketAddress(serverIP,port);
		addList.add(addr);
		//密码认证示例
		CommandService cmd=new SocketServiceImpl(addList.toArray(new InetSocketAddress[0]));
		LoginBody body=new LoginBody(userName,passwd,LoginBody.ACTION_TOKEN);
		LoginRequest request=new LoginRequest(body);
		try{
			LoginResponse r=cmd.loginPassword(request);
			assertNotNull(r);
			assertTrue(r.getResult()==LoginResponse.SUCCESS);
			System.out.println(Thread.currentThread().getName()+" "+r.getToken()+" "+userName);
			
			//令牌认证示例
			TokenBody tokenBody=new TokenBody(r.getToken());
			TokenRequest tokenRequest=new TokenRequest(tokenBody);
			LoginResponse tokenR=cmd.loginToken(tokenRequest);
			assertNotNull(tokenR);
			System.out.println(Thread.currentThread().getName()+" "+r.getToken()+" "+userName+" "+tokenR.getResult());
			assertTrue(tokenR.getResult()==LoginResponse.SUCCESS);
			assertTrue(tokenR.getToken().equals(userName));
			
			//令牌销毁示例
			TokenBody logoutBody=new TokenBody(r.getToken());
			TokenLogout logout=new TokenLogout(logoutBody);
			LoginResponse logoutR=cmd.logout(logout);
			assertNotNull(logoutR);
			assertTrue(logoutR.getResult()==LoginResponse.SUCCESS);
			assertNull(logoutR.getToken());
		}catch(ChachaException e){
			e.printStackTrace();
		}finally{
			count.countDown();
		}
		
	}

}
