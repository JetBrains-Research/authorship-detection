package com.newweb.dao.business.imp;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderLhjWinPropDao;
import com.newweb.model.business.OrderLhjWinProp;

@Component("orderLhjWinPropDao")
public class OrderLhjWinPropDaoImp implements OrderLhjWinPropDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int insert(OrderLhjWinProp orderLhjWinProp) {
		if(sessionFactory.getCurrentSession().save(orderLhjWinProp) != null)
			return 1;
		return 0;
	}

	@Override
	public OrderLhjWinProp selectOrderLhjWinPropById(int ID) {
		return (OrderLhjWinProp) sessionFactory.getCurrentSession().get(OrderLhjWinProp.class, ID);
	}

	@Override
	public boolean update(OrderLhjWinProp prop) {
		try {
			sessionFactory.getCurrentSession().update(prop);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(OrderLhjWinProp olwp) {
		try {
			sessionFactory.getCurrentSession().delete(olwp);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
