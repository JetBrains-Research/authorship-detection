/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: LessonParserStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 下午6:00:01
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import javax.management.modelmbean.XMLParseException;

import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.schedule.dom.LessonInfo;
import com.yeshj.pacman.schedule.dom.LessonInfoParser;
import com.yeshj.pacman.utils.FileHelper;

/**
 * TODO
 * @Class: LessonParserStep
 * @author: zhangxinyu
 * @date: 2014年12月24日 下午6:00:01
 */
public class LessonParserStep extends StepBase {
	
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

		LessonInfo info = null;
		String tempDir = context.getAttribute(Constants.CONTEXT_TEMP_DIR);
		int lessonId = context.getAttribute(Constants.CONTEXT_LESSON_ID);
		String url = context.getAttribute(Constants.LESSON_XML_URL);
		
		url = url + lessonId;
		
		FileHelper.ensureExists(tempDir, false);
		
		tempDir = FileHelper.combinePath(tempDir, "index.xml");
		
		LessonInfoParser parser = new LessonInfoParser();
		try {
			
			info = parser.parseAndSave(url, tempDir);
			if (info == null) {
				throw new XMLParseException();
			}
			
			context.setAttribute(Constants.CONTEXT_WEB_RESOURCES, info.getAllFiles());
			
			logger.info("LessonParserStep...execute[SUCCESS]");	
		} catch (Exception e) {
			
			logger.error("LessonParserStep...execute[ERROR]", e);
			context.setRollbackFlag(true);
			throw e;
		}
		
		return info;
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: name
	 * @return
	 * @see com.yeshj.pacman.schedule.StepBase#name()
	 */
	@Override
	public String name() {

		return "step.lesson.parser";
	}

}
