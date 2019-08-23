package com.newweb.interceptor;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.newweb.model.base.User;
import com.newweb.service.base.UserService;

@Repository
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html;charset=UTF-8"); 
        String url = request.getRequestURI();
        String root = request.getContextPath();
        url = url.replaceAll(root, "");
        if(url.equals("/") || url.equals("\\")){
        	return true;
        }
        if(url.indexOf("isUserLogin.ajax") != -1){
        	if(request.getSession().getAttribute("userID") == null){
        		if(!cookieLogin(request, response)){
        			response.getWriter().write("noUserLogin");
        			return false;
        		}else{
        			return true;
        		}
        	}else{
        		if(userService.findUserByID((Integer) request.getSession().getAttribute("userID")) == null){
        			request.getSession().setAttribute("userID", null);
        			response.getWriter().write("noUserLogin");
        			return false;
        		}
        		return true;
        	}
        }
        String[] noFilters = new String[] {"userLogin.action","logout.action","login.action",
        		"indexPage","Register","isUserExist.ajax","isLinkNameExist.ajax",
        		"indexGetShowDone.action","indexGetShowProcessed.action","indexGetShowUnprocessed.action",
        		"nearOrder"}; 
        boolean beFilter = true; 
        for (String s : noFilters) {  
            if (url.indexOf(s) != -1) {  
                beFilter = false;  
                break;  
            }  
        }  
		
		if(beFilter){
			int sessionUserID = -1;
			try {
				sessionUserID = (Integer) request.getSession().getAttribute("userID");
			} catch (Exception e) {}
			if(sessionUserID == -1){
				if(!cookieLogin(request, response)){//若cookie无法自动登录
					response.getWriter().write("noUserLogin");
					response.sendRedirect(root + "/" + "indexPage?warnMsg=" + URLEncoder.encode(URLEncoder.encode("此操作需要登陆","UTF-8"),"UTF-8"));
					return false;
				}else{//cookie自动登录成功,进行权限控制
					return SecurityController(request, response, url);
				}
			}else{//用户已登陆，返回true
				return SecurityController(request, response, url);
			}
		}else{//不需要过滤的请求，返回true
			return true;
		}
	}
	
	private boolean SecurityController(HttpServletRequest request, HttpServletResponse response,String url) throws IOException{
		int sessionUserID = -1;
		try {
			sessionUserID = (Integer) request.getSession().getAttribute("userID");
		} catch (Exception e) {}
		
		//需要管理员权限才能访问的请求
		String[] adminFilter = {"cmsDo.action","businessSystemDo.action"};
		for (String string : adminFilter) {
			if(url.contains(string)){
				if(!userService.findUserByID(sessionUserID).getType().equals("system")){
					response.sendRedirect(request.getContextPath() + "/" + "indexPage?warnMsg=" + URLEncoder.encode(URLEncoder.encode("非法访问：访问权限不够！","UTF-8"),"UTF-8"));
					return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean cookieLogin(HttpServletRequest request, HttpServletResponse response){
		Cookie[] cs = request.getCookies();
		String name = "";
		String password = "";
		if(cs != null){
			for (Cookie c : cs) {
				if(c.getName().equals("loginName")){
					name = c.getValue();
				}else if(c.getName().equals("loginPassword")){
					password = c.getValue();
				}
			}
		}
		if(!name.equals("") && !password.equals("")){//若cookie值有效
			User user = userService.findUserByName(name);
			if(user != null && user.isValid()){
				if(user.getPassword().equals(password)){//若符合自动登陆条件，记入session，返回true
					request.getSession().setAttribute("userID", user.getID());
					userService.userLoginRecord(request, user);
					return true;
				}
			}else{
				return false;
			}
		}else{//cookie值无效
			return false;
		}
		return false;
	}
	
}
