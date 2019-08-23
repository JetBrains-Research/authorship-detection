/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: TaskRepository.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.repo
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月8日 下午4:37:04
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.dao;

import java.util.List;
import java.util.Map;

import com.yeshj.pacman.model.TaskModel;

/**
 * TODO
 * @Class: ITaskDAO
 * @author: zhangxinyu
 * @date: 2015年1月8日 下午4:37:04
 */
public interface ITaskDAO {
	
	/**
	 * add
	 * 
	 * @param task
	 */
	void add(TaskModel task);
	
	/**
	 * deletes.
	 * 
	 * @param task
	 */
	void delete(TaskModel task);
	
	/**
	 * save.
	 * 
	 * @param task
	 */
	void save(TaskModel task);
	
	/**
	 * update.
	 * 
	 * @param task
	 * @param map
	 */
	void update(int pk, Map<String, Object> map);
	
	/**
	 * finds the model by primary key.
	 * 
	 * @param taskid
	 * @return
	 */
	TaskModel findByPk(int taskid);
	
	/**
	 * finds models by lesson id.
	 * 
	 * @param lessonId
	 * @return
	 */
	List<TaskModel> findByLessonId(int lessonId);
}
