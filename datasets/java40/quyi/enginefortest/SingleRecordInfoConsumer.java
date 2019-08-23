package com.nercis.isscp.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SingleRecordInfoConsumer implements Runnable {
	ConcurrentLinkedQueue<SingleRecordInfo> queue = SingleRecordInfoQueue.getInstance().getSingleRecordInfoQueue();
	String filename = "";

	public SingleRecordInfoConsumer(String filename) {
		this.filename = filename;

	}

	@Override
	public void run() {
		while (true) {
			if (!queue.isEmpty()) {
				SingleRecordInfo singleRecordInfo = queue.poll();
				if (singleRecordInfo.missionId.contains("Multi") || singleRecordInfo.missionId.contains("SingleTest")) {
					new ExportExcel().exportToSingleExcel(filename, singleRecordInfo);
					System.out.println("写了文件");
				}
			}

		}

	}

}
