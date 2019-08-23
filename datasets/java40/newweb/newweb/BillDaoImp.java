package com.newweb.dao.business.imp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.BillDao;
import com.newweb.model.business.Bill;

@Component("billDao")
public class BillDaoImp implements BillDao {

	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean insert(Bill bill) {
		try {
			sessionFactory.getCurrentSession().save(bill);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Bill bill) {
		try {
			sessionFactory.getCurrentSession().delete(bill);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Bill selectBillById(String billid) {
		return (Bill) sessionFactory.getCurrentSession().get(Bill.class, billid);
	}

	@Override
	public boolean update(Bill bill) {
		try {
			sessionFactory.getCurrentSession().update(bill);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List selectBillByStatusACreateDateACustomerLikeCondition(String status,String startDate, String endDate,
			String customer, int start, int limit) {
		List list = new ArrayList();
		Query query1 = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bill where status != '等待结帐' and status like '送货结帐' and createDate < ? order by lastModifyDate DESC,lastModifyTime DESC")
				.addEntity(Bill.class);
		Query query2 = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bill where status != '等待结帐' and status like ? and createDate between ? and ? and customerID like ? order by lastModifyDate DESC,lastModifyTime DESC")
				.addEntity(Bill.class);
		query1.setString(0, startDate);
		query2.setString(0, status);
		query2.setString(1, startDate);
		query2.setString(2, endDate);
		query2.setString(3, customer);
		int size = query2.list().size();
		query2.setFirstResult(start);
		query2.setMaxResults(limit);
		list.add(0,size);
		List allList = query2.list();
		allList.addAll(query1.list());
		list.add(1,allList);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectDistinctStatus() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select distinct status from t_bill");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bill> selectAllBills() {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bill").addEntity(Bill.class);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> selectDistinctCustomerByDateACustomer(
			String startDate, String endDate, String customer) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select distinct customerID from t_bill where status != '等待结帐' and createDate between ? and ? and customerID like ?");
		query.setString(0, startDate);
		query.setString(1, endDate);
		query.setString(2, customer);
		return  query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bill> selectBillByStatusACreateDateACustomerLikeCondition(
			String status, String startDate, String endDate, String customer) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from t_bill where status != '等待结帐' and status like ? and createDate between ? and ? and customerID like ? order by lastModifyDate DESC,lastModifyTime DESC")
				.addEntity(Bill.class);
		query.setString(0, status);
		query.setString(1, startDate);
		query.setString(2, endDate);
		query.setString(3, customer);
		return query.list();
	}


}
