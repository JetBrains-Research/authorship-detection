package ipower.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IWeChatAccessTokenDao;
import ipower.wechat.domain.WeChatAccessToken;
import ipower.wechat.modal.WeChatAccessTokenInfo;

/**
 * 公众号全局唯一票据数据访问实现。
 * @author yangyong.
 * @since 2014-04-03.
 * */
public class WeChatAccessTokenDaoImpl  extends BaseDaoImpl<WeChatAccessToken> implements IWeChatAccessTokenDao {

	/**
	 * 添加查询条件。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数集合。
	 * @return
	 * HQL
	 * */
	protected String addWhere(WeChatAccessTokenInfo info, String hql,Map<String, Object> parameters) {
		if(info.getAccountName() != null && !info.getAccountName().trim().isEmpty()){
			hql += " and (w.account.name like :accountName or w.account.account like :accountName) ";
			parameters.put("accountName", "%"+ info.getAccountName()+"%");
		}
		if(info.getAccessToken() != null && !info.getAccessToken().trim().isEmpty()){
			hql +=" and (w.accessToken like :accessToken) ";
			parameters.put("accessToken", "%"+ info.getAccessToken()+"%");
		}
		return hql;
	}
	
	@Override
	public List<WeChatAccessToken> findAccessTokens(WeChatAccessTokenInfo info) {
		String hql = "from WeChatAccessToken w where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
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
	public Long total(WeChatAccessTokenInfo info) {
		String hql = "select count(*) from WeChatAccessToken w where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters); 
		return this.count(hql, parameters);
	}

	@Override
	public WeChatAccessToken loadAccessToken(String accountId) {
		final String hql = "from WeChatAccessToken w where w.account.id = :accountId  order by w.failureTime desc,w.createTime desc";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountId", accountId);
		List<WeChatAccessToken> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}

}