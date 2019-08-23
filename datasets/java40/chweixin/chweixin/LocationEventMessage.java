package ipower.wechat.message.events;
/**
 * 上报地理位置事件消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class LocationEventMessage extends EventMessage {
	private static final long serialVersionUID = 1L;
	private String latitude,longitude,precision;
	/**
	 * 获取地理位置纬度。
	 * @return 地理位置纬度。
	 * */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置地理位置纬度。
	 * @param latitude
	 * 	地理位置纬度。
	 * */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取地理位置经度。
	 * @return 地理位置经度。
	 * */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置地理位置经度。
	 * @param longitude
	 * 	地理位置经度。
	 * */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取地理位置精度。
	 * @return 地理位置精度。
	 * */
	public String getPrecision() {
		return precision;
	}
	/**
	 * 设置地理位置精度。
	 * @param precision
	 * 	地理位置精度。
	 * */
	public void setPrecision(String precision) {
		this.precision = precision;
	}
}