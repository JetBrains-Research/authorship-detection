package ipower.wechat.message;

import java.io.Serializable;

/**
 * 音乐信息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class Music implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title,description,musicURL,HQMusicUrl,thumbMediaId;
	/**
	 * 获取音乐标题。
	 * @return 音乐标题。
	 * */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置音乐标题。
	 * @param title
	 * 	音乐标题。
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取音乐描述。
	 * @return 音乐描述。
	 * */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置音乐描述。
	 * @param description
	 * 	音乐描述。
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取音乐链接。
	 * @return 音乐链接。
	 * */
	public String getMusicURL() {
		return musicURL;
	}
	/**
	 * 设置音乐链接。
	 * @param musicURL
	 * 	音乐链接。
	 * */
	public void setMusicURL(String musicURL) {
		this.musicURL = musicURL;
	}
	/**
	 * 获取高质量音乐链接，WIFI环境优先使用该链接播放音乐。
	 * @return 高质量音乐链接。
	 * */
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	/**
	 * 设置高质量音乐链接，WIFI环境优先使用该链接播放音乐。
	 * @param hQMusicUrl
	 * 	高质量音乐链接。
	 * */
	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}
	/**
	 * 获取缩略图的媒体id，通过上传多媒体文件，得到的id。
	 * @return 缩略图的媒体id。
	 * */
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	/**
	 * 设置缩略图的媒体id，通过上传多媒体文件，得到的id。
	 * @param thumbMediaId
	 * 	缩略图的媒体id。
	 * */
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
}