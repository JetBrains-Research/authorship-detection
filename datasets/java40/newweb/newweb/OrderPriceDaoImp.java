package com.newweb.dao.business.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderPriceDao;
import com.newweb.model.business.OrderPrice;

@Component("orderPriceDao")
public class OrderPriceDaoImp implements OrderPriceDao {

	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderPrice> selectOrderPriceByCondition(String type,
			int materialBrandID, String orderID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_orderprice where type=? and productID=? and orderid=?")
				.addEntity(OrderPrice.class);
		query.setString(0, type);
		query.setInteger(1, materialBrandID);
		query.setString(2, orderID);
		return query.list();
	}

	@Override
	public boolean delete(OrderPrice op) {
		try {
			sessionFactory.getCurrentSession().delete(op);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean insert(OrderPrice op) {
		try {
			sessionFactory.getCurrentSession().save(op);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(OrderPrice op) {
		try {
			sessionFactory.getCurrentSession().update(op);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
