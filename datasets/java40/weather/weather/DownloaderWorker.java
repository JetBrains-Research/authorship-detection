package com.weico.core.fileDownLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloaderWorker extends Thread {
	final static int size=1048576; // buffer size
	
	private DownloadWorkerDelegate delegate;
	private String urlName;
	private InputStream inputStream;
	private int testCount;
	private int fileSize;
	
	public DownloaderWorker(DownloadWorkerDelegate delegate) {
		this.delegate = delegate;
	}
	
	public void setUrlName(String urlName){
		this.urlName = urlName;
	}
	
	public void run() {
		try {
			this.initializeDownload();
			
			this.delegate.didStartDownload(this);
			
			this.beginDownload();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.inputStream.close();
				this.delegate.didFinishDownload(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void initializeDownload() throws Exception {
		URL url = new URL(this.urlName);
		URLConnection urlConnnection = url.openConnection();
		this.setFileSize(urlConnnection.getContentLength());
		this.inputStream = urlConnnection.getInputStream();
	}
	
	private void beginDownload() throws Exception {
		byte[] data = new byte[size];
		int byteRead;
		while ( (byteRead = this.inputStream.read(data)) != -1) {
			//System.out.println("Bytes read: " + new Integer(byteRead).toString());
			this.delegate.didReceiveData(this, data, byteRead);
			this.testCount++;
		}
		System.out.println("Total count: " + new Integer(this.testCount).toString());
	}

	public int getFileSize() {
		return fileSize;
	}

	private void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
}