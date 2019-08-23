package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.LhjPriceDao;
import com.newweb.model.base.LhjPrice;

@Component("lhjPriceDao")
public class LhjPriceDaoImp implements LhjPriceDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public LhjPrice selectLhjPriceByMaterialBrandID(int materialBrandID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_lhjprice where materialbrandid=?")
				.addEntity(LhjPrice.class);
		query.setInteger(0, materialBrandID);
		return (LhjPrice) query.uniqueResult();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List selectAllLhjPrices(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_lhjprice")
				.addEntity(LhjPrice.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public LhjPrice selectLhjPriceByID(int lid) {
		return (LhjPrice) sessionFactory.getCurrentSession().get(LhjPrice.class, lid);
	}

	@Override
	public boolean update(LhjPrice lhjp) {
		try {
			sessionFactory.getCurrentSession().update(lhjp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean insert(LhjPrice lhjp) {
		try {
			sessionFactory.getCurrentSession().save(lhjp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(LhjPrice lhjp) {
		try {
			sessionFactory.getCurrentSession().delete(lhjp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
