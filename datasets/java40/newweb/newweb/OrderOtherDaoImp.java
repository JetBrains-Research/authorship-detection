package com.newweb.dao.business.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderOtherDao;
import com.newweb.model.business.OrderOther;

@Component("orderOtherDao")
public class OrderOtherDaoImp implements OrderOtherDao {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean insert(OrderOther other) {
		try {
			sessionFactory.getCurrentSession().save(other);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderOther> selectOrderOthersByOrderID(String orderid) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order_others where orderid=?")
				.addEntity(OrderOther.class);
		return query.setString(0, orderid).list();
	}

	@Override
	public OrderOther selectOrderOtherById(int id) {
		return (OrderOther) sessionFactory.getCurrentSession().get(OrderOther.class, id);
	}

	@Override
	public boolean update(OrderOther other) {
		try {
			sessionFactory.getCurrentSession().update(other);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(OrderOther other) {
		try {
			sessionFactory.getCurrentSession().delete(other);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
