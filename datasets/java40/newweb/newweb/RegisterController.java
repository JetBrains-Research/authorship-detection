package com.newweb.controller.system;

import java.util.Locale;

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
public class RegisterController {

	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	

	@RequestMapping(value = "getRegisterPage.action", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "page/register/register.jsp";
	}
	
	/**
	 * 用户注册的业务处理
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "userRegister.action", method = RequestMethod.POST)
	public String userRegister(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String linkName = request.getParameter("linkName");
		String contact = request.getParameter("contact");
		String password = request.getParameter("password");
		
		User user = new User();
		Customer[] cs = customerService.queryCustomerByName(linkName);
		Customer customer = null;
		boolean linkValid = false;
		for (Customer c : cs) {
			if(c.getName().equals(linkName) &&
					c.getContact().equals(contact)){
				linkValid = true;
				customer = c;
				break;
			}
		}
		if(!linkValid){
			return "page/register/register.jsp?registerName=" + userName + 
					"&linkName=" + linkName+"&contact=" + contact;
		}
		
		user.setUserName(userName);
		user.setType(customer != null ? "customer" : "");
		user.setLinkid(customer.getID());
		user.setPassword(MD5Util.getMD5(password));
			
		if(!userService.saveUser(user)){
			return "page/login/login.jsp?registerStatus=" + "failed";
		}
		
		return "page/login/login.jsp?registerName=" + userName;
	}
	
	/**
	 * 用户名是否已存在
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isUserExist.ajax")
	public String isUserExist(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("userName");
		
		User user = userService.findUserByName(name);
		if(user != null){	//登录名有重复
			return "nameRepeat";
		}
		return null;
	}
	
	/**
	 * 判断用户关联的名字存在情况
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isLinkNameExist.ajax")
	public String islinkNameExist(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String name = request.getParameter("linkName");
		
		Customer[] cs = customerService.queryCustomerByName(name);
		
		if(cs != null){
			if(cs.length == 1){
				User u = userService.findUserByLinkID(cs[0].getID());
				if(u != null){
					return "linkNameIsUsed";	//此关联名已被注册
				}
				return "linkNameValid";		//关联名可用
			}
			if(cs.length == 0){
				return "linkNameUnvalid";	//关联名不可用
			}
			if(cs.length > 1){
				return "nameRepead";	//系统检测此名存在多个结果
			}
		}
		
		if(cs == null || cs.length == 0){
			return "linkNameUnvalid";	//关联名不可用
		}
		return null;
	}
	
}
