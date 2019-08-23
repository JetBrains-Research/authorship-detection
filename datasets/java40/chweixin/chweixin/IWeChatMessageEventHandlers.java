package ipower.wechat.service;

import ipower.wechat.message.WeChatContext;
import ipower.wechat.message.events.ClickEventMessage;
import ipower.wechat.message.events.EventMessage;
import ipower.wechat.message.events.LocationEventMessage;
import ipower.wechat.message.events.ScanEventMessage;
import ipower.wechat.message.events.SubscribeEventMessage;
import ipower.wechat.message.resp.BaseRespMessage;
/**
 * 微信消息事件处理接口。
 * @author yangyong.
 * @since 2014-04-10.
 * */
public interface IWeChatMessageEventHandlers {
	/**
	 * 关注消息事件处理。
	 * @param context
	 * 上下文。
	 * @param event
	 *  关注消息事件。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, SubscribeEventMessage event);
	/**
	 * 取消关注消息事件处理。
	 * @param context
	 * 上下文。
	 * @param event
	 *  取消关注消息事件。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, EventMessage event);
	/**
	 * 扫描消息事件处理。
	 * @param context
	 * 上下文。
	 * @param event
	 *  扫描消息事件。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, ScanEventMessage event);
	/**
	 * 上报地理位置消息事件处理。
	 * @param context
	 * 上下文。
	 * @param event
	 *  上报地理位置消息事件。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, LocationEventMessage event);
	/**
	 * 菜单消息事件处理。
	 * @param context
	 * 上下文。
	 * @param event
	 *  菜单点击事件。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, ClickEventMessage event);
}