package ipower.wechat.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import ipower.model.UserIdentity;
import ipower.utils.MD5Util;
import ipower.wechat.dao.IUserDao;
import ipower.wechat.domain.User;
import ipower.wechat.modal.UserInfo;
import ipower.wechat.service.IUserService;

/**
 * 用户服务类。
 * @author yangyong.
 * @since 2014-04-04.
 * */
public class UserServiceImpl extends DataServiceImpl<User, UserInfo> implements IUserService {
	private IUserDao userDao;
	/**
	 * 设置用户数据访问接口。
	 * @param userDao
	 * 用户数据访问接口。
	 * */
	public void setUserDao(IUserDao userDao){
		this.userDao = userDao;
	}
	@Override
	protected List<User> find(UserInfo info) {
		return this.userDao.findUsers(info);
	}

	@Override
	protected UserInfo changeModel(User data) {
		if(data == null) return null;
		UserInfo info = new UserInfo();
		BeanUtils.copyProperties(data, info,new String[]{ "password"});
		info.setPassword(null);
		return info;
	}

	@Override
	protected Long total(UserInfo info) {
		return this.userDao.total(info);
	}
	
	private String createPassword(String source){
		return  MD5Util.MD5(MD5Util.MD5(source) + source);
	}

	@Override
	public UserInfo update(UserInfo info) {
		if(info != null){
			boolean isAdded = false;
			User data = (info.getId() == null || info.getId().trim().isEmpty()) ?  null :this.userDao.load(User.class, info.getId());
			if(isAdded = (data == null)){
				if(info.getId() == null || info.getId().trim().isEmpty()){
					info.setId(UUID.randomUUID().toString());
				}
				data = new User();
			}
			BeanUtils.copyProperties(info, data, new String[]{ "password"});
			if(info.getPassword() != null && !info.getPassword().trim().isEmpty()){
				data.setPassword(this.createPassword(info.getPassword()));
			}
			if(isAdded)this.userDao.save(data);
			info.setPassword(null);
		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length;i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			User data = this.userDao.load(User.class, ids[i]);
			if(data != null) this.userDao.delete(data);
		}
	}
	@Override
	public synchronized  UserIdentity authen(String account, String password) throws Exception {
		if(account == null || account.trim().isEmpty() || password == null || password.trim().isEmpty()) throw new Exception("账号或密码为空！");
		 User user = this.userDao.loadUser(account);
		 if(user == null) throw new Exception("账号不存在！");
		 String pwd = this.createPassword(password);
		 if(!user.getPassword().equalsIgnoreCase(pwd)){
			 throw new Exception("密码不正确！");
		 }
		 UserIdentity  identity = new UserIdentity();
		 identity.setId(user.getId());
		 identity.setName(user.getName());
		return identity;
	}
	@Override
	public void init(String account, String password) throws Exception {
		User user = this.userDao.loadUser(account);
		if(user != null) throw new Exception("账号已经存在不能被再次初始化！");
		user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName(account);
		user.setAccount(account);
		user.setPassword(this.createPassword(password));
		this.userDao.save(user);
	}
}