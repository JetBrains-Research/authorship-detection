package com.lz.game.game3.gateway;
import cn.teaey.fenrisulfr.CompressHelper;
import cn.teaey.fenrisulfr.IDispatcher;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import com.google.inject.Singleton;
import com.google.protobuf.ByteString;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
/**
 * User: Teaey
 * Date: 13-6-16
 */
@Singleton
public class BridgeJoint implements IDispatcher<Fenrisulfr.TransPacket>
{
    private static final Logger       log               = LoggerFactory.getLogger(BridgeJoint.class);
    private              ChannelGroup allInboundChanels = new DefaultChannelGroup();
    private static final int          COMPRESS_VALUE    = 1024;
    private static final int          COMPRESSED        = 0x01;
    private static final int          ENCRYPTED         = 0x02;
    @Override
    public void diapatch(Channel channel, Fenrisulfr.TransPacket packet)
    {
        int channelId = packet.getChannelId();
        Channel inbound = allInboundChanels.find(channelId);
        if (null == inbound)
        {
            log.warn("inbound channel is null {}", channelId);
        }
        else if (!inbound.isOpen() || !inbound.isConnected() || !inbound.isWritable())
        {
            log.warn("inbound channel is closed or disconnected or not writable {}", inbound);
        }
        else
        {
            inbound.write(getRpcPacket(packet));
        }
    }
    public Fenrisulfr.RpcPacket getRpcPacket(Fenrisulfr.TransPacket trans)
    {
        Fenrisulfr.RpcPacket.Builder ret = Fenrisulfr.RpcPacket.newBuilder();
        ret.setCounter(trans.getCounter());
        ret.setOpcode(trans.getOpcode());
        ret.setTimestamp(System.currentTimeMillis());
        int responseCode = trans.getResponseCode();
        ret.setResponseCode(responseCode);
        int flag = 0;
        if (responseCode > 0)
        {
            ByteString content = trans.getContent();
            if (content.size() > COMPRESS_VALUE)
            {
                byte[] origin = content.toByteArray();
                try
                {
                    byte[] compress = CompressHelper.zlibCompress(origin);
                    content = ByteString.copyFrom(compress);
                    flag |= COMPRESSED;
                } catch (IOException e)
                {
                    log.warn("compress failed packet opcode={} size={}", trans.getOpcode(), content.size());
                }
            }
            ret.setContent(content);
        }
        ret.setFlag(flag);
        return ret.build();
    }
    public void addInboundChannel(Channel channel)
    {
        allInboundChanels.add(channel);
    }
    //    private BridgeJoint()
    //    {
    //    }
    //    private static final BridgeJoint bridgeJoint = new BridgeJoint();
    //    public static BridgeJoint getInstance()
    //    {
    //        return bridgeJoint;
    //    }
}
