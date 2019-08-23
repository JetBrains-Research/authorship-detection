package com.newweb.dao.business.imp;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.LhjPriceCutDao;
import com.newweb.model.business.LhjPriceCut;

@Component("lhjPriceCutDao")
public class LhjPriceCutDaoImp implements LhjPriceCutDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public LhjPriceCut selectPriceCutByCustomerIDAndPriceID(int priceID,
			int customerID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_lhjpricecut where lhjpriceid=? and customerid=?")
				.addEntity(LhjPriceCut.class);
		query.setInteger(0, priceID);
		query.setInteger(1, customerID);
		return (LhjPriceCut) query.uniqueResult();
	}

	@Override
	public boolean insert(LhjPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().save(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(LhjPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().delete(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(LhjPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().update(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}


}
