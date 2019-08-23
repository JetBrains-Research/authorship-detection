package com.lz.game.game3.logic.network;
import cn.teaey.fenrisulfr.ProtocolBuffersDecoder;
import cn.teaey.fenrisulfr.ProtocolBuffersEncoder;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import com.google.protobuf.Message;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class LogicPipelineFactory implements ChannelPipelineFactory
{
    private static final Logger           log              = LoggerFactory.getLogger(LogicPipelineFactory.class);
    private final        Message          defaultInstance  = Fenrisulfr.TransPacket.getDefaultInstance();
    private final        ExecutionHandler executionHandler = new ExecutionHandler(new MemoryAwareThreadPoolExecutor(8, 1048576, 1048576));
    private final        LogicHandler     handler          = new LogicHandler();
    @Override
    public ChannelPipeline getPipeline() throws Exception
    {
        ChannelPipeline p = Channels.pipeline();
        p.addLast("protobufDecoder", new ProtocolBuffersDecoder(defaultInstance));
        p.addLast("protobufEncoder", new ProtocolBuffersEncoder());
        p.addLast("executionHandler", executionHandler);
        p.addLast("handler", handler);
        return p;
    }
}
