package com.newweb.service.base.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newweb.dao.base.TypeForBrandDao;
import com.newweb.model.base.TypeForBrand;
import com.newweb.service.base.TypeForBrandService;

@Component("typeForBrandService")
public class TypeForBrandServiceImp implements TypeForBrandService {
	@Autowired
	private TypeForBrandDao typeForBrandDao;

	@Override
	public TypeForBrand[] queryTypeForBrandByMaterialBrand(String brandID) {
		List<TypeForBrand> list = typeForBrandDao.selectTypeForBrandByMaterialBrand(brandID);
		TypeForBrand[] tfbs = new TypeForBrand[list.size()];
		int count = 0;
		for (TypeForBrand tfb : list) {
			tfbs[count++] = tfb;
		}
		return tfbs;
	}

	@Override
	public boolean modify(TypeForBrand typeForBrand) {
		return typeForBrandDao.update(typeForBrand);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List queryAllTypeForBrands(int start, int limit) {
		List list = typeForBrandDao.selectAllTyepForBrands(start,limit);
		int size =(Integer) list.get(0);
		List cList = (List) list.get(1);
		TypeForBrand[] types = new TypeForBrand[cList.size()];
		int count = 0;
		for (Object c : cList) {
			TypeForBrand type = (TypeForBrand) c;
			types[count++] = type;
		}
		List returnList = new ArrayList();
		returnList.add(size);//第一个对象保存结果总数
		returnList.add(types);//第二个对象保存实体数组
		return returnList;
	}

	@Override
	public TypeForBrand findTypeForBrandById(int typeID) {
		return typeForBrandDao.selectTypeForBrandByID(typeID);
	}

	@Override
	public TypeForBrand findTypeForBrandByCondition(String name,
			int materialBrandID) {
		return typeForBrandDao.selectTypeForBrandByCondition(name,materialBrandID);
	}

	@Override
	public boolean save(TypeForBrand type) {
		return typeForBrandDao.insert(type);
	}

	@Override
	public int getOrderLhjWinPropsCount(int id) {
		TypeForBrand type = typeForBrandDao.selectTypeForBrandByID(id);
		return type.getOrderLhjWinProps().size();
	}

	@Override
	public boolean remove(TypeForBrand type) {
		return typeForBrandDao.delete(type);
	}
}
