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
package org.chacha.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Random;

import org.chacha.exception.ChachaException;

/**
 *  socket请求发送实现
 *	@author: Fully
 */
public class SocketSender implements Sender{
	private static Random r=new Random();
	private static int BUFFERSIZE=4096;
	private InetSocketAddress[] hosts; //服务器地址集合，支持集群
	
	
	public SocketSender(InetSocketAddress[] hosts) {
		super();
		this.hosts = hosts;
	}

	public SocketSender(InetSocketAddress host){
		this(new InetSocketAddress[]{host});
	}
	
	@Override
	public String syncRequest(String request) throws ChachaException {
		if(hosts==null||hosts.length==0)
			throw new ChachaException("No server hosts information");
		InetSocketAddress hostAddr=hosts[r.nextInt(hosts.length)];
		String response=null;
		SocketChannel channel=null;
		try{
			channel=SocketChannel.open(hostAddr);
			Charset charset=Charset.forName("utf-8");
			channel.write(charset.encode(request));
			ByteBuffer buffer=ByteBuffer.allocate(BUFFERSIZE);
			if(channel.read(buffer)!=-1){
				buffer.flip();
				CharBuffer cb=charset.decode(buffer);
				buffer.clear();//清空缓存
				response=cb.toString();
			}
		}catch(IOException e){
			throw new ChachaException(e);
		}finally{
			if(channel!=null)
				try{
					channel.close();
				}catch(IOException e){
					e.printStackTrace();
				}
		}
		return response;
	}

}
