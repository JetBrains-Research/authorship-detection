/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: IdGenerator.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月2日 上午2:03:40
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: IdGenerator
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月2日 上午2:03:40
 */
public class IdGenerator {

	private final static AtomicInteger seed = new AtomicInteger();
	
	public static int nextInt() {
		
		return nextInt(-1);
	}
	
	public static int nextInt(int initValue) {
		
		if (initValue < 0) {
			
			return seed.getAndSet(initValue);
		} else {
			
			return seed.getAndAdd(1);
		}
	}
}
