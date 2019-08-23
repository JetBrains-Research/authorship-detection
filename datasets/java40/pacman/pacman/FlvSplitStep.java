/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: OnlinePackStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月5日 下午5:11:08
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yeshj.pacman.schedule.Constants;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.schedule.StepExecuteException;
import com.yeshj.pacman.utils.ExternalTool;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.ProcessExecutor;

/**
 * TODO
 * @Class: OnlinePackStep
 * @author: zhangxinyu
 * @date: 2015年1月5日 下午5:11:08
 */
public class FlvSplitStep extends StepBase {

	private final static String PREFIX = "[FlvSplitStep]";
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
		
		if (!FileHelper.exists(mediaFile)) {

			throw new FileNotFoundException(mediaFile);
		}
		
		String command = ExternalTool.buildFlvSlicerCommand(mediaFile);
		
		logger.info("===>CMD:" + command);
		List<String> output = new ArrayList<String>();
		ProcessExecutor pe = new ProcessExecutor();
		if (pe.execute(output, command))
		{
			logger.warn(PREFIX + " successfully!");
		} else {
			
			logger.error(PREFIX + " ERROR!\n");
			String buffer = StringUtils.join(output, '\n');
			logger.error(buffer);
			throw new StepExecuteException();
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
		
		return "flv.split.step";
	}

}
