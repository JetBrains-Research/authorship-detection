package ipower.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IPictureDao;
import ipower.wechat.domain.Picture;
import ipower.wechat.modal.PictureInfo;
/**
 * 图片库数据访问实现。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class PictureDaoImpl extends BaseDaoImpl<Picture> implements IPictureDao {
	/**
	 * 添加查询条件。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL.
	 * @param parameters
	 * 查询参数。
	 * @return HQL
	 * */
	protected String addWhere(PictureInfo info,String hql,Map<String, Object> parameters){
		if(info.getCode() != null && !info.getCode().trim().isEmpty()){
			hql += " and (p.code = :code) ";
			parameters.put("code", info.getCode());
		}
		if(info.getName() != null && !info.getName().trim().isEmpty()){
			hql += " and (p.name like :name) ";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if(info.getExt() != null && !info.getExt().trim().isEmpty()){
			hql += " and (p.ext like :ext) ";
			parameters.put("ext", "%" + info.getExt() + "%");
		}
		return hql;
	}
	
	@Override
	public List<Picture> findPictures(PictureInfo info) {
		String hql = "from Picture p where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}

	@Override
	public Long total(PictureInfo info) {
		String hql = "select count(*) from Picture p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}

}