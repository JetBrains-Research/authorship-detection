/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: MockStep2.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.task.mock
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月25日 下午3:36:36
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.task.mock;

import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;

/**
 * TODO
 * @Class: MockStep2
 * @author: zhangxinyu
 * @date: 2014年12月25日 下午3:36:36
 */
public class MockStep2 extends StepBase {

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
		
		Thread.sleep(2000);
		int p2 = context.getAttribute("p2", 0);
		int re = context.getAttribute("steps.result", 0);
		context.setAttribute("steps.result", p2 + re);
		
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

		return "mock.step.2";
	}

}
