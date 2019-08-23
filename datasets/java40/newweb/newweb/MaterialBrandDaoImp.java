package com.newweb.dao.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.MaterialBrandDao;
import com.newweb.model.base.MaterialBrand;
import com.newweb.model.base.TypeForBrand;

@SuppressWarnings("unchecked")
@Component("materialBrandDao")
public class MaterialBrandDaoImp implements MaterialBrandDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<MaterialBrand> selectMaterialBrandsByType(String type) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_materialbrand where valid = true and type=?")
				.addEntity(MaterialBrand.class);
		return query.setString(0, type).list();
	}

	@Override
	public MaterialBrand selectMaterialBrandById(int iD) {
		return (MaterialBrand) sessionFactory.getCurrentSession().get(MaterialBrand.class, iD);
	}

	@Override
	public TypeForBrand selectTypeForBrandById(int iD) {
		return (TypeForBrand) sessionFactory.getCurrentSession().get(TypeForBrand.class, iD);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List selectAllMaterialBrands(int start, int limit) {
		List list = new ArrayList();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_materialbrand")
				.addEntity(MaterialBrand.class);
		int size = query.list().size();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		list.add(0, size);
		list.add(1, query.list());
		return list;
	}

	@Override
	public boolean update(MaterialBrand mb) {
		try {
			sessionFactory.getCurrentSession().update(mb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public MaterialBrand selectMaterialBrandByName(String name) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_materialbrand where name=?")
				.addEntity(MaterialBrand.class);
		query.setString(0, name);
		return (MaterialBrand) query.uniqueResult();
	}

	@Override
	public boolean insert(MaterialBrand mb) {
		try {
			sessionFactory.getCurrentSession().save(mb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(MaterialBrand mb) {
		try {
			sessionFactory.getCurrentSession().delete(mb);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
