package com.nercis.isscp.util;
/**
 * 
 * @author luantingyuan
 *
 */
public class UserAppInfo {
	String appName;
	String size;
	String md5;
	String appPath;

	public UserAppInfo(String appName, String size, String md5, String appPath) {
		this.appName = appName;
		this.size = size;
		this.md5 = md5;
		this.appPath = appPath;
	}

	@Override
	public String toString() {
		return "UserAppInfo [appName=" + appName + ", size=" + size + ", md5=" + md5 + ", appPath=" + appPath + "]";
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

}
