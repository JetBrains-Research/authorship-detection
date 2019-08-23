package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.BxgDao;
import com.newweb.model.base.Bxg;

@SuppressWarnings("unchecked")
@Component("bxgDao")
public class BxgDaoImp implements BxgDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Bxg> selectAllBxgs() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bxg where valid=true order by buycount desc,CONVERT(name USING GBK),norms desc")
				.addEntity(Bxg.class);
		return query.list();
	}

	@Override
	public Bxg selectBxgById(int id) {
		return (Bxg) sessionFactory.getCurrentSession().get(Bxg.class, id);
	}

	@Override
	public boolean update(Bxg bxg) {
		try {
			bxg = (Bxg) sessionFactory.getCurrentSession().merge(bxg);
			sessionFactory.getCurrentSession().update(bxg);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Bxg> selectBxgByLikeNorms(String condition) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bxg where valid=true and norms like ? order by buycount desc,CONVERT(name USING GBK)")
				.addEntity(Bxg.class);
		query.setString(0, condition);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List selectAllBxgs(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bxg order by valid DESC,buycount desc,CONVERT(name USING GBK)")
				.addEntity(Bxg.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public boolean delete(Bxg bxg) {
		try {
			sessionFactory.getCurrentSession().delete(bxg);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Bxg selectBxgByCondition(String name, double thickness,String norms,int brandID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bxg where name=? and thickness=? and norms=? and materialbrandID=?")
				.addEntity(Bxg.class);
		query.setString(0, name);
		query.setDouble(1, thickness);
		query.setString(2, norms);
		query.setInteger(3, brandID);
		return (Bxg) query.uniqueResult();
	}

	@Override
	public boolean insert(Bxg b) {
		try {
			sessionFactory.getCurrentSession().save(b);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
