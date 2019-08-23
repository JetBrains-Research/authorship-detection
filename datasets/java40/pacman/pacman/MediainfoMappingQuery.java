/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: MediainfoMappingQuery.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.dao.impl
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月9日 下午2:16:22
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.object.MappingSqlQuery;

import com.yeshj.pacman.model.MediainfoModel;
import com.yeshj.pacman.model.TaskModel;

/**
 * TODO
 * @Class: MediainfoMappingQuery
 * @author: zhangxinyu
 * @date: 2015年1月9日 下午2:16:22
 */
public class MediainfoMappingQuery extends MappingSqlQuery<MediainfoModel>{

	/** (non Javadoc)
	 * TODO
	 * @Title: mapRow
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws SQLException
	 * @see org.springframework.jdbc.object.MappingSqlQuery#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	protected MediainfoModel mapRow(ResultSet rs, int row)
			throws SQLException {

		MediainfoModel result = new MediainfoModel();
		result.setTid(rs.getInt("tid"));
		result.setMedia(rs.getString("media"));

		result.setAudio_rate(rs.getInt("audio_rate"));
		result.setAudio_sampling(rs.getInt("audio_sampling"));
		result.setAudio_duration(rs.getInt("audio_duration"));
		result.setAudio_codec(rs.getString("audio_codec"));
		
		result.setVideo_width(rs.getInt("video_width"));
		result.setVideo_height(rs.getInt("video_height"));
		result.setVideo_rate(rs.getInt("video_rate"));
		result.setVideo_duration(rs.getInt("video_duration"));
		result.setVideo_codec(rs.getString("video_codec"));
		
		return result;
	}

}
