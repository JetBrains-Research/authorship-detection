package com.lz.center.listener;
import com.lz.center.ContextParamHolder;
import com.lz.center.ServerState;
import com.lz.center.TimeBaseTimerTask;
import com.lz.center.rpc.RpcClientManager;
import com.lz.center.service.SynService;
import com.lz.center.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * UserEntity: Teaey
 * Date: 13-7-6
 */
public class ServerLifecycleListener implements ServletContextAttributeListener, ServletContextListener
{
    private static final Logger log = LoggerFactory.getLogger(ServerLifecycleListener.class);
    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent)
    {
    }
    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent)
    {
    }
    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent)
    {
    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        ServerState.run();
        ContextParamHolder.init(servletContextEvent.getServletContext());
        initServer(servletContextEvent);
    }
    private void initServer(ServletContextEvent servletContextEvent)
    {
        try
        {
            //init rpc server & client
            initRpc();
            //init timers
            initTimer();
            //init websocket
            initWebSocketServer();
            log.info("Center服务器启动");
        } catch (Exception e)
        {
            log.error("", e);
        }
    }
    /**
     * 初始化到其他服务器的Rpc
     */
    private void initRpc()
    {
        //syn data 2 chat server
        RpcClientManager.getInstance();
        SynService.syn();
    }
    /**
     * 初始化定时器
     */
    private void initTimer()
    {
        TimeBaseTimerTask.FiveMinutesTimer.startup();
        TimeBaseTimerTask.TwoMinutesTimer.startup();
    }
    /**
     * 初始化Websocket服务器
     */
    private void initWebSocketServer()
    {
        WebSocketServer.getInstance().init(ContextParamHolder.getInt(ContextParamHolder.WEB_SOCKET_PORT_KEY));
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        try
        {
            ServerState.shutdown();
            TimeBaseTimerTask.FiveMinutesTimer.shutdown();
            TimeBaseTimerTask.TwoMinutesTimer.shutdown();
            WebSocketServer.getInstance().shutdown();
            RpcClientManager.getInstance().shutdown();
        } catch (Exception e)
        {
            log.error("", e);
        }
        log.info("Center服务器关闭");
    }
}
