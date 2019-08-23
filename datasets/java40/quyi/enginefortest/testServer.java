package com.nercis.isscp.engine.bupt.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.nercis.isscp.engine.bupt.impl.TSOperateImpl;
import com.nercis.isscp.ts.TSOperate;
import com.nercis.isscp.util.SingleRecordInfoConsumer;

public class testServer {
//	@Test
	public static void main(String args[]) throws Exception{
		//new Thread(new SingleRecordInfoConsumer("D:\\批量下发统计.xls")).start();
		//System.out.println("开启线程写文件");
		TServerTransport serverTransport;
		int listenerPort = 7778;
		try {
			serverTransport = new TServerSocket(listenerPort);
			final TSOperate.Processor processor = new TSOperate.Processor(new TSOperateImpl());
			final TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
			System.out.println("start listen on 7778");
			server.serve();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
