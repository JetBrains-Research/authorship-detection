package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.SmallDao;
import com.newweb.model.base.Small;

@SuppressWarnings({"unchecked"})
@Component("smallDao")
public class SmallDaoImp implements SmallDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<String> selectDistinctType() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select distinct type from t_small where valid=true");
		return query.list();
	}

	@Override
	public List<Small> selectAllSmalls() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_small where valid=true order by buycount desc,type desc,CONVERT(name USING GBK)")
				.addEntity(Small.class);
		return query.list();
	}

	@Override
	public List<Small> selectSmallsByType(String type) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_small where valid=true and type=? order by buycount desc,type desc,CONVERT(name USING GBK)")
				.addEntity(Small.class);
		query.setString(0, type);
		return query.list();
	}

	@Override
	public Small selectSmallById(int id) {
		return (Small) sessionFactory.getCurrentSession().get(Small.class, id);
	}

	@Override
	public int update(Small small) {
		small = (Small) sessionFactory.getCurrentSession().merge(small);
		sessionFactory.getCurrentSession().update(small);
		return 1;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List selectAllSmalls(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_small order by valid DESC,buycount desc,type desc,CONVERT(name USING GBK)")
				.addEntity(Small.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public Small selectSmallByName(String name) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_small where name=?")
				.addEntity(Small.class);
		query.setString(0, name);
		return (Small) query.uniqueResult();
	}

	@Override
	public boolean insert(Small small) {
		try {
			sessionFactory.getCurrentSession().save(small);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Small small) {
		try {
			sessionFactory.getCurrentSession().delete(small);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
