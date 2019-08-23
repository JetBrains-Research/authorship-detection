package ipower.wechat.message;

import java.io.Serializable;

/**
 * 图文信息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	private String Title,Description,PicUrl,Url;
	/**
	 * 构造函数。
	 * */
	public Article(){
		
	}
	/**
	 * 构造函数。
	 * @param title
	 * 	消息标题。
	 * @param url
	 * 	消息连接。
	 * */
	public Article(String title,String url){
		this.setTitle(title);
		if(url != null && !url.trim().isEmpty()){
			this.setUrl(url);
		}
	}
	/**
	 * 构造函数。
	 * @param title
	 * 	消息标题。
	 * */
	public Article(String title){
		this(title,null);
	}
	/**
	 * 获取图文消息标题。
	 * @return 图文消息标题。
	 * */
	public String getTitle() {
		return Title;
	}
	/**
	 * 设置图文消息标题。
	 * @param title
	 * 	图文消息标题。
	 * */
	public void setTitle(String title) {
		this.Title = title;
	}
	/**
	 * 获取图文信息描述。
	 * @return 图文信息描述。
	 * */
	public String getDescription() {
		return Description;
	}
	/**
	 * 设置图文信息描述。
	 * @param description
	 * 	图文信息描述。
	 * */
	public void setDescription(String description) {
		this.Description = description;
	}
	/**
	 * 获取图片链接。
	 * @return 图片链接。
	 * */
	public String getPicUrl() {
		return PicUrl;
	}
	/**
	 * 设置图片链接。
	 * @param picUrl
	 * 	图片链接。
	 * */
	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}
	/**
	 * 获取图文消息跳转链接。
	 * @return 图文消息跳转链接。
	 * */
	public String getUrl() {
		return Url;
	}
	/**
	 * 设置图文消息跳转链接。
	 * @param url
	 * 	图文消息跳转链接。
	 * */
	public void setUrl(String url) {
		this.Url = url;
	}
}
