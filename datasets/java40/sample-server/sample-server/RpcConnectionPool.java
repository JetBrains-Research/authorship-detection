package com.lz.game.rpc.core;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
/**
 * User: Teaey
 * Date: 13-7-11
 */
public class RpcConnectionPool extends GenericObjectPool<BaseRpcConnection>
{
    private static final Logger log = LoggerFactory.getLogger(RpcConnectionPool.class);
    public RpcConnectionPool(final String host, final int port, final int timeout)
    {
        super(new RpcConnectionFactory(host, port, timeout), new Config());
    }
    public RpcConnectionPool(final String host, final int port, final int timeout, final Config poolConfig)
    {
        super(new RpcConnectionFactory(host, port, timeout), poolConfig);
    }
    public BaseRpcConnection borrowRpcClient()
    {
        try
        {
            return borrowObject();
        } catch (Exception e)
        {
            throw new RpcException("Could not borrow a resource from the pool", e);
        }
    }
    public void returnRpcClient(final BaseRpcConnection rpcClient)
    {
        try
        {
            if (null != rpcClient)
                this.returnObject(rpcClient);
        } catch (Exception e)
        {
            throw new RpcException("Could not return the resource to the pool", e);
        }
    }
    public void returnBrokenRpcClient(final BaseRpcConnection rpcClient)
    {
        try
        {
            if (null != rpcClient)
                this.invalidateObject(rpcClient);
        } catch (Exception e)
        {
            throw new RpcException("Could not return the resource to the pool", e);
        }
    }
    public void destroy()
    {
        try
        {
            super.close();
        } catch (Exception e)
        {
            throw new RpcException("Could not destroy the pool", e);
        }
    }
    private static final class RpcConnectionFactory extends BasePoolableObjectFactory<BaseRpcConnection>
    {
        private final String host;
        private final int    port;
        private final int    timeout;
        private RpcConnectionFactory(final String host, final int port, final int timeout)
        {
            this.host = host;
            this.port = port;
            this.timeout = timeout;
        }
        @Override
        public BaseRpcConnection makeObject() throws Exception
        {
            BaseRpcConnection rpcClient = new NettyRpcConnection(host, port, timeout);
            //            BaseRpcConnection rpcClient = new OioRpcConnection(host, port, timeout);
            rpcClient.connect();
            int attempts = 0;
            while (!rpcClient.isConnected() && attempts < 10)
            {
                log.info("BaseRpcConnection reconnect remote server, host = {} port = {} attempts = {}", new Object[]{host, port, ++attempts});
                rpcClient.connect();
            }
            if (!rpcClient.isConnected())
            {
                log.info("BaseRpcConnection failed to connect remote server, host = {} port = {} attempts = {}", new Object[]{host, port, attempts});
                throw new ConnectException("failed to connect remote server");
            }
            return rpcClient;
        }
        @Override
        public void destroyObject(final BaseRpcConnection obj) throws Exception
        {
            obj.disconnect();
        }
        @Override
        public boolean validateObject(final BaseRpcConnection obj)
        {
            try
            {
                return obj.isConnected();
            } catch (Exception e)
            {
                log.error("", e);
                return false;
            }
        }
    }
}
