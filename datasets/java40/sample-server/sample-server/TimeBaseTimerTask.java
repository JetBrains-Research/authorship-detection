package com.lz.center;
import com.lz.center.dao.DaoManager;
import com.lz.center.entity.ActiveUserInfoEntity;
import com.lz.center.entity.OnlineInfoEntity;
import com.lz.center.entity.RegisterInfoEntity;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.service.SynService;
import com.lz.game.rpc.ChatServerRpc;
import com.lz.game.rpc.ChatServerRpcClient;
import com.lz.game.rpc.RpcDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * UserEntity: Teaey
 * Date: 13-7-16
 */
public abstract class TimeBaseTimerTask extends TimerTask
{
    public static final    TimeBaseTimerTask TwoMinutesTimer  = new TimeBaseTimerTask(2, TimeUnit.MINUTES)
    {
        @Override
        protected void doRun()
        {
            SynService.syn();
        }
    };
    public static final    TimeBaseTimerTask FiveMinutesTimer = new TimeBaseTimerTask(5, TimeUnit.MINUTES)
    {
        @Override
        protected void doRun()
        {
            {
                //获取当前在线信息
                ChatServerRpc.GetOnlineInfoReq req = ChatServerRpc.GetOnlineInfoReq.newBuilder().build();
                for (ChatServerRpcClient client : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.GetOnlineInfoResp resp = client.getOnlineInfo(req);
                    for (RpcDto.OnlineInfoDTO each : resp.getOnlineInfoDtoList())
                    {
                        OnlineInfoEntity info = OnlineInfoEntity.fromDTO(each);
                        info.setNum(resp.getTotalNum());
                        info.setAddTime(this.getLastStart());
                        info.setModifyTime(this.getLastStart());
                        DaoManager.getLocalDao().saveOrUpdate(info);
                    }
                }
            }
            {
                //获取当前注册人数信息
                ChatServerRpc.GetRegisterInfoReq req = ChatServerRpc.GetRegisterInfoReq.newBuilder().build();
                for (ChatServerRpcClient client : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.GetRegisterInfoResp resp = client.getRegisterInfo(req);
                    for (RpcDto.RegisterInfoDTO each : resp.getRegisterInfoDTOList())
                    {
                        RegisterInfoEntity registerInfoEntity = RegisterInfoEntity.fromDTO(each);
                        registerInfoEntity.setAddTime(this.getLastStart());
                        registerInfoEntity.setModifyTime(this.getLastStart());
                        DaoManager.getLocalDao().saveOrUpdate(registerInfoEntity);
                    }
                }
            }
            {
                //获取活跃用户信息
                ChatServerRpc.GetActiveUserReq req = ChatServerRpc.GetActiveUserReq.newBuilder().build();
                for (ChatServerRpcClient client : RpcClientManager.getInstance().listChatServer())
                {
                    ChatServerRpc.GetActiveUserResp resp = client.getActiveUser(req);
                    ActiveUserInfoEntity activeUserInfoEntity = new ActiveUserInfoEntity();
                    activeUserInfoEntity.setNum(resp.getNum());
                    DaoManager.getLocalDao().saveOrUpdate(activeUserInfoEntity);
                }
            }
        }
    };
    protected static final Logger            log              = LoggerFactory.getLogger(TimeBaseTimerTask.class);
    private                AtomicBoolean     START            = new AtomicBoolean(false);
    private Timer timer;
    public TimeBaseTimerTask(long timeInterval, TimeUnit timeUnit)
    {
        if (null == timeUnit)
            throw new NullPointerException("timeUnit");
        if (timeInterval <= 0)
            throw new IllegalArgumentException("timeInterval must be big than zero");
        this.timeIntervalInMillis = timeUnit.toMillis(timeInterval);
    }
    private long timeIntervalInMillis;
    private Date lastStart = new Date();
    private Date lastStop  = new Date();
    public void startup()
    {
        if (START.compareAndSet(false, true))
        {
            timer = new Timer(this.getClass().getSimpleName() + "-Timer", true);
            timer.schedule(this, getTimeIntervalInMillis(), getTimeIntervalInMillis());
        }
    }
    public void shutdown()
    {
        try
        {
            if (null != timer)
                timer.cancel();
        } catch (Exception e)
        {
            log.error("", e);
        }
    }
    @Override
    public void run()
    {
        lastStart = new Date();
        try
        {
            doRun();
        } catch (Exception e)
        {
            log.error("", e);
        }
        lastStop = new Date();
    }
    protected abstract void doRun();
    public long getTimeIntervalInMillis()
    {
        return timeIntervalInMillis;
    }
    public Date getLastStart()
    {
        return lastStart;
    }
    public Date getLastStop()
    {
        return lastStop;
    }
}
