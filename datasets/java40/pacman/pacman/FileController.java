/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: FileController.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.controller
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年2月2日 上午10:28:57
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.utils.FileHelper;
import com.yeshj.pacman.utils.NumericHelper;

/**
 * TODO
 * @Class: FileController
 * @author: zhangxinyu
 * @date: 2015年2月2日 上午10:28:57
 */
@Repository
@Controller
public class FileController extends BaseController{
	
	private static final ILog logger = LogFactory.getLog(FileController.class);
	
	private final String resDir 		= AppConfig.getInstance().getWebResDir();
	private final String audioDir 		= AppConfig.getInstance().getWebAudioDir();
	private final String videoDir		= AppConfig.getInstance().getWebVideoDir();
	
	/**
	 * TODO
	 * @Title:FileController
	 */
	public FileController() {

		FileHelper.ensureExists(resDir,   false);
		FileHelper.ensureExists(audioDir, false);
		FileHelper.ensureExists(videoDir, false);
	}
	
	@RequestMapping(value="/file/upload.go", 
			        method = RequestMethod.POST, 
			        produces="application/json")
	@ResponseBody
	public JsonResult uploadFile(@RequestParam("file") MultipartFile image,
			                       HttpServletRequest request) {
		
		JsonResult result = new JsonResult();
		
		String libId 	= request.getParameter("lid"); // library id.
		String cwId 	= request.getParameter("cid"); // courseware id.
		
		saveFile(image, result, resDir, libId, cwId, false);
		
		return result;
	}

	@RequestMapping(value="/audio/upload.go", 
			        method = RequestMethod.POST, 
			        produces="application/json")
	@ResponseBody	
	public JsonResult uploadAudio(@RequestParam("file") MultipartFile audio,
            						HttpServletRequest request) {
		
		JsonResult result = new JsonResult();
		
		String libId 	= request.getParameter("lid"); // library id.
		String cwId 	= request.getParameter("cid"); // courseware id.
		
		saveFile(audio, result, audioDir, libId, cwId, false);
		
		return result;
	}
	
	@RequestMapping(value="/video/upload.go", 
			        method = RequestMethod.POST, 
			        produces="application/json")
	@ResponseBody
	public JsonResult uploadVideo(@RequestParam("file") MultipartFile video,
			HttpServletRequest request) {
		
		JsonResult result = new JsonResult();
		
		String libId 	= request.getParameter("lid"); // library id.
		String cwId 	= request.getParameter("cid"); // courseware id.
		
		saveFile(video, result, videoDir, libId, cwId, false);
		
		return result;		
	}
	
	/**
	 * @Title: saveFile
	 * @param file
	 * @param result
	 * @param libId
	 * @param cwId
	 * @return: void
	 */
	private void saveFile(
			MultipartFile file, 
			JsonResult result,
			String preDir,
			String libId,
			String cwId,
			boolean isPrimary) {
		
		if (!NumericHelper.isNumeric(libId)) {
			
			result.setSuccess(0);
			result.setMsg("Invalid parameter: lid!");
			return;
		}
		
		if (!NumericHelper.isNumeric(cwId)) {
			
			result.setSuccess(0);
			result.setMsg("Invalid parameter: cid");
			return;
		}
		
		String imgDir = String.format("%s/%s/%s/", preDir, libId, cwId);
		FileHelper.ensureExists(imgDir, false);

		File f = new File(imgDir + file.getOriginalFilename());
		
		try {
			file.transferTo(f);
		} catch (Exception e) {

			logger.error("Error occurs on saving file. " + f.getName(), e);
			result.setSuccess(0);
			result.setMsg("Error occurs on saving file. " + f.getName());
			return;
		}
		
		String pubDir;
		if (isPrimary)
			pubDir = String.format("slicer/%s/%s/%s", libId, cwId, file.getOriginalFilename());
		else
			pubDir = String.format("res/%s/%s/%s", libId, cwId, file.getOriginalFilename());
		
		result.setSuccess(1);
		result.setMsg(pubDir);
	}
}
