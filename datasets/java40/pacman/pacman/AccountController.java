/******************************************************************  
 * Copyright © 2014 hujiang.com. All rights reserved.
 *
 * @Title: AccountController.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.controller
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2014年12月16日 上午12:30:48
 * @version: V1.0  
 ******************************************************************/

package com.yeshj.pacman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ClassName: AccountController
 * @Description: login/authorization & authentication
 * @author: Dellinger
 * @date: 2014年12月16日 上午12:32:57
 */
@Controller
public class AccountController extends BaseController{

	@RequestMapping(value="login.go", method=RequestMethod.POST)
	private ModelAndView login(String user, String pass) {

		if ("dellinger".equalsIgnoreCase(user)
				&& "pwd123".equals(pass))
		{
			getSession().setAttribute(SESSION_KEY_AUTH, "1");
			return new ModelAndView("account/home");
		}
		else
		{
			ModelMap map = new ModelMap();
			map.addAttribute("msg", "Invalid user or password!");
			return new ModelAndView("account/login", map);
		}
	}
	
	@RequestMapping(value="login.go", method=RequestMethod.GET)
	private ModelAndView login() {
		
		return new ModelAndView("account/login");
	}
	
	@RequestMapping("home.go")
	private ModelAndView home() {
		
		if (canAccess())
			return new ModelAndView("account/home");
		else
			return login();
	}
}
