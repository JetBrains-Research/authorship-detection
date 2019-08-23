/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: BaseController.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.controller
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月8日 上午11:47:36
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * TODO
 * @Class: BaseController
 * @author: zhangxinyu
 * @date: 2015年1月8日 上午11:47:36
 */
public abstract class BaseController {
	
	protected final static String SESSION_KEY_AUTH = "secure.auth";
	
	/**
	 * Gets HttpServletRequest.
	 * 
	 * @Title: getRequest
	 * @return
	 * @return: HttpServletRequest
	 */
	protected static HttpServletRequest getRequest() {

		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	/**
	 * Gets HttpSession.
	 * 
	 * @Title: getSession
	 * @return
	 * @return: HttpSession
	 */
	protected static HttpSession getSession() {
		
		return getRequest().getSession();
	}
	
	/**
	 * Checks logged or not.
	 * 
	 * @Title: canAccess
	 * @return
	 * @return: boolean
	 */
	protected boolean canAccess() {
		
		return "1".equals(getSession().getAttribute(SESSION_KEY_AUTH));
	}
}
