package com.nercis.isscp.util;

import com.nercis.isscp.idl.PlotsType;

/**
 * 
 * @author luantingyuan
 * 
 */
public class SingleRecordInfo {
	String userAppId;
	String missionId;
	String appName;
	String md5;
	String appSize;
	long startedTime = 0;
	long finishedTime = 0;
	PlotsType plotsType;
	String appCheckResult;

	public String getUserAppId() {
		return userAppId;
	}

	public void setUserAppId(String userAppId) {
		this.userAppId = userAppId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public long getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(long startedTime) {
		this.startedTime = startedTime;
	}

	public long getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(long finishedTime) {
		this.finishedTime = finishedTime;
	}

	public PlotsType getPlotsType() {
		return plotsType;
	}

	public void setPlotsType(PlotsType plotsType) {
		this.plotsType = plotsType;
	}

	public String getAppCheckResult() {
		return appCheckResult;
	}

	public void setAppCheckResult(String appCheckResult) {
		this.appCheckResult = appCheckResult;
	}

	@Override
	public String toString() {
		return "SingleRecordInfo [userAppId=" + userAppId + ", missionId=" + missionId + ", appName=" + appName + ", md5=" + md5 + ", appSize="
				+ appSize + ", startedTime=" + startedTime + ", finishedTime=" + finishedTime + ", plotsType=" + plotsType + ", appCheckResult="
				+ appCheckResult + "]";
	}

}
