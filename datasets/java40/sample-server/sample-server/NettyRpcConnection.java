package com.lz.game.rpc.core;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * User: Teaey
 * Date: 13-7-13
 */
public class NettyRpcConnection extends BaseRpcConnection
{
    private static final Logger log = LoggerFactory.getLogger(NettyRpcConnection.class);
    public NettyRpcConnection(String host, int port, int timeout)
    {
        super(host, port, timeout);
    }
    private ClientBootstrap bootstrap;
    private Channel         channel;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private          Rpc.RpcPacket response;
    private volatile int           responseId;
    private class NettyRpcClientPipelineFactory implements ChannelPipelineFactory
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
                    log.info("NettyRpcConnection connect:{}", ctx.getChannel());
                }
                @Override
                public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
                {
                    log.info("NettyRpcConnection closed:{}", ctx.getChannel());
                }
                @Override
                public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
                {
                    Object msg = e.getMessage();
                    if (msg instanceof Rpc.RpcPacket)
                    {
                        response = (Rpc.RpcPacket) msg;
                        if (response.getCounter() == responseId)
                        {
                            countDownLatch.countDown();
                        }
                        else
                        {
                            log.info("NettyRpcConnection recv a expire packet responseId={} receviedId={}", responseId, response.getCounter());
                        }
                    }
                    else
                    {
                        log.info("NettyRpcConnection unknown packet type:{} disconnect", msg.getClass().getSimpleName(), channel);
                        disconnect();
                    }
                }
                @Override
                public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
                {
                    log.info("NettyRpcConnection caught exception disconnect:{}", channel, e.getCause());
                    disconnect();
                }
            });
            return p;
        }
    }
    @Override
    public void connect()
    {
        if (!isConnected())
        {
            try
            {
                bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
                bootstrap.setPipelineFactory(new NettyRpcClientPipelineFactory());
                bootstrap.setOption("keepAlive", true);
                bootstrap.setOption("tcpNoDelay", true);
                bootstrap.setOption("soTimeout", timeout);
                bootstrap.setPipelineFactory(new NettyRpcClientPipelineFactory());
                ChannelFuture cf = bootstrap.connect(new InetSocketAddress(host, port));
                cf.awaitUninterruptibly(timeout);
                if (cf.isDone() && cf.isSuccess())
                {
                    channel = cf.getChannel();
                }
                else
                {
                    throw new RpcException("failed connection");
                }
            } catch (Exception e)
            {
                disconnect();
                throw new RpcIOException(e);
            }
        }
    }
    @Override
    public boolean isConnected()
    {
        return null != bootstrap && null != channel && channel.isConnected() && channel.isOpen() && channel.isBound() && channel.isWritable() && channel.isReadable();
    }
    @Override
    public void disconnect()
    {
        //        if (null != channel && channel.isOpen())
        //        {
        //            try
        //            {
        //                channel.close();
        //            } catch (Exception e)
        //            {
        //            }
        //        }
        if (null != bootstrap)
        {
            try
            {
                bootstrap.releaseExternalResources();
            } catch (Exception e)
            {
            }
        }
    }
    @Override
    public void send(Rpc.RpcPacket packet)
    {
        connect();
        channel.write(packet);
    }
    @Override
    public Rpc.RpcPacket sendWaitBack(Rpc.RpcPacket packet)
    {
        connect();
        responseId = packet.getCounter();
        channel.write(packet);
        try
        {
            boolean isDone = countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            if (isDone)
            {
                return read();
            }
            else
            {
                throw new RpcIOException("read rpc response timeout");
            }
        } catch (InterruptedException e)
        {
            throw new RpcException(e);
        }
    }
    @Override
    public Rpc.RpcPacket read()
    {
        countDownLatch = new CountDownLatch(1);
        return response;
    }
}
