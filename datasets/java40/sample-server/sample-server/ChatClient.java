package com.lz.center.rpc;
import com.google.protobuf.MessageLite;
import com.googlecode.protobuf.format.JsonFormat;
import com.lz.center.ServerState;
import com.lz.center.websocket.WebSocketServer;
import com.lz.game.rpc.ChatServerRpc;
import com.lz.game.rpc.core.ProtocolBuffersDecoder;
import com.lz.game.rpc.core.ProtocolBuffersEncoder;
import com.lz.game.rpc.core.Rpc;
import com.lz.game.rpc.util.MD5Encrypt;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
/**
 * UserEntity: Teaey
 * Date: 13-7-10
 */
public class ChatClient
{
    public ChatClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }
    private static final Logger log = LoggerFactory.getLogger(ChatClient.class);
    private volatile ClientBootstrap bootstrap;
    private volatile Channel         channel;
    private final    String          host;
    private final    int             port;
    private final class ChatPipelineFactory implements ChannelPipelineFactory
    {
        private InternalHandler internalHandler = new InternalHandler();
        @Override
        public ChannelPipeline getPipeline() throws Exception
        {
            ChannelPipeline p = Channels.pipeline();
            p.addLast("protobufDecoder", new ProtocolBuffersDecoder(Rpc.RpcPacket.getDefaultInstance()));
            p.addLast("protobufEncoder", new ProtocolBuffersEncoder());
            p.addLast("handler", internalHandler);
            return p;
        }
        private final class InternalHandler extends SimpleChannelHandler
        {
            @Override
            public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
            {
                log.info("从聊天服务器端口:{}", ctx.getChannel());
            }
            @Override
            public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
            {
                log.info("连上聊天服务器:{}", ctx.getChannel());
            }
            @Override
            public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
            {
                if (!(e.getMessage() instanceof Rpc.RpcPacket))
                {
                    log.info("未知聊天数据包:{}", e.getMessage().getClass().getSimpleName());
                }
                Rpc.RpcPacket resp = (Rpc.RpcPacket) e.getMessage();
                ChatServerRpc.ChatReq req = ChatServerRpc.ChatReq.parseFrom(resp.getContent());
                String str = JsonFormat.printToString(req);
                WebSocketServer.getInstance().send(new TextWebSocketFrame(str));
            }
        }
    }
    public void connect()
    {
        if (!isConnected())
        {
            long start = System.currentTimeMillis();
            int times = 3;
            while (ServerState.isRunning() && times > 0)
            {
                try
                {
                    shutdown();
                    bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
                    bootstrap.setOption("keepAlive", true);
                    bootstrap.setOption("tcpNoDelay", true);
                    bootstrap.setOption("reuseAddress", false);
                    bootstrap.setPipelineFactory(new ChatPipelineFactory());
                    ChannelFuture cf = bootstrap.connect(new InetSocketAddress(host, port));
                    cf.awaitUninterruptibly();
                    if (cf.isDone() && cf.isSuccess())
                    {
                        channel = cf.getChannel();
                        break;
                    }
                } catch (Exception e)
                {
                    log.error("", e);
                    shutdown();
                } finally
                {
                    times--;
                }
            }
            if (isConnected())
            {
                ChatServerRpc.AuthReq.Builder req = ChatServerRpc.AuthReq.newBuilder();
                req.setTimestamp(start);
                req.setSign(channel.toString());
                req.setToken(MD5Encrypt.internalEncrypt(start, channel.toString()));
                Rpc.RpcPacket conn = Rpc.RpcPacket.newBuilder().setContent(req.build().toByteString()).setOpcode(ChatServerRpc.AuthReq.class.getSimpleName()).build();
                channel.write(conn);
            }
        }
    }
    public final Rpc.RpcPacket newRpcPacket(MessageLite req)
    {
        if (null == req)
        {
            throw new NullPointerException("req");
        }
        int counter = 0 ;
        Rpc.RpcPacket.Builder builder = Rpc.RpcPacket.newBuilder();
        builder.setCounter(counter);
        builder.setOpcode(req.getClass().getSimpleName());
        builder.setContent(req.toByteString());
        return builder.build();
    }
    public void write(MessageLite msg)
    {
        connect();
        channel.write(newRpcPacket(msg));
    }
    public boolean isConnected()
    {
        return null != channel && channel.isConnected() && channel.isBound() && channel.isOpen();
    }
    public void shutdown()
    {
        try
        {
            if (null != channel)
                channel.close();
        } catch (Exception e)
        {
            log.error("", e);
        }
        try
        {
            if (null != bootstrap)
                bootstrap.releaseExternalResources();
        } catch (Exception e)
        {
            log.error("", e);
        }
    }
}
