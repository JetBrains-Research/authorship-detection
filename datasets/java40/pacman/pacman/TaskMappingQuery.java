/******************************************************************  
 * Copyright © 2015 hujiang.com. All rights reserved.
 *
 * @Title: TaskMappingQuery.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.dao.impl
 * @Description: packing and transcoding classware of hujiang.com.
 *                http://class.hujiang.com/
 * @author: Dellinger  
 * @date: 2015年1月9日 上午3:33:27
 * @version: V1.0  
 ******************************************************************/


package com.yeshj.pacman.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.object.MappingSqlQuery;

import com.yeshj.pacman.model.TaskModel;

/**
 * @ClassName: TaskMappingQuery
 * @Description: TODO
 * @author: Dellinger
 * @date: 2015年1月9日 上午3:33:27
 */
public class TaskMappingQuery extends MappingSqlQuery<TaskModel> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	protected TaskModel mapRow(ResultSet rs, int row) throws SQLException {

		TaskModel result = new TaskModel();
		result.setTid(rs.getInt("tid"));
		result.setCid(rs.getInt("cid"));
		result.setLid(rs.getInt("lid"));
		result.setGuid(rs.getString("guid"));
		result.setFile(rs.getString("file"));
		result.setBegin(rs.getTimestamp("begin"));
		result.setEnd(rs.getTimestamp("end"));
		result.setType(rs.getInt("type"));
		result.setStatus(rs.getInt("status"));
		result.setErr(rs.getString("err"));
		return result;
	}


}
