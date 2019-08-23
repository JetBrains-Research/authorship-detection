package com.newweb.service.business;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.newweb.model.business.Order;
import com.newweb.model.business.OrderBxg;
import com.newweb.model.business.OrderBxgFdw;
import com.newweb.model.business.OrderLhjWeight;
import com.newweb.model.business.OrderLhjWinProp;
import com.newweb.model.business.OrderOther;
import com.newweb.model.business.OrderSmall;

@SuppressWarnings("rawtypes")
public interface OrderService {

	/**
	 * 添加订单服务
	 * 会同时向数据库中写入该订单关联的铝窗、小见、不锈钢等数据
	 */
	boolean addOrder(Order order, List<OrderLhjWinProp> orderLhjWinPropsList, List<Map<String, String>> smallList, List<Map<String, String>> bxgList, List<OrderBxgFdw> orderBxgFdws);

	Order findOrderById(String string);
	
	/**
	 * 保存订单服务
	 * 只是单方面的保存Order实体
	 */
	boolean saveOrder(Order order);

	/**
	 * 获取分页查询结果，第一项为结果总数，第二个为实体数组
	 */
	Order[] queryOrderByAcceptStatus(String string);
	
	List queryOrderByAcceptStatus(String string,int start, int limit);

	BigInteger getUnprocessedOrderCount();

	OrderLhjWinProp[] queryOrderLhjWinPropsByOrderID(String orderid);

	boolean modifyOrder(Order order);

	boolean removeOrder(Order order);

	Order[] queryAllOrders();

	List queryDoneOrderByCreateDateACustomerLikeCondition(String startDate,String endDate, String customer,
			int start, int limit);

	OrderLhjWeight[] queryOrderLhjWeightsByOrderID(String id);

	OrderSmall[] queryOrderSmallsByOrderID(String id);

	OrderBxg[] queryOrderBxgsByOrderID(String orderID);

	double getOrderLhjWinArea(String orderID);

	double getOrderBxgFdwArea(String orderID);

	OrderOther[] queryOrderOthersByOrderId(String id);

	double getOrderAccountByOrderID(String id);

	Order[] queryDoneOrderByCreateDateACustomerLikeCondition(String startDate,
			String endDate, String customer);

	Order[] queryProcessedOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer);

	Order[] queryUnprocessedOrderByCreateDateACustomerLikeCondition(
			String startDate, String endDate, String customer);

	String getOrderSimpleContent(String ID);

	void flush();
	
}
