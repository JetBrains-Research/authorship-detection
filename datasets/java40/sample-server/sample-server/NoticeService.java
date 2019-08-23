package com.lz.center.service;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.NoticeEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.game.rpc.ChatServerRpc;
import com.lz.game.rpc.ChatServerRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * UserEntity: Teaey
 * Date: 13-7-16
 */
public class NoticeService
{
    private static final Logger log = LoggerFactory.getLogger(NoticeService.class);
    public static boolean add(NoticeEntity sysNotice)
    {
        boolean ret = true;
        try
        {
            ChatServerRpc.AddSysNoticeReq.Builder reqBuilder = ChatServerRpc.AddSysNoticeReq.newBuilder();
            reqBuilder.addSysNoticeDto(sysNotice.toDTO());
            ChatServerRpc.AddSysNoticeReq req = reqBuilder.build();
            if (sysNotice.getZoneId() == 0)
            {
                for (ChatServerRpcClient each : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.AddSysNoticeResp resp = each.addSysNotice(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
            else
            {
                ChatServerRpc.AddSysNoticeResp resp = RpcClientManager.getInstance().getChatServerRpcClientByZoneId(sysNotice.getZoneId()).addSysNotice(req);
                if (!resp.getSuccess())
                {
                    ret = false;
                }
            }
            if (ret)
            {
                DaoManager.getLocalDao().saveOrUpdate(sysNotice);
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        return ret;
    }
    public static boolean delete(NoticeEntity sysNotice)
    {
        boolean ret = true;
        try
        {
            ChatServerRpc.DeleteSysNoticeReq.Builder reqBuilder = ChatServerRpc.DeleteSysNoticeReq.newBuilder();
            reqBuilder.addSysNoticeDto(sysNotice.toDTO());
            ChatServerRpc.DeleteSysNoticeReq req = reqBuilder.build();
            if (sysNotice.getZoneId() == 0)
            {
                for (ChatServerRpcClient each : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.DeleteSysNoticeResp resp = each.deleteSysNotice(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
            else
            {
                ChatServerRpc.DeleteSysNoticeResp resp = RpcClientManager.getInstance().getChatServerRpcClientByZoneId(sysNotice.getZoneId()).deleteSysNotice(req);
                if (!resp.getSuccess())
                {
                    ret = false;
                }
            }
            if (ret)
            {
                DaoManager.getLocalDao().delete(sysNotice);
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        return ret;
    }
    public static boolean update(NoticeEntity sysNotice)
    {
        boolean ret = true;
        try
        {
            ChatServerRpc.UpdateSysNoticeReq.Builder reqBuilder = ChatServerRpc.UpdateSysNoticeReq.newBuilder();
            reqBuilder.addSysNoticeDTO(sysNotice.toDTO());
            ChatServerRpc.UpdateSysNoticeReq req = reqBuilder.build();
            if (sysNotice.getZoneId() == 0)
            {
                for (ChatServerRpcClient each : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.UpdateSysNoticeResp resp = each.updateSysNotice(req);
                    if (!resp.getSuccess())
                    {
                        ret = false;
                    }
                }
            }
            else
            {
                ChatServerRpc.UpdateSysNoticeResp resp = RpcClientManager.getInstance().getChatServerRpcClientByZoneId(sysNotice.getZoneId()).updateSysNotice(req);
            }
            if (ret)
            {
                DaoManager.getLocalDao().saveOrUpdate(sysNotice);
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        return ret;
    }
}
