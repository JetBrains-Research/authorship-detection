package com.newweb.service.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.CustomerDao;
import com.newweb.model.base.Customer;
import com.newweb.service.base.CustomerService;

@Component("customerService")
public class CustomerServiceImp implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer findCustomerByID(int id) {
		return customerDao.selectCustomerByID(id);
	}

	@Override
	public Customer[] queryCustomerByName(String name) {
		List<Customer> list = customerDao.selectCustomerByName(name);
		Customer[] cs = new Customer[list.size()];
		int count = 0;
		for (Customer customer : list) {
			cs[count++] = customer;
		}
		return cs;
	}

	@Override
	public Customer[] queryAllCustomers() {
		List<Customer> list = customerDao.selectAllCustomers();
		Customer[] cs = new Customer[list.size()];
		int count = 0;
		for (Customer c : list) {
			cs[count++] = c;
		}
		return cs;
	}

	@Override
	public Customer[] queryCustomerByLikePy(String py) {
		List<Customer> list = customerDao.selectCustomersByLikePy(py);
		Customer[] cs = new Customer[list.size()];
		int count = 0;
		for (Customer c : list) {
			cs[count++] = c;
		}
		return cs;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryAllCustomers(int start, int limit) {
		List list = customerDao.selectAllCustomers(start,limit);
		int size =(Integer) list.get(0);
		List cList = (List) list.get(1);
		Customer[] cs = new Customer[cList.size()];
		int count = 0;
		for (Object c : cList) {
			Customer cus = (Customer) c;
			cs[count++] = cus;
		}
		List returnList = new ArrayList();
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(cs);//第二个对象保存实体数组
		return returnList;
	}

	@Override
	public boolean save(Customer c) {
		return customerDao.insert(c);
	}

	@Override
	public boolean modify(Customer c) {
		return customerDao.update(c);
	}

	@Override
	public boolean remove(Customer customer) {
		return customerDao.delete(customer);
	}

}
