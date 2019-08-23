package ipower.wechat.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;

import ipower.wechat.dao.IWeChatAccessTokenDao;
import ipower.wechat.dao.IWeChatAccountDao;
import ipower.wechat.domain.WeChatAccessToken;
import ipower.wechat.domain.WeChatAccount;
import ipower.wechat.modal.WeChatAccessTokenInfo;
import ipower.wechat.service.IWeChatAccessTokenService;
import ipower.wechat.utils.HttpUtil;

public class WeChatAccessTokenServiceImpl  extends DataServiceImpl<WeChatAccessToken, WeChatAccessTokenInfo> implements IWeChatAccessTokenService  {
	private static Logger logger = Logger.getLogger(WeChatAccessTokenServiceImpl.class);
	private IWeChatAccessTokenDao weChatAccessTokenDao;
	private IWeChatAccountDao weChatAccountDao;
	private String tokenUrl;
	
	public void setWeChatAccessTokenDao(IWeChatAccessTokenDao weChatAccessTokenDao) {
		this.weChatAccessTokenDao = weChatAccessTokenDao;
	}

	public void setWeChatAccountDao(IWeChatAccountDao weChatAccountDao) {
		this.weChatAccountDao = weChatAccountDao;
	}

	public void setAccessTokenUrl(String accessTokenUrl) {
		this.tokenUrl = accessTokenUrl;
	}

	@Override
	public String loadAccessToken(String accountId) throws Exception {
		if(accountId == null || accountId.trim().isEmpty()) {
			logger.error("公众号全局唯一票据时未传入公众号ID！");
			return null;
		}
		WeChatAccessToken token = this.weChatAccessTokenDao.loadAccessToken(accountId);
		if(token == null || !token.isEffective())
			return this.refreshAccessToken(accountId);
		return token.getAccessToken();
	}
	
	@Override
	public synchronized String refreshAccessToken(String accountId) throws Exception {
		if(accountId == null || accountId.trim().isEmpty()) return null;
		String error = null;
		WeChatAccount account = this.weChatAccountDao.load(WeChatAccount.class, accountId);
		if(account == null || account.getAppId() == null || account.getAppId().trim().isEmpty()){
			logger.error(error = "未找到公众号信息或未配置第三方用户凭证信息！");
			throw new Exception(error);
		}
		if(this.tokenUrl == null || this.tokenUrl.trim().isEmpty()){
			logger.error(error = "未注入tokenUrl!");
			throw new Exception(error);
		}
		String url = String.format(this.tokenUrl, account.getAppId(), account.getAppSecret());
		logger.info("token请求url:" + url);
		JSONObject resultObject = HttpUtil.httpsRequest(url, "GET", null);
		if(resultObject == null){
			logger.error(error = "未得到微信服务器的反馈！");
			throw new Exception(error);
		}
		String access_token = resultObject.getString("access_token");
		int expires_in = resultObject.getIntValue("expires_in");
		if(access_token == null || access_token.trim().isEmpty()){
			logger.error(error = "获取微信服务反馈令牌异常："  + resultObject.toJSONString());
			throw new Exception(error);
		}
		logger.info("获取[" + account.getName() + "]公众号令牌:" + access_token);
		WeChatAccessToken token = new WeChatAccessToken();
		token.setId(UUID.randomUUID().toString());
		token.setAccount(account);
		token.setAccessToken(access_token);
		token.setCreateTime(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(token.getCreateTime());
		calendar.add(Calendar.SECOND, expires_in);
		token.setFailureTime(calendar.getTime());
		this.weChatAccessTokenDao.save(token);
		return token.getAccessToken();
	}

	@Override
	protected List<WeChatAccessToken> find(WeChatAccessTokenInfo info) {
		return this.weChatAccessTokenDao.findAccessTokens(info);
	}

	@Override
	protected WeChatAccessTokenInfo changeModel(WeChatAccessToken data) {
		if(data == null) return null;
		WeChatAccessTokenInfo info = new WeChatAccessTokenInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAccount() != null){
			info.setAccountId(data.getAccount().getId());
			info.setAccountName(data.getAccount().getName());
		}
		return info;
	}

	@Override
	protected Long total(WeChatAccessTokenInfo info) {
		return this.weChatAccessTokenDao.total(info);
	}

	@Override
	public WeChatAccessTokenInfo update(WeChatAccessTokenInfo info) {
		if(info == null) return null;
		WeChatAccessToken data = (info.getId() == null || info.getId().trim().isEmpty()) ? null : this.weChatAccessTokenDao.load(WeChatAccessToken.class, info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			data = new WeChatAccessToken();
			info.setId(UUID.randomUUID().toString());
		}
		BeanUtils.copyProperties(info, data);
		if(info.getAccountId() != null && (data.getAccount() == null || !data.getAccount().getId().equalsIgnoreCase(info.getAccountId()))){
			WeChatAccount account = this.weChatAccountDao.load(WeChatAccount.class, info.getAccountId());
			if(account != null) data.setAccount(account);
		}
		if(data.getAccount() != null){
			info.setAccountName(data.getAccount().getName());
		}
		if(isAdded)this.weChatAccessTokenDao.save(data);
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0)return;
		for(int i = 0; i < ids.length; i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			WeChatAccessToken data = this.weChatAccessTokenDao.load(WeChatAccessToken.class, ids[i]);
			if(data != null)this.weChatAccessTokenDao.delete(data);
		}
	}
}