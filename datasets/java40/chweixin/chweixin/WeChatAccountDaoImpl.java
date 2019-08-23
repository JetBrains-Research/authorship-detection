package ipower.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IWeChatAccountDao;
import ipower.wechat.domain.WeChatAccount;
import ipower.wechat.modal.WeChatAccountInfo;

/**
 * 微信公众账号数据访问实现类。
 * @author yangyong.
 * @since 2014-03-31.
 * */
public class WeChatAccountDaoImpl extends BaseDaoImpl<WeChatAccount> implements IWeChatAccountDao {
	
	@Override
	public WeChatAccount loadAccount(String openId) {
		if(openId == null || openId.trim().isEmpty()) return null;
		final String hql = "from WeChatAccount w where w.openId = :openId order by w.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("openId", openId);
		List<WeChatAccount> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	@Override
	public List<WeChatAccount> loadAllAccounts() {
		final String hql = "from WeChatAccount w order by w.name";
		return this.find(hql, null, null, null);
	}

	@Override
	public List<WeChatAccount> findAccounts(WeChatAccountInfo info) {
		String hql = "from WeChatAccount w where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by w." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	
	@Override
	public Long total(WeChatAccountInfo info) {
		String hql = "select count(*) from WeChatAccount w where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters); 
		return this.count(hql, parameters);
	}
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
	protected String addWhere(WeChatAccountInfo info, String hql,Map<String, Object> parameters) {
		if(info.getAccount() != null && !info.getAccount().trim().isEmpty()){
			hql += " and (w.name like :account or w.account like :account) ";
			parameters.put("account", "%" +info.getAccount() + "%");
		}
		if(info.getStatus() != null && info.getStatus() >= 0){
			hql += " and (w.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(info.getType() != null && info.getType() >= 0){
			hql += " and (w.type = :type) ";
			parameters.put("type", info.getType());
		}
		return hql;
	}

	@Override
	public WeChatAccount findAccount(String account) {
		if(account == null || account.trim().isEmpty()) return null;
		final String hql = "from WeChatAccount w where w.account = :account order by w.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("account", account);
		List<WeChatAccount> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}