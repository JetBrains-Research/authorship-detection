package com.nercis.isscp.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SearchRecordInfoQueue {

private ConcurrentLinkedQueue<SearchRecordInfo> queue=new ConcurrentLinkedQueue<SearchRecordInfo>();
	
	private static final SearchRecordInfoQueue searchRecordInfoQueueSingleton = new SearchRecordInfoQueue();

	public static SearchRecordInfoQueue getInstance() {
		return searchRecordInfoQueueSingleton;
	}

	public synchronized ConcurrentLinkedQueue<SearchRecordInfo> getSearchRecordInfoQueue() {
		return queue;
	}

	public synchronized boolean addToSingleRecordInfoQueue(SearchRecordInfo singleRecordInfo) {
		return queue.add(singleRecordInfo);
	}

}
