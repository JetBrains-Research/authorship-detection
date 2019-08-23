package ipower.wechat.message.req;
/**
 * 地理位置消息。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public class LocationReqMessage extends BaseReqMessage {
	private static final long serialVersionUID = 1L;
	private String label, location_X,location_Y;
	private Integer scale;
	/**
	 * 获取地理位置信息。
	 * @return 地理位置信息。
	 * */
	public String getLabel() {
		return label;
	}
	/**
	 * 设置地理位置信息。
	 * @param label
	 * 	地理位置信息。
	 * */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * 获取地理位置纬度。
	 * @return 地理位置纬度。
	 * */
	public String getLocation_X() {
		return location_X;
	}
	/**
	 * 设置地理位置纬度。
	 * @param location_X
	 * 	地理位置纬度。
	 * */
	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}
	/**
	 * 获取地理位置经度。
	 * @return 地理位置经度。
	 * */
	public String getLocation_Y() {
		return location_Y;
	}
	/**
	 * 设置地理位置经度。
	 * @param location_Y
	 * 	地理位置经度。
	 * */
	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}
	/**
	 * 获取地图缩放大小。
	 * @return 地图缩放大小。
	 * */
	public Integer getScale() {
		return scale;
	}
	/**
	 * 设置地图缩放大小。
	 * @param scale
	 * 	地图缩放大小。
	 * */
	public void setScale(Integer scale) {
		this.scale = scale;
	}
}