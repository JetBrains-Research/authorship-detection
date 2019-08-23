package ipower.wechat.message.events;
/**
 * 用户未关注时，进行关注后的事件推送消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class SubscribeEventMessage extends EventMessage {
	private static final long serialVersionUID = 1L;
	private String eventKey,ticket;
	/**
	 * 获取事件KEY值，qrscene_为前缀，后面为二维码的参数值。
	 * @return 事件KEY值。
	 * */
	public String getEventKey() {
		return eventKey;
	}
	/**
	 * 设置事件KEY值，qrscene_为前缀，后面为二维码的参数值。
	 * @param eventKey
	 * 	事件KEY值。
	 * */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	/**
	 * 获取二维码的ticket,可用来换取二维码图片。
	 * @return 二维码的ticket。
	 * */
	public String getTicket() {
		return ticket;
	}
	/**
	 * 设置二维码的ticket,可用来换取二维码图片。
	 * @param ticket
	 * 	二维码的ticket。
	 * */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}