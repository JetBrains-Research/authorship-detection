package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.UserDao;
import com.newweb.model.base.User;

@SuppressWarnings({"unchecked"})
@Component("userDao")
public class UserDaoImp implements UserDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User selectUserByName(String name) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_user where userName=?")
				.addEntity(User.class);
		return (User) query.setString(0, name).uniqueResult();
	}

	@Override
	public User selectUserByID(int id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public List<User> selectUserByLinkID(int id) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_user where linkid=?")
				.addEntity(User.class);
		return query.setInteger(0, id).list();
	}

	@Override
	public boolean insertUser(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List selectAllUsers(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_user")
				.addEntity(User.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public boolean update(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
