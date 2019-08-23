/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: Environment.java
 * @Prject: AppPackSever
 * @Package: com.yeshj.pacman.startup
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月12日 下午11:22:07
 * @version: V1.0  
 ******************************************************************/

package com.yeshj.pacman.startup;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.ProcessExecutor;
import com.yeshj.pacman.utils.StringHelper;

/**
 * @ClassName: Environment
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月12日 下午11:22:07
 */
public final class Environment {

	private final static ILog logger = LogFactory.getLog(Environment.class);
	
	private final static String PREFIX = "[Environment prechecking]";
	private static Map<String, Field> gFields = getFileds(AppConfig.class);

	/**
	 * Environment prechecking.
	 * 
	 * @return
	 */
	public static boolean precheck() {

		boolean result = false;

		logger.info(PREFIX + " ... start");
		try {

			result = ensureConfigDir("tempDir");
			result = ensureConfigDir("resTempDir");
			//result = ensureConfigDir("mediaTempDir");
			//result = ensureConfigDir("packTempDir");
			result = ensureConfigDir("packTargetDir");
			result = ensureConfigDir("sliceTargetDir");
			result = ensureConfigDir("resTargetDir");

			result = ensureToolExists(AppConfig.getInstance().getExtool_ffmpeg(), true);
			result = ensureToolExists(AppConfig.getInstance().getExtool_python() + " -h", false);

		} catch (Exception e) {

			logger.error(PREFIX + " ... Fail!", e);
		}

		logger.info(PREFIX + " ... done.[" + result + "]");
		return result;
	}

	/**
	 * makes sure the external tool working.
	 * 
	 * @param cmd
	 * @param args
	 * @return
	 */
	private static boolean ensureToolExists(String cmd, boolean ignoreResult) {
		
		boolean result = false;
		
		
		ProcessExecutor pe = new ProcessExecutor();
		try {
			logger.info(PREFIX + " CMD:" + cmd); 
			result = pe.execute(null, cmd);
			if (ignoreResult)
				result = true;
			logger.info(PREFIX + " RESULT:" + result);
		} catch (Exception e) {
			
			logger.error(PREFIX + " Checking Fail!", e);
		}
		
		return result;
	}
	
	/**
	 * makes sure the existing of dir.
	 * 
	 * @param key
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static boolean ensureConfigDir(String key)
			throws IllegalArgumentException, IllegalAccessException {

		if (StringHelper.isEmpty(key)) {
			return true;
		}

		AppConfig config = AppConfig.getInstance();

		if (config == null) {
			logger.error("[Environment prechecking] ERROR: AppConfig is null!");
			return false;
		}

		Field field = gFields.get(key);
		
		if (field != null) {
			field.setAccessible(true);
			String str = (String) field.get(config);
			FileHelper.ensureExists(str, false);
		} else {
			logger.error("[Environment prechecking] ERROR: AppConfig doesn't contain [" + key + "]");
			return false;
		}

		logger.info("[Environment prechecking] OK: " + key);
		return true;
	}

	private static Map<String, Field> getFileds(Class<?> clazz) {

		Field[] fields = clazz.getDeclaredFields();
		Map<String, Field> map = new HashMap<String, Field>();

		for (int i = 0; i < fields.length; i++) {
			
			map.put(fields[i].getName(), fields[i]);
		}

		return map;
	}
}
