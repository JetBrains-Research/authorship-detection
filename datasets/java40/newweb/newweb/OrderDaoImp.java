package com.newweb.dao.business.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderDao;
import com.newweb.model.business.Order;

@SuppressWarnings({"unchecked","rawtypes"})
@Component("orderDao")
public class OrderDaoImp implements OrderDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean insert(Order order) {
		try {
			sessionFactory.getCurrentSession().save(order);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Order selectOrderById(String iD) {
		return (Order) sessionFactory.getCurrentSession().get(Order.class, iD);
	}

	@Override
	public List<Order> selectOrderByAcceptStatus(String acceptStatus) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order where acceptStatus=? order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		return query.setString(0, acceptStatus).list();
	}

	@Override
	public BigInteger selectUnprocessedOrderCount() {
		Query query = sessionFactory.getCurrentSession().
				createSQLQuery("select count(id) from t_order where acceptStatus=?");
		return (BigInteger) query.setString(0, "Unprocessed").uniqueResult();
	}

	/**
	 * 分页查询，List第一个对象为结果总数
	 */
	@Override
	public List selectOrderByAcceptStatus(String acceptStatus,int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order where acceptStatus=? and doneStatus='notDone' order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		query.setString(0, acceptStatus);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0,size);
		list.add(1,query.list());
		return list;
	}
 
	@Override
	public boolean updateOrder(Order order) {
		try {
			sessionFactory.getCurrentSession().update(order);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteOrder(Order order) {
		try {
			sessionFactory.getCurrentSession().delete(order);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Order> selectAllOrders() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		return query.list();
	}

	@Override
	public List selectDoneOrderByCreateDateACustomerLikeCondition(String startDate,String endDate, String customer,
			int start, int limit) {
		List list = new ArrayList();
		Query query1 = sessionFactory.getCurrentSession().createSQLQuery(
				"select * from t_order where doneStatus='done' and billID is null "
						+ "and createDate < ? and customerID like ? order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		Query query2 = sessionFactory.getCurrentSession().createSQLQuery(
				"select * from t_order where doneStatus='done' and createDate between ? and ? and customerID like ? order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		query1.setString(0, startDate);
		query1.setString(1, customer);
		query2.setString(0, startDate);
		query2.setString(1, endDate);
		query2.setString(2, customer);
		int size = query2.list().size();
		query2.setFirstResult(start);
		query2.setMaxResults(limit);
		list.add(0,size);
		List allList = query2.list();
		allList.addAll(query1.list());
		list.add(1,allList);
		return list;
	}

	@Override
	public List<Order> selectDoneOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order where doneStatus='done' and createDate between ? and ? and customerID like ? order by billID, createDate DESC,createTime DESC")
				.addEntity(Order.class);
		query.setString(0, startDate);
		query.setString(1, endDate);
		query.setString(2, customer);
		return query.list();
	}

	@Override
	public List<Order> selectProcessedOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order where doneStatus='notDone' and acceptStatus='processed' and createDate between ? and ? and customerID like ? order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		query.setString(0, startDate);
		query.setString(1, endDate);
		query.setString(2, customer);
		return query.list();
	}

	@Override
	public List<Order> selectUnprocessedOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_order where doneStatus='notDone' and acceptStatus='Unprocessed' and createDate between ? and ? and customerID like ? order by createDate DESC,createTime DESC")
				.addEntity(Order.class);
		query.setString(0, startDate);
		query.setString(1, endDate);
		query.setString(2, customer);
		return query.list();
	}

	@Override
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

}
