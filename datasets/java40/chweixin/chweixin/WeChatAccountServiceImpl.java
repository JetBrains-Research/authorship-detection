package ipower.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import ipower.wechat.dao.IWeChatAccountDao;
import ipower.wechat.domain.WeChatAccount;
import ipower.wechat.modal.WeChatAccountInfo;
import ipower.wechat.service.IWeChatAccountService;

/**
 * 微信公众账号服务实现类。
 * @author yangyong.
 * @since 2014-04-01.
 * */
public class WeChatAccountServiceImpl extends DataServiceImpl<WeChatAccount, WeChatAccountInfo> implements IWeChatAccountService {
	private IWeChatAccountDao weChatAccountDao;
	
	public void setWeChatAccountDao(IWeChatAccountDao weChatAccountDao) {
		this.weChatAccountDao = weChatAccountDao;
	}
	
	@Override
	public List<WeChatAccountInfo> loadAllAccounts() {
		List<WeChatAccount> list  = this.weChatAccountDao.loadAllAccounts();
		if(list == null || list.size() == 0) return null;
		List<WeChatAccountInfo> results = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			WeChatAccountInfo info = this.changeModel(list.get(i));
			if(info != null) results.add(info);
		}
		return results;
	}
	
	@Override
	protected List<WeChatAccount> find(WeChatAccountInfo info) {
		return this.weChatAccountDao.findAccounts(info);
	}

	@Override
	protected WeChatAccountInfo changeModel(WeChatAccount data) {
		if(data == null) return null;
		WeChatAccountInfo info = new WeChatAccountInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	@Override
	protected Long total(WeChatAccountInfo info) {
		return this.weChatAccountDao.total(info);
	}

	@Override
	public WeChatAccountInfo update(WeChatAccountInfo info) {
		if(info != null){
			boolean isAdded = false;
			WeChatAccount data = (info.getId() == null || info.getId().trim().isEmpty()) ?  null : this.weChatAccountDao.load(WeChatAccount.class, info.getId());
			if(isAdded = (data == null)){
				if(info.getId() == null || info.getId().trim().isEmpty()){
					info.setId(UUID.randomUUID().toString());
				}
				data = new WeChatAccount();
			}
			BeanUtils.copyProperties(info, data);
			if(isAdded) this.weChatAccountDao.save(data);
		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0)return;
		for(int i = 0; i < ids.length;i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			WeChatAccount data = this.weChatAccountDao.load(WeChatAccount.class, ids[i]);
			if(data != null)this.weChatAccountDao.delete(data);
		}
	}
}