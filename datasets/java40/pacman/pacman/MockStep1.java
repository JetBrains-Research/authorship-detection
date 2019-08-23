/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: MockStep1.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.mock
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月25日 下午3:36:20
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.task.mock;

import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;

/**
 * TODO
 * @Class: MockStep1
 * @author: zhangxinyu
 * @date: 2014年12月25日 下午3:36:20
 */
public class MockStep1 extends StepBase {

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

		Thread.sleep(1000);
		int p1 = context.getAttribute("p1", 0);
		int re = context.getAttribute("steps.result", 0);
		context.setAttribute("steps.result", re + p1);
		
		return "ok";
	}

	/** (non Javadoc)
	 * TODO
	 * @Title: name
	 * @return
	 * @see com.yeshj.pacman.schedule.IStep#name()
	 */
	@Override
	public String name() {

		return "mock.step.1";
	}

}
