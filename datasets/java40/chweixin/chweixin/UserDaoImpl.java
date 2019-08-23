package ipower.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IUserDao;
import ipower.wechat.domain.User;
import ipower.wechat.modal.UserInfo;

/**
 * 用户数据访问实现类。
 * @author  yangyong.
 * @since 2014-04-04.
 * */
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	
	/**
	 * 添加查询条件。
	 * @param info
	 * 可选查询参数。
	 * @param hql
	 *  HQL
	 *  @param parameters
	 *  参数集合。
	 *  @return
	 *  结果HQL。
	 * */
	 protected String addWhere(UserInfo info,String hql,Map<String, Object> parameters){
		  if(info.getAccount() != null && !info.getAccount().trim().isEmpty()){
			  hql += " and (u.name like :account or u.account like :account) ";
			  parameters.put("account", "%" + info.getAccount() + "%");
		  }
		  return hql;
	 }

	@Override
	public List<User> findUsers(UserInfo info) {
		String hql = "from User u where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}

	@Override
	public Long total(UserInfo info) {
		String hql = "select count(*) from User u where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters); 
		return this.count(hql, parameters);
	}

	@Override
	public User loadUser(String account) {
		if(account == null || account.trim().isEmpty()) return null;
		final String hql = "from User u where u.account = :account ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("account", account);
		List<User> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}