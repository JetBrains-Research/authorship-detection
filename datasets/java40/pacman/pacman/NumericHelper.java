/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: NumericHelper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月30日 上午11:43:09
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 * @Class: NumericHelper
 * @author: zhangxinyu
 * @date: 2014年12月30日 上午11:43:09
 */
public final class NumericHelper {

	/**
	 * @Title: safeParseInt
	 * @param str
	 * @return
	 * @return: int
	 */
	public static int safeParseInt(String str) {
		
		return safeParseInt(str, 0);
	}
	
	/**
	 * 
	 * 
	 * @Title: safeParseInt
	 * @param str
	 * @param defaultValue
	 * @return
	 * @return: int
	 */
	public static int safeParseInt(String str, int defaultValue) {
		
		try {
			
			defaultValue = Integer.parseInt(str);
		} catch (NumberFormatException e) {}
		
		return defaultValue;
	}
	
	/**
	 * @Title: isNumeric
	 * @param str
	 * @return
	 * @return: boolean
	 */
	public static boolean isNumeric(String str) {
		
		return StringUtils.isNumeric(str);
	}
}
