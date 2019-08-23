/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: HttpDownloadStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月3日 下午7:22:59
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.schedule.step;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.StringHelper;
import com.yeshj.pacman.utils.WebHelper;

/**
 * @ClassName: HttpDownloadStep
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月3日 下午7:22:59
 */
public class ResourceProcessingStep extends StepBase {

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.schedule.IStep#execute(com.yeshj.pacman.schedule.ExecuteContext)
	 */
	@Override
	public Object execute(ExecuteContext context) throws Exception {

		List<String> resList = context.getAttribute(Constants.CONTEXT_WEB_RESOURCES);
		String baseDir = context.getAttribute(Constants.CONTEXT_WEB_RESOURCES_TEMP_DIR);
		String mediaSrc = context.getAttribute(Constants.CONTEXT_MEDIA_PATH);
		String mediaTarget = FileHelper.combinePath(baseDir, StringHelper.getFileBareName(mediaSrc));
		String resTargetDir = context.getAttribute(Constants.CONTEXT_WEB_RESOURCES_TARGET_DIR);
		
		logger.info("===>" + mediaSrc + " >> " + mediaTarget);
		FileHelper.copyFile(mediaSrc, mediaTarget);
		
		context.setAttribute(Constants.CONTEXT_MEDIA_PATH, mediaTarget);
		List<String> newList = new ArrayList<String>();
		
		String target = null, pubfile = null;
		if (resList != null) {
			for(String res : resList) {

				logger.info("=========>" + res);
				if (WebHelper.isWebResource(res)) {
					URL uri = new URL(res);
					target = FileHelper.combinePath(baseDir, uri.getFile());
					WebHelper.download(res, target);
					pubfile = FileHelper.combinePath(resTargetDir, uri.getFile());
					FileHelper.copyFile(target, pubfile);
				} else {
					
					target = FileHelper.combinePath(baseDir, StringHelper.getFileBareName(res));
					FileHelper.copyFile(res, target);
				}
				newList.add(target);
			}
		}
		
		context.setAttribute(Constants.CONTEXT_WEB_RESOURCES, newList);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.schedule.IStep#name()
	 */
	@Override
	public String name() {

		return "web.resource.process.step";
	}

}
