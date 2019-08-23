package com.nercis.isscp.engine.bupt.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.nercis.isscp.engine.EngineOperate;
import com.nercis.isscp.engine.MissionResults;
import com.nercis.isscp.util.SearchRecordInfo;
import com.nercis.isscp.util.SearchRecordInfoConsumer;
import com.nercis.isscp.util.SearchRecordInfoQueue;

public class SearchMissionThread implements Runnable {

	
	private String engine_address;
	private int engine_port;
	private String missionId;
	private String number;
	
	
	public SearchMissionThread(String engineAddress, int enginePort, String missionId,String number) {
		this.engine_address = engineAddress;
		this.engine_port = enginePort;
		this.missionId = missionId;
		this.number=number;
	}
	@Override
	public void run() {
		// TODO 记录查询开始时间
		boolean isSearchSuccess = false;
		try {
			TTransport transport = new TSocket(engine_address, engine_port);
			transport.open();
			// 设置传输协议为TBinaryProtocol
			TProtocol protocol1 = new TBinaryProtocol(transport);
			// 调用服务的方法
			EngineOperate.Client client = new EngineOperate.Client(protocol1);
			long startTime=System.currentTimeMillis();
			MissionResults missionResults = client.searchMission(missionId);
			long finishTime=System.currentTimeMillis();
			if (missionResults != null && missionResults.getMissionId() != null) {
				isSearchSuccess = true;
			}
			String result="失败";
			if(isSearchSuccess){
				result="成功";
			}
			SearchRecordInfo searchRecordInfo=new SearchRecordInfo();
			searchRecordInfo.setStartedTime(startTime);
			searchRecordInfo.setFinishedTime(finishTime);
			searchRecordInfo.setNumber(number);
			searchRecordInfo.setCheckResult(result);
			SearchRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(searchRecordInfo);
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO 记录查询结束时间及状态
	}

}
