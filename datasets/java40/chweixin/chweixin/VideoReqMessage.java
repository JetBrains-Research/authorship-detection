package ipower.wechat.message.req;
/**
 * 视频消息。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public class VideoReqMessage extends BaseReqMessage {
	private static final long serialVersionUID = 1L;
	private String mediaId,thumbMediaId;
	/**
	 * 获取视频消息媒体ID。
	 * 	可以调用多媒体文件下载接口拉取数据。
	 * @return 视频消息媒体ID。
	 * */
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * 设置视频消息媒体ID。
	 * @param mediaId
	 * 	视频消息媒体ID。
	 * */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	/**
	 * 获取视频消息缩略图的媒体id,
	 * 	可以调用多媒体文件下载接口拉取数据。
	 * */
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	/**
	 * 设置视频消息缩略图的媒体id。
	 * @param thumbMediaId
	 * 	视频消息缩略图的媒体id。
	 * */
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
}