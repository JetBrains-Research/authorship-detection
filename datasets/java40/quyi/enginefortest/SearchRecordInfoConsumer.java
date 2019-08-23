package com.nercis.isscp.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SearchRecordInfoConsumer implements Runnable {
	ConcurrentLinkedQueue<SearchRecordInfo> queue = SearchRecordInfoQueue
			.getInstance().getSearchRecordInfoQueue();
	String filename = "";

	public SearchRecordInfoConsumer(String filename) {
		this.filename = filename;

	}

	@Override
	public void run() {
		while (true) {
			if (!queue.isEmpty()) {
				SearchRecordInfo searchRecordInfo = queue.poll();
				new ExportExcel().exportToSearchExcel(filename,
						searchRecordInfo);
				System.out.println("写了文件");
			}

		}

	}
}
