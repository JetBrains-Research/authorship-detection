/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: MessageHandler.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.web
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月12日 上午3:29:07
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.yeshj.pacman.config.AppConfig;
import com.yeshj.pacman.dao.IMediainfoDAO;
import com.yeshj.pacman.dao.ITaskDAO;
import com.yeshj.pacman.jms.IMQListener;
import com.yeshj.pacman.jms.message.BaseMessage;
import com.yeshj.pacman.jms.message.FeedbackMessage;
import com.yeshj.pacman.log.ILog;
import com.yeshj.pacman.log.LogFactory;
import com.yeshj.pacman.model.TaskModel;
import com.yeshj.pacman.utils.DateHelper;
import com.yeshj.pacman.utils.WebHelper;
import com.yeshj.pacman.utils.Wrapper;

/**
 * @ClassName: MessageHandler
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月12日 上午3:29:07
 */
public class MessageHandler implements IMQListener {

	private final static ILog logger = LogFactory.getLog(MessageHandler.class);
	
	private ITaskDAO taskDAO;
	/**
	 * @param taskDAO
	 */
	public MessageHandler(ITaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message arg0) {
		
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQListener#onTextMessage(java.lang.String)
	 */
	@Override
	public void onTextMessage(String msg) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQListener#onStreamMessage(byte[])
	 */
	@Override
	public void onStreamMessage(byte[] buffer) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQListener#onMapMessage(java.util.Map)
	 */
	@Override
	public void onMapMessage(Map<String, Object> map) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.jms.IMQListener#onObjectMessage(java.lang.Object)
	 */
	@Override
	public void onObjectMessage(Object obj) {

		//Callback ocs's address and notify the status of task.
		boolean callOcsOk = true;
		if (obj instanceof FeedbackMessage) {
			
			FeedbackMessage fm = (FeedbackMessage) obj;
			
			try {
				
				TaskModel task = taskDAO.findByPk(fm.getCommandId());
				
				Wrapper<String> content = new Wrapper<String>();
				ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
				String url = "";
				if (1 == task.getType()) {
					//CT lesson callback url.
					url = AppConfig.getInstance().getOcsCallbackUrl();
					data.add(new BasicNameValuePair("lessonid", task.getLid().toString()));
				} else {
					//OCS transcode callback url.
					url = AppConfig.getInstance().getTransCallbackUrl();
				}
				
				data.add(new BasicNameValuePair("taskid", task.getTid().toString()));
				data.add(new BasicNameValuePair("stat", fm.isSuccess() ? "2" : "3"));
				
				
				if (!WebHelper.postUrl(url, data, content)) {
					callOcsOk = false;
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("end", DateHelper.getCurrentTime());
				map.put("status", fm.isSuccess() ? 2 : 3);
				if (callOcsOk) {
					map.put("err", fm.getMsg());
				} else {
					map.put("err", "CALLBACK FAIL, " + fm.getMsg());
				}
				logger.warn("callback url:" + url + " ret:" + callOcsOk);
				map.put("modified", DateHelper.getCurrentTime());
				
				taskDAO.update(fm.getCommandId(), map);
			} catch (Exception e) {
				logger.error("Fail to save task callback status.", e);
			}
		}
	}

}
