package com.lz.game.rpc.core;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * User: Teaey
 * Date: 13-6-24
 */
public abstract class BaseRpcServer
{
    protected static final Logger log  = LoggerFactory.getLogger(BaseRpcServer.class);
    protected              String host = "0.0.0.0";
    protected int port;
    public BaseRpcServer(int port)
    {
        this.port = port;
    }
    public BaseRpcServer(String host, int port)
    {
        if (null == host)
            throw new NullPointerException("host");
        this.host = host;
        this.port = port;
    }
    public final void startup()
    {
        try
        {
            InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
            beforeStartup();
            doStartup();
            log.info("{} server startup on {}:{}", new Object[]{this.getClass().getSimpleName(), host, port});
        } catch (Throwable t)
        {
            log.error("", t);
        }
    }
    public final void shutdown()
    {
        try
        {
            beforeShutdown();
            doShutdown();
            log.info("{} server shutdown", this.getClass().getSimpleName());
        } catch (Throwable t)
        {
            log.error("", t);
        }
    }
    protected abstract void beforeStartup() throws Exception;
    protected abstract void doStartup() throws Exception;
    protected abstract void beforeShutdown() throws Exception;
    protected abstract void doShutdown() throws Exception;
}
