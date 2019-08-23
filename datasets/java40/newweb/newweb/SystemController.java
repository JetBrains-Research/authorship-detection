package com.newweb.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newweb.model.base.User;
import com.newweb.service.base.UserService;

@Controller
public class SystemController {
	
	@Autowired
	private UserService userService;
	

	/**
	 * 系统管理员用户登陆
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="systemLogin.action")
	public String systemLogin(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		int sessionUserID = (Integer) request.getSession().getAttribute("userID");
		
		User user = userService.findUserByID(sessionUserID);
		if(!user.getType().equals("system")){	//若当前用户不是系统管理员身份
			return "page/index.jsp?warnMsg=" + URLEncoder.encode(URLEncoder.encode("非法访问：访问权限不够！","UTF-8"),"UTF-8");
		}
		
		return "page/system/systemIndex.jsp";
	}
	
	@RequestMapping(value="cmsDo.action")
	public String systemManagerDo(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		return "page/system/cms.jsp";
	}
	
	@RequestMapping(value="businessSystemDo.action")
	public String businessSystemDo(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		
		return "page/system/businessSystem.jsp";
	}
	
	@RequestMapping(value="htmlContentEdit")
	public String htmlContentEdit(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		return "page/system/htmlContnetEdit.jsp";
	}
	
}
