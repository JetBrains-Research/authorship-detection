/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: WebHelper.java
 * @Prject: libCommon
 * @Package: com.yeshj.pacman.utils
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月5日 上午12:59:28
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.springframework.util.StringUtils;

import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;

/**
 * @ClassName: WebHelper
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月5日 上午12:59:28
 */
public final class WebHelper {

	private final static ILog logger = LogFactory.getLog(WebHelper.class);
	
	/**
	 * Whether the resource is local file or not.
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isWebResource(String resource) {

		boolean result = false;
		if (!StringUtils.isEmpty(resource)) {
			
			result = resource.startsWith("http") || resource.startsWith("ftp");
		}
		return result;
	}
	
	/**
	 * Downloads the specified file to local file.
	 * 
	 * @param url
	 * @param localFile
	 * @return success or not.
	 */
	public static boolean download(String url, String localFile) {
		
		boolean result = true;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		HttpGet getMethod = null;
		FileOutputStream output = null;
		
		try {
			
			FileHelper.ensureExists(localFile, true);
			output = new FileOutputStream(localFile);
			client = HttpClients.createDefault();
			getMethod = new HttpGet(url);
			
			response = client.execute(getMethod);
			if (response != null) {
				
				if (response.getStatusLine().getStatusCode() == 200) {
					
					IOUtils.copy(response.getEntity().getContent(), output);
					
				} else {
					
					logger.error("Fail to download[" + url + "], http status:" + response.getStatusLine().getStatusCode());
				}
				
				response.close();
			} else {
				
				logger.error("Fail to download[" + url + "], response is null.");
			}

		} catch (Exception e) {
			
			logger.error("Fail to download[" + url + "]", e);
			result = false;
		} finally {
			
			if (output != null) {
				try {
					output.flush();
					output.close();
				} catch (IOException e) {
					
					logger.error("Fail to close output.", e);
				}
			}
			
			if (client != null) {
				try {

					client.close();
				} catch (IOException e) {
					
					logger.error("Fail to close httpclient.", e);
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Loads the url and return the content of it.
	 * 		
	 * @param url
	 * @param content
	 * @return success or not.
	 */
	public static boolean loadUrl(String url, Wrapper<String> content) {
		
		boolean result = false;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		HttpGet getMethod = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			
			client = HttpClients.createDefault();
			getMethod = new HttpGet(url);
			
			response = client.execute(getMethod);
			if (response != null) {
				
				if (response.getStatusLine().getStatusCode() == 200) {
					
					reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = null;
					while((line = reader.readLine()) != null) {
						sb.append(line);
					}
					content.setData(sb.toString());
					
					result = true;
				} else {
					
					logger.error("Fail to download[" + url + "], http status:" + response.getStatusLine().getStatusCode());
				}
				
				response.close();
				
			} else {
				
				logger.error("Fail to download[" + url + "], response is null.");
			}

		} catch (Exception e) {
			
			logger.error("Fail to download[" + url + "]", e);
			result = false;
		} finally {
			
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
					logger.error("Fail to close reader.", e);
				}
			}
			
			if (client != null) {
				try {

					client.close();
				} catch (IOException e) {
					
					logger.error("Fail to close httpclient.", e);
				}
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean postUrl(String url, List<NameValuePair> postData, Wrapper<String> content) {
		
		boolean result = false;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;

		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		HttpPost postMethod = null;

		try {
			
			client = HttpClients.createDefault();
			postMethod = new HttpPost(url);
			postMethod.setEntity(new UrlEncodedFormEntity(postData, HTTP.UTF_8));
			
			response = client.execute(postMethod);
			if (response != null) {
				
				if (response.getStatusLine().getStatusCode() == 200) {
					
					reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = null;
					while((line = reader.readLine()) != null) {
						sb.append(line);
					}
					content.setData(sb.toString());
					
					result = true;
				} else {
					
					logger.error("Fail to download[" + url + "], http status:" + response.getStatusLine().getStatusCode());
				}
				
				response.close();
				
			} else {
				
				logger.error("Fail to download[" + url + "], response is null.");
			}

		} catch (Exception e) {
			
			logger.error("Fail to download[" + url + "]", e);
			result = false;
		} finally {
			
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
					logger.error("Fail to close reader.", e);
				}
			}
			
			if (client != null) {
				try {

					client.close();
				} catch (IOException e) {
					
					logger.error("Fail to close httpclient.", e);
				}
			}
		}
		
		return result;
	}
}
