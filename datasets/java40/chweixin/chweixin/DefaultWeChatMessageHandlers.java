package ipower.wechat.service.impl;

import org.apache.log4j.Logger;

import ipower.wechat.dao.IWeChatDefaultReplyDao;
import ipower.wechat.dao.IWeChatMenuDao;
import ipower.wechat.domain.WeChatDefaultReply;
import ipower.wechat.domain.WeChatMenu;
import ipower.wechat.message.BaseMessage;
import ipower.wechat.message.WeChatContext;
import ipower.wechat.message.events.ClickEventMessage;
import ipower.wechat.message.events.EventMessage;
import ipower.wechat.message.events.LocationEventMessage;
import ipower.wechat.message.events.ScanEventMessage;
import ipower.wechat.message.events.SubscribeEventMessage;
import ipower.wechat.message.req.ImageReqMessage;
import ipower.wechat.message.req.LinkReqMessage;
import ipower.wechat.message.req.LocationReqMessage;
import ipower.wechat.message.req.TextReqMessage;
import ipower.wechat.message.req.VideoReqMessage;
import ipower.wechat.message.req.VoiceReqMessage;
import ipower.wechat.message.resp.BaseRespMessage;
import ipower.wechat.message.resp.RespMesssageHelper;
import ipower.wechat.message.resp.TextRespMessage;
import ipower.wechat.service.IWeChatMessageEventHandlers;
import ipower.wechat.service.IWeChatMessageFactory;
import ipower.wechat.service.IWeChatMessageHandlers;

/**
 * 默认微信消息处理。
 * @author yangyong.
 * @since 2014-04-14.
 */
public class DefaultWeChatMessageHandlers implements IWeChatMessageHandlers,IWeChatMessageEventHandlers {
	private static Logger logger = Logger.getLogger(DefaultWeChatMessageHandlers.class);
	private IWeChatDefaultReplyDao weChatDefaultReplyDao;
	private IWeChatMenuDao weChatMenuDao;
	private IWeChatMessageFactory orderMessageHandlers;
	/**
	 * 设置菜单数据接口。
	 * @param weChatMenuDao
	 */
	public void setWeChatMenuDao(IWeChatMenuDao weChatMenuDao) {
		this.weChatMenuDao = weChatMenuDao;
	}
	/**
	 * 设置文本指令消息处理。
	 * @param orderMessageHandlers
	 */
	public void setOrderMessageHandlers(IWeChatMessageFactory orderMessageHandlers) {
		this.orderMessageHandlers = orderMessageHandlers;
	}
	/**
	 * 设置微信默认回复数据接口。
	 * @param weChatDefaultReplyDao
	 */
	public void setWeChatDefaultReplyDao(IWeChatDefaultReplyDao weChatDefaultReplyDao) {
		this.weChatDefaultReplyDao = weChatDefaultReplyDao;
	}
	/**
	 * 创建默认回复。
	 * @param accountId
	 * @return 默认回复对象。
	 */
	protected synchronized BaseRespMessage createDefaultReply(String accountId){
		logger.info("开始创建默认回复消息...");
		if(accountId == null || accountId.trim().isEmpty()){
			logger.error("公众号ID为空，无法查询公众号下的默认回复！");
			return null;
		}
		WeChatDefaultReply defaultReply = this.weChatDefaultReplyDao.loadDefaultReply(accountId);
		if(defaultReply == null){
			logger.info("公众号ID："+ accountId+",没有设置默认回复消息！");
			return null;
		}
		if(defaultReply.getContent() == null || defaultReply.getContent().trim().isEmpty()){
			logger.info("公众号["+ accountId+"," +defaultReply.getAccount().getName()+ "]设置的默认回复信息为空！");
			return null;
		}
		String content = defaultReply.getContent();
		logger.info("公众号["+ accountId+"," +defaultReply.getAccount().getName()+ "]设置的默认回复信息：\r\n" + content);
		BaseRespMessage  resp = RespMesssageHelper.respXmlToObject(content);
		if(resp == null){
				logger.info("解析成回复对象失败，将以文本形式发送！");
				resp = new TextRespMessage();
				((TextRespMessage)resp).setContent(content);
		}
		return resp;
	}
	/**
	 * 构建默认消息回复。
	 * @param context
	 * 上下文。
	 * @param req
	 * 请求消息。
	 * @return
	 * 回复消息。
	 */
	protected BaseRespMessage buildDefaultReply(WeChatContext context,BaseMessage req){
		if(context.getAccountId() == null || context.getAccountId().trim().isEmpty()){
			logger.info("上下中公众号ID为空，无法查找默认回复！");
			return null;
		}
		BaseRespMessage respMessage = this.createDefaultReply(context.getAccountId());
		if(respMessage != null){
			respMessage.setFromUserName(req.getToUserName());
			respMessage.setToUserName(req.getFromUserName());
			return respMessage;
		}
		return null;
	}
	
	@Override
	public BaseRespMessage handler(WeChatContext context, SubscribeEventMessage event) {//关注
		return this.buildDefaultReply(context, event);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, EventMessage event) {//取消关注
		return null;
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, ScanEventMessage event) {
		return this.buildDefaultReply(context, event);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, LocationEventMessage event) {
		return this.buildDefaultReply(context, event);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, ClickEventMessage event) {
		return this.buildDefaultReply(context, event);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, ImageReqMessage req) {
		return this.buildDefaultReply(context, req);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, LinkReqMessage req) {
		return this.buildDefaultReply(context, req);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, LocationReqMessage req) {
		return this.buildDefaultReply(context, req);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, TextReqMessage req) {//文本输入
		String order = req.getContent();
		logger.info("根据文本输入的指令["+ order+"]查找对应的菜单进行处理！");
		WeChatMenu menu =  this.weChatMenuDao.loadMenu(context.getAccountId(), order);
		if(menu == null || menu.getUrl() == null || menu.getUrl().trim().isEmpty()){
			logger.info("未找到指令["+order+"]对应的菜单或者菜单没有数据接口无法处理，返回默认回复。");
			return this.buildDefaultReply(context, req);
		}
		logger.info("模拟菜单点击事件处理...");
		context.setLastMenuKey(menu.getCode());
		ClickEventMessage clickEventMessage = new ClickEventMessage();
		clickEventMessage.setEvent(WeChatContext.EVENT_MESSAGE_TYPE_CLICK);
		clickEventMessage.setEventKey(context.getLastMenuKey());
		clickEventMessage.setFromUserName(req.getFromUserName());
		clickEventMessage.setToUserName(req.getToUserName());
		clickEventMessage.setCreateTime(req.getCreateTime());
		return this.orderMessageHandlers.messageHandler(context, clickEventMessage);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, VideoReqMessage req) {
		return this.buildDefaultReply(context, req);
	}

	@Override
	public BaseRespMessage handler(WeChatContext context, VoiceReqMessage req) {
		return this.buildDefaultReply(context, req);
	}

}