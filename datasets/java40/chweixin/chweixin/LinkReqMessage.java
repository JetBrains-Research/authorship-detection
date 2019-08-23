package ipower.wechat.message.req;
/**
 * 链接消息。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public class LinkReqMessage extends BaseReqMessage {
	private static final long serialVersionUID = 1L;
	private String title,description,url;
	/**
	 * 获取消息标题。
	 * @return 消息标题。
	 * */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置消息标题。
	 * @param title
	 * 	消息标题。
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取消息描述。
	 * @return 消息描述。
	 * */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置消息描述。
	 * @param description
	 * 消息描述。
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取消息链接。
	 * @return 消息链接。
	 * */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置消息链接。
	 * @param url
	 * 	消息链接。
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
}