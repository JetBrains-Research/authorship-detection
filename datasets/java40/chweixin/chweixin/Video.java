package ipower.wechat.message;

import java.io.Serializable;

/**
 * 视频信息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class Video implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title,mediaId,description;
	/**
	 * 获取视频标题。
	 * @return 视频标题。
	 * */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置视频标题。
	 * @param title
	 * 	视频标题。
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取通过上传多媒体文件，得到的id。
	 * @return 通过上传多媒体文件，得到的id。
	 * */
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * 设置通过上传多媒体文件，得到的id。
	 * @param mediaId
	 * 	通过上传多媒体文件，得到的id。
	 * */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	/**
	 * 获取视频描述。
	 * @return 视频描述。
	 * */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置视频描述。
	 * @param description
	 * 	视频描述。
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
}