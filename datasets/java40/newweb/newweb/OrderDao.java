package com.newweb.dao.business;

import java.math.BigInteger;
import java.util.List;

import com.newweb.model.business.Order;



public interface OrderDao {

	boolean insert(Order order);

	Order selectOrderById(String iD);

	List<Order> selectOrderByAcceptStatus(String acceptStatus);

	BigInteger selectUnprocessedOrderCount();

	List<Order> selectOrderByAcceptStatus(String acceptStatus, int start,int limit);

	boolean updateOrder(Order order);

	boolean deleteOrder(Order order);

	List<Order> selectAllOrders();

	@SuppressWarnings("rawtypes")
	List selectDoneOrderByCreateDateACustomerLikeCondition(String startDate,String endDate, String customer,
			int start, int limit);

	List<Order> selectDoneOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer);

	List<Order> selectProcessedOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer);

	List<Order> selectUnprocessedOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer);

	void flush();

}
