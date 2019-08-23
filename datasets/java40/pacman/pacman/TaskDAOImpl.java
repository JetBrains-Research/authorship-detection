/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: TaskDAOImpl.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.dao.impl
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月9日 上午1:00:37
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

import com.yeshj.pacman.dao.ITaskDAO;
import com.yeshj.pacman.model.TaskModel;
import com.yeshj.pacman.utils.DateHelper;

/**
 * @ClassName: TaskDAOImpl
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月9日 上午1:00:37
 */
public class TaskDAOImpl extends JdbcDaoSupport implements ITaskDAO {

	class TaskAddAction extends SqlUpdate {
		
		public TaskAddAction(DataSource ds) {
			setDataSource(ds);
			setSql("INSERT INTO task (tid, cid, lid, guid, type, file, begin, modified) VALUES(?, ?, ?, ?, ?, ?, now(), now())");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();			
		}
	}
	
	class TaskSaveAction extends SqlUpdate {
		
		public TaskSaveAction(DataSource ds) {
			setDataSource(ds);
			setSql("UPDATE task SET cid=?, lid=?, guid=?, type=?, file=?, begin=?, end=?, status=?, err=?, modified=now() WHERE tid=?");
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.TIMESTAMP));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			compile();
		}
	}
	
	private TaskAddAction addAction;
	private TaskSaveAction saveAction;
	
	/* (non-Javadoc)
	 * @see com.yeshj.pacman.dao.ITaskDAO#add(com.yeshj.pacman.model.TaskModel)
	 */
	@Override
	public void add(TaskModel task) {
		
		if (addAction == null)
			addAction = new TaskAddAction(getDataSource());
		
		addAction.update(task.getTid(), task.getCid(), task.getLid(), task.getGuid(), task.getType(), task.getFile());
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.dao.ITaskDAO#delete(com.yeshj.pacman.model.TaskModel)
	 */
	@Override
	public void delete(TaskModel task) {
		
		String sql = "DELETE task WHERE tid=" + task.getTid();
		getJdbcTemplate().execute(sql);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.dao.ITaskDAO#save(com.yeshj.pacman.model.TaskModel)
	 */
	@Override
	public void save(TaskModel task) {
		
		if (saveAction == null)
			saveAction = new TaskSaveAction(getDataSource());
		
		saveAction.update(
				task.getCid(),
				task.getLid(),
				task.getGuid(),
				task.getType(),
				task.getFile(),
				DateHelper.getCurrentTime(),
				task.getEnd(),
				task.getStatus(),
				task.getErr(),
				task.getTid());
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.dao.ITaskDAO#findByPk(int)
	 */
	@Override
	public TaskModel findByPk(int taskid) {
		
		TaskMappingQuery query = new TaskMappingQuery();
		query.setDataSource(getDataSource());
		query.setSql("SELECT * FROM task WHERE tid=?");
		query.declareParameter(new SqlParameter(Types.INTEGER));
		query.compile();
		
		return query.findObject(taskid);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.dao.ITaskDAO#findByLessonId(int)
	 */
	@Override
	public List<TaskModel> findByLessonId(int lessonId) {

		TaskMappingQuery query = new TaskMappingQuery();
		query.setDataSource(getDataSource());
		query.setSql("SELECT * FROM task WHERE lid=? ORDER BY modified LIMIT 100");
		query.declareParameter(new SqlParameter(Types.INTEGER));
		query.compile();
		
		return query.execute(lessonId);
	}

	/* (non-Javadoc)
	 * @see com.yeshj.pacman.dao.ITaskDAO#update(com.yeshj.pacman.model.TaskModel, java.util.Map)
	 */
	@Override
	public void update(int taskid, Map<String, Object> map) {
		
		ArrayList<Object> params = new ArrayList<Object>();
		
		if (map == null || map.size() < 1)
			return;
		
		StringBuffer sqlBuilder = new StringBuffer("UPDATE task SET modified=now()");
		for(String key : map.keySet()) {
			if (!"tid".equalsIgnoreCase(key)) {
				sqlBuilder.append("," + key + "=?");
				params.add(map.get(key));
			}
		}
		
		params.add(taskid);
		
		sqlBuilder.append(" WHERE tid=?");
		
		getJdbcTemplate().update(sqlBuilder.toString(), params.toArray());
	}

}
