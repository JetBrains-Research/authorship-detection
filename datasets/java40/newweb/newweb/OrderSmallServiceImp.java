package com.newweb.service.business.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.OrderSmallDao;
import com.newweb.model.base.Small;
import com.newweb.model.business.Order;
import com.newweb.model.business.OrderPrice;
import com.newweb.model.business.OrderSmall;
import com.newweb.service.base.SmallService;
import com.newweb.service.business.OrderPriceService;
import com.newweb.service.business.OrderService;
import com.newweb.service.business.OrderSmallService;
import com.newweb.util.FileUtil;

@Component("orderSmallService")
public class OrderSmallServiceImp implements OrderSmallService {
	
	@Autowired
	private OrderSmallDao orderSmallDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SmallService smallService;
	@Autowired
	private OrderPriceService orderPriceService;


	@Override
	public boolean save(OrderSmall os) {
		OrderPrice op = new OrderPrice();
		op.setOrder(os.getOrder());
		op.setProductID(os.getSmall().getID());
		op.setType("small");
		double price = smallService.findSmallByIdBindCut(os.getSmall().getID(), 
				os.getOrder().getCustomer().getID(), null).getLsprice();
		op.setPrice(price);
		if(!orderPriceService.save(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据保存异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("orderID:"+ os.getOrder().getID()+"\n");
			sb.append("productID:"+os.getSmall().getID()+"\n");
			sb.append("type:small"+"\n");
			sb.append("price:" + price+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，小件订单价格保存失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		Small small = os.getSmall();
		small.setBuycount(small.getBuycount()+1);//被购买次数加1
		smallService.modify(small);
		int result = orderSmallDao.insert(os);
		if(result > 0)
			return true;
		return false;
	}

	@Override
	public List<Map<String, Object>> queryOrderSmallASmallByOrderID(
			String orderID) {
		Order order = orderService.findOrderById(orderID);
		List<Map<String,Object>> oslist = new ArrayList<Map<String,Object>>();
		if(order != null){
			for (OrderSmall	os : order.getOrderSmalls()) {
				if(os.getCount() == 0){
					remove(os);
					continue;
				}
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("count", os.getCount());
				map.put("small", smallService.findSmallByIdBindCut(os.getSmall().getID(),order.getCustomer().getID(),orderID));
				map.put("operation", os.getOperation());
				oslist.add(map);
			}
		}
		return oslist;
	}

	@Override
	public OrderSmall findOrderSmallById(int ID) {
		return orderSmallDao.selectOrderSmallById(ID);
	}

	@Override
	public boolean remove(OrderSmall os) {
		OrderPrice op = orderPriceService.findOrderPriceByCondition("small",os.getSmall().getID(),
				os.getOrder().getID());
		if(!orderPriceService.remove(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据删除异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("ID:" + op.getID()+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，小件订单价格同步删除失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderSmallDao.delete(os);
	}

	@Override
	public boolean modify(OrderSmall os) {
		OrderPrice op = orderPriceService.findOrderPriceByCondition("small",os.getSmall().getID(),
				os.getOrder().getID());
		double price = smallService.findSmallByIdBindCut(os.getSmall().getID(), 
				os.getOrder().getCustomer().getID(), null).getLsprice();
		op.setPrice(price);
		if(!orderPriceService.modify(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据更新异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("orderID:"+ os.getOrder().getID()+"\n");
			sb.append("productID:"+os.getSmall().getID()+"\n");
			sb.append("type:small"+"\n");
			sb.append("price:" + price+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，小件订单价格更新失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderSmallDao.update(os);
	}

	@Override
	public OrderSmall findOrderSmallBySmallIdInOrderID(int smallId, String orderid) {
		return orderSmallDao.selectOrderSmallBySmallIdInOrderId(smallId,orderid);
	}

	@Override
	public OrderSmall[] queryAllOrderSmalls() {
		List<OrderSmall> list = orderSmallDao.selectAllOrderSmalls();
		OrderSmall[] oss = new OrderSmall[list.size()];
		int count = 0;
		for (OrderSmall os : list) {
			oss[count++] = os;
		}
		return oss;
	}
	
}
