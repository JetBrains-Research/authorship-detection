package com.newweb.service.business.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderDao;
import com.newweb.dao.business.OrderLhjWinPropDao;
import com.newweb.model.business.Order;
import com.newweb.model.business.OrderLhjWinProp;
import com.newweb.service.business.OrderLhjWinPropService;

@Component("orderLhjWinPropService")
public class OrderLhjWinPropServiceImp implements OrderLhjWinPropService {
	
	@Autowired
	private OrderLhjWinPropDao orderLhjWinPropDao;
	@Autowired
	private OrderDao orderDao;
	

	@Override
	public boolean save(OrderLhjWinProp orderLhjWinProp) {
		int result = orderLhjWinPropDao.insert(orderLhjWinProp);
		if(result > 0){
			return true;
		}
		return false;
	}


	@Override
	public OrderLhjWinProp[] queryOrderLhjWinPropsByOrderID(String id) {
		Order order = orderDao.selectOrderById(id);
		OrderLhjWinProp[] ops = new OrderLhjWinProp[order.getOrderLhjWinProps().size()];
		int count = 0;
		for (OrderLhjWinProp op : order.getOrderLhjWinProps()) {
			if(op.getWinCount() == 0){
				remove(op.getID());
				continue;
			}
			ops[count++] = op;
		}
		return ops;
	}


	@Override
	public OrderLhjWinProp findOrderLhjWinPropById(int ID) {
		return orderLhjWinPropDao.selectOrderLhjWinPropById(ID);
	}


	@Override
	public boolean modify(OrderLhjWinProp prop) {
		return orderLhjWinPropDao.update(prop);
	}


	@Override
	public boolean remove(int iD) {
		OrderLhjWinProp olwp = orderLhjWinPropDao.selectOrderLhjWinPropById(iD);
		return orderLhjWinPropDao.delete(olwp);
	}

	
}
