package com.newweb.dao.business.imp;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.SmallPriceCutDao;
import com.newweb.model.business.SmallPriceCut;

@Component("smallPriceCutDao")
public class SmallPriceCutDaoImp implements SmallPriceCutDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SmallPriceCut selectSmallPriceCutBySmallIDAndCustomerID(int smallID,
			int customerID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_smallpricecut where smallid=? and customerid=?")
				.addEntity(SmallPriceCut.class);
		query.setInteger(0, smallID);
		query.setInteger(1, customerID);
		return (SmallPriceCut) query.uniqueResult();
	}

	@Override
	public boolean insert(SmallPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().save(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(SmallPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().delete(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(SmallPriceCut cut) {
		try {
			sessionFactory.getCurrentSession().update(cut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
