package com.newweb.service.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.LhjPriceDao;
import com.newweb.model.base.LhjPrice;
import com.newweb.model.business.LhjPriceCut;
import com.newweb.model.business.OrderPrice;
import com.newweb.service.base.LhjPriceService;
import com.newweb.service.business.LhjPriceCutService;
import com.newweb.service.business.OrderPriceService;

@Component("lhjPriceService")
public class LhjPriceServiceImp implements LhjPriceService {
	
	@Autowired
	private LhjPriceDao lhjPriceDao;
	@Autowired
	private LhjPriceCutService lhjPriceCutService;
	@Autowired
	private OrderPriceService orderPriceService;

	@Override
	public LhjPrice findLhjPriceByMaterialBrandIDBindCut(int materialBrandID,int customerID,String orderID) {
		LhjPrice price = lhjPriceDao.selectLhjPriceByMaterialBrandID(materialBrandID);
		LhjPrice returnPrice = (LhjPrice) price.clone();
		if(orderID != null){
			OrderPrice op = orderPriceService.findOrderPriceByCondition("lhj",materialBrandID,orderID);
			if(op != null){
				returnPrice.setPrice(op.getPrice());
				return returnPrice;
			}else{
				LhjPriceCut cut = null;
				try {
					cut = lhjPriceCutService.findLhjPriceCutByCustomerIDAndPriceID(returnPrice.getID(),customerID);
				} catch (Exception e) {
					return returnPrice;
				}
				if(cut != null){
					returnPrice.setPrice(cut.getPrice());
				}
				return returnPrice;
			}
		}else{
			LhjPriceCut cut = null;
			try {
				cut = lhjPriceCutService.findLhjPriceCutByCustomerIDAndPriceID(returnPrice.getID(),customerID);
			} catch (Exception e) {
				return returnPrice;
			}
			if(cut != null){
				returnPrice.setPrice(cut.getPrice());
			}
			return returnPrice;
		}
	}
	
	@Override
	public LhjPrice findLhjPriceByMaterialBrandID(int materialBrandID) {
		LhjPrice price = lhjPriceDao.selectLhjPriceByMaterialBrandID(materialBrandID);
		return price;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryAllLhjPrices(int start, int limit) {
		List list = lhjPriceDao.selectAllLhjPrices(start,limit);
		int size =(Integer) list.get(0);
		List sList = (List) list.get(1);
		LhjPrice[] lps = new LhjPrice[sList.size()];
		int count = 0;
		for (Object o : sList) {
			LhjPrice lp = (LhjPrice) o;
			lps[count++] = lp;
		}
		List returnList = new ArrayList();
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(lps);//第二个对象保存实体数组
		return returnList;
	}

	@Override
	public LhjPrice findLhjPriceByID(int lid) {
		return lhjPriceDao.selectLhjPriceByID(lid);
	}

	@Override
	public boolean modify(LhjPrice lhjp) {
		return lhjPriceDao.update(lhjp);
	}

	@Override
	public boolean save(LhjPrice lhjp) {
		return lhjPriceDao.insert(lhjp);
	}

	@Override
	public boolean remove(LhjPrice lhjp) {
		return lhjPriceDao.delete(lhjp);
	}
	
}
