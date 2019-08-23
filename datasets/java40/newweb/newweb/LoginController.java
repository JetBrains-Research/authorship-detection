package com.newweb.controller.system;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newweb.model.base.Customer;
import com.newweb.model.base.User;
import com.newweb.service.base.CustomerService;
import com.newweb.service.base.UserService;
import com.newweb.util.MD5Util;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	

	/**
	 * 登陆页面请求
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login.action", method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response,
			Locale locale, Model model) {
		String url = request.getParameter("url");
		return "page/login/login.jsp?reqUrl=" + url;
	}
	
	/**
	 * 用户登陆的逻辑处理
	 * 返回ajax请求
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "userLogin.action",method = RequestMethod.GET)
	public String userLogin(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		String loginKeeping = request.getParameter("loginKeeping");	//cookie
		
		User user = userService.findUserByName(name);
		if(user == null || !user.isValid()){	//如果用户不存在
			return "userIsNotExist";
		}
		
		if(user.getType().equals("system")){
			if(user.getPassword().equals(MD5Util.getMD5(password))){
				request.getSession().setAttribute("userID", user.getID());
				if(loginKeeping.equals("yes")){
					Cookie nameCookie = new Cookie("loginName", name);
					Cookie pswdCookie = new Cookie("loginPassword",MD5Util.getMD5(password));
					nameCookie.setMaxAge(60 * 60 * 24 * 30);
					pswdCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(nameCookie);
					response.addCookie(pswdCookie);
				}
				userService.userLoginRecord(request, user);
				return "systemLogin";
			}else{
				return "passwordError";
			}
			
		}else if(user.getType().equals("customer")){
			if(user.getPassword().equals(MD5Util.getMD5(password))){
				request.getSession().setAttribute("userID", user.getID());
				if(loginKeeping.equals("yes")){
					Cookie nameCookie = new Cookie("loginName", name);
					Cookie pswdCookie = new Cookie("loginPassword",MD5Util.getMD5(password));
					nameCookie.setMaxAge(60 * 60 * 24 * 30);
					pswdCookie.setMaxAge(60 * 60 * 24 * 7);
					response.addCookie(nameCookie);
					response.addCookie(pswdCookie);
				}
				userService.userLoginRecord(request, user);
				return "customerLogin";
			}else{
				return "passwordError";
			}
		}
		return "unknowUser";
	}
	
	/**
	 * 根据当前用户登陆的不同身份，跳转相应的页面
	 * 触发链接：点击首页的当前用户名
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="userManager.action")
	public String userManager(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		int sessionUserID = (Integer) request.getSession().getAttribute("userID");
		User user = userService.findUserByID(sessionUserID);
		if(user.getType().equals("system")){	//如用户身份为管理员
			return "page/system/systemIndex.jsp";
		}else if(user.getType().equals("customer")){//若用户身份为客户
			return "page/customer/customerCentre.jsp";
		}else{
			return "page/index.jsp?warnMsg=" + URLEncoder.encode(URLEncoder.encode("系统未能识别出您的身份，请联系本站！","UTF-8"),"UTF-8");
		}
	}
	
	/**
	 * 用户登陆注销请求
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="logout.action")
	public String logout(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		request.getSession().setAttribute("userID", null);
		response.addCookie(new Cookie("loginPassword", ""));
		return "page/index.jsp";
	}
	
	/**
	 * ajax请求
	 * 获取当前登录用户对应的用户名
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "isUserLogin.ajax")
	@ResponseBody
	public String isUserLogin(HttpServletRequest request, HttpServletResponse response){
		String result = "";
		response.setCharacterEncoding("UTF-8");
		
		int sessionUserID = (Integer) request.getSession().getAttribute("userID");
		User user = userService.findUserByID(sessionUserID);
		if(user.getType().equals("customer")){
			Customer c = customerService.findCustomerByID(user.getLinkid());
			result = c.getName();
		}
		if(user.getType().equals("system")){
			result = "系统管理员";
		}
		try {
			response.getWriter().write(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
