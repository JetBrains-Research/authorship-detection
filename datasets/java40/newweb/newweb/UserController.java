package com.newweb.controller.manager.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newweb.model.base.User;
import com.newweb.service.base.CustomerService;
import com.newweb.service.base.UserService;
import com.newweb.util.MD5Util;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="getUsersJsonData.ajax")
	@ResponseBody
	public String getUsersJsonData(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		List list = userService.queryAllUsers(start,limit);
		int size = (Integer) list.get(0);
		User[] users = (User[]) list.get(1);
		
		String json = "{\"total\":\"" +size+"\",\"data\":[";	//json串头
		
		for (User user : users) {
			String str = "{" +
					"\"id\":\"" + user.getID() +"\"" +
					",\"linkid\":\"" + user.getLinkid() + "\"" +
					",\"lastLoginTime\":\"" + (user.getLastLoginTime()==null?"无记录":user.getLastLoginTime()) + "\"" +
					",\"loginCount\":\"" + user.getLoginCount() + "\"" +
					",\"type\":\"" + user.getType() + "\"" +
					",\"userName\":\"" + user.getUserName() +"" + "\"" +
					",\"linkName\":\"" + (user.getType().equals("customer")?(
							customerService.findCustomerByID(user.getLinkid()).getName()
							):user.getUserName()) + "\"" +
					",\"typeName\":\"" + (user.getType().equals("customer")?"客户":
						user.getType().equals("system")?"系统":"未知") + "\"" +
					"},";
			json += str;
		}
		
		json = json.substring(0, json.length()-1);	//去掉多余的逗号
		json += "]}";	//json串尾
		try {
			response.getWriter().write(json);//将JSON数据写入response中
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="modifyUserPasswordTo888888.ajax")
	@ResponseBody
	public String modifyUserPasswordTo888888(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String ids = request.getParameter("ids");
		StringBuilder sb = new StringBuilder();
		
		String[] ss = ids.split(",");
		for (String s : ss) {
			int id = 0;
			try {
				id = Integer.parseInt(s);
			} catch (Exception e) {
				sb.append("用户标识符:'" + s +"'未能识别!</br>");
				continue;
			}
			int sessionID = (Integer) request.getSession().getAttribute("userID");
			User user = userService.findUserByID(id);
			if(user == null){
				sb.append("该标识符:'" + id +"'没有找到对应的用户,可能已经被删除,或ID被修改</br>");
				continue;
			}
			user.setPassword(MD5Util.getMD5("888888"));
			if(userService.modify(user)){
				if(sessionID == id){
					sb.append("用户: " + user.getUserName() + " ' 密码重置成功!(注意，此号为当前登陆用户)</br>");
				}else{
					sb.append("用户: " + user.getUserName() + " ' 密码重置成功!</br>");
				}
			}else{
				sb.append("用户 : " + user.getUserName() + " ' 密码重置失败!</br>");
				}
		}
		try {
			response.getWriter().write(sb.toString());//将JSON数据写入response中
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
