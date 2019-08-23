package com.newweb.dao.business.imp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderLhjWeightDao;
import com.newweb.model.business.OrderLhjWeight;

@Component("orderLhjWeightDao")
public class OrderLhjWeightDaoImp implements OrderLhjWeightDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public OrderLhjWeight selectOrderLhjWeightByOrderIDAndMaterialBrandID(
			String orderid, int materialBrandID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order_lhjweight where orderID=? and materialbrandid=?")
				.addEntity(OrderLhjWeight.class);
		query.setString(0, orderid);
		query.setInteger(1, materialBrandID);
		if(query.list().size() == 0){
			return (OrderLhjWeight) query.uniqueResult();
		}
		return (OrderLhjWeight) query.list().get(0);
	}

	@Override
	public boolean delete(OrderLhjWeight lhjWeight) {
		try {
			sessionFactory.getCurrentSession().delete(lhjWeight);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean insert(OrderLhjWeight w) {
		try {
			sessionFactory.getCurrentSession().save(w);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(OrderLhjWeight lhjWeight) {
		try {
			sessionFactory.getCurrentSession().update(lhjWeight);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderLhjWeight> selectAllOrderLhjWeights() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order_lhjweight")
				.addEntity(OrderLhjWeight.class);
		return query.list();
	}

}
