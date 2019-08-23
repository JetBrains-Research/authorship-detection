package ipower.wechat.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import ipower.wechat.dao.IWeChatAccountDao;
import ipower.wechat.dao.IWeChatDefaultReplyDao;
import ipower.wechat.domain.WeChatAccount;
import ipower.wechat.domain.WeChatDefaultReply;
import ipower.wechat.modal.WeChatDefaultReplyInfo;
import ipower.wechat.service.IWeChatDefaultReplyService;

/**
 * 默认回复服务实现。
 * @author yangyong.
 * @since 2014-04-09.
 * */
public class WeChatDefaultReplyServiceImpl extends DataServiceImpl<WeChatDefaultReply,WeChatDefaultReplyInfo> implements IWeChatDefaultReplyService {
	private IWeChatDefaultReplyDao weChatDefaultReplyDao;
	private IWeChatAccountDao weChatAccountDao;
	/**
	 * 设置默认回复数据接口。
	 * @param weChatDefaultReplyDao
	 * 默认回复数据接口。
	 * */
	public void setWeChatDefaultReplyDao(IWeChatDefaultReplyDao weChatDefaultReplyDao) {
		this.weChatDefaultReplyDao = weChatDefaultReplyDao;
	}
	/**
	 * 设置公众号数据接口。
	 * @param weChatAccountDao
	 *  公众号数据接口。
	 * */
	public void setWeChatAccountDao(IWeChatAccountDao weChatAccountDao) {
		this.weChatAccountDao = weChatAccountDao;
	}

	@Override
	protected List<WeChatDefaultReply> find(WeChatDefaultReplyInfo info) {
		return this.weChatDefaultReplyDao.findReplies(info);
	}

	@Override
	protected WeChatDefaultReplyInfo changeModel(WeChatDefaultReply data) {
		if(data == null) return null;
		WeChatDefaultReplyInfo info = new WeChatDefaultReplyInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAccount() != null){
			info.setAccountId(data.getAccount().getId());
			info.setAccountName(data.getAccount().getName());
		}
		return info;
	}

	@Override
	protected Long total(WeChatDefaultReplyInfo info) {
		return this.weChatDefaultReplyDao.total(info);
	}

	@Override
	public WeChatDefaultReplyInfo update(WeChatDefaultReplyInfo info) {
		if(info != null){
			boolean isAdded = false;
			WeChatDefaultReply data = (info.getId() == null || info.getId().trim().isEmpty()) ? null : this.weChatDefaultReplyDao.load(WeChatDefaultReply.class, info.getId());
			if(isAdded = (data == null)){
				if(info.getId() == null || info.getId().trim().isEmpty()){
					info.setId(UUID.randomUUID().toString());
				}
				data = new WeChatDefaultReply();
			}
			BeanUtils.copyProperties(info, data);
			if(info.getAccountId() != null && (data.getAccount() == null || !data.getAccount().getId().equalsIgnoreCase(info.getAccountId()))){
				WeChatAccount account = this.weChatAccountDao.load(WeChatAccount.class, info.getAccountId());
				if(account != null) {
					data.setAccount(account);
				}
			}
			if(isAdded) this.weChatDefaultReplyDao.save(data);
			if(data.getAccount() != null){
				info.setAccountName(data.getAccount().getName());
			}
		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			WeChatDefaultReply data = this.weChatDefaultReplyDao.load(WeChatDefaultReply.class, ids[i]);
			if(data != null) this.weChatDefaultReplyDao.delete(data);
		}
	}

}