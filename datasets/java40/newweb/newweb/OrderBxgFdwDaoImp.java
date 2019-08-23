package com.newweb.dao.business.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderBxgFdwDao;
import com.newweb.model.business.OrderBxgFdw;

@SuppressWarnings("unchecked")
@Component("orderBxgFdwDao")
public class OrderBxgFdwDaoImp implements OrderBxgFdwDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean insert(OrderBxgFdw orderBxgFdw) {
		try {
			sessionFactory.getCurrentSession().save(orderBxgFdw);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<OrderBxgFdw> selectOrderBxgFdwsByOrderID(String orderid) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order_bxgfdws where orderID=?")
				.addEntity(OrderBxgFdw.class);
		return query.setString(0, orderid).list();
	}

	@Override
	public OrderBxgFdw selectBxgFdwById(int iD) {
		return (OrderBxgFdw) sessionFactory.getCurrentSession().get(OrderBxgFdw.class, iD);
	}

	@Override
	public boolean update(OrderBxgFdw fdw) {
		try {
			sessionFactory.getCurrentSession().update(fdw);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(OrderBxgFdw fdw) {
		try {
			sessionFactory.getCurrentSession().delete(fdw);
		} catch (Exception e) {
			return false;
		}
		return true;
	}


}
