/********************************************************  
 * Copyright © 2014 HuJiang.com. All rights reserved.
 *
 * @Title: LessonInfoParser.java
 * @Prject: libTask
 * @Package: com.yeshj.pacman.schedule.dom
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2014年12月24日 下午6:39:48
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.schedule.dom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.yeshj.pacman.utils.StringHelper;

/**
 * TODO
 * @Class: LessonInfoParser
 * @author: zhangxinyu
 * @date: 2014年12月24日 下午6:39:48
 */
public class LessonInfoParser {

	/**
	 * Gets the output of parser.
	 * 
	 * @Title: parse
	 * @param: url
	 * @return
	 * @return: LessonInfo
	 * @throws Exception 
	 */
	public LessonInfo parseAndSave(String url, String xmlFile) throws Exception {
		
		if (StringUtils.isEmpty(url)) {
			throw new InvalidParameterException("url");
		}
		
		XmlSocket socket = new XmlSocket(url);
		Document doc = null;
		try {
			doc = socket.connect();
		} catch (Exception ex) {
			throw new DocumentException("lesson xml url unavailable:" + url);
		}
		LessonInfo info = new LessonInfo();
		
		Element root = doc.getRootElement();		
		parseGeneralInfo(root, info);
		parseAllFiles(root, info);
		
		convertToXml(doc, xmlFile);
		
		return info;
	}
	
	private void convertToXml(Document doc, String xmlFile) throws IOException {
		
		File target = new File(xmlFile);
		if (!target.exists()) {
			target.createNewFile();
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		FileOutputStream fos = new FileOutputStream(xmlFile);
		XMLWriter writer = new XMLWriter(fos, format);
		writer.write(doc);
		writer.close();
	}
	
	/**
	 * @param root
	 * @param info2
	 */
	@SuppressWarnings("unchecked")
	private void parseAllFiles(Element root, LessonInfo info) {
		
		List<Element> pages = root.elements("page");
		for(Element node: pages) {
			Attribute attribute =node.attribute("thumbnailUrl");
			if (attribute != null)
				node.remove(attribute); 
			
			attribute = node.attribute("backgroundUrl");
			if (attribute != null) {
				String file = StringHelper.getFileBareName(attribute.getValue());
				if (!StringHelper.isEmpty(file) && !info.getAllFiles().contains(file)) {
					info.addFile(file);
				}
				attribute.setValue(trimToBareName(file));
			}
			
			List<Element> eles = node.elements("ele");
			List<Element> subeles = null;
			if (eles != null && eles.size() > 0) {
				
				for(Element item : eles) {
	                subeles = item.elements("ele"); //双层ele处理...只有一个上层ele节点，阿辉
					if (subeles != null && subeles.size() > 0) {
						eles = subeles;
						break;
					}					
				}
			}
			
			if (eles != null) {
				for(Element subnode: eles) {
					
					attribute = subnode.attribute("url");
					if (attribute != null) {
						//String file = StringHelper.getFileBareName(attribute.getValue());
						String file = attribute.getValue();
						if (!!StringHelper.isEmpty(file)) {
							if (file.indexOf(".ashx") < 0
								&& file.indexOf("pic_title_") < 0 //排除掉pic_title_xzt.png pic_title_tkt.png ... 阿辉
								&& !info.getAllFiles().contains(file)) {
								info.addFile(file);
							}
							attribute.setValue(trimToBareName(file));
						}
					}
					
					Element urlNode = subnode.element("url");
					if (urlNode != null) {
						
						String file = urlNode.getTextTrim();//StringHelper.getFileBareName(urlNode.getTextTrim());
						if (!StringHelper.isEmpty(file)) {
							if (file.indexOf("pic_title_") < 0 //排除掉pic_title_xzt.png pic_title_tkt.png ... 阿辉
								&& file.indexOf(".ashx") < 0) {
								
								if (!StringHelper.isEmpty(file) && !info.getAllFiles().contains(file)) {
									info.addFile(file);
								}
								urlNode.setText(trimToBareName(file));
							}
						}
					}
					
					urlNode = subnode.element("releaseUrl");
					if (urlNode != null){
						String file = urlNode.getTextTrim();//StringHelper.getFileBareName(urlNode.getTextTrim());
						
						if (!StringHelper.isEmpty(file)) {
							if (!info.getAllFiles().contains(file)) {
								info.addFile(file);
							}
							urlNode.setText(trimToBareName(file));	
						}
					}
				}
			}
		} //end of for
	}

	/**
	 * @param root
	 */
	private void parseGeneralInfo(Element root, LessonInfo info) {
		
		
		Element lessonEle = root.element("lessonInfo");
	
		String item = lessonEle.element("classID").getTextTrim();
		info.setClassID(Integer.parseInt(item));
		
		item = lessonEle.element("lessonID").getTextTrim();
		info.setLessonID(Integer.parseInt(item));
		
		info.setLessonName(lessonEle.element("lessonName").getTextTrim());
		info.setMedia(lessonEle.element("mediaAudio").getTextTrim());
		
		item = lessonEle.element("mediaType").getTextTrim();
		info.setMediaType(Integer.parseInt(item));
	}
	
	public String trimToBareName(String url) {
		
		String result = "";
		
		if (StringHelper.isEmpty(url)) 
			return result;
		
		if (url.startsWith("http")) {
			int lastDotPosition = url.lastIndexOf('.');
			int lastSlashPosition = url.lastIndexOf('/');
			
			if (lastSlashPosition > 0 && 
				lastDotPosition > 0 && 
				lastDotPosition > lastSlashPosition) {
				
				result = url.substring(lastSlashPosition + 1, lastDotPosition);
			}
		} else {
			
			result = StringHelper.removeFileExtName(url);
		}
		
		return result;
	}
}
