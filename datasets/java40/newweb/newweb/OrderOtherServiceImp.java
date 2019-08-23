package com.newweb.service.business.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderOtherDao;
import com.newweb.model.business.OrderOther;
import com.newweb.service.business.OrderOtherService;

@Component("orderOtherService")
public class OrderOtherServiceImp implements OrderOtherService {
	
	@Autowired
	private OrderOtherDao orderOtherDao;

	@Override
	public boolean save(OrderOther other) {
		return orderOtherDao.insert(other);
	}

	@Override
	public OrderOther[] queryOrderOthersByOrderID(String orderid) {
		List<OrderOther> list = orderOtherDao.selectOrderOthersByOrderID(orderid);
		OrderOther[] others = new OrderOther[list.size()];
		int count = 0;
		for (OrderOther other : list) {
			if(other.getCount() == 0){
				remove(other);
				continue;
			}
			others[count++] = other;
		}
		return others;
	}

	@Override
	public OrderOther findOrderOtherById(int id) {
		return orderOtherDao.selectOrderOtherById(id);
	}

	@Override
	public boolean modify(OrderOther other) {
		return orderOtherDao.update(other);
	}

	@Override
	public boolean remove(OrderOther other) {
		return orderOtherDao.delete(other);
	}
}
