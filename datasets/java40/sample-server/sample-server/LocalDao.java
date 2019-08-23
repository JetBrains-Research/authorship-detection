package com.lz.center.dao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
/**
 * 操作GM数据库
 * User: Teaey
 * Date: 13-9-17
 */
public class LocalDao extends BaseDao
{
    LocalDao(Configuration c)
    {
        super(c);
    }
    public List<Object> getAverageOnlineData(Date date)
    {
        Session session = null;
        try
        {
            Timestamp timeStamp = new Timestamp(date.getTime());
            session = sessionFactory.openSession();
            Query query = session.createSQLQuery("select count(*),sum(num) from gm_online_info " + " where  day(add_time)=day('" + timeStamp + "') and   month(add_time)=month('" + timeStamp + "') and   year(add_time)=year('" + timeStamp + "')");
            List<Object> ret = query.list();
            return ret;
        } finally
        {
            if (session != null)
            {
                session.close();
            }
        }
    }
}
