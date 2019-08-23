package com.weico.core.fileDownLoader;

import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.util.Date;

public class FileDownloader implements DownloadWorkerDelegate {
	
	/*
	 * Properties
	 */
	private String urlName;
	private String localLocation;


    public FileDownloaderDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(FileDownloaderDelegate delegate) {
        this.delegate = delegate;
    }

    private FileDownloaderDelegate delegate;
	private int bytesWritten;
	private int previousBytesWritten;
	private OutputStream outputStream;
	private String kbPerSecond;
	private double lastTimeReceivedData;
	private String totalTimeToDownload;
	private long downloadBeginTime;
	private long downloadEndTime;
	private int percentComplete;
	private boolean isDownloading;
	private DownloaderWorker downloadWorker;
	
	/*
	 * Constructor
	 */
	public FileDownloader(FileDownloaderDelegate delegate) {
		this.delegate = delegate;
	}



	
	/*
	 * Public methods
	 */
	public void beginDownload(){
		if (this.isDownloading()){
			System.out.println("FileDownloader is already downloading a file.");
			return;
		}
		if (urlName != null) {
			this.setDownloadWorker(new DownloaderWorker(this));
			this.getDownloadWorker().setUrlName(this.urlName);
			this.getDownloadWorker().start();
            if (this.delegate != null)
			this.delegate.didStartDownload(this);
		}
	}
	
	/*
	 * Private methods
	 */
	private void calculateKBPerSecond() {
		double currentTime = new Date().getTime();
		double timeDifference = currentTime - this.lastTimeReceivedData;
		
		if (this.lastTimeReceivedData == 0){
			this.lastTimeReceivedData = currentTime;
			return;
		} else if ( timeDifference < 1000) {
			return;
		}
		
		double timeDifferenceInSeconds = timeDifference / 1000; // millisecond conversion to seconds
		double bytesWrittenDifference = this.bytesWritten - this.previousBytesWritten;
		double kiloBytesWrittenDifference = bytesWrittenDifference / 1024; // byte conversion to seconds
		
		if (timeDifferenceInSeconds != 0) {
			this.setKbPerSecond(new Double(kiloBytesWrittenDifference / timeDifferenceInSeconds).toString());
		}
		this.lastTimeReceivedData = currentTime;
		this.previousBytesWritten = this.getBytesWritten();
	}
	
	/*
	 * FileDownloaderDelegate delegate methods
	 */
	@Override
	public void didStartDownload(DownloaderWorker downloaderWorker) {
		try {
			this.outputStream = new BufferedOutputStream(new FileOutputStream(this.localLocation));
			this.setDownloadBeginTime(new Date().getTime());
			this.setDownloading(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void didReceiveData(DownloaderWorker downloaderWorker, byte[] data, int byteRead) {
		try {
			this.outputStream.write(data, 0, byteRead);
			this.setBytesWritten(this.getBytesWritten() + byteRead);
			this.calculateKBPerSecond();
			this.setPercentComplete();
            if (this.delegate != null)
			this.delegate.didProgressDownload(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void didFinishDownload(DownloaderWorker downloaderWorker) {
		
		this.setDownloadEndTime(new Date().getTime());
		this.setTotalTimeToDownload();
		this.setDownloading(false);
        if (this.delegate != null)
		this.delegate.didFinishDownload(this);
		try {
			this.outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void didFailDownload(DownloaderWorker downloaderWorker) {
        if (this.delegate != null)
		this.delegate.didFailDownload(this);
	}
	
	/*
	 * Getters and setters
	 */
	public String getLocalLocation() {
		return localLocation;
	}

	public void setLocalLocation(String localLocation) {
		this.localLocation = localLocation;
	}
	
	public String getUrl() {
		return urlName;
	}

	public void setUrl(String urlName) {
		this.urlName = urlName;
	}
	
	public int getBytesWritten() {
		return bytesWritten;
	}

	private void setBytesWritten(int bytesWritten) {
		this.bytesWritten = bytesWritten;
	}

	public String getKbPerSecond() {
		return kbPerSecond;
	}

	private void setKbPerSecond(String kbPerSecond) {
		this.kbPerSecond = kbPerSecond;
	}

	public String getTotalTimeToDownload() {
		return totalTimeToDownload;
	}

	private void setTotalTimeToDownload() {
		String totalTimeToDownload = 
				new Long( (this.getDownloadEndTime() - this.getDownloadBeginTime()) / 1000 /* milliseconds to seconds conversion*/).toString();
		this.totalTimeToDownload = totalTimeToDownload;
	}

	public long getDownloadBeginTime() {
		return downloadBeginTime;
	}

	public void setDownloadBeginTime(long downloadBeginTime) {
		this.downloadBeginTime = downloadBeginTime;
	}

	public long getDownloadEndTime() {
		return downloadEndTime;
	}

	public void setDownloadEndTime(long downloadEndTime) {
		this.downloadEndTime = downloadEndTime;
	}

	public int getPercentComplete() {
		return percentComplete;
	}

	private void setPercentComplete() {
		int totalFileSize = this.getDownloadWorker().getFileSize();
		Double percentComplete = new Double(new Double(this.getBytesWritten()) / new Double(totalFileSize));
	    this.percentComplete = (int)(percentComplete*100);
	}

	public boolean isDownloading() {
		return isDownloading;
	}

	private void setDownloading(boolean isDownloading) {
		this.isDownloading = isDownloading;
	}

	public DownloaderWorker getDownloadWorker() {
		return downloadWorker;
	}

	public void setDownloadWorker(DownloaderWorker downloadWorker) {
		this.downloadWorker = downloadWorker;
	}
}
