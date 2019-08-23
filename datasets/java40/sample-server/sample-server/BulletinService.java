package com.lz.center.service;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.BulletinEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.game.rpc.ChatServerRpc;
import com.lz.game.rpc.ChatServerRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * UserEntity: Teaey
 * Date: 13-7-17
 */
public class BulletinService
{
    private static final Logger log = LoggerFactory.getLogger(BulletinService.class);
    public static boolean add(BulletinEntity bulletinEntity)
    {
        DaoManager.getLocalDao().saveOrUpdate(bulletinEntity);
        boolean ret = true;
        ChatServerRpc.AddBulletinReq.Builder reqBuilder = ChatServerRpc.AddBulletinReq.newBuilder();
        reqBuilder.addBulletinDto(bulletinEntity.toDTO());
        ChatServerRpc.AddBulletinReq req = reqBuilder.build();
        try
        {
            if (bulletinEntity.getZoneId() == 0)
            {
                for (ChatServerRpcClient each : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.AddBulletinResp resp = each.addBulletin(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
            else
            {
                ChatServerRpcClient client = RpcClientManager.getInstance().getChatServerRpcClientByZoneId(bulletinEntity.getZoneId());
                if (null == client)
                {
                    log.error("未知ZoneId:{}", bulletinEntity.getZoneId());
                    ret = false;
                }
                else
                {
                    ChatServerRpc.AddBulletinResp resp = client.addBulletin(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        if (!ret)
        {
            DaoManager.getLocalDao().delete(bulletinEntity);
        }
        return ret;
    }
    public static boolean delete(BulletinEntity bulletinEntity)
    {
        ChatServerRpc.DeleteBulletinReq.Builder reqBuilder = ChatServerRpc.DeleteBulletinReq.newBuilder();
        reqBuilder.addBulletinDto(bulletinEntity.toDTO());
        ChatServerRpc.DeleteBulletinReq req = reqBuilder.build();
        boolean ret = true;
        try
        {
            if (bulletinEntity.getZoneId() == 0)
            {
                for (ChatServerRpcClient each : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.DeleteBulletinResp resp = each.deleteBulletin(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
            else
            {
                ChatServerRpc.DeleteBulletinResp resp = RpcClientManager.getInstance().getChatServerRpcClientByZoneId(bulletinEntity.getZoneId()).deleteBulletin(req);
                if (!resp.getSuccess())
                {
                    ret = false;
                }
            }
            if (ret)
            {
                DaoManager.getLocalDao().delete(bulletinEntity);
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        return ret;
    }
    public static boolean modify(BulletinEntity bulletin)
    {
        ChatServerRpc.UpdateBulletinReq.Builder reqBuilder = ChatServerRpc.UpdateBulletinReq.newBuilder();
        reqBuilder.addBulletinDTO(bulletin.toDTO());
        ChatServerRpc.UpdateBulletinReq req = reqBuilder.build();
        boolean ret = true;
        try
        {

            if (bulletin.getZoneId() == 0)
            {
                for (ChatServerRpcClient each : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.UpdateBulletinResp resp = each.updateBulletin(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
            else
            {
                ChatServerRpc.UpdateBulletinResp resp = RpcClientManager.getInstance().getChatServerRpcClientByZoneId(bulletin.getZoneId()).updateBulletin(req);
                if (!resp.getSuccess())
                {
                    ret = false;
                }
            }
            if (ret)
            {
                DaoManager.getLocalDao().saveOrUpdate(bulletin);
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        return ret;
    }
}
