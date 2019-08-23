package ipower.wechat.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import ipower.utils.IOUtil;
import ipower.utils.MD5Util;
import ipower.wechat.dao.IPictureDao;
import ipower.wechat.domain.Picture;
import ipower.wechat.modal.PictureInfo;
import ipower.wechat.service.IPictureService;

public class PictureServiceImpl extends DataServiceImpl<Picture, PictureInfo> implements IPictureService {
	private final static Logger logger = Logger.getLogger(PictureServiceImpl.class);
	private IPictureDao pictureDao;
	private String imageFolder;
	/**
	 * 构造函数。
	 * */
	public PictureServiceImpl(){
		this.setImageFolder("images");
	}
	/**
	 * 设置图片库数据接口。
	 * @param pictureDao
	 * 图片库数据接口。
	 * */
	public void setPictureDao(IPictureDao pictureDao) {
		this.pictureDao = pictureDao;
	}
	/**
	 * 设置图片库文件存储目录。
	 * @param imageFolder
	 * 图片库文件存储目录。
	 * */
	public void setImageFolder(String imageFolder) {
		this.imageFolder = imageFolder;
	}

	@Override
	protected List<Picture> find(PictureInfo info) {
		return this.pictureDao.findPictures(info);
	}

	@Override
	protected PictureInfo changeModel(Picture data) {
		if(data == null)return null;
		PictureInfo info = new PictureInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	@Override
	protected Long total(PictureInfo info) {
		return this.pictureDao.total(info);
	}

	@Override
	public PictureInfo update(PictureInfo info) {
		if(info != null){
			boolean isAdded = false;
			Picture data = (info.getId() == null || info.getId().trim().isEmpty()) ? null : this.pictureDao.load(Picture.class, info.getId());
			if(isAdded = (data == null)){
				if(info.getId() == null || info.getId().trim().isEmpty()){
					info.setId(UUID.randomUUID().toString());
				}
				data = new Picture();
			}
			if(info.getPath() != null && !info.getPath().trim().isEmpty() && 
					(data.getPath() == null || data.getPath().trim().isEmpty() || !data.getPath().equalsIgnoreCase(info.getPath()))){
				logger.info("准备移动上传图片文件...");
				String path = ServletActionContext.getServletContext().getRealPath(info.getPath()),
						  destFolder = this.createTargetStoreFolder(info.getCreateUserId(), this.imageFolder),
						  destPath = this.createTargetStorePath(destFolder);
				File srcFile = new File(path);
				if(!srcFile.exists()){
					logger.error("未找到上传图片文件:" + path);
					return info;
				}
				logger.info("找到上传图片文件:" + path);
				 info.setExt(IOUtil.getExtension(path));
				 logger.info("文件扩展名:" + info.getExt());
				 info.setSize(srcFile.length()/1024);
				 logger.info("文件大小:" + info.getSize() + " (KB)");
				 try {
					info.setCode(MD5Util.MD5(new FileInputStream(srcFile)));
				} catch (FileNotFoundException e) {
					logger.error(e);
					e.printStackTrace();
				}
				 logger.info("文件MD5校验码:" + info.getCode());
				 info.setCreateTime(new Date());
				 String newFileName = info.getId() + info.getExt();
				 info.setPath(destFolder + newFileName);
				 logger.info("存储相对路径:" + info.getPath());
				 logger.info("存储绝对路径:" + destPath + newFileName);
				 if(IOUtil.copyFile(srcFile, new File(destPath + newFileName), true)){
					 	BeanUtils.copyProperties(info, data);
						if(isAdded) this.pictureDao.save(data);
						logger.info("图片已成功移动到图片库！");
				 }else{
					 logger.error("图片移动到图片库失败！");
				 }
				 logger.info("移动图片完成！");
			}else if(!isAdded) {
					data.setCreateTime(new Date());
					data.setName(info.getName());
					BeanUtils.copyProperties(data, info);
			}
			if(info.getUrl() != null && !info.getUrl().trim().isEmpty()){
				info.setUrl(info.getUrl() + info.getPath());
			}
		}
		return info;
	}
	/**
	 * 构建目标存储目录相对路径。
	 * @param userId
	 * 当前用户ID。
	 * @param root
	 * 根目录。
	 * @return 相对路径。
	 * */
	private String createTargetStoreFolder(String userId, String root){
		StringBuilder folderBuilder = new StringBuilder(File.separator);
		if(root != null && !root.trim().isEmpty()){
			folderBuilder.append(root);
			if(root.charAt(root.length()  - 1) != File.separatorChar){
				folderBuilder.append(File.separatorChar);
			}
		}
		if(userId != null && !userId.trim().isEmpty()){ 
			folderBuilder.append(userId).append(File.separatorChar);
		}
		folderBuilder.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append(File.separatorChar);
		return folderBuilder.toString();
	}
	/**
	 *  创建绝对路径。
	 *  @param folder
	 *  相对路径。
	 *  @return 
	 *  绝对路径。
	 * */
	private String createTargetStorePath(String folder){
		if(folder == null || folder.trim().isEmpty()) return null;
		String path = ServletActionContext.getServletContext().getRealPath(folder) + File.separator;
		File destDir = new File(path);
		if(!destDir.exists()){
			destDir.mkdirs();
			logger.info("创建图片库存储目录:" + path);
		}
		return path;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			Picture data = this.pictureDao.load(Picture.class, ids[i]);
			if(data != null) this.pictureDao.delete(data);
		}
	}

}