package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.SupplierDao;
import com.newweb.model.base.Supplier;

@Component("supplierDao")
public class SupplierDaoImp implements SupplierDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supplier> selectAllSuppliers() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_supplier where valid=true")
				.addEntity(Supplier.class);
		return query.list();
	}

	@Override
	public Supplier selectSupplierById(int ID) {
		return (Supplier) sessionFactory.getCurrentSession().get(Supplier.class, ID);
	}

	@Override
	public boolean update(Supplier supplier) {
		try {
			sessionFactory.getCurrentSession().update(supplier);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Supplier selectSupplierByName(String name) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_supplier where name=?)")
				.addEntity(Supplier.class);
		query.setString(0, name);
		return (Supplier) query.list();
	}

	@Override
	public boolean insert(Supplier supplier) {
		try {
			sessionFactory.getCurrentSession().save(supplier);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List selectAllSuppliers(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_supplier")
				.addEntity(Supplier.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public boolean delete(Supplier supplier) {
		try {
			sessionFactory.getCurrentSession().delete(supplier);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
