package ipower.wechat.service.impl;

import java.util.Date;
import org.apache.log4j.Logger;
import ipower.cache.CacheEntity;
import ipower.cache.CacheListHandler;
import ipower.wechat.dao.IWeChatUserDao;
import ipower.wechat.domain.WeChatUser;
import ipower.wechat.message.WeChatContext;
import ipower.wechat.service.IWeChatContextService;
/**
 * 微信用户上下文服务接口实现。
 * @author yangyong.
 * @since 2014-04-12.
 * */
public class WeChatContextServiceImpl implements IWeChatContextService {
	private static Logger logger = Logger.getLogger(WeChatContextServiceImpl.class);
	private static int CONST_CACHE_ValidityTime = 900;
	private IWeChatUserDao weChatUserDao;
	/**
	 * 设置微信用户数据接口。
	 * @param weChatUserDao
	 * 	微信用户数据接口。
	 * */
	public void setWeChatUserDao(IWeChatUserDao weChatUserDao) {
		this.weChatUserDao = weChatUserDao;
	}

	@Override
	public synchronized WeChatContext get(String accountId, String userOpenId) {
		if(accountId == null || accountId.trim().isEmpty()){
			logger.error("公众号ID(accountId)为空，无法加载上下文！");
			return null;
		}
		if(userOpenId == null || userOpenId.trim().isEmpty()){
			logger.error("微信用户OpenID(userOpenId)为空，无法加载上下文！");
			return null;
		}
		WeChatContext context = null;
		String key = accountId.trim() + "_" + userOpenId.trim();
		@SuppressWarnings("unchecked")
		CacheEntity<WeChatContext>  entity = (CacheEntity<WeChatContext>) CacheListHandler.getCache(key);
		if(entity != null && entity.getEntity() != null){
			context = entity.getEntity();
		}
		if(context == null){
			logger.info("未找到上下文缓存，重新构建。");
			context = new WeChatContext(accountId, userOpenId);
			entity = new CacheEntity<WeChatContext>(key, context, CONST_CACHE_ValidityTime);
		}
		context.setLastActiveTime(new Date());
		if(!context.isAuthen()){//未身份认证。
			logger.info("上下文中没有身份信息，从用户信息表中加载用户信息。");
			WeChatUser user = this.weChatUserDao.loadUser(accountId, userOpenId);
			if(user != null){
				logger.info("加载关联用户[openid:"+ user.getOpenId() +"]:" + user.getUserName());
				context.setUserId(user.getUserId());
				//更新缓存
				entity.setEntity(context);
				CacheListHandler.addCache(entity);
			}else {
				logger.info("该用户未身份验证过，找不到用户信息！");
			}
		}
		//检测缓存过期状态。
		if(entity.getTimeoutStamp() - System.currentTimeMillis() < CONST_CACHE_ValidityTime){
			logger.info("上下文缓存快过期，重新延时。");
			//更新缓存
			entity.setEntity(context);
			entity.setValidityTime(CONST_CACHE_ValidityTime);
			CacheListHandler.addCache(entity);
		}
		if(entity != null && !CacheListHandler.isConcurrent(key)){
			logger.info("初次加入上下文缓存。");
			CacheListHandler.addCache(entity);
		}
		
		return context;
	}

	@Override
	public synchronized void update(WeChatContext context) {
		if(context == null) return;
		String key = context.getAccountId() + "_" + context.getOpenId();
		@SuppressWarnings("unchecked")
		CacheEntity<WeChatContext>  entity = (CacheEntity<WeChatContext>) CacheListHandler.getCache(key);
		if(entity == null){
			entity = new CacheEntity<WeChatContext>(key, context, CONST_CACHE_ValidityTime);
		}
		CacheListHandler.addCache(entity);
	}

	@Override
	public synchronized void remove(WeChatContext context) {
		if(context == null) return;
		CacheListHandler.removeCache(context.getAccountId() + "_" + context.getOpenId());
	}
}