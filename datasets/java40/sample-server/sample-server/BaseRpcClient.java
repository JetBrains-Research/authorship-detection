package com.lz.game.rpc.core;
import com.google.protobuf.MessageLite;
import com.lz.game.rpc.ChatServerRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * User: Teaey
 * Date: 13-7-14
 */
public abstract class BaseRpcClient
{
    protected static final Logger log             = LoggerFactory.getLogger(ChatServerRpcClient.class);
    private static final   int    DEFAULT_TIMEOUT = 5000;
    protected RpcConnectionPool rpcClientPool;
    public BaseRpcClient(String host, int port)
    {
        rpcClientPool = new RpcConnectionPool(host, port, DEFAULT_TIMEOUT);
    }
    private final AtomicInteger counter = new AtomicInteger(Integer.MAX_VALUE);
    private final int newCounter()
    {
        int ret = counter.incrementAndGet();
        //        if (ret == Integer.MAX_VALUE)
        //        {
        //            counter.compareAndSet(ret, 0);
        //        }
        return ret;
    }
    public final Rpc.RpcPacket newRpcPacket(MessageLite req)
    {
        if (null == req)
        {
            throw new NullPointerException("req");
        }
        int counter = newCounter();
        Rpc.RpcPacket.Builder builder = Rpc.RpcPacket.newBuilder();
        builder.setCounter(counter);
        builder.setOpcode(req.getClass().getSimpleName());
        builder.setContent(req.toByteString());
        return builder.build();
    }
    public final void shutdown()
    {
        try
        {
            rpcClientPool.destroy();
        } catch (Exception e)
        {
            log.error("", e);
        }
    }
}
