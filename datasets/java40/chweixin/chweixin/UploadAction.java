package ipower.wechat.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import ipower.action.BaseAction;
import ipower.utils.IOUtil;
import ipower.utils.MD5Util;

/**
 * 上传文件Action。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class UploadAction extends BaseAction {
	private final static Logger logger = Logger.getLogger(UploadAction.class);
	private File attachment;
	private String tempFolder,attachmentContentType,attachmentFileName;
	/**
	 * 构造函数。
	 * */
	public UploadAction(){
		this.setTempFolder("/temp/");
	}
	/**
	 * 设置上传附加文件。
	 * @param attachment
	 * 上传附加文件。
	 * */
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	/**
	 * 设置上传附加文件类型。
	 * @param attachmentContentType
	 * 上传附加文件类型。
	 * */
	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}
	/**
	 * 设置上传附件文件名称。
	 * @param attachmentFileName
	 * 上传附件文件名称。
	 * */
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	/**
	 * 设置上传附件临时存储目录。
	 * @param tempFolder
	 * 上传附件临时存储目录。
	 * */
	public void setTempFolder(String tempFolder) {
		this.tempFolder = tempFolder;
	}
	
	@Override
	public synchronized String execute() throws IOException{
		Map<String, Object> result = new HashMap<>();
		StringBuilder folderBuilder = new StringBuilder(File.separator);
		if(this.tempFolder == null  || this.tempFolder.trim().isEmpty()){
			this.tempFolder = "temp" + File.separator; 
		}
		folderBuilder.append(this.tempFolder);
		if(this.tempFolder.charAt(this.tempFolder.length()  - 1) != File.separatorChar){
			folderBuilder.append(File.separatorChar);
		}
		folderBuilder.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).append(File.separatorChar);
		if(this.getUserIdentity() != null){ 
			folderBuilder.append(this.getUserIdentity().getId()).append(File.separatorChar);
		}
		String folder = folderBuilder.toString(),
				   root = ServletActionContext.getServletContext().getRealPath(folder) + File.separator;
		File dir = new File(root);
		if(!dir.exists()){
			dir.mkdirs();
			logger.info("创建目录：" + root);
		}
		logger.info("上传文件名:" + this.attachmentFileName);
		logger.info("上传文件类型:" + this.attachmentContentType);
		File srcFile = this.attachment;
		if(srcFile  != null){
			String id = MD5Util.MD5(new FileInputStream(srcFile)),
					  ext = IOUtil.getExtension(this.attachmentFileName);
			String newFileName = id + ext;
			logger.info("存储文件名:" + newFileName);
			logger.info("存储相对路径:" + (folder + newFileName));
			logger.info("存储绝对路径:" + (root + newFileName));
			if(IOUtil.copyFile(srcFile, new File(root + newFileName), false)){
				result.put("name",this.attachmentFileName);
				result.put("contentType", this.attachmentContentType);
				result.put("ext", ext);
				result.put("path", folder + newFileName); 
				result.put("url", this.host() + folder + newFileName);
			}
		}
		this.writeJson(result);
		return null;
	}
}