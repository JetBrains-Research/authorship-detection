package com.newweb.service.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.SmallDao;
import com.newweb.model.base.Small;
import com.newweb.model.business.OrderPrice;
import com.newweb.model.business.SmallPriceCut;
import com.newweb.service.base.SmallService;
import com.newweb.service.business.OrderPriceService;
import com.newweb.service.business.SmallPriceCutService;

@Component("smallService")
public class SmallServiceImp implements SmallService {
	
	@Autowired
	private SmallDao smallDao;
	@Autowired
	private SmallPriceCutService smallPriceCutService;
	@Autowired
	private OrderPriceService orderPriceService;
	

	@Override
	public String[] queryDistinctTypes() {
		List<String> list = smallDao.selectDistinctType();
		String[] types = new String[list.size()];
		int count = 0;
		for (String string : list) {
			types[count++] = string;
		}
		return types;
	}


	@Override
	public Small[] querySmallsByType(String type) {
		List<Small> list = null;
		if(type.equals("全部")){
			list = smallDao.selectAllSmalls();
		}else{
			list = smallDao.selectSmallsByType(type);
		}
		Small[] smalls = new Small[list.size()];
		int count = 0;
		for (Small small : list) {
			smalls[count++] = small;
		}
		return smalls;
	}


	@Override
	public Small findSmallByIdBindCut(int smallID,int customerID,String orderID) {
		Small small =  smallDao.selectSmallById(smallID);
		Small returnSmall = (Small) small.clone();
		if(orderID != null){
			OrderPrice op = orderPriceService.findOrderPriceByCondition("small",smallID,orderID);
			if(op != null){
				returnSmall.setLsprice(op.getPrice());
				return returnSmall;
			}else{
				SmallPriceCut cut = null;
				try {
					cut = smallPriceCutService.findSmallPriceCutBySmallIDAndCustomerID(smallID,customerID);
				} catch (Exception e) {
					return returnSmall;
				}
				if(cut != null){
					returnSmall.setLsprice(cut.getPrice());
				}
				return returnSmall;
			}
		}else{
			SmallPriceCut cut = null;
			try {
				cut = smallPriceCutService.findSmallPriceCutBySmallIDAndCustomerID(smallID,customerID);
			} catch (Exception e) {
				return returnSmall;
			}
			if(cut != null){
				returnSmall.setLsprice(cut.getPrice());
			}
			return returnSmall;
		}
	}
	
	@Override
	public Small findSmallById(int smallID) {
		Small small =  smallDao.selectSmallById(smallID);
		return small;
	}


	@Override
	public boolean modify(Small small) {
		int result = smallDao.update(small);
		if(result > 0)
			return true;
		return false;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryAllSmalls(int start, int limit) {
		List list = smallDao.selectAllSmalls(start,limit);
		int size =(Integer) list.get(0);
		List sList = (List) list.get(1);
		Small[] ss = new Small[sList.size()];
		int count = 0;
		for (Object s : sList) {
			Small sm = (Small) s;
			ss[count++] = sm;
		}
		List returnList = new ArrayList();
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(ss);//第二个对象保存实体数组
		return returnList;
	}


	@Override
	public Small findSmallByName(String name) {
		return smallDao.selectSmallByName(name);
	}


	@Override
	public boolean save(Small small) {
		return smallDao.insert(small);
	}


	@Override
	public boolean remove(Small small) {
		return smallDao.delete(small);
	}


	@Override
	public int getOrderSmallsCount(int id) {
		Small small = smallDao.selectSmallById(id);
		return small.getOrderSmalls().size();
	}

	
}
