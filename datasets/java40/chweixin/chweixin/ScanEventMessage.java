package ipower.wechat.message.events;
/**
 * 用户已关注时的事件推送消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ScanEventMessage extends EventMessage {
	private static final long serialVersionUID = 1L;
	private Integer eventKey;
	private String ticket;
	/**
	 * 获取事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id。
	 * @return 事件KEY值。
	 * */
	public Integer getEventKey() {
		return eventKey;
	}
	/**
	 * 设置事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id。
	 * @param eventKey
	 * 	事件KEY值。
	 * */
	public void setEventKey(Integer eventKey) {
		this.eventKey = eventKey;
	}
	/**
	 * 获取二维码的ticket，可用来换取二维码图片。
	 * @return 二维码的ticket。
	 * */
	public String getTicket() {
		return ticket;
	}
	/**
	 * 设置二维码的ticket，可用来换取二维码图片。
	 * @param ticket
	 * 	二维码的ticket。
	 * */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}