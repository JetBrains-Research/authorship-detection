package ipower.wechat.message.events;

import java.util.Map;
/**
 * 事件消息解析帮助类。
 * @author yangyong.
 * @since 2014-02-26.
 * */
public final class EventMessageHelper {
	/**
	 * 将请求事件消息解析成对象。
	 * @param event
	 *  事件消息基类。
	 *  @param data
	 *  请求消息。
	 * */
	private static void parseEventMessage(EventMessage event, Map<String, String> data){
		if(event != null && data != null && data.size() > 0){
			event.setToUserName(data.get("ToUserName"));
			event.setFromUserName(data.get("FromUserName"));
			event.setCreateTime(Integer.parseInt(data.get("CreateTime")));
			event.setMsgType(data.get("MsgType"));
			event.setEvent(data.get("Event"));
		}
	}
	/**
	 * 取消关注事件解析成对象。
	 * @param data
	 * 	事件消息数据。
	 * @return
	 * 	事件消息对象。
	 * */
	public static EventMessage parseEventMessage(Map<String, String> data){
		EventMessage eventMessage = new EventMessage();
		//
		parseEventMessage(eventMessage, data);
		
		return eventMessage;
	}
	/**
	 * 用户未关注时，进行关注后的事件推送消息解析成关注对象。
	 * @param data
	 * 	事件消息数据。
	 * @return
	 * 	事件消息对象。
	 * */
	public static SubscribeEventMessage parseSubscribeEventMessage(Map<String, String> data){
		SubscribeEventMessage subscribeEventMessage = new SubscribeEventMessage();
		//
		parseEventMessage(subscribeEventMessage, data);
		//
		subscribeEventMessage.setEventKey(data.get("EventKey"));
		subscribeEventMessage.setTicket(data.get("Ticket"));
		
		return subscribeEventMessage;
	}
	/**
	 * 用户已关注时的事件推送消息解析成已关注对象。
	 * @param data
	 * 	事件消息数据。
	 * @return
	 * 	事件消息对象。
	 * */
	public static ScanEventMessage parseScanEventMessage(Map<String, String> data){
		ScanEventMessage scanEventMessage = new ScanEventMessage();
		//
		parseEventMessage(scanEventMessage, data);
		//
		scanEventMessage.setEventKey(Integer.parseInt(data.get("EventKey")));
		scanEventMessage.setTicket(data.get("Ticket"));
		
		return scanEventMessage;
	}
	/**
	 * 上报地理位置事件消息解析成对象。
	 * @param data
	 * 	消息数据。
	 * @return
	 *  事件消息对象。
	 * */
	public static LocationEventMessage parseLocationEventMessage(Map<String, String> data){
		LocationEventMessage locationEventMessage = new LocationEventMessage();
		//
		parseEventMessage(locationEventMessage, data);
		//
		locationEventMessage.setLatitude(data.get("Latitude"));
		locationEventMessage.setLongitude(data.get("Longitude"));
		locationEventMessage.setPrecision(data.get("Precision"));
		
		return locationEventMessage;
	}
	/**
	 * 自定义菜单事件解析成对象。
	 * @param data
	 * 	消息数据。
	 * @return
	 *  事件消息对象。
	 * */
	public static ClickEventMessage parseClickEventMessage(Map<String, String> data){
		ClickEventMessage clickEventMessage = new ClickEventMessage();
		//
		parseEventMessage(clickEventMessage, data);
		//
		clickEventMessage.setEventKey(data.get("EventKey"));
		
		return clickEventMessage;
	}
}