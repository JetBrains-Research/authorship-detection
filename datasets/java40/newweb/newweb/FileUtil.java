package com.newweb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * 文件操作工具类
 * 
 * @author qianlong
 * 
 */
public class FileUtil {

	/**
	 * 获取文本文件的文本内容 若没有此文件，则创建一个新文件，返回空串
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String getTextFileContents(String fileName){
		String text = "";
		File f = new File(fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null,"创建文件失败，请检查是否有文件写入权限或磁盘是否有可用空间", "系统消息",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		}
		try {
			FileInputStream fis = new FileInputStream(f);
			// 读取数据，并将读取到的数据存储到数组中
			byte[] data = new byte[(int) f.length()+100]; // 数据存储的数组
			int i = 0; // 当前下标
			// 读取流中的第一个字节数据
			int n = fis.read();
			// 依次读取后续的数据
			while (n != -1) { // 未到达流的末尾
				// 将有效数据存储到数组中
				data[i] = (byte) n;
				// 下标增加
				i++;
				// 读取下一个字节的数据
				n = fis.read();
			}
			// 解析数据
			text = new String(data, 0, i);
			fis.close();
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,"文件读取失败，请检查是否有文件读取权限，或指定文件是否损坏等", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return text;
	}

	/**
	 * 获取制定路径下的所有文件中的文本
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllFileTextFromDir(String path){
		List<String> filesText = new ArrayList<String>();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		File[] fs = f.listFiles();
		try {
			for (File file : fs) {
				FileInputStream fis = new FileInputStream(file);
				// 读取数据，并将读取到的数据存储到数组中
				byte[] data = new byte[100000000]; // 数据存储的数组
				int i = 0; // 当前下标
				// 读取流中的第一个字节数据
				int n = fis.read();
				// 依次读取后续的数据
				while (n != -1) { // 未到达流的末尾
					// 将有效数据存储到数组中
					data[i] = (byte) n;
					// 下标增加
					i++;
					// 读取下一个字节的数据
					n = fis.read();
				}
				// 解析数据
				String text = new String(data, 0, i);
				filesText.add(text);
				fis.close();
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,"获取文件List内容失败", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return filesText;
	}

	/**
	 * 获取传入的路径下的文件的文件内容
	 * 如果文件不存在，将自动根据路径及文件名创建一个新的，返回空串
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getFileTextFromDirFile(String path, String fileName){
		String text = "";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(path + File.separator + fileName);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null,"创建文件失败，请检查是否有文件写入权限或磁盘是否有可用空间", "系统消息",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		}
		try {
			FileInputStream fis = new FileInputStream(f);
			// 读取数据，并将读取到的数据存储到数组中
			byte[] data = new byte[(int) f.length() +50]; // 数据存储的数组
			int i = 0; // 当前下标
			// 读取流中的第一个字节数据
			int n = fis.read();
			// 依次读取后续的数据
			while (n != -1) { // 未到达流的末尾
				// 将有效数据存储到数组中
				data[i] = (byte) n;
				// 下标增加
				i++;
				// 读取下一个字节的数据
				n = fis.read();
			}
			// 解析数据
			text = new String(data, 0, i);
			fis.close();
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,"获取指定路径文件内容失败", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		return text;
	}

	/**
	 * 将制定的字符串写入指定路径下的指定文件中
	 * 如果路径及文件不存在，将自动创建
	 * @param text
	 * @param path
	 * @param fileName
	 * @param append : true为在文件后追加内容
	 * @return
	 */
	public static boolean writeTextToTextFile(String text,String path, String fileName,boolean append) {
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		f = new File(path + File.separator + fileName);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null,"创建文件失败，请检查是否有文件写入权限或磁盘是否有可用空间", "系统消息",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f, append);
			byte[] write = text.getBytes();
			fos.write(write);
			fos.close();
			return true;
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null,"发生错误", "系统消息",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

}
