package ipower.wechat.service;

import ipower.wechat.message.WeChatContext;
import ipower.wechat.message.req.ImageReqMessage;
import ipower.wechat.message.req.LinkReqMessage;
import ipower.wechat.message.req.LocationReqMessage;
import ipower.wechat.message.req.TextReqMessage;
import ipower.wechat.message.req.VideoReqMessage;
import ipower.wechat.message.req.VoiceReqMessage;
import ipower.wechat.message.resp.BaseRespMessage;

/**
 * 微信普通消息处理。
 * @author yangyong.
 * @since 2014-04-11.
 * */
public interface IWeChatMessageHandlers {
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  图片请求。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, ImageReqMessage req);
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  链接请求。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, LinkReqMessage req);
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  位置请求。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, LocationReqMessage req);
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  文本请求。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, TextReqMessage req);
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  视频请求。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, VideoReqMessage req);
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  语音请求。
	 *  @return
	 *  回复消息。
	 * */
	BaseRespMessage handler(WeChatContext context, VoiceReqMessage req);
}