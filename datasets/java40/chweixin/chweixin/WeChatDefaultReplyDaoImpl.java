package ipower.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IWeChatDefaultReplyDao;
import ipower.wechat.domain.WeChatDefaultReply;
import ipower.wechat.modal.WeChatDefaultReplyInfo;

/**
 * 默认回复数据访问实现。
 * @author yangyong.
 * @since 2014-04-09.
 * */
public class WeChatDefaultReplyDaoImpl extends BaseDaoImpl<WeChatDefaultReply> implements IWeChatDefaultReplyDao {
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
	protected String addWhere(WeChatDefaultReplyInfo info,String hql,Map<String, Object> parameters){
		if(info.getAccountName() != null && !info.getAccountName().trim().isEmpty()){
			hql += " and (w.account.name like :accountName or w.account.account like :accountName) ";
			parameters.put("accountName", "%" + info.getAccountName() + "%");
		}
		return hql;
	}
	
	@Override
	public List<WeChatDefaultReply> findReplies(WeChatDefaultReplyInfo info) {
		String hql = "from WeChatDefaultReply w where 1=1 ";
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
	public Long total(WeChatDefaultReplyInfo info) {
		String hql = "select count(*) from WeChatDefaultReply w where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	
	@Override
	public WeChatDefaultReply loadDefaultReply(String accountId) {
		if(accountId == null || accountId.trim().isEmpty()) return null;
		final String hql = "from WeChatDefaultReply w where w.status = 1 and w.account.id = :accountId order by createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accountId", accountId);
		List<WeChatDefaultReply> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}

}