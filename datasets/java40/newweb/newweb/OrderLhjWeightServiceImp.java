package com.newweb.service.business.imp;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderLhjWeightDao;
import com.newweb.model.business.OrderLhjWeight;
import com.newweb.model.business.OrderPrice;
import com.newweb.service.base.LhjPriceService;
import com.newweb.service.business.OrderLhjWeightService;
import com.newweb.service.business.OrderPriceService;
import com.newweb.service.business.OrderService;
import com.newweb.util.FileUtil;

@Component("orderLhjWeightService")
public class OrderLhjWeightServiceImp implements OrderLhjWeightService {
	@Autowired
	private  OrderLhjWeightDao orderLhjWeightDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderPriceService orderPriceService;
	@Autowired
	private LhjPriceService lhjPriceService;

	@Override
	public OrderLhjWeight findOrderLhjWeightByOrderIDAndMaterialBrandID(
			String orderid, int materialBrandID) {
		return orderLhjWeightDao.selectOrderLhjWeightByOrderIDAndMaterialBrandID(orderid,materialBrandID);
	}

	@Override
	public boolean remove(OrderLhjWeight lhjWeight) {
		OrderPrice op = orderPriceService.findOrderPriceByCondition("lhj",lhjWeight.getMaterialBrand().getID(),
				lhjWeight.getOrder().getID());
		if(!orderPriceService.remove(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据删除异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("ID:" + op.getID()+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，铝合金订单价格同步删除失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderLhjWeightDao.delete(lhjWeight);
	}

	@Override
	public boolean save(OrderLhjWeight w) {
		OrderPrice op = new OrderPrice();
		op.setOrder(w.getOrder());
		op.setProductID(w.getMaterialBrand().getID());
		op.setType("lhj");
		double price = lhjPriceService.findLhjPriceByMaterialBrandIDBindCut(w.getMaterialBrand().getID(), 
				w.getOrder().getCustomer().getID(),null).getPrice();
		op.setPrice(price);
		if(!orderPriceService.save(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据保存异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("orderID:"+ w.getOrder().getID()+"\n");
			sb.append("productID:"+w.getMaterialBrand().getID()+"\n");
			sb.append("type:lhj"+"\n");
			sb.append("price:" + price+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，铝合金订单价格保存失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderLhjWeightDao.insert(w);
	}

	@Override
	public boolean modify(OrderLhjWeight lhjWeight) {
		OrderPrice op = orderPriceService.findOrderPriceByCondition("lhj",lhjWeight.getMaterialBrand().getID(),
				lhjWeight.getOrder().getID());
		double price = lhjPriceService.findLhjPriceByMaterialBrandIDBindCut(lhjWeight.getMaterialBrand().getID(), 
				lhjWeight.getOrder().getCustomer().getID(),null).getPrice();
		op.setPrice(price);
		if(!orderPriceService.modify(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据更新异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("orderID:"+ lhjWeight.getOrder().getID()+"\n");
			sb.append("productID:"+lhjWeight.getMaterialBrand().getID()+"\n");
			sb.append("type:lhj"+"\n");
			sb.append("price:" + price+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，铝合金订单价格更新失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderLhjWeightDao.update(lhjWeight);
	}

	@Override
	public OrderLhjWeight[] queryOrderLhjWeightsByOrderID(String orderid) {
		Set<OrderLhjWeight> set = orderService.findOrderById(orderid).getOrderLhjWeights();
		OrderLhjWeight[] ws = new OrderLhjWeight[set.size()];
		int count = 0;
		for (OrderLhjWeight w : set) {
			ws[count++] = w;
		}
		return ws;
	}

	@Override
	public OrderLhjWeight[] queryAllOrderLhjWeights() {
		List<OrderLhjWeight> list = orderLhjWeightDao.selectAllOrderLhjWeights();
		OrderLhjWeight[] ws = new OrderLhjWeight[list.size()];
		int count = 0;
		for (OrderLhjWeight w : list) {
			ws[count++] = w;
		}
		return ws;
	}
	
}
