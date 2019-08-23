package com.newweb.dao.business.imp;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.BxgPriceCutDao;
import com.newweb.model.business.BxgPriceCut;

@Component("bxgPriceCutDao")
public class BxgPriceCutDaoImp implements BxgPriceCutDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public BxgPriceCut selectBxgPriceCutByBxgIdAndCustomerID(int bxgID,
			int customerID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bxgpricecut where bxgid=? and customerid=?")
				.addEntity(BxgPriceCut.class);
		query.setInteger(0, bxgID);
		query.setInteger(1, customerID);
		return (BxgPriceCut) query.uniqueResult();
	}

	@Override
	public boolean insert(BxgPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().save(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(BxgPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().delete(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(BxgPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().update(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
