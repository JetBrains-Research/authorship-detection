/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: FileHelper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月4日 下午11:51:34
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: FileHelper
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月4日 下午11:51:34
 */
public final class FileHelper {

	public final static String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");
	
	/**
	 * Checks current environment is window or *nix.
	 * 
	 * @Title: isWindow
	 * @return
	 * @return: boolean
	 */
	public static boolean isWindow() {
		
		return "\\".equals(FILE_SEPARATOR);
	}
	
	/**
	 * Checks the specified dir or file exists or not.
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean exists(String dirOrFile) {
		
		if (StringUtils.isBlank(dirOrFile)) {
			return false;
		}
		return new File(dirOrFile).exists();
	}
	
	/**
	 * Makes sure the dir exists, otherwise create it and its parent dir.
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean ensureExists(String dir, boolean isFile) {
		
		if (StringHelper.isEmpty(dir)) {
			return false;
		}
		
		try {
			File file = new File(dir);
			if (isFile) {
				FileUtils.forceMkdir(file.getParentFile());
				if (!file.exists()) {
					file.createNewFile();
				}
			} else {
				FileUtils.forceMkdir(file);
			}
			
			return true;
		} catch (IOException e) {
			
			return false;
		}
	}
	
	/**
	 * Deletes a dir including its all subdirs.
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(String dir) {
		
		try {
			
			FileUtils.deleteDirectory(new File(dir));
			return true;
		} catch (IOException e) {

			return false;
		}
	}
	
	/**
	 * Copies the specified file to target file.
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean copyFile(String src, String dest) {
		
		try {
			
			FileUtils.copyFile(new File(src), new File(dest));
			return true;
		} catch (IOException e) {
			
			return false;
		}
	}
	
	/**
	 * Moves the specified file to target file.
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean moveFile(String src, String dest) {
		
		try {
			
			FileUtils.moveFile(new File(src), new File(dest));
			return true;
		} catch (IOException e) {
			
			return false;
		}
	}
	
	/**
	 * Copies the specified dir to target dir.
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 */
	public static boolean copyDir(String srcDir, String destDir) {
		
		if (!exists(srcDir)) {
			return false;
		}
		
		try {
			
			FileUtils.copyDirectory(new File(srcDir), new File(destDir), true);
			return true;
		} catch (IOException e) {
			
			return false;
		}
	}
	
	/**
	 * Moves the specified dir to target dir.
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 */
	public static boolean moveDir(String srcDir, String destDir) {
		
		if (!exists(srcDir)) {
			return false;
		}
		
		try {
			
			FileUtils.moveDirectoryToDirectory(new File(srcDir), new File(destDir), true);
			return true;
		} catch (IOException e) {
			
			return false;
		}
	}
	
	/**
	 * Combines two paths into one.
	 * 
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String combinePath(String path1, String path2) {
		
		return new File(path1, path2).getPath();
	}
	
	/**
	 * 
	 * 
	 * @Title: sizeOf
	 * @param fileOrPath
	 * @param isFile
	 * @return
	 * @return: long
	 */
	public static long sizeOf(String fileOrPath, boolean isFile) {
		
		if (isFile)
			return FileUtils.sizeOf(new File(fileOrPath));
		else
			return FileUtils.sizeOfDirectory(new File(fileOrPath));
	}

	/**
	 * 
	 * 
	 * @Title: getAllFileInDir
	 * @param dir
	 * @param recursive
	 * @return
	 * @return: List<String>
	 */
	public static List<String> getAllFileInDir(String dir, boolean recursive) {
		
		List<String> result = new ArrayList<String>();
		Iterator<File> files = FileUtils.iterateFiles(new File(dir), null, recursive);
		while(files.hasNext()) {
			result.add(files.next().getAbsolutePath());
		}
		
		return result;
	}
	
	/**
	 * 
	 * 
	 * @Title: archiveFiles
	 * @param files
	 * @param zipFile
	 * @return
	 * @return: boolean
	 * @throws Exception 
	 */
	public static boolean archiveFiles(
			List<String> 	files, 
			String 			target,
			String			ignoreExtNames,
			boolean			trimExtName
			) throws Exception {
		
		boolean result = true;
		ZipArchiveOutputStream zip = null;
		try {
			
			File zipFile = new File(target);
			zip = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(
					ArchiveStreamFactory.ZIP, new FileOutputStream(zipFile));
			zip.setEncoding("UTF-8");
			zip.setUseZip64(Zip64Mode.AsNeeded);
			
			String entryName = null;
			InputStream in = null;
			List<String> dupList = new ArrayList<String>();
			
			for(String file : files) {

				if (ignoreExtNames.contains(getExtName(file)))
					continue;

				if (file.endsWith("index.dat") || file.endsWith("index.xml")) {
					entryName = StringHelper.getFileBareName(file);
				} else {
					entryName = trimExtName ? 
							StringHelper.removeFileExtName(StringHelper.getFileBareName(file)) :
							StringHelper.getFileBareName(file);
				}
				
				if (!dupList.contains(entryName)) {
					dupList.add(entryName);
				} else {
					continue;
				}
				
				in = new FileInputStream(new File(file));
				ZipArchiveEntry entry = new ZipArchiveEntry(new File(file), entryName);
				zip.putArchiveEntry(entry);
				IOUtils.copy(in, zip);
				zip.closeArchiveEntry();
			}
			
		} catch (Exception e) {
			
			result = false;
			throw e;
		} finally {
			
			if (zip != null) {
				try {
					zip.finish();
					zip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public static String getExtName(String fileName) {
		
		String ext = "";
		
		if (!StringUtils.isBlank(fileName) && fileName.indexOf('.') > -1)
			ext = fileName.substring(fileName.lastIndexOf('.'));
		
		return ext;
	}
}
