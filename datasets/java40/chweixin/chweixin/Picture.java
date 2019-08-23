package ipower.wechat.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 图片库。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,code,ext,path,createUserId,createUserName;
	private Long size;
	private Date createTime;
	/**
	 * 构造函数。
	 * */
	public Picture(){
		 this.setCreateTime(new Date());
	}
	/**
	 * 获取图片ID。
	 * @return 图片ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置图片ID。
	 * @param id
	 * 图片ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取图片名称。
	 * @return 图片名称。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置图片名称。
	 * @param name
	 * 图片名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取图片MD5校验码。
	 * @return 图片MD5校验码。
	 * */
	public String getCode() {
		return code;
	}
	/**
	 * 设置图片MD5校验码。
	 * @param code
	 * 图片MD5校验码。
	 * */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取图片扩展名。
	 * @return 图片扩展名。
	 * */
	public String getExt() {
		return ext;
	}
	/**
	 * 设置图片扩展名。
	 * @param ext
	 * 图片扩展名。
	 * */
	public void setExt(String ext) {
		this.ext = ext;
	}
	/**
	 * 获取图片存储路径。
	 * @return 图片存储路径。
	 * */
	public String getPath() {
		return path;
	}
	/**
	 * 设置图片存储路径。
	 * @param path
	 * 图片存储路径。
	 * */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取图片大小(K).
	 * @return 图片大小(K).
	 * */
	public Long getSize() {
		return size;
	}
	/**
	 * 设置图片大小(K).
	 * @param size
	 * 图片大小(K).
	 * */
	public void setSize(Long size) {
		this.size = size;
	}
	/**
	 * 获取图片上传时间。
	 * @return 图片上传时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置图片上传时间。
	 * @param createTime
	 * 图片上传时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 *  获取上传用户ID。
	 *  @return 上传用户ID。
	 * */
	public String getCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置上传用户ID。
	 * @param createUserId
	 * 上传用户ID。
	 * */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取上传用户名称。
	 * @return 上传用户名称。
	 * */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * 设置上传用户名称。
	 * @param createUserName
	 * 上传用户名称。
	 * */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}