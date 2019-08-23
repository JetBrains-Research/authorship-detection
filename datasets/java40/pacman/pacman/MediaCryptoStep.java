/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: MediaCyptoStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 下午6:34:36
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import java.io.FileNotFoundException;
import java.util.List;

import com.yeshj.pacman.MediaEncrypt;
import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.StringHelper;

/**
 * TODO
 * @Class: MediaCyptoStep
 * @author: zhangxinyu
 * @date: 2015年1月5日 下午6:34:36
 */
public class MediaCryptoStep extends StepBase {

	/** (non Javadoc)
	 * TODO
	 * @Title: execute
	 * @param context
	 * @return
	 * @throws Exception
	 * @see com.yeshj.pacman.schedule.IStep#execute(com.yeshj.pacman.schedule.ExecuteContext)
	 */
	@Override
	public Object execute(ExecuteContext context) throws Exception {

		String packfile = context.getAttribute(Constants.CONTEXT_MEDIA_PATH);
		String tempDir = context.getAttribute(Constants.CONTEXT_TEMP_DIR);
		boolean isFree = context.getAttribute(Constants.CONTEXT_ISFREE_CLASS);
		logger.info("isFree:" + isFree);
		if (!FileHelper.exists(packfile)) {
			
			throw new FileNotFoundException(packfile);
		}
		
		FileHelper.ensureExists(tempDir, false);
		
		MediaEncrypt encrypt = new MediaEncrypt();
		encrypt.encodeMedia(packfile, FileHelper.combinePath(tempDir, "index.dat"));
		
		if (isFree) {
			
			int class_id = context.getAttribute(Constants.CONTEXT_CLASS_ID);
			int lesson_id = context.getAttribute(Constants.CONTEXT_LESSON_ID);
			String targetDir = AppConfig.getInstance().getRawTargetDir() + "/" + class_id + "/" + lesson_id + "/";
			FileHelper.ensureExists(targetDir, false);
			String rawFileName = StringHelper.getFileBareName(packfile);
			FileHelper.copyFile(packfile, targetDir + rawFileName);
		}
		
		
		return null;
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: name
	 * @return
	 * @see com.yeshj.pacman.schedule.IStep#name()
	 */
	@Override
	public String name() {

		return "media.cypto.step";
	}

}
