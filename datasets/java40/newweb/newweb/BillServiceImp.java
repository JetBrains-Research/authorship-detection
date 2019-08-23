package com.newweb.service.business.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.business.BillDao;
import com.newweb.model.business.Bill;
import com.newweb.model.business.Order;
import com.newweb.service.business.BillService;
import com.newweb.service.business.OrderService;
import com.newweb.util.CustomerMath;
import com.newweb.util.DateUtil;

@Component("billService")
public class BillServiceImp implements BillService {
	
	@Autowired
	private BillDao billDao;
	@Autowired
	private OrderService orderService;

	@Override
	public boolean save(Bill bill) {
		bill.setCreateDate(DateUtil.getLocationCurrentDate());
		bill.setCreateTime(DateUtil.getLocationCurrentTime());
		bill.setLastModifyDate(DateUtil.getLocationCurrentDate());
		bill.setLastModifyTime(DateUtil.getLocationCurrentTime());
		return billDao.insert(bill);
	}

	@Override
	public boolean remove(Bill bill) {
		return billDao.delete(bill);
	}

	@Override
	public Order[] queryOrdersByBillId(String billid) {
		Bill bill = billDao.selectBillById(billid);
		Set<Order> osSet = bill.getOrder();
		Order[]	os = new Order[osSet.size()];
		int count = 0;
		for (Order order : osSet) {
			os[count++] = order;
		}
		return os;
	}

	@Override
	public Bill findBillById(String billid) {
		return billDao.selectBillById(billid);
	}

	@Override
	public double getBillAccountByBillID(String billid) {
		Order[] orders = queryOrdersByBillId(billid);
		double account = 0;
		for (Order order : orders) {
			account = CustomerMath.add(account, orderService.getOrderAccountByOrderID(order.getID()));
		}
		return account;
	}

	@Override
	public boolean modify(Bill bill) {
		bill.setLastModifyDate(DateUtil.getLocationCurrentDate());
		bill.setLastModifyTime(DateUtil.getLocationCurrentTime());
		return billDao.update(bill);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryBillStoreByStatusACreateDateTimeACustomerJsons(String status,String startDate, String endDate,
			String customer,int start, int limit) {
		List returnList = new ArrayList();
		List list = billDao.selectBillByStatusACreateDateACustomerLikeCondition(status,startDate,endDate,customer,start, limit);
		int count = 0;
		int size = (Integer) list.get(0);
		List billList = (List) list.get(1);
		Bill[]	os = new Bill[billList.size()];
		for (Object bill : billList) {
			Bill b = (Bill) bill;
			os[count++] = b;
		}
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(os);//第二个对象保存实体数组
		return returnList;
	}

	@Override
	public String[] queryDistinctStatus() {
		List<String> list = billDao.selectDistinctStatus();
		String[] s = new String[list.size()];
		int count = 0;
		for (String string : list) {
			s[count++] = string;
		}
		return s;
	}

	@Override
	public Bill[] queryAllBills() {
		List<Bill> list = billDao.selectAllBills();
		Bill[] bs = new Bill[list.size()];
		int count = 0;
		for (Bill b : list) {
			bs[count++] = b;
		}
		return bs;
	}

	@Override
	public Integer[] queryDistinctCustomerByDateACustomer(String startDate,
			String endDate, String customer) {
		List<Integer> list = billDao.selectDistinctCustomerByDateACustomer(startDate,endDate,customer);
		Integer[] ids = new Integer[list.size()];
		int count = 0;
		for (Integer id : list) {
			ids[count++] = id;
		}
		return ids;
	}

	@Override
	public Bill[] queryBillStoreByStatusACreateDateTimeACustomerJsons(
			String status, String startDate, String endDate, String customer) {
		List<Bill> billList = billDao.selectBillByStatusACreateDateACustomerLikeCondition(status,startDate,endDate,customer);
		int count = 0;
		Bill[]	os = new Bill[billList.size()];
		for (Bill bill : billList) {
			os[count++] = bill;
		}
		return os;
	}

	@Override
	public String modifyBillsToConform(String[] ids, double money) {
		Bill newBill = new Bill();
		newBill.setAccount(money);
		newBill.setCreateDate(DateUtil.getLocationCurrentDate());
		newBill.setCreateTime(DateUtil.getLocationCurrentTime());
		newBill.setCustomer(billDao.selectBillById(ids[0]).getCustomer());
		newBill.setID(UUID.randomUUID().toString());
		newBill.setLastModifyDate(DateUtil.getLocationCurrentDate());
		newBill.setLastModifyTime(DateUtil.getLocationCurrentTime());
		newBill.setRealIncomeMoney(0);
		newBill.setReceivableMoney(money);
		newBill.setStatus("欠款");
		double realIn = 0;
		//信息收集
		for (String id : ids) {
			Bill bill = billDao.selectBillById(id);
			realIn = CustomerMath.add(realIn, bill.getRealIncomeMoney());
		}
		newBill.setRemark("本账单为多个欠款账单整合后的账单。整合时，该批账单共实收款：" + realIn + "元。【注：由于是人工整合，此账单的总额与应收款可能会有差距，此为正常情况】");
		if(!save(newBill)){
			throw new RuntimeException("整合失败：新Bill创建失败！");
		}
		for (String id : ids) {
			Order[] orders = queryOrdersByBillId(id);
			for (Order order : orders) {
				order.setBill(newBill);
				if(!orderService.modifyOrder(order)){
					throw new RuntimeException("整合失败：订单的Bill对象搬家失败");
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("整合成功，新账单信息如下：</br>");
		sb.append("客户：" + newBill.getCustomer().getName() + "</br>");
		sb.append("账单日期:" + newBill.getCreateDate() + "</br>");
		sb.append("账单全款金额：" + newBill.getReceivableMoney() + "</br>");
		return sb.toString();
	}
	
}
