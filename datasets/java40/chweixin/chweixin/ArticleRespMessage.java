package ipower.wechat.message.resp;
import ipower.wechat.message.Article;
import ipower.wechat.message.BaseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 响应回复图文消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ArticleRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	/**
	 * 最大数据条数为10.
	 */
	public final static int MAX_COUNT = 10;
	private Integer ArticleCount;
	private List<Article> Articles;
	/**
	 * 构造函数。
	 * */
	public ArticleRespMessage(){
		super();
		this.setMsgType("news");
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public ArticleRespMessage(BaseMessage req){
		super(req);
		this.setMsgType("news");
	}
	/**
	 * 获取图文消息个数，限制为10条以内。
	 * @return 图文消息个数。
	 * */
	public Integer getArticleCount() {
		return ArticleCount;
	}
	/**
	 * 设置图文消息个数，限制为10条以内。
	 * @param articleCount。
	 * 	图文消息个数。
	 * */
	public void setArticleCount(Integer articleCount) {
		this.ArticleCount = articleCount;
	}
	/**
	 * 获取多条图文消息信息，默认第一个item为大图；
	 * 注意：如果图文数超过10个，则将会无响应。
	 * @return 多条图文消息信息。
	 * */
	public List<Article> getArticles() {
		return Articles;
	}
	/**
	 * 设置多条图文消息信息，默认第一个item为大图；
	 * 注意：如果图文数超过10个，则将会无响应。
	 * @param articles
	 * 多条图文消息信息。
	 * */
	public void setArticles(List<Article> articles) {
		 if(articles == null || articles.size() == 0)return;
		 this.setArticleCount(articles.size() > MAX_COUNT ? MAX_COUNT : articles.size());
		 if(articles.size() > MAX_COUNT){
			List<Article> dest = new ArrayList<>();
			for(int i = 0; i < MAX_COUNT - 1; i ++){
				dest.add(articles.get(i));
			}
			this.Articles = dest;
			 return;
		 }
		this.Articles = articles;
	}	
}