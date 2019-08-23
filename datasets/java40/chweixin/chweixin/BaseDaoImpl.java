package ipower.wechat.dao.impl;

import ipower.wechat.dao.IBaseDao;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
/**
 * 数据操作实现类.
 * @author 杨勇.
 * @since 2013-11-27.
 * */
public class BaseDaoImpl<T> implements IBaseDao<T> {
	private SessionFactory sessionFactory;
	/**
	 * 设置SessionFactory。
	 * @param sessionFactory
	 * 	Hibernate Session 对象。
	 * */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取当前会话。
	 * @return 当前会话。
	 * */
	protected Session getCurrentSession(){
		return this.sessionFactory == null ? null : this.sessionFactory.getCurrentSession();
	}
	/**
	 * 加载指定主键对象。
	 * @param c
	 * 	对象类型。
	 * @param id
	 * 	主键值。
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Class<T> c, Serializable id) {
		if(c != null && id != null){
			return (T)this.getCurrentSession().get(c, id);
		}
		return null;
	}
	/**
	 * 保存新增对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public Serializable save(T data) {
		if(data != null){
			return this.getCurrentSession().save(data);
		}
		return null;
	}
	/**
	 * 更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void update(T data) {
		if(data != null){
			this.getCurrentSession().update(data);
		}
	}
	/**
	 * 保存或更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void saveOrUpdate(T data) {
		if(data != null) this.getCurrentSession().saveOrUpdate(data);
	}
	/**
	 * 删除对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void delete(T data) {
		if(data != null){
			this.getCurrentSession().delete(data);
		}
	}
	/**
	 * 查找对象集合。
	 * @param hql
	 * 	HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @param page
	 * 	页码。
	 * @param rows
	 * 	页数据量
	 * <pre>
	 * 	当page与rows同时为null时，则查询全部数据。
	 * </pre>
	 * @return 结果数据集合。
	 * */
	@SuppressWarnings("unchecked") 
	protected List<T> find(String hql, Map<String, Object> parameters,Integer page, Integer rows) {
		if(hql == null || hql.isEmpty()) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			if(parameters != null && parameters.size() > 0){
				for(String key : parameters.keySet()){
					query.setParameter(key, parameters.get(key));
				}
			}
			if((page == null) && (rows == null))return query.list();
			if((page == null) || (page < 1)) page = 1;
			if((rows == null) || (rows < 10)) rows = 10;
			return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		}
		return null;
	}
	/**
	 * 统计数据总数。
	 * @param hql
	 *  HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @return 数据总数。
	 * */
	protected Long count(String hql, Map<String, Object> parameters) {
		if(hql == null || hql.isEmpty()) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			if(parameters != null && parameters.size() > 0){
				for(String key : parameters.keySet()){
					query.setParameter(key, parameters.get(key));
				}
			}
			return (Long)query.uniqueResult();
		}
		return null;
	}
}