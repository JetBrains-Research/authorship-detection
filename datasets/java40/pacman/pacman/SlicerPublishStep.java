/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: SlicerPublishStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 下午5:57:14
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import java.io.FileNotFoundException;
import java.util.List;

import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.StringHelper;

/**
 * TODO
 * @Class: SlicerPublishStep
 * @author: zhangxinyu
 * @date: 2015年1月5日 下午5:57:14
 */
public class SlicerPublishStep extends StepBase {

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

		String mediaFile = context.getAttribute(Constants.CONTEXT_MEDIA_FULLPATH);

		int classid = context.getAttribute(Constants.CONTEXT_CLASS_ID);
		int lessonid = context.getAttribute(Constants.CONTEXT_LESSON_ID);
		String identity = context.getAttribute(Constants.CONTEXT_IDENTITY);
		
		String targetDir = String.format("%s/%s/%s/", 
				context.getAttribute(Constants.CONTEXT_SLICER_PUBLISH_PATH), classid, lessonid);
		
		String bareName = StringHelper.removeFileExtName(StringHelper.getFileBareName(mediaFile));
		String mediaPath = mediaFile.replace(".flv", FileHelper.FILE_SEPARATOR); 
		String xmlFile = FileHelper.combinePath(mediaPath, bareName + ".xml");

		if (!FileHelper.exists(xmlFile)) {
			
			throw new FileNotFoundException(xmlFile);
		}
		
		List<String> files = FileHelper.getAllFileInDir(mediaPath, true);
		String target = null;
		for(String file : files) {
			if (file.endsWith(".xml")) {
				target = FileHelper.combinePath(targetDir, 
						StringHelper.getFileBareName(file).replace(".xml", "_") +  identity + ".xml");
			} else {
				target = FileHelper.combinePath(targetDir, StringHelper.getFileBareName(file));
			}
			FileHelper.moveFile(file, target);
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

		return "flv.publish.step";
	}

}
