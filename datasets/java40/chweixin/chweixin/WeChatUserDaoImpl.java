package ipower.wechat.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IWeChatUserDao;
import ipower.wechat.domain.WeChatUser;
import ipower.wechat.modal.WeChatUserInfo;

/**
 * 微信关注用户数据实现类。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class WeChatUserDaoImpl extends BaseDaoImpl<WeChatUser> implements IWeChatUserDao {
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
	protected String addWhere(WeChatUserInfo info, String hql, Map<String, Object> parameters){
		if(info.getAccountId() != null && !info.getAccountId().trim().isEmpty()){
			hql += " and (w.account.id = :accountId) ";
			parameters.put("accountId", info.getAccountId());
		}
		if(info.getAccountName() != null && !info.getAccountName().trim().isEmpty()){
			hql += " and (w.account.name like :accountName or w.account.account like :accountName) ";
			parameters.put("accountName", "%" + info.getAccountName() + "%");
		}
		if(info.getUserName() != null && !info.getUserName().trim().isEmpty()){
			hql += " and (w.userName like :userName or w.userSign like :userName) ";
			parameters.put("userName", "%"+ info.getUserName() +"%");
		}
		if(info.getOpenId() != null && !info.getOpenId().trim().isEmpty()){
			hql += " and (w.openId = :openId)";
			parameters.put("openId", info.getOpenId());
		}
		if(info.getStatus() != null && info.getStatus() != 0){
			hql += " and (w.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	
	@Override
	public List<WeChatUser> findUsers(WeChatUserInfo info) {
		String hql = "from WeChatUser w where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			if(info.getSort().equalsIgnoreCase("accountName")){
				info.setSort("account.name");
			}
			hql += " order by w." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}

	@Override
	public Long total(WeChatUserInfo info) {
		String hql = "select count(*) from WeChatUser w where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	
	/**
	 * 重载加载实体数据。
	 * @param c
	 * 实体数据类型。
	 * @param id
	 * 带联合主键的WeChatUserInfo对象。
	 * @return
	 * 实体对象。
	 * */
	@Override
	public WeChatUser load(Class<WeChatUser> c, Serializable id){
		if(id == null) return null;
		if(id instanceof WeChatUserInfo){
			WeChatUserInfo info = (WeChatUserInfo)id;
			return this.loadUser(info.getAccountId(), info.getOpenId());
		}
		return null;
	}

	@Override
	public WeChatUser loadUser(String accountId, String openId) {
		final String hql = "from WeChatUser w where w.openId = :openId and w.account.id = :accountId order by lastTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accountId", accountId);
		parameters.put("openId", openId);
		List<WeChatUser> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}

}