package com.lz.center.rpc;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.ZoneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * User: Teaey
 * Date: 13-9-18
 */
public class ZoneEntityManager
{
    private static final Logger                   log           = LoggerFactory.getLogger(ZoneEntityManager.class);
    private static final ZoneEntityManager        instance      = new ZoneEntityManager();
    /**
     * key: zoneId
     * value: zoneEntity
     */
    private final        Map<Integer, ZoneEntity> zoneEntityMap = new ConcurrentHashMap<>();
    private ZoneEntityManager()
    {
        List<ZoneEntity> list = DaoManager.getLocalDao().selectList(ZoneEntity.class);
        for (ZoneEntity each : list)
        {
            zoneEntityMap.put(each.getZoneId(), each);
            RpcClientManager.getInstance().connectRpcServer(each);
        }
    }
    public static ZoneEntityManager getInstance()
    {
        return instance;
    }
    public void addOrUpdateZoneEntity(ZoneEntity zoneEntity)
    {
        ZoneEntity old = zoneEntityMap.put(zoneEntity.getZoneId(), zoneEntity);
        RpcClientManager.getInstance().connectRpcServer(zoneEntity);
        log.info("添加ZoneEntiry:{}", zoneEntity);
    }
    public void deleteZoneEntity(ZoneEntity zoneEntity)
    {
        ZoneEntity old = zoneEntityMap.remove(zoneEntity.getZoneId());
        RpcClientManager.getInstance().disconnectRpcServer(old);
        log.info("删除ZoneEntiry:{}", old);
    }
    public Collection<ZoneEntity> getAllZoneEntiry()
    {
        return zoneEntityMap.values();
    }
    public ZoneEntity getFirstZoneEntiry()
    {
        Collection<ZoneEntity> coll = zoneEntityMap.values();
        if (coll.size() > 0)
        {
            return coll.iterator().next();
        }
        else
        {
            return null;
        }
    }
    public ZoneEntity getZoneEntiryByZoneId(int zoneId)
    {
        return zoneEntityMap.get(zoneId);
    }
    public ZoneEntity getByZoneId(int zoneId)
    {
        return zoneEntityMap.get(zoneId);
    }
    public ZoneEntity getById(int id)
    {
        for (ZoneEntity each : zoneEntityMap.values())
        {
            if (each.getId() == id)
            {
                return each;
            }
        }
        return null;
    }
}
