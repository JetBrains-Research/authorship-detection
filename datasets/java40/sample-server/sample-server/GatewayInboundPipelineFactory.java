package com.lz.game.game3.gateway.network;
import cn.teaey.fenrisulfr.BandwidthMeterHandler;
import cn.teaey.fenrisulfr.ProtocolBuffersDecoder;
import cn.teaey.fenrisulfr.ProtocolBuffersEncoder;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import com.google.protobuf.Message;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
public class GatewayInboundPipelineFactory implements ChannelPipelineFactory
{
    private static final Logger                       log                          = LoggerFactory.getLogger(GatewayInboundPipelineFactory.class);
    //public final static  ChannelTrafficShapingHandler channelTrafficHandler        = new ChannelTrafficShapingHandler(new HashedWheelTimer());
    //private static final int                          MAX_FRAME_BYTES_LENGTH       = 1048576;
    private final        Message                      defaultInstance              = Fenrisulfr.RpcPacket.getDefaultInstance();
    private final        ExecutionHandler             executionHandler             = new ExecutionHandler(new OrderedMemoryAwareThreadPoolExecutor(16, 1048576, 1048576));
    private final        Timer                        timer                        = new HashedWheelTimer();
    //private final        GlobalTrafficShapingHandler  globalTrafficHandler         = new GlobalTrafficShapingHandler(new HashedWheelTimer());
    private final        BandwidthMeterHandler        bandwidthHandler             = new BandwidthMeterHandler(timer);
    private final        IdleStateHandler             idleStateHandler             = new IdleStateHandler(timer, 5 * 60, 0, 0);//(timer,read,write,all)s
    private final        IdleStateAwareChannelHandler idleStateAwareChannelHandler = new IdleStateAwareChannelHandler()
    {
        public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception
        {
            IdleState state = e.getState();
            Channel channel = e.getChannel();
            ChannelFuture cf = channel.close();
            cf.awaitUninterruptibly(2, TimeUnit.SECONDS);
            if (cf.isDone() && cf.isSuccess())
                log.warn("channel {} closed:{}", state, channel);
            else
                log.warn("cant close channel {} in 2s, {}", channel, state);
        }
    };
    private final        GatewayInboundHandler        gatewayInboundHandler        = new GatewayInboundHandler();
    @Override
    public ChannelPipeline getPipeline() throws Exception
    {
        ChannelPipeline p = Channels.pipeline();
        p.addLast("bandwidthHandler", bandwidthHandler);
        //p.addLast("GLOBAL_TRAFFIC_SHAPING", globalTrafficHandler);
        //p.addLast("CHANNEL_TRAFFIC_SHAPING", channelTrafficHandler);
        p.addLast("idleStateHandler", idleStateHandler);
        p.addLast("hearbeat", idleStateAwareChannelHandler);
        p.addLast("protobufDecoder", new ProtocolBuffersDecoder(defaultInstance));
        p.addLast("protobufEncoder", new ProtocolBuffersEncoder());
        p.addLast("executionHandler", executionHandler);
        p.addLast("handler", gatewayInboundHandler);
        return p;
    }
}
