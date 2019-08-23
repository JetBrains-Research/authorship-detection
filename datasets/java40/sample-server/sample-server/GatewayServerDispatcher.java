package com.lz.game.game3.gateway;
import cn.teaey.fenrisulfr.DispatcherHelper;
import cn.teaey.fenrisulfr.IDispatcher;
import cn.teaey.fenrisulfr.ServerType;
import cn.teaey.fenrisulfr.conn.NettyClient;
import cn.teaey.fenrisulfr.conn.ShutdownException;
import cn.teaey.fenrisulfr.conn.TransClient;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import cn.teaey.fenrisulfr.zk.TransClientFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lz.game.game3.common.proto.GatewayMoudleDispatcher;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Teaey
 * @date 13-6-16
 */
@Singleton
public class GatewayServerDispatcher implements IDispatcher<Fenrisulfr.RpcPacket>
{
    private static final Logger log = LoggerFactory.getLogger(GatewayServerDispatcher.class);
    @Inject
    private GatewayMoudleDispatcher gatewayMoudleDispatcher;
    @Override
    public void diapatch(Channel channel, Fenrisulfr.RpcPacket pkt)
    {
        int opcode = pkt.getOpcode();
        int serverType = DispatcherHelper.serverType(opcode);
        ServerType targetServerType = ServerType.get(serverType);
        if (null == targetServerType)
        {
            log.error("unknow servertype:{}", DispatcherHelper.serverType(opcode));
            return;
        }
        switch (targetServerType)
        {
            case GATEWAY:
                gatewayMoudleDispatcher.diapatch(channel, pkt);
                break;
            case LOGIC:
            {
                GatewayConnection conn = (GatewayConnection) channel.getAttachment();
                TransClient client = TransClientFactory.getClient(serverType, conn.getLogicServerid());
                if (null != client)
                {
                    Fenrisulfr.TransPacket trans = Fenrisulfr.TransPacket.newBuilder().setChannelId(channel.getId()).setCounter(pkt.getCounter()).setOpcode(opcode).setContent(pkt.getContent()).build();
                    try
                    {
                        client.send(trans);
                    } catch (ShutdownException e)
                    {
                        log.error("error diapatch transpacket 2 Logic Server");
                    }
                }
                break;
            }
            default:
                break;
        }
    }
}
