package com.lz.center.dao;
import com.lz.center.entity.ZoneEntity;
import org.hibernate.cfg.Configuration;
import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;
import org.lilystudio.smarty4j.Template;
import org.lilystudio.smarty4j.TemplateException;
import org.lilystudio.util.StringWriter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
/**
 * User: Teaey
 * Date: 13-9-17
 */
public class DaoManager
{
    static final LocalDao localDao = new LocalDao(new Configuration().configure());
    public static LocalDao getLocalDao()
    {
        return localDao;
    }
    public static ZoneDao getZoneDao(ZoneEntity zoneEntity)
    {
        return getDao(zoneEntity.getDbUrl(), zoneEntity.getDbUsername(), zoneEntity.getDbPasswd());
    }
    private static final ConcurrentHashMap<String, ZoneDao> daoPool = new ConcurrentHashMap<>();
    public static ZoneDao getDao(String dbUrl, String dbUsername, String dbPasswd)
    {
        try
        {
            ZoneDao ret = daoPool.get(dbUrl);
            if (null == ret)
            {
                ret = getDao0(dbUrl, dbUsername, dbPasswd);
                ZoneDao old = daoPool.putIfAbsent(dbUrl, ret);
                if (null != old)
                {
                    ret = old;
                }
            }
            return ret;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    private static ZoneDao getDao0(String dbUrl, String dbUsername, String dbPasswd) throws IOException, TemplateException
    {
        Engine engine = new Engine();
        Template tpt = engine.parseTemplate(DaoHelper.getConfitTemplate());
        Context context = new Context(); // 生成数据容器对象
        context.set("dbUrl", dbUrl);
        context.set("dbUsername", dbUsername);
        context.set("dbPasswd", dbPasswd);
        StringWriter outData = new StringWriter();
        tpt.merge(context, outData);
        ZoneDao dao = new ZoneDao(new ZoneConfiguration(outData.toString(), dbUrl).doConfig());
        return dao;
    }
}
