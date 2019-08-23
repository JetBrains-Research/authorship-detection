package com.lz.game.game3.gateway.network;
import cn.teaey.fenrisulfr.ServerType;
import cn.teaey.fenrisulfr.conn.NettyClient;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import com.lz.game.game3.gateway.*;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * User: Teaey
 * Date: 13-6-14
 */
public class GatewayInboundHandler extends SimpleChannelHandler
{
    private static final Logger                  log                     = LoggerFactory.getLogger(GatewayInboundHandler.class);
    private static       GatewayServerDispatcher gatewayServerDispatcher = GatewayBeanFactory.getInstance(GatewayServerDispatcher.class);
    private static       BridgeJoint             bridgeJoint             = GatewayBeanFactory.getInstance(BridgeJoint.class);
    //private              NettyClient             toLogic                 = new NettyClient("127.0.0.1", 8889, GatewayBeanFactory.getInstance(BridgeJoint.class), Fenrisulfr.TransPacket.getDefaultInstance());
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
    {
        Channel channel = ctx.getChannel();
        log.info(">>>>>>>>>>>>>>>>channel connected:{}", channel);
        bridgeJoint.addInboundChannel(channel);
        //FIXME:
        GatewayConnection conn = new GatewayConnection();
        conn.setAuth(true);
        conn.setLogicServerid(1);
        channel.setAttachment(conn);
    }
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
    {
        Channel channel = ctx.getChannel();
        log.info("<<<<<<<<<<<<<<<<channel closed:{}", channel);
        channel.setAttachment(null);
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
        Object msg = e.getMessage();
        if (!(msg instanceof Fenrisulfr.RpcPacket))
        {
            log.warn("不明数据包:{},{}", msg.getClass(), ctx.getChannel());
            return;
        }
        Fenrisulfr.RpcPacket pkt = (Fenrisulfr.RpcPacket) msg;
        log.info("收到数据包{}", pkt);
        gatewayServerDispatcher.diapatch(ctx.getChannel(), pkt);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
    {
        log.info("exception caught:{} {}", ctx.getChannel(), e.getCause());
        ctx.getChannel().close();
    }
}
