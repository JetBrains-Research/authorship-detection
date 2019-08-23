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
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 
 *	@author: Fully
 */
public class ChannelHelper {
	protected SocketChannel sc;
	protected ByteBuffer requestBB;
	private static int requestBBSize=1024;
	private static int WriteCharBufferSize=1024;
	
	public ChannelHelper(SocketChannel sc,boolean block) throws IOException{
		this.sc=sc;
		sc.configureBlocking(block);
	}
	/**
	 * 
	 * @param sc    socket通道
	 * @param block 是否阻塞
	 * @return
	 * @throws IOException
	 */
	public static ChannelHelper getInstance(SocketChannel sc,boolean block) throws IOException{
		ChannelHelper ch=new ChannelHelper(sc,block);
		ch.requestBB=ByteBuffer.allocate(requestBBSize);
		return ch;
	}
	
	public int read() throws IOException {
		//如果剩余空间小于5%,分配更大空间
		resizeRequestBB(requestBBSize/20);
		return sc.read(requestBB);
	}
	/**
	 * 调整buffer大小
	 * @param i
	 */
	private void resizeRequestBB(int remain) {
		if(requestBB.remaining()<remain){
			ByteBuffer bb=ByteBuffer.allocate(requestBB.capacity()*2);
			requestBB.flip();
			bb.put(requestBB);
			requestBB=bb;
		}
		
	}
	public void close() throws IOException {
		sc.close();
		
	}
	
	public SocketChannel getSocketChannel(){
		return sc;
	}
	public ByteBuffer getReadBuf() {
		return requestBB;
	}
	public int write(String jsonReponse, Charset utf8) throws IOException {
		CharBuffer wcb=CharBuffer.allocate(WriteCharBufferSize);
		while(true){
			try{
				wcb.put(jsonReponse);
				break;
			}catch(BufferOverflowException ex){
				assert(wcb.capacity()<(1<<16));
				wcb=CharBuffer.allocate(wcb.capacity()*2);
				continue;
			}
		}
		wcb.flip();
		ByteBuffer wbb=utf8.encode(wcb);
		return write(wbb);
	}
	private int write(ByteBuffer wbb) throws IOException {
		return sc.write(wbb);
	}
}
