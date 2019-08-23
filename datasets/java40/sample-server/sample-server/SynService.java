package com.lz.center.service;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.BulletinEntity;
import com.lz.center.entity.NoticeEntity;
import com.lz.center.entity.ZoneEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.rpc.ZoneEntityManager;
import com.lz.game.rpc.ChatServerRpc;
import com.lz.game.rpc.ChatServerRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * User: Teaey
 * Date: 13-8-19
 */
public class SynService
{
    private static final Logger log = LoggerFactory.getLogger(SynService.class);
    public static final void syn()
    {
        Map<Integer, ChatServerRpc.SynReq.Builder> map = new HashMap<>();
        //ChatServerRpc.SynReq.Builder req = ChatServerRpc.SynReq.newBuilder();
        List<NoticeEntity> noticeList = DaoManager.getLocalDao().selectList(NoticeEntity.class);
        if (noticeList.size() > 0)
        {
            for (NoticeEntity each : noticeList)
            {
                Integer zoneId = each.getZoneId();
                ChatServerRpc.SynReq.Builder req = map.get(zoneId);
                if (null == req)
                {
                    req = ChatServerRpc.SynReq.newBuilder();
                    map.put(zoneId, req);
                }
                req.addSysNoticeDto(each.toDTO());
            }
        }
        List<BulletinEntity> bulletinList = DaoManager.getLocalDao().selectList(BulletinEntity.class);
        if (bulletinList.size() > 0)
        {
            for (BulletinEntity each : bulletinList)
            {
                Integer zoneId = each.getZoneId();
                ChatServerRpc.SynReq.Builder req = map.get(zoneId);
                if (null == req)
                {
                    req = ChatServerRpc.SynReq.newBuilder();
                    map.put(zoneId, req);
                }
                req.addBulletinDto(each.toDTO());
            }
        }
        for (ZoneEntity each : ZoneEntityManager.getInstance().getAllZoneEntiry())
        {
            ChatServerRpcClient client = RpcClientManager.getInstance().getChatServerRpcClientByZone(each);
            ChatServerRpc.SynReq.Builder builder = map.get(each.getZoneId());
            if (null != client && null != builder)
            {
                client.syn(map.get(each.getZoneId()).build());
            }
        }
        //RpcClientManager.getChatServerRpcClient().syn(req.build());
        log.info("同步了Notice&Bulletin");
    }
}
