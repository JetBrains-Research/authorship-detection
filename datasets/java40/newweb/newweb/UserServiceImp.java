package com.newweb.service.base.imp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.UserDao;
import com.newweb.model.base.User;
import com.newweb.service.base.UserService;
import com.newweb.util.DateUtil;

@Component("userService")
public class UserServiceImp implements UserService {
	
	@Autowired
	private UserDao userDao;


	@Override
	public User findUserByName(String name) {
		User user =  userDao.selectUserByName(name);
		return user;
	}

	@Override
	public User findUserByID(int id) {
		return userDao.selectUserByID(id);
	}

	@Override
	public User findUserByLinkID(int id) {
		List<User> list = userDao.selectUserByLinkID(id);
		if(list.size() == 1){
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean saveUser(User user) {
		return userDao.insertUser(user);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List queryAllUsers(int start, int limit) {
		List list = userDao.selectAllUsers(start,limit);
		int size =(Integer) list.get(0);
		List sList = (List) list.get(1);
		User[] us = new User[sList.size()];
		int count = 0;
		for (Object o : sList) {
			User u = (User) o;
			us[count++] = u;
		}
		List returnList = new ArrayList();
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(us);//第二个对象保存实体数组
		return returnList;
	}

	@Override
	public void userLoginRecord(HttpServletRequest request, User user) {
		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginTime(DateUtil.getLocationCurrentDate() + " " + DateUtil.getLocationCurrentTime());
		userDao.update(user);
	}

	@Override
	public boolean modify(User user) {
		return userDao.update(user);
	}
}
