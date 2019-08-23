package com.nercis.isscp.engine.bupt.client;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.nercis.isscp.engine.Engine;
import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.util.SearchRecordInfo;
import com.nercis.isscp.util.SearchRecordInfoQueue;

public class SearchAllEngineThread implements Runnable {

	private String engine_address;
	private int engine_port;
	private String number;

	public SearchAllEngineThread(String engineAddress, int enginePort, String number) {
		this.engine_address = engineAddress;
		this.engine_port = enginePort;
		this.number = number;
	}

	@Override
	public void run() {
		// TODO 记录查询开始时间
		boolean isSearchSuccess = false;
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			long startTime = System.currentTimeMillis();
			List<Engine> engineList = client.searchAllEng();
			long finishTime = System.currentTimeMillis();
			transport.close();
			if (engineList != null && engineList.size() != 0) {
				isSearchSuccess = true;
			}
			String result = "失败";
			if (isSearchSuccess) {
				result = "成功";
			}
			SearchRecordInfo searchRecordInfo = new SearchRecordInfo(number, startTime, finishTime, 0, result);
			SearchRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(searchRecordInfo);
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO 记录查询结束时间及状态

	}

}
