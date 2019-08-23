/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: IMediainfoDAO.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.dao
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月9日 下午1:00:17
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.dao;

import java.util.List;
import java.util.Map;

import com.yeshj.pacman.model.MediainfoModel;
import com.yeshj.pacman.model.TaskModel;

/**
 * TODO
 * @Class: IMediainfoDAO
 * @author: zhangxinyu
 * @date: 2015年1月9日 下午1:00:17
 */
public interface IMediainfoDAO {
	/**
	 * add
	 * 
	 * @param mi
	 */
	void add(MediainfoModel mi);
	
	/**
	 * deletes.
	 * 
	 * @param mi
	 */
	void delete(MediainfoModel mi);
	
	/**
	 * save.
	 * 
	 * @param task
	 */
	void save(MediainfoModel mi);
	
	/**
	 * finds the model by primary key.
	 * 
	 * @param taskid
	 * @return
	 */
	MediainfoModel findByPk(int taskid);
}
