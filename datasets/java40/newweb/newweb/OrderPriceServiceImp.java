package com.newweb.service.business.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderPriceDao;
import com.newweb.model.business.OrderPrice;
import com.newweb.service.business.OrderPriceService;

@Component("orderPriceService")
public class OrderPriceServiceImp implements OrderPriceService {
	@Autowired
	private OrderPriceDao orderPriceDao;
	
	@Override
	public OrderPrice findOrderPriceByCondition(String type,
			int materialBrandID, String orderID) {
		List<OrderPrice> ops = orderPriceDao.selectOrderPriceByCondition(type,materialBrandID,orderID);
		try {
			OrderPrice op = ops.get(0);
			if(ops.size() > 1){
				for(int i = 1;i<ops.size();i++){
					OrderPrice p = ops.get(i);
					if(p.getOrder().getID().equals(op.getOrder().getID()) && p.getProductID()==op.getProductID()){
						orderPriceDao.delete(p);
					}
				}
			}
			return op;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean remove(OrderPrice op) {
		return orderPriceDao.delete(op);
	}

	@Override
	public boolean save(OrderPrice op) {
		return orderPriceDao.insert(op);
	}

	@Override
	public boolean modify(OrderPrice op) {
		return orderPriceDao.update(op);
	}

}
