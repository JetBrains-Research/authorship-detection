package com.nercis.isscp.util;

public class SearchRecordInfo {

	String number;
	long startedTime;
	long finishedTime;
	int count;
	String checkResult;

	public SearchRecordInfo() {

	}

	public SearchRecordInfo(String number, long startedTime, long finishedTime,
			int count,String checkResult) {
		this.number = number;
		this.startedTime = startedTime;
		this.finishedTime = finishedTime;
		this.count = count;
		this.checkResult=checkResult;

	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "SearchRecordInfo [number=" + number + ", startedTime="
				+ startedTime + ", finishedTime=" + finishedTime + ", count="
				+ count + "]";
	}

}
