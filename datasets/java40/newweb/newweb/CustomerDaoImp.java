package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.CustomerDao;
import com.newweb.model.base.Customer;

@SuppressWarnings("unchecked")
@Component("customerDao")
public class CustomerDaoImp implements CustomerDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public Customer selectCustomerByID(int id) {
		return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
	}


	@Override
	public List<Customer> selectCustomerByName(String name) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_customer where name=? order by py")
				.addEntity(Customer.class);
		return query.setString(0, name).list();
	}


	@Override
	public List<Customer> selectAllCustomers() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_customer where valid=true")
				.addEntity(Customer.class);
		return query.list();
	}


	@Override
	public List<Customer> selectCustomersByLikePy(String py) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_customer where valid=true and py like ?")
				.addEntity(Customer.class);
		return query.setString(0, py).list();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List selectAllCustomers(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_customer order by py")
				.addEntity(Customer.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}


	@Override
	public boolean insert(Customer c) {
		try {
			sessionFactory.getCurrentSession().save(c);
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	@Override
	public boolean update(Customer c) {
		try {
			sessionFactory.getCurrentSession().update(c);
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	@Override
	public boolean delete(Customer customer) {
		try {
			sessionFactory.getCurrentSession().delete(customer);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
