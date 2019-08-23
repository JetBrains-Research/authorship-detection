/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: StringHelper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月28日 下午10:27:21
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: StringHelper
 * @Description: TODO
 * @author: Dellinger
 * @date: 2014年12月28日 下午10:27:21
 */
public final class StringHelper {

	/**
	 * remove extend file name.
	 * 
	 * @param file
	 * @return
	 */
	public static String removeFileExtName(String file) {
		
		if (StringUtils.isEmpty(file)) {
			return "";
		} else if (file.indexOf('.') < 0) {
			return file;
		}
		
		return file.substring(0, file.indexOf('.'));
	}
	
	/**
	 * Gets bare file name.
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileBareName(String url) {
		
		if (StringUtils.isBlank(url)) {
			return "";
		}
		String[] arr = url.split("\\/|\\\\");
		return arr[arr.length - 1];
	}
	
	/**
	 * 
	 * 
	 * @Title: tryParse
	 * @param str
	 * @param defaultValue
	 * @return
	 * @return: int
	 */
	public static int tryParse(String str, int defaultValue) {

		if (StringUtils.isNumeric(str)) {
			
			return Integer.parseInt(str);
		} else {
			
			return defaultValue;
		}
	}

	/**
	 * TODO
	 * @Title: isEmpty
	 * @param pFileName
	 * @return
	 * @return: boolean
	 */
	public static boolean isEmpty(String str) {
		
		return StringUtils.isBlank(str);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String random() {
		
		return UUID.randomUUID().toString().replace("-", "");
	}
}
