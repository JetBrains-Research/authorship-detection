package com.newweb.dao.business.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderSmallDao;
import com.newweb.model.business.OrderSmall;

@Component("orderSmallDao")
public class OrderSmallDaoImp implements OrderSmallDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int insert(OrderSmall os) {
		int result = (Integer) sessionFactory.getCurrentSession().save(os);
		return result;
	}

	@Override
	public OrderSmall selectOrderSmallById(int iD) {
		return (OrderSmall) sessionFactory.getCurrentSession().get(OrderSmall.class, iD);
	}

	@Override
	public boolean delete(OrderSmall os) {
		try {
			sessionFactory.getCurrentSession().delete(os);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(OrderSmall os) {
		try {
			sessionFactory.getCurrentSession().update(os);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public OrderSmall selectOrderSmallBySmallIdInOrderId(int smallId,
			String orderid) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from  t_order_smallGoods where smallID=? and orderID=? ")
				.addEntity(OrderSmall.class);
		query.setInteger(0, smallId);
		query.setString(1, orderid);
		return (OrderSmall) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderSmall> selectAllOrderSmalls() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from  t_order_smallGoods")
				.addEntity(OrderSmall.class);
		return query.list();
	}

}
