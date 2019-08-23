package com.lz.game.rpc.core;
import com.google.protobuf.MessageLite;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * User: Teaey
 * Date: 13-7-14
 */
public abstract class RpcPacketHandler
{
    protected static final Logger               log        = LoggerFactory.getLogger(RpcPacketHandler.class);
    private volatile       boolean              RUN        = true;
    private static final   Map<String, Invoker> invokerMap = new HashMap<>();
    private class Invoker
    {
        private Method targetMethod;
        private Method parseFromMethod;
    }
    private final Thread worker = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            RpcPacketWrapper wrapper = null;
            while (RUN)
            {
                try
                {
                    if ((wrapper = DefaultRpcServer.take()) != null)
                    {
                        //                        wrapper.channel.write(wrapper.packet);
                        handle(wrapper);
                    }
                } catch (Throwable e)
                {
                    log.error("", e);
                }
            }
        }
    }, "Rpc-Handler-Worker");
    public static class RpcPacketWrapper implements Runnable
    {
        public RpcPacketWrapper(Channel channel, Rpc.RpcPacket packet)
        {
            this.channel = channel;
            this.packet = packet;
        }
        private final Channel       channel;
        private final Rpc.RpcPacket packet;
        @Override
        public void run()
        {
        }
        public Rpc.RpcPacket getPacket()
        {
            return this.packet;
        }
        public Channel getChannel()
        {
            return this.channel;
        }
    }
    public final void startup()
    {
        init();
        worker.setDaemon(true);
        worker.start();
    }
    private final void init()
    {
        Class clazz = this.getClass();
        for (Method each : clazz.getDeclaredMethods())
        {
            if (each.getName().endsWith("Req"))
            {
                Class[] params = each.getParameterTypes();
                if (params.length > 1)
                {
                    throw new RpcException("invalid handler methold");
                }
                Class reqType = params[0];
                Invoker invoker = new Invoker();
                try
                {
                    Method parseFromMethod = reqType.getMethod("parseFrom", com.google.protobuf.ByteString.class);
                    invoker.parseFromMethod = parseFromMethod;
                } catch (Exception e)
                {
                    throw new RpcException(e);
                }
                invoker.targetMethod = each;
                invokerMap.put(each.getName(), invoker);
            }
        }
    }
    public void handle(final RpcPacketWrapper wrapper)
    {
        Rpc.RpcPacket rpcPacket = wrapper.packet;
        String requestClassName = rpcPacket.getOpcode();
        Invoker invoker = invokerMap.get(requestClassName);
        if (null == invoker)
        {
            log.info("unknown request type:{}", requestClassName);
        }
        else
        {
            try
            {
                MessageLite resp = (MessageLite) invoker.targetMethod.invoke(this, invoker.parseFromMethod.invoke(null, rpcPacket.getContent()));
                if (null != resp)
                {
                    if (!wrapper.channel.isOpen())
                    {
                        log.info("channel has closed discard rpc response {}", wrapper.channel);
                    }
                    else
                    {
                        wrapper.channel.write(Rpc.RpcPacket.newBuilder(rpcPacket).setContent(resp.toByteString()).build());
                    }
                }
            } catch (Throwable e)
            {
                log.error("close channel:{}", wrapper.getChannel(), e);
                wrapper.channel.close();
            }
        }
    }
}
