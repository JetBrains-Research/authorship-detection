package com.newweb.service.base.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.SupplierDao;
import com.newweb.model.base.Bxg;
import com.newweb.model.base.MaterialBrand;
import com.newweb.model.base.Small;
import com.newweb.model.base.Supplier;
import com.newweb.service.base.BxgService;
import com.newweb.service.base.MaterialBrandService;
import com.newweb.service.base.SmallService;
import com.newweb.service.base.SupplierService;

@Component("supplierService")
public class SupplierServiceImp implements SupplierService {
	
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private BxgService bxgService;
	@Autowired
	private SmallService smallService;
	@Autowired
	private MaterialBrandService materialBrandService;
	
	@Override
	public Supplier[] queryAllSuppliers() {
		List<Supplier> list = supplierDao.selectAllSuppliers();
		Supplier[] ss = new Supplier[list.size()];
		int count = 0;
		for (Supplier s : list) {
			ss[count++] = s;
		}
		return ss;
	}

	@Override
	public Supplier findSupplierById(int ID) {
		return supplierDao.selectSupplierById(ID);
	}

	@Override
	public boolean modify(Supplier supplier) {
		return supplierDao.update(supplier);
	}

	@Override
	public Supplier querySupplierByName(String name) {
		return supplierDao.selectSupplierByName(name);
	}

	@Override
	public boolean save(Supplier supplier) {
		return supplierDao.insert(supplier);
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public List queryAllSuppliers(int start, int limit) {
		List list = supplierDao.selectAllSuppliers(start,limit);
		int size =(Integer) list.get(0);
		List cList = (List) list.get(1);
		Supplier[] suppliers = new Supplier[cList.size()];
		int count = 0;
		for (Object c : cList) {
			Supplier supplier = (Supplier) c;
			suppliers[count++] = supplier;
		}
		List returnList = new ArrayList();
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(suppliers);//第二个对象保存实体数组
		return returnList;
	}

	@Override
	public boolean isUsed(int id) {
		Supplier supplier = supplierDao.selectSupplierById(id);
		Set<Bxg> bxgs = supplier.getBxgs();
		for (Bxg bxg : bxgs) {
			if(bxg.getOrderBxgs().size() > 0)
				return true;
		}
		Set<Small> smalls = supplier.getSmalls();
		for (Small small : smalls) {
			if(small.getOrderSmalls().size() > 0)
				return true;
		}
		Set<MaterialBrand> materialBrands = supplier.getMaterialBrands();
		for (MaterialBrand materialBrand : materialBrands) {
			if(materialBrand.getOrderLhjs().size() > 0)
				return true;
		}
		return false;
	}

	@Override
	public boolean remove(Supplier supplier) {
		return supplierDao.delete(supplier);
	}

	@Override
	public String modifyToUnvalid(int id) {
		StringBuilder sb = new StringBuilder();
		Supplier supplier = supplierDao.selectSupplierById(id);
		int i1 = 0;
		int i2 = 0;
		Set<Bxg> bxgs = supplier.getBxgs();
		for (Bxg bxg : bxgs) {
			bxg.setValid(false);
			if(bxgService.modify(bxg)){
				i1++;
			}else{
				throw new RuntimeException("供应商： " + supplier.getName() + " 【不可用】状态更改失败</br>原因：不锈钢【" + 
						bxg.getName() + bxg.getNorms() + "】状态更改失败</br>");
			}
		}
		Set<Small> smalls = supplier.getSmalls();
		for (Small small : smalls) {
			small.setValid(false);
			if(smallService.modify(small)){
				i2++;
			}else{
				throw new RuntimeException("供应商： " + supplier.getName() + " 【不可用】状态更改失败</br>原因：小件【" + 
						small.getName() +  "】状态更改失败</br>");
			}
		}
		
		supplier.setValid(false);
		if(supplierDao.update(supplier)){
			sb.append("供应商： "+supplier.getName() + " 已更改为【不可用】状态!</br>");
		}else{
			throw new RuntimeException("供应商： " + supplier.getName() + " 【不可用】状态更改失败</br>");
		}
		if(i1 !=0 ){
			sb.append("已同步更改" + i1 +"个不锈钢为【不可用】状态</br>");
		}
		if(i2 !=0 ){
			sb.append("已同步更改" + i2 +"个小件为【不可用】状态</br>");
		}
		Set<MaterialBrand> materialBrands = supplier.getMaterialBrands();
		for (MaterialBrand materialBrand : materialBrands) {
			try {
				sb.append(materialBrandService.modifyToUnvalid(materialBrand.getID()));
			} catch (Exception e) {
				sb.append(e.getMessage());
			}
		}
		return sb.toString();
	}

	@Override
	public String modifyToValid(int id) {
		StringBuilder sb = new StringBuilder();
		Supplier supplier = supplierDao.selectSupplierById(id);
		int i1 = 0;
		int i2 = 0;
		Set<Bxg> bxgs = supplier.getBxgs();
		for (Bxg bxg : bxgs) {
			bxg.setValid(true);
			if(bxgService.modify(bxg)){
				i1++;
			}else{
				throw new RuntimeException("供应商： " + supplier.getName() + " 【可用】状态更改失败</br>原因：不锈钢【" + 
						bxg.getName() + bxg.getNorms() + "】状态更改失败</br>");
			}
		}
		Set<Small> smalls = supplier.getSmalls();
		for (Small small : smalls) {
			small.setValid(true);
			if(smallService.modify(small)){
				i2++;
			}else{
				throw new RuntimeException("供应商： " + supplier.getName() + " 【可用】状态更改失败</br>原因：小件【" + 
						small.getName() +  "】状态更改失败</br>");
			}
		}
		
		supplier.setValid(true);
		if(supplierDao.update(supplier)){
			sb.append("供应商： "+supplier.getName() + " 已更改为【可用】状态!</br>");
		}else{
			throw new RuntimeException("供应商： " + supplier.getName() + " 【可用】状态更改失败</br>");
		}
		if(i1 !=0 ){
			sb.append("已同步更改" + i1 +"个不锈钢为【可用】状态</br>");
		}
		if(i2 !=0 ){
			sb.append("已同步更改" + i2 +"个小件为【可用】状态</br>");
		}
		Set<MaterialBrand> materialBrands = supplier.getMaterialBrands();
		for (MaterialBrand materialBrand : materialBrands) {
			try {
				sb.append(materialBrandService.modifyToValid(materialBrand.getID()));
			} catch (Exception e) {
				sb.append(e.getMessage());
			}
		}
		return sb.toString();
	}
	
}
