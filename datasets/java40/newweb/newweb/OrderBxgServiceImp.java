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

import com.newweb.dao.business.OrderBxgDao;
import com.newweb.model.base.Bxg;
import com.newweb.model.business.Order;
import com.newweb.model.business.OrderBxg;
import com.newweb.model.business.OrderPrice;
import com.newweb.service.base.BxgService;
import com.newweb.service.business.OrderBxgService;
import com.newweb.service.business.OrderPriceService;
import com.newweb.service.business.OrderService;
import com.newweb.util.FileUtil;

@Component("orderBxgService")
public class OrderBxgServiceImp implements OrderBxgService {
	
	@Autowired
	private OrderBxgDao orderBxgDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BxgService	bxgService;
	@Autowired
	private OrderPriceService orderPriceService;


	@Override
	public boolean save(OrderBxg ob) {
		OrderPrice op = new OrderPrice();
		op.setOrder(ob.getOrder());
		op.setProductID(ob.getBxg().getID());
		op.setType("bxg");
		double price = bxgService.findBxgByIdBindCut(ob.getBxg().getID(), 
				ob.getOrder().getCustomer().getID(), null).getLsprice();
		op.setPrice(price);
		if(!orderPriceService.save(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据保存异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("orderID:"+ ob.getOrder().getID()+"\n");
			sb.append("productID:"+ob.getBxg().getID()+"\n");
			sb.append("type:bxg"+"\n");
			sb.append("price:" + price+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，不锈钢订单价格保存失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		Bxg bxg = ob.getBxg();
		bxg.setBuycount(bxg.getBuycount()+1);//被购买次数加1
		bxgService.modify(bxg);
		int result = orderBxgDao.insert(ob);
		if(result > 0)
			return true;
		return false;
	}

	@Override
	public List<Map<String, Object>> queryOrderBxgABxgByOrderID(
			String orderID) {
		Order order = orderService.findOrderById(orderID);
		List<Map<String,Object>> oblist = new ArrayList<Map<String,Object>>();
		for (OrderBxg	ob : order.getOrderBxgs()) {
			if(ob.getCount() == 0){
				remove(ob);
				continue;
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("count", ob.getCount());
			map.put("bxg", bxgService.findBxgByIdBindCut(ob.getBxg().getID(),order.getCustomer().getID(),orderID));
			map.put("operation", ob.getOperation());
			oblist.add(map);
		}
		return oblist;
	}

	@Override
	public OrderBxg findOrderBxgByBxgIdInOrderID(int bxgID, String orderid) {
		return orderBxgDao.selectOrderBxgByBxgIdInOrderID(bxgID,orderid);
	}

	@Override
	public boolean remove(OrderBxg ob) {
		OrderPrice op = orderPriceService.findOrderPriceByCondition("bxg",ob.getBxg().getID(),
				ob.getOrder().getID());
		if(!orderPriceService.remove(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据删除异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("ID:" + op.getID()+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，不锈钢订单价格同步删除失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderBxgDao.delete(ob);
	}

	@Override
	public boolean modify(OrderBxg ob) {
		OrderPrice op = orderPriceService.findOrderPriceByCondition("bxg",ob.getBxg().getID(),
				ob.getOrder().getID());
		double price = bxgService.findBxgByIdBindCut(ob.getBxg().getID(), 
				ob.getOrder().getCustomer().getID(), null).getLsprice();
		op.setPrice(price);
		if(!orderPriceService.modify(op)){
			StringBuilder sb = new StringBuilder();
			sb.append("数据更新异常\t" + new Date());
			sb.append("\n目标表：t_orderprice\n");
			sb.append("orderID:"+ ob.getOrder().getID()+"\n");
			sb.append("productID:"+ob.getBxg().getID()+"\n");
			sb.append("type:small"+"\n");
			sb.append("price:" + price+"\n\n");
			FileUtil.writeTextToTextFile(sb.toString(), "log" + File.separator +"database", "record.txt",true);
			JOptionPane.showConfirmDialog(null,
					"警告，小件订单价格更新失败，请查看" + "log" + File.separator +"database" + 
			File.separator + "record.txt", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return orderBxgDao.update(ob);
	}

	@Override
	public OrderBxg[] queryAllOrderBxgs() {
		List<OrderBxg> list = orderBxgDao.selectAllOrderBxgs();
		OrderBxg[] obs = new OrderBxg[list.size()];
		int count = 0;
		for (OrderBxg ob : list) {
			obs[count++] = ob;
		}
		return obs;
	}
	
}
