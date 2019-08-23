/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: MediaPackStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月6日 上午11:02:37
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.StringHelper;

/**
 * TODO
 * @Class: MediaPackStep
 * @author: zhangxinyu
 * @date: 2015年1月6日 上午11:02:37
 */
public class MediaPackStep extends StepBase {

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
		
		List<String> packFiles = new ArrayList<String>();
		String tempDir = context.getAttribute(Constants.CONTEXT_TEMP_DIR);
		String mediaFile = FileHelper.combinePath(tempDir, "index.dat");
		String xmlFile = FileHelper.combinePath(tempDir, "index.xml");
		String resDir = context.getAttribute(Constants.CONTEXT_WEB_RESOURCES_TEMP_DIR);
		String publishFile = context.getAttribute(Constants.CONTEXT_HJPACK);
		
		if (!FileHelper.exists(resDir)) {
			
			throw new FileNotFoundException(resDir);
		}
		
		if (!FileHelper.exists(mediaFile)) {
			
			throw new FileNotFoundException(mediaFile);
		} else {
			
			packFiles.add(mediaFile);
		}
		
		if (!FileHelper.exists(xmlFile)) {
			
			throw new FileNotFoundException(xmlFile);
		} else {
			
			packFiles.add(xmlFile);
		}

		FileHelper.ensureExists(publishFile, true);
		
		packFiles.addAll(FileHelper.getAllFileInDir(resDir, true));
		
		FileHelper.archiveFiles(
				packFiles, 
				publishFile, 
				AppConfig.getInstance().getIgnoreExtNames(), 
				true);
		
		FileHelper.deleteDir(resDir);
		FileHelper.deleteDir(tempDir);
		
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

		return "media.pack.step";
	}

}
