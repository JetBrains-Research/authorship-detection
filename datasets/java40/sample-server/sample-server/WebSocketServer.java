package com.lz.center.websocket;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
/**
 * UserEntity: Teaey
 * Date: 13-7-11
 */
public class WebSocketServer
{
    private static final Logger          log      = LoggerFactory.getLogger(WebSocketServer.class);
    private static final WebSocketServer instance = new WebSocketServer();
    private WebSocketServer()
    {
    }
    public static WebSocketServer getInstance()
    {
        return instance;
    }
    public void init(String ip, int port)
    {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new WebSocketServerPipelineFactory(new ChatWebSocketServerHandler(this)));
        bootstrap.bind(new InetSocketAddress(ip, port));
    }
    public void init(int port)
    {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new WebSocketServerPipelineFactory(new ChatWebSocketServerHandler(this)));
        bootstrap.bind(new InetSocketAddress(port));
    }
    private ServerBootstrap bootstrap;
    private ChannelGroup channelGroup = new DefaultChannelGroup();
    public void addClientChannel(Channel channel)
    {
        channelGroup.add(channel);
    }
    public void shutdown()
    {
        if (null != bootstrap)
            bootstrap.releaseExternalResources();
    }
    public void send(WebSocketFrame msg)
    {
        channelGroup.write(msg);
        log.info("WebSocketServer broadcast msg {} : {}", channelGroup.size(), msg);
    }
}
