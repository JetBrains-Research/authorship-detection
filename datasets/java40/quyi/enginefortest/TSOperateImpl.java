package com.nercis.isscp.engine.bupt.impl;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.nercis.isscp.idl.APICheck;
import com.nercis.isscp.idl.AppCheckResult;
import com.nercis.isscp.idl.AppStatus;
import com.nercis.isscp.idl.CheckResultStatus;
import com.nercis.isscp.idl.DynamicCheckResultData;
import com.nercis.isscp.idl.InvalidRequestException;
import com.nercis.isscp.idl.PlotsType;
import com.nercis.isscp.idl.Result;
import com.nercis.isscp.idl.StaticCheckResultData;
import com.nercis.isscp.idl.UnavailableException;
import com.nercis.isscp.idl.UserApp;
import com.nercis.isscp.idl.virus.VirusDetectionResultData;
import com.nercis.isscp.ts.NodeHeat;
import com.nercis.isscp.ts.TSOperate;
import com.nercis.isscp.util.ExportExcel;
import com.nercis.isscp.util.SingleRecordInfo;
import com.nercis.isscp.util.SingleRecordInfoQueue;

public class TSOperateImpl implements TSOperate.Iface {

	public TSOperateImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String genMissionId(String submitId) throws InvalidRequestException, UnavailableException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result submitMission(String missionId, List<UserApp> userApps, List<PlotsType> plots, String useRule) throws InvalidRequestException,
			UnavailableException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppCheckResult queryApp(String missionId, String userAppId) throws InvalidRequestException, UnavailableException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppCheckResult> queryMission(String missionId) throws InvalidRequestException, UnavailableException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result updateAppStatus(String missionId, String userAppId, AppStatus status, List<Map<String, APICheck>> usedPermissions,
			StaticCheckResultData stcRD, DynamicCheckResultData dycRD, VirusDetectionResultData virRD) throws InvalidRequestException,
			UnavailableException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result updateUserAppInfo(String missionId, String userAppId, Map<PlotsType, CheckResultStatus> plotsStatus,
			List<Map<String, APICheck>> usedPermissions, StaticCheckResultData stcRD, DynamicCheckResultData dycRD, VirusDetectionResultData virRD)
			throws InvalidRequestException, UnavailableException, TException {
		// TODO Auto-generated method stub
		System.out.println(userAppId + plotsStatus.toString());
		return Result.success;
	}

	@Override
	public Result heart(NodeHeat nodeHeat, long timestamp) throws InvalidRequestException, UnavailableException, TException {
		// TODO Auto-generated method stub
	//	System.out.println(timestamp + nodeHeat.toString());
		return Result.success;
	}

	@Override
	public Result updateUserAppStatus(String missionId, String userAppId, Map<PlotsType, CheckResultStatus> plotsStatus,
			Map<String, CheckResultStatus> functionStatus) throws InvalidRequestException, UnavailableException, TException {
		// TODO Auto-generated method stub
	/*	long finishedTime = System.currentTimeMillis();
		if (plotsStatus.values().contains(CheckResultStatus.breaked) || plotsStatus.values().contains(CheckResultStatus.executed)
				|| plotsStatus.values().contains(CheckResultStatus.timeout) || plotsStatus.values().contains(CheckResultStatus.failure)) {
		//	if (missionId.contains（)) {
				SingleRecordInfo singleRecordInfo = new SingleRecordInfo();
				singleRecordInfo.setMissionId(missionId);
				singleRecordInfo.setUserAppId(userAppId);
				singleRecordInfo.setFinishedTime(finishedTime);
				if (plotsStatus.keySet().contains(PlotsType.staticType)) {
					singleRecordInfo.setPlotsType(PlotsType.staticType);
				} else {
					singleRecordInfo.setPlotsType(PlotsType.virusType);
				}
				if(plotsStatus.containsValue(CheckResultStatus.breaked)){
					singleRecordInfo.setAppCheckResult("中断");
				}else if(plotsStatus.containsValue(CheckResultStatus.executed)){
					singleRecordInfo.setAppCheckResult("成功");
				}else if(plotsStatus.containsValue(CheckResultStatus.timeout)){
					singleRecordInfo.setAppCheckResult("超时");
				}else if(plotsStatus.containsValue(CheckResultStatus.failure)){
					singleRecordInfo.setAppCheckResult("失败");
				}
				
				//new ExportExcel().exportToSingleExcel("E:\\实验室\\项目\\信工所\\6月6日张妍测试文档\\单个应用统计.xls", singleRecordInfo);
			    SingleRecordInfoQueue.getInstance().addToSingleRecordInfoQueue(singleRecordInfo);
			}
		//}*/
		System.out.println("missionID:"+missionId+",userappId:"+userAppId +",status:"+ plotsStatus.toString());
		return Result.success;
	}

}
