package com.nercis.isscp.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SingleRecordInfoQueue {

	private ConcurrentLinkedQueue<SingleRecordInfo> queue=new ConcurrentLinkedQueue<SingleRecordInfo>();
	
	private static final SingleRecordInfoQueue singleRecordInfoQueueSingleton = new SingleRecordInfoQueue();

	public static SingleRecordInfoQueue getInstance() {
		return singleRecordInfoQueueSingleton;
	}

	public synchronized ConcurrentLinkedQueue<SingleRecordInfo> getSingleRecordInfoQueue() {
		return queue;
	}

	public synchronized boolean addToSingleRecordInfoQueue(SingleRecordInfo singleRecordInfo) {
		return queue.add(singleRecordInfo);
	}

}
