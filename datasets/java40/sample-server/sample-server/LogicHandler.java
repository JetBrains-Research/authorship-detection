package com.lz.game.game3.logic.network;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import com.lz.game.game3.common.proto.LogicMoudleDispatcher;
import com.lz.game.game3.logic.LogicBeanFactory;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * User: Teaey
 * Date: 13-6-14
 */
public class LogicHandler extends SimpleChannelHandler
{
    private static final Logger                log                   = LoggerFactory.getLogger(LogicHandler.class);
    private static final LogicMoudleDispatcher logicMoudleDispatcher = LogicBeanFactory.getService(LogicMoudleDispatcher.class);
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
    {
        Channel channel = ctx.getChannel();
        log.info(">>>>>>>>>>>>>>>>channel connected:{}", channel);
    }
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
    {
        Channel channel = ctx.getChannel();
        log.info("<<<<<<<<<<<<<<<<channel closed:{}", channel);
    }
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
        Object msg = e.getMessage();
        if (!(msg instanceof Fenrisulfr.TransPacket))
        {
            log.warn("不明数据包:{},{}", msg.getClass(), ctx.getChannel());
            return;
        }
        Fenrisulfr.TransPacket pkt = (Fenrisulfr.TransPacket) msg;
        logicMoudleDispatcher.diapatch(ctx.getChannel(), pkt);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
    {
        log.info("exception caught:{} {}", ctx.getChannel(), e.getCause());
        ctx.getChannel().close();
    }
}
