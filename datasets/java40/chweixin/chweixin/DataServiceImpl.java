package ipower.wechat.service.impl;

import ipower.model.DataGrid;
import ipower.service.IDataService;

import java.util.ArrayList;
import java.util.List;
/**
 * 数据服务抽象类.
 * @author 杨勇.
 * @since 2013-11-27.
 * */
public abstract class DataServiceImpl<K,T> implements IDataService<T> {
	/**
	 * 获取列表数据。
	 * @param info
	 * 	查询条件。
	 * */
	@Override
	public DataGrid<T> datagrid(T info) {
		DataGrid<T> grid = new DataGrid<T>();
		grid.setRows(this.changeModel(this.find(info)));
		grid.setTotal(this.total(info));
		return grid;
	}
	
	/**
	 * 查询数据。
	 * @param info
	 * 	查询条件。
	 * @return 结构数据集合。
	 * */
	protected abstract List<K> find(T info);
	/**
	 * 数据转换。
	 * @param list
	 * 	查询结果集合。
	 * @return 转换结果集合。
	 * */
	protected List<T> changeModel(List<K> list){
		List<T> results = new ArrayList<>();
		if(list != null && list.size() > 0){
			for(K data : list){
				T info = this.changeModel(data);
				if(info != null){
					results.add(info);
				}
			}
		}
		return results;
	}
	/**
	 * 数据类型转换。
	 * @param data
	 * 	数据实体对象。
	 * @return 结果数据。
	 * */
	protected abstract T changeModel(K data);
	/**
	 * 统计查询数据。
	 * @param info
	 * 	查询条件。
	 * @return 统计结果。
	 * */
	protected abstract Long total(T info);
	/**
	 * 数据更新。
	 * @param info
	 * 	更新实体。
	 * @return 更新后的结果。
	 * */
	@Override
	public abstract T update(T info);
	/**
	 * 删除数据。
	 * @param ids
	 * 	主键值数组。
	 * */
	@Override
	public abstract void delete(String[] ids);
}