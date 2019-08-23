package com.lz.game.game3.logic;
import cn.teaey.fenrisulfr.BaseServer;
import cn.teaey.fenrisulfr.Config;
import cn.teaey.fenrisulfr.LogbackConfigUtil;
import cn.teaey.fenrisulfr.ServerType;
import cn.teaey.fenrisulfr.zk.ServerRegister;
import com.lz.game.game3.common.ServerContext;
import com.lz.game.game3.logic.network.LogicPipelineFactory;
import org.apache.zookeeper.KeeperException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
/**
 * User: Teaey
 * Date: 13-6-14
 */
public class Logicserver extends BaseServer
{
    static
    {
        LogbackConfigUtil.config();
    }
    private static final Logger log = LoggerFactory.getLogger(Logicserver.class);
    public Logicserver(String ip, int port, String name)
    {
        super(ip, port, name);
    }
    private ServerBootstrap bootstrap;
    @Override
    public void beforeStart() throws Exception
    {
        ServerContext.init(ServerType.LOGIC);
        if (!name.startsWith(ServerContext.getServerType().toString().toLowerCase()))
            throw new IllegalArgumentException(name);
        String[] args = Config.getStringServerConf(name).split(":");
        if (args.length <= 2)
            throw new IllegalArgumentException("必须指定至少一个逻辑服务器ID");
        List<Integer> logicServers = new ArrayList<>(args.length - 2);
        for (int i = 2; i < args.length; i++)
            logicServers.add(Integer.valueOf(args[i]));
        ServerRegister.regist(ip, port, ServerType.LOGIC.getType(), false, true, true, logicServers);
        log.info("logic servers:{}", logicServers);
    }
    @Override
    public void doStart() throws Exception
    {
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("reuseAddress", false);
        bootstrap.setPipelineFactory(new LogicPipelineFactory());
        bootstrap.bind(new InetSocketAddress(ip, port));
    }
    @Override
    public void doStop()
    {
        if (null != bootstrap)
            bootstrap.releaseExternalResources();
    }
    public static void main(String[] args) throws KeeperException, InterruptedException
    {
        if (args.length == 0)
            throw new IllegalArgumentException("指定一个服务器");
        String ip = Config.getServerIp(args[0]);
        int port = Config.getServerPort(args[0]);
        new Logicserver(ip, port, args[0]).start();
    }
}
