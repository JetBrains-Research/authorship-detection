package com.lz.game.game3.gateway;
import cn.teaey.fenrisulfr.BaseServer;
import cn.teaey.fenrisulfr.Config;
import cn.teaey.fenrisulfr.LogbackConfigUtil;
import cn.teaey.fenrisulfr.ServerType;
import cn.teaey.fenrisulfr.zk.ServerMonitor;
import com.lz.game.game3.common.ServerContext;
import com.lz.game.game3.gateway.network.GatewayInboundPipelineFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
/**
 * User: Teaey
 * Date: 13-6-14
 */
public class Gatewayserver extends BaseServer
{
    static
    {
        LogbackConfigUtil.config();
    }
    public Gatewayserver(String ip, int port, String name)
    {
        super(ip, port, name);
    }
    private ServerBootstrap bootstrap;
    @Override
    public void beforeStart() throws Exception
    {
        ServerContext.init(ServerType.GATEWAY);
        if (!name.startsWith(ServerContext.getServerType().toString().toLowerCase()))
            throw new IllegalArgumentException(name);
        ServerMonitor.monitor(GatewayBeanFactory.getInstance(BridgeJoint.class));
    }
    @Override
    public void doStart() throws Exception
    {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("reuseAddress", false);
        bootstrap.setPipelineFactory(new GatewayInboundPipelineFactory());
        bootstrap.bind(new InetSocketAddress(ip, port));
    }
    @Override
    public void doStop()
    {
        if (null != bootstrap)
            bootstrap.releaseExternalResources();
    }
    public static void main(String[] args)
    {                 System.out.print(0x7fffffff);
        if (args.length == 0)
            throw new IllegalArgumentException("没有指定服务器");
        String serverName = args[0];
        String ip = Config.getServerIp(serverName);
        int port = Config.getServerPort(serverName);
        new Gatewayserver(ip, port, serverName).start();

    }
}
