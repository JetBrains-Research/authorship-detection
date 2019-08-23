package ipower.wechat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.wechat.dao.IWeChatMenuDao;
import ipower.wechat.domain.WeChatMenu;
/**
 * 微信公众号菜单数据访问实现类。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public class WeChatMenuDaoImpl  extends BaseDaoImpl<WeChatMenu> implements IWeChatMenuDao {

	@Override
	public List<WeChatMenu> findMenus(String accountId) {
		String hql = "from WeChatMenu w  where w.parent = null ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(accountId != null && !accountId.trim().isEmpty()){
			hql += "  and w.account.id = :accountId  ";
			parameters.put("accountId", accountId);
		}
		hql += " order by w.orderNo";
		return this.find(hql, parameters, null, null);
	}

	@Override
	public WeChatMenu loadMenu(String accountId,String code) {
		if(accountId == null || accountId.trim().isEmpty()) return null;
		if(code == null || code.trim().isEmpty()) return null;
		final String hql = "from WeChatMenu w where w.account.id = :accountId and w.code = :code";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountId", accountId);
		parameters.put("code", code);
		List<WeChatMenu> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
}