/********************************************************  
 * Copyright © 2015 HuJiang.com. All rights reserved.
 *
 * @Title: MediainfoDAOImpl.java
 * @Prject: pacman
 * @Package: com.yeshj.pacman.dao.impl
 * @Description: packing and transcoding the classware of hujiang.com.
 *                 http://class.hujiang.com
 *
 * @author: zhangxinyu  
 * @date: 2015年1月9日 下午1:03:35
 * @version: V1.0  
 ********************************************************/
package com.yeshj.pacman.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.SqlUpdate;

import com.yeshj.pacman.dao.IMediainfoDAO;
import com.yeshj.pacman.dao.impl.TaskDAOImpl.TaskAddAction;
import com.yeshj.pacman.model.MediainfoModel;

/**
 * TODO
 * 
 * @Class: MediainfoDAOImpl
 * @author: zhangxinyu
 * @date: 2015年1月9日 下午1:03:35
 */
public class MediainfoDAOImpl extends JdbcDaoSupport implements IMediainfoDAO {

	class MiAddAction extends SqlUpdate {

		private final static String INS_SQL = 
				"INSERT INTO mediainfo (" +
						"tid, media, audio_rate, audio_sampling, audio_duration, audio_codec, " +
						"video_width, video_height, video_rate, video_duration, video_codec)" +
						"VALUES(?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)";
		
		public MiAddAction(DataSource ds) {
			setDataSource(ds);
			setSql(INS_SQL);
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			compile();
		}
	}

	class MiSaveAction extends SqlUpdate {
		
		private final static String UPD_SQL = 
				"UPDATE mediainfo " + 
				"SET media=?," + 
				"    audio_rate=?," + 
				"    audio_sampling=?," + 
				"    audio_duration=?," + 
				"    audio_codec=?," + 
				"    video_width=?," + 
				" 	 video_height=?," + 
				" 	 video_rate=?," + 
				" 	 video_duration=?," + 
				" 	 video_codec=?" + 
				"WHERE tid=?";
		
		public MiSaveAction(DataSource ds) {
			setDataSource(ds);
			setSql(UPD_SQL);
			
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.INTEGER));
			declareParameter(new SqlParameter(Types.VARCHAR));
			declareParameter(new SqlParameter(Types.INTEGER));
			
			compile();
		}
	}

	private MiAddAction addAction;
	private MiSaveAction saveAction;
	
	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: add
	 * @param mi
	 * @see com.yeshj.pacman.dao.IMediainfoDAO#add(com.yeshj.pacman.model.MediainfoModel)
	 */
	@Override
	public void add(MediainfoModel mi) {
		
		if (addAction == null)
			addAction = new MiAddAction(getDataSource());
		
		addAction.update(mi.getTid(), 
						 mi.getMedia(),
						 mi.getAudio_rate(),
						 mi.getAudio_sampling(),
						 mi.getAudio_duration(),
						 mi.getAudio_codec(),
						 mi.getVideo_width(),
						 mi.getVideo_height(),
						 mi.getVideo_rate(),
						 mi.getVideo_duration(),
						 mi.getVideo_codec());
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: delete
	 * @param mi
	 * @see com.yeshj.pacman.dao.IMediainfoDAO#delete(com.yeshj.pacman.model.MediainfoModel)
	 */
	@Override
	public void delete(MediainfoModel mi) {
		
		String sql = "DELETE FROM mediainfo WHERE tid=" + mi.getTid();
		getJdbcTemplate().execute(sql);
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: save
	 * @param mi
	 * @see com.yeshj.pacman.dao.IMediainfoDAO#save(com.yeshj.pacman.model.MediainfoModel)
	 */
	@Override
	public void save(MediainfoModel mi) {

		if (saveAction == null)
			saveAction = new MiSaveAction(getDataSource());
		
		saveAction.update(mi.getMedia(),
				          mi.getAudio_rate(),
				          mi.getAudio_sampling(),
				          mi.getAudio_duration(),
				          mi.getAudio_codec(),
				          mi.getVideo_width(),
				          mi.getVideo_height(),
				          mi.getVideo_rate(),
				          mi.getVideo_duration(),
				          mi.getVideo_codec(),
				          mi.getTid());
	}

	/**
	 * (non Javadoc) TODO
	 * 
	 * @Title: findByPk
	 * @param taskid
	 * @return
	 * @see com.yeshj.pacman.dao.IMediainfoDAO#findByPk(int)
	 */
	@Override
	public MediainfoModel findByPk(int taskid) {

		MediainfoMappingQuery query = new MediainfoMappingQuery();
		query.setDataSource(getDataSource());
		query.setSql("SELECT * FROM mediainfo WHERE tid=?");
		query.declareParameter(new SqlParameter(Types.INTEGER));
		query.compile();
		
		return query.findObject(taskid);
	}

}
