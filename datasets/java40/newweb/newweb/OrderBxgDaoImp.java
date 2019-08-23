package com.newweb.dao.business.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderBxgDao;
import com.newweb.model.business.OrderBxg;

@Component("orderBxgDao")
public class OrderBxgDaoImp implements OrderBxgDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int insert(OrderBxg ob) {
		int result = (Integer) sessionFactory.getCurrentSession().save(ob);
		return result;
	}

	@Override
	public OrderBxg selectOrderBxgByBxgIdInOrderID(int bxgID, String orderid) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from  t_order_bxgs where bxgID=? and orderID=? ")
				.addEntity(OrderBxg.class);
		query.setInteger(0, bxgID);
		query.setString(1, orderid);
		return (OrderBxg) query.uniqueResult();
	}

	@Override
	public boolean delete(OrderBxg ob) {
		try {
			sessionFactory.getCurrentSession().delete(ob);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(OrderBxg ob) {
		try {
			sessionFactory.getCurrentSession().update(ob);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBxg> selectAllOrderBxgs() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from  t_order_bxgs")
				.addEntity(OrderBxg.class);
		return query.list();
	}

}
