package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.TypeForBrandDao;
import com.newweb.model.base.TypeForBrand;

@Component("typeForBrandDao")
public class TypeForBrandDaoImp implements TypeForBrandDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TypeForBrand> selectTypeForBrandByMaterialBrand(String brandID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_typeforbrand where valid=true and materialbrandID=?")
				.addEntity(TypeForBrand.class);
		return query.setString(0, brandID).list();
	}

	@Override
	public boolean update(TypeForBrand typeForBrand) {
		try {
			sessionFactory.getCurrentSession().update(typeForBrand);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List selectAllTyepForBrands(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_typeforbrand")
				.addEntity(TypeForBrand.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public TypeForBrand selectTypeForBrandByID(int typeID) {
		return (TypeForBrand) sessionFactory.getCurrentSession().get(TypeForBrand.class, typeID);
	}

	@Override
	public TypeForBrand selectTypeForBrandByCondition(String name,
			int materialBrandID) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bxg where name=? and materialbrandID=?")
				.addEntity(TypeForBrand.class);
		query.setString(0, name);
		query.setInteger(1, materialBrandID);
		return (TypeForBrand) query.uniqueResult();
	}

	@Override
	public boolean insert(TypeForBrand type) {
		try {
			sessionFactory.getCurrentSession().save(type);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(TypeForBrand type) {
		try {
			sessionFactory.getCurrentSession().delete(type);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
