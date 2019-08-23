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
package org.chacha.socket.server;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.chacha.log.LogPrn;
import org.chacha.socket.Dispatcher;
import org.chacha.socket.Handler;

/**
 * Selector调度线程
 *	@author: Fully
 */
public class NioDispatcher implements Dispatcher {
	private final LogPrn log=new LogPrn(getClass());
	private Selector sel;
	private Object lock=new Object();
	//请求处理线程池
	private ExecutorService pool=Executors.newCachedThreadPool();	
	
	public NioDispatcher() throws IOException {
		sel=Selector.open();
	}

	@Override
	public void run() {
		while(true){
			try{
				dispatch();
			}catch(IOException e){
				log.error("NioDispatch error:",e);
			}
		}

	}

	private void dispatch() throws IOException{
		sel.select();
		for(Iterator i=sel.selectedKeys().iterator();i.hasNext();){
			SelectionKey sk=(SelectionKey)i.next();
			i.remove();
			SocketChannel sc=(SocketChannel)sk.channel();
			if(!sk.isValid()||sc.socket().isClosed())
				continue;
			ChannelHelper ch=ChannelHelper.getInstance((SocketChannel)sk.channel(),false);
			try{
				int count=ch.read();
				//客户端主动中断连接，关闭channel
				if(count<0){
					sk.cancel();
					ch.close();
					continue;
				}
				if(count<=0)
					continue;
			}catch(IOException e1){
				log.error("dispatch exception "+ch.getSocketChannel().socket().getRemoteSocketAddress(),e1);
				sk.cancel();
				ch.close();
				continue;
			}
			
			Handler rh=new RequestHandler(ch,sk);
			pool.execute(rh);
		}
		synchronized(lock){}
	}

	@Override
	public void register(SelectableChannel ch, int ops) throws IOException {
		synchronized(lock){
			sel.wakeup();
			ch.register(sel, ops);
		}
	}

}
