/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: SystemMonitorStep.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.step
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 上午10:17:10
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.step;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.schedule.ExecuteContext;
import com.yeshj.pacman.schedule.StepBase;
import com.yeshj.pacman.schedule.SystemInfo;
import com.yeshj.pacman.utils.ProcessExecutor;
import com.yeshj.pacman.utils.Wrapper;

/**
 * TODO
 * 
 * @Class: SystemMonitorStep
 * @author: zhangxinyu
 * @date: 2014年12月24日 上午10:17:10
 */
public class SystemMonitorStep extends StepBase {

	private final static ILog logger = LogFactory.getLog(SystemMonitorStep.class);
	private final static String PREFIX = "[SystemMonitorStep]";
	
	private final static String CMDLINE = "top -bn1 -d 0.1";

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: execute
	 * @param context
	 * @return
	 * @throws Exception
	 * @see com.yeshj.pacman.schedule.IStep#execute(com.yeshj.pacman.schedule.ExecuteContext)
	 */
	@Override
	public Object execute(ExecuteContext context) throws Exception {

		ProcessExecutor pe = new ProcessExecutor();
		List<String> output = new ArrayList<String>();
		String item = null;
		StringTokenizer token = null;
		SystemInfo info = new SystemInfo();

		if (pe.execute(output, CMDLINE)) {

			try {

				for (String line : output) {
					
					if (line.startsWith("Cpu(s):")) {

						boolean fragFormat = line.indexOf("%us,") > 0;
						
						token = new StringTokenizer(line);
						token.nextToken();
						item = token.nextToken(); // user
						info.setCpuUser(Double.valueOf(
								item.substring(0, item.indexOf('%')))
								.doubleValue());

						if (!fragFormat) {
							token.nextToken();
						}
						item = token.nextToken(); // sys
						info.setCpuSys(Double.valueOf(
								item.substring(0, item.indexOf('%')))
								.doubleValue());

						if (!fragFormat) {
							token.nextToken();
						}
						
						item = token.nextToken(); // nice
						info.setCpuNice(Double.valueOf(
								item.substring(0, item.indexOf('%')))
								.doubleValue());

						if (!fragFormat) {
							token.nextToken();
						}
						item = token.nextToken(); // idle
						info.setCpuIdle(Double.valueOf(
								item.substring(0, item.indexOf('%')))
								.doubleValue());

					} else if (line.startsWith("Mem:")) {

						token = new StringTokenizer(line);
						token.nextToken();
						item = token.nextToken(); // total
						info.setMemTotal(Long.valueOf(
								item.substring(0, item.indexOf('k')))
								.longValue());

						token.nextToken();
						item = token.nextToken(); // used
						info.setMemUsed(Long.valueOf(
								item.substring(0, item.indexOf('k')))
								.longValue());

						token.nextToken();
						item = token.nextToken(); // free
						info.setMemFree(Long.valueOf(
								item.substring(0, item.indexOf('k')))
								.longValue());
						
						if (info.getMemTotal() > 0) {
							info.setMemUsage(info.getMemUsed() * 100.0d / info.getMemTotal());
						}
					}
				}
			} finally {
//				logger.info("CPU:" + info.getCpuUser() + " " + info.getCpuSys() + " " + info.getCpuIdle());
//				logger.info("MEM:" + info.getMemTotal() + " " + info.getMemUsage() + " " + info.getMemFree());
			}
		}

		return info;
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: name
	 * @return
	 * @see com.yeshj.pacman.schedule.StepBase#name()
	 */
	@Override
	public String name() {

		return "step.system.monitor";
	}

}
