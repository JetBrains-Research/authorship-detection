package com.lz.center.websocket;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import static org.jboss.netty.channel.Channels.pipeline;
/**
 */
public class WebSocketServerPipelineFactory implements ChannelPipelineFactory
{
    private final BaseWebSocketServerHandler baseWebSocketServerHandler;
    public WebSocketServerPipelineFactory(BaseWebSocketServerHandler baseWebSocketServerHandler)
    {
        this.baseWebSocketServerHandler = baseWebSocketServerHandler;
    }
    public ChannelPipeline getPipeline() throws Exception
    {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("handler", baseWebSocketServerHandler);
        return pipeline;
    }
}