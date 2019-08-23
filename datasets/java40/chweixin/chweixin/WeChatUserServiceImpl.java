package ipower.wechat.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import ipower.wechat.dao.IWeChatAccountDao;
import ipower.wechat.dao.IWeChatUserDao;
import ipower.wechat.domain.WeChatAccount;
import ipower.wechat.domain.WeChatUser;
import ipower.wechat.modal.WeChatUserInfo;
import ipower.wechat.service.IWeChatUserService;

/**
 * 微信关注用户服务实现类。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class WeChatUserServiceImpl extends DataServiceImpl<WeChatUser, WeChatUserInfo> implements IWeChatUserService {
	private IWeChatUserDao weChatUserDao;
	private IWeChatAccountDao weChatAccountDao;
	/**
	 * 设置微信关注用户数据接口。
	 * @param weChatUserDao
	 * 微信关注用户数据访问接口。
	 * */
	public void setWeChatUserDao(IWeChatUserDao weChatUserDao) {
		this.weChatUserDao = weChatUserDao;
	}
	/**
	 * 设置微信公众号数据接口。
	 * @param weChatAccountDao
	 * 微信公众号数据接口。
	 * */
	public void setWeChatAccountDao(IWeChatAccountDao weChatAccountDao) {
		this.weChatAccountDao = weChatAccountDao;
	}

	@Override
	protected List<WeChatUser> find(WeChatUserInfo info) {
		return this.weChatUserDao.findUsers(info);
	}

	@Override
	protected WeChatUserInfo changeModel(WeChatUser data) {
		if(data == null) return null;
		WeChatUserInfo info = new WeChatUserInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAccount() != null){
			info.setAccountId(data.getAccount().getId());
			info.setAccountName(data.getAccount().getName());
		}
		return info;
	}

	@Override
	protected Long total(WeChatUserInfo info) {
		return this.weChatUserDao.total(info);
	}

	@Override
	public WeChatUserInfo update(WeChatUserInfo info) {
		if(info != null){
			boolean isAdded = false;
			WeChatUser data = this.weChatUserDao.load(WeChatUser.class, info);
			if(isAdded = (data == null)){
				data = new WeChatUser();
			}
			BeanUtils.copyProperties(info, data);
			if(info.getAccountId() != null && (data.getAccount() == null || !data.getAccount().getId().equalsIgnoreCase(info.getAccountId()))){
				WeChatAccount account = this.weChatAccountDao.load(WeChatAccount.class, info.getAccountId());
				if(account != null)data.setAccount(account);
			}
			if(data.getAccount() != null){
				info.setAccountName(data.getAccount().getName());
			}
			if(isAdded && data.getAccount() != null){
				this.weChatUserDao.save(data);
			}
		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			String[] prs = ids[i].split(",");
			if(prs != null && prs.length >= 2){
				WeChatUser data = this.weChatUserDao.load(WeChatUser.class, new WeChatUserInfo(prs[0], prs[1]));
				if(data != null) this.weChatUserDao.delete(data);
			}
		}
	}

}