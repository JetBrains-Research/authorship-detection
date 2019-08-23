package com.nercis.isscp.engine.bupt.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.idl.Sample;
import com.nercis.isscp.util.SearchRecordInfo;
import com.nercis.isscp.util.SearchRecordInfoQueue;

public class SearchAppThread implements Runnable {

	private String engine_address;
	private int engine_port;
	private String missionId;
	private String userAppId;
	private String number;
	private int count;

	public SearchAppThread(String engineAddress, int enginePort, String missionId, String userAppId,String number,int count) {
		this.engine_address = engineAddress;
		this.engine_port = enginePort;
		this.missionId = missionId;
		this.userAppId = userAppId;
		this.number=number;
		this.count=count;
	}

	@Override
	public void run() {
		// TODO 记录查询开始时间
		long startTime =System.currentTimeMillis();
		boolean isSearchSuccess = false;
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			TProtocol protocol1 = new TBinaryProtocol(transport);
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			Sample sample = client.searchApp(missionId, userAppId);
			long finishedTime=System.currentTimeMillis();
			if (sample != null && sample.getMissionId() != null) {
				isSearchSuccess = true;
			}
			String result="失败";
			if(isSearchSuccess){
				result="成功";
			}
			SearchRecordInfo searchRecordInfo=new SearchRecordInfo(number,startTime,finishedTime,count,result);
			SearchRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(searchRecordInfo);
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO 记录查询结束时间及状
	}

}
