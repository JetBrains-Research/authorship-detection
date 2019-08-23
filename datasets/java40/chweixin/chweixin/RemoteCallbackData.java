package ipower.wechat.modal;

import ipower.wechat.message.Article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 远程业务调用回复消息。
 * @author yangyong.
 * @since 2014-04-14.
 */
public class RemoteCallbackData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type,content;
	private List<Article> articles;
	
	/**
	 * 构造函数。
	 */
	public RemoteCallbackData(){
		this.setArticles(new ArrayList<Article>());
	}
	/**
	 * 获取消息类型。
	 * @return 消息类型。
	 * */
	public String getType() {
		return type;
	}
	/**
	 *设置消息类型。
	 * @param type
	 * 消息类型。
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取文本消息内容。
	 * @return 文本消息内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置文本消息内容。
	 * @param content
	 * 文本消息内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取图文消息数组。
	 * @return 图文消息数组。
	 */
	public List<Article> getArticles() {
		return articles;
	}
	/**
	 * 设置图文消息数组。
	 * @param articles
	 * 图文消息数组。
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}