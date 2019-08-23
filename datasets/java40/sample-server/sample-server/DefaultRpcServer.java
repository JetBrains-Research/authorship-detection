package com.lz.game.rpc.core;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * User: Teaey
 * Date: 13-7-14
 */
public class DefaultRpcServer extends BaseRpcServer
{
    public DefaultRpcServer(int port)
    {
        super(port);
    }
    private ServerBootstrap bootstrap;
    /**
     * 当请求队列长度超出数值 会输出一条警告
     */
    private static final int                                                    QUEUE_SIZE_WARN_THRESHOLD = 50;
    private static final LinkedBlockingQueue<RpcPacketHandler.RpcPacketWrapper> rpcRequestQueue           = new LinkedBlockingQueue<>();
    /**
     * 请求处理器消费请求
     * @return 处理器需要判断返回是否为null，为null忽略
     * @throws InterruptedException
     */
    public static final RpcPacketHandler.RpcPacketWrapper take()
    {
        try
        {
            return rpcRequestQueue.poll(1000L, TimeUnit.MILLISECONDS);
        } catch (Exception e)
        {
            throw new RpcException(e);
        }
    }
    private static final void put(RpcPacketHandler.RpcPacketWrapper diapatcherWrapper)
    {
        if (rpcRequestQueue.size() >= QUEUE_SIZE_WARN_THRESHOLD)
        {
            log.warn("size of rpc request queue is big than {}", QUEUE_SIZE_WARN_THRESHOLD);
        }
        try
        {
            boolean success = rpcRequestQueue.offer(diapatcherWrapper, 1000L, TimeUnit.MILLISECONDS);
            if (!success)
            {
                log.error("put rpc request to queue timeout {}", diapatcherWrapper.getPacket());
            }
        } catch (InterruptedException e)
        {
            log.error("", e);
        }
    }
    private class NettyRpcServerPipelineFactory implements ChannelPipelineFactory
    {
        @Override
        public ChannelPipeline getPipeline() throws Exception
        {
            ChannelPipeline p = Channels.pipeline();
            p.addLast("encoder", new ProtocolBuffersEncoder());
            p.addLast("decoder", new ProtocolBuffersDecoder(Rpc.RpcPacket.getDefaultInstance()));
            p.addLast("handler", new SimpleChannelHandler()
            {
                @Override
                public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
                {
                    log.info("DefaultRpcServer connect:{}", ctx.getChannel());
                }
                @Override
                public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
                {
                    log.info("DefaultRpcServer closed:{}", ctx.getChannel());
                }
                @Override
                public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
                {
                    Object msg = e.getMessage();
                    if (msg instanceof Rpc.RpcPacket)
                    {
                        put(new RpcPacketHandler.RpcPacketWrapper(ctx.getChannel(), (Rpc.RpcPacket) msg));
                    }
                    else
                    {
                        log.info("DefaultRpcServer unknown packet type:{} disconnect", msg.getClass().getSimpleName(), ctx.getChannel());
                    }
                }
                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
                {
                    log.info("DefaultRpcServer caught exception disconnect:{}", ctx.getChannel(), e.getCause());
                    ctx.getChannel().close();
                }
            });
            return p;
        }
    }
    @Override
    protected void beforeStartup() throws Exception
    {
    }
    @Override
    protected void doStartup() throws Exception
    {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setPipelineFactory(new NettyRpcServerPipelineFactory());
        bootstrap.bind(new InetSocketAddress(port));
    }
    @Override
    protected void beforeShutdown() throws Exception
    {
    }
    @Override
    protected void doShutdown() throws Exception
    {
        if (null != bootstrap)
        {
            bootstrap.releaseExternalResources();
        }
    }
}
