package com.lz.game.game3.gateway;
import cn.teaey.fenrisulfr.IDispatcher;
import cn.teaey.fenrisulfr.conn.RpcClient;
import cn.teaey.fenrisulfr.conn.ShutdownException;
import cn.teaey.fenrisulfr.core.Fenrisulfr;
import com.lz.game.game3.common.proto.Logic2Login1;
import com.lz.game.game3.common.proto.LogicLoginOpcode;
import org.jboss.netty.channel.Channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * User: Teaey
 * Date: 13-6-16
 */
public class Client
{
    public static void main(String[] args) throws InterruptedException, IOException, ShutdownException
    {
        RpcClient client = new RpcClient("192.168.1.210", 10000, new IDispatcher()
        {
            @Override
            public void diapatch(Channel channel, Object pkt)
            {
                System.out.println("收到消息：");
                System.out.println(pkt.toString());
            }
        }, "", 0);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Channel c = null;
        int counter = 0;
        while (true)
        {
            String cmd = reader.readLine().trim();
            switch (cmd)
            {
                case "send":
                    Fenrisulfr.RpcPacket.Builder ret = Fenrisulfr.RpcPacket.newBuilder();
                    ret.setCounter(++counter).setFlag(0).setOpcode(LogicLoginOpcode.CP_Login).setTimestamp(System.currentTimeMillis()).setToken("empty_str");
                    Logic2Login1.CP_Login.Builder realReq = Logic2Login1.CP_Login.newBuilder();
                    realReq.setTimestamp(System.currentTimeMillis());
                    ret.setContent(realReq.build().toByteString());
                    client.send(ret.build());
                    break;
                case "close":
                    if (null != c)
                    {
                        c.close();
                        System.out.println("closed");
                    }
                    break;
                case "shutdown":
                    client.disconnect();
            }
        }
    }
}
