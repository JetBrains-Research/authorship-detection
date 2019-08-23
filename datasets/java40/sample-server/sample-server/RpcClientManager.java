package com.lz.center.rpc;
import com.lz.center.entity.ZoneEntity;
import com.lz.game.rpc.ChatServerRpcClient;
import com.lz.game.rpc.GameServerRpcClient;
import com.lz.game.rpc.RpcHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * UserEntity: Teaey
 * Date: 13-7-15
 */
public class RpcClientManager
{
    private static final Logger           log      = LoggerFactory.getLogger(RpcClientManager.class);
    private static final RpcClientManager instance = new RpcClientManager();
    public static RpcClientManager getInstance()
    {
        return instance;
    }
    private final Map<String, ChatServerRpcClient> chatServerRpcClientMap = new ConcurrentHashMap<>();
    private final Map<String, GameServerRpcClient> gameServerRpcClientMap = new ConcurrentHashMap<>();
    private final Map<String, ChatClient>          chatClientMap          = new ConcurrentHashMap<>();
    private RpcClientManager()
    {
    }
    public void disconnectRpcServer(ZoneEntity zoneEntity)
    {
        //TODO:是否一个服务器组对应一个区？如果不是，移除
        GameServerRpcClient gameServerRpcClient = gameServerRpcClientMap.remove(zoneEntity.getGameServerHost());
        if (null != gameServerRpcClient)
        {
            gameServerRpcClient.shutdown();
        }
        ChatServerRpcClient chatServerRpcClient = chatServerRpcClientMap.remove(zoneEntity.getChatServerHost());
        if (null != chatServerRpcClient)
        {
            chatServerRpcClient.shutdown();
        }
        ChatClient chatClient = chatClientMap.remove(zoneEntity.getChatServerHost());
        if (null != chatClient)
        {
            chatClient.shutdown();
        }
    }
    public void connectRpcServer(ZoneEntity zoneEntity)
    {
        String gameServer = zoneEntity.getGameServerHost() == null ? null : zoneEntity.getGameServerHost().trim();
        String chatServer = zoneEntity.getChatServerHost() == null ? null : zoneEntity.getChatServerHost().trim();
        if (gameServer != null)
        {
            if (!gameServerRpcClientMap.containsKey(gameServer))
            {
                try
                {
                    String[] subs = gameServer.split(":");
                    GameServerRpcClient gameServerRpcClient = new GameServerRpcClient(subs[0], RpcHelper.getDefaultRpcPort(Integer.valueOf(subs[1])));
                    gameServerRpcClientMap.put(gameServer, gameServerRpcClient);
                } catch (Exception e)
                {
                    log.error("创建GameServerRpcClient失败:{}", zoneEntity, e);
                }
            }
        }
        else
        {
            log.error("GameServerHost为null:{}", zoneEntity);
        }
        if (chatServer != null)
        {
            if (!chatServerRpcClientMap.containsKey(chatServer))
            {
                try
                {
                    String[] subs = chatServer.split(":");
                    ChatServerRpcClient chatServerRpcClient = new ChatServerRpcClient(subs[0], RpcHelper.getDefaultRpcPort(Integer.valueOf(subs[1])));
                    chatServerRpcClientMap.put(chatServer, chatServerRpcClient);
                    ChatClient chatClient = new ChatClient(subs[0], RpcHelper.getDefaultRpcPort(Integer.valueOf(subs[1])));
                    chatClient.connect();
                    chatClientMap.put(chatServer, chatClient);
                } catch (Exception e)
                {
                    log.error("创建ChatServerRpcClient失败:{}", zoneEntity, e);
                }
            }
        }
        else
        {
            log.error("ChatServerHost为null:{}", zoneEntity);
        }
    }
    public List<GameServerRpcClient> listGameServer()
    {
        List<GameServerRpcClient> ret = new ArrayList<>();
        ret.addAll(gameServerRpcClientMap.values());
        return ret;
    }
    public List<ChatServerRpcClient> listChatServer()
    {
        List<ChatServerRpcClient> ret = new ArrayList<>();
        ret.addAll(chatServerRpcClientMap.values());
        return ret;
    }
    public List<ChatClient> listChatClient()
    {
        List<ChatClient> ret = new ArrayList<>();
        ret.addAll(chatClientMap.values());
        return ret;
    }
    public GameServerRpcClient getGameServerRpcClientByZone(ZoneEntity zoneEntity)
    {
        return gameServerRpcClientMap.get(zoneEntity.getGameServerHost());
    }
    public ChatServerRpcClient getChatServerRpcClientByZone(ZoneEntity zoneEntity)
    {
        return chatServerRpcClientMap.get(zoneEntity.getChatServerHost());
    }
    public GameServerRpcClient getGameServerRpcClientByZoneId(int zoneId)
    {
        ZoneEntity zoneEntity = ZoneEntityManager.getInstance().getZoneEntiryByZoneId(zoneId);
        return gameServerRpcClientMap.get(zoneEntity.getGameServerHost());
    }
    public ChatServerRpcClient getChatServerRpcClientByZoneId(int zoneId)
    {
        ZoneEntity zoneEntity = ZoneEntityManager.getInstance().getZoneEntiryByZoneId(zoneId);
        return chatServerRpcClientMap.get(zoneEntity.getChatServerHost());
    }
    public ChatClient getChatClientByZoneId(int zoneId)
    {
        ZoneEntity zoneEntity = ZoneEntityManager.getInstance().getZoneEntiryByZoneId(zoneId);
        return chatClientMap.get(zoneEntity.getChatServerHost());
    }
    public ChatClient getChatClientByZone(ZoneEntity zoneEntity)
    {
        return chatClientMap.get(zoneEntity.getChatServerHost());
    }
    public void shutdown()
    {
        for (ChatServerRpcClient each : chatServerRpcClientMap.values())
        {
            try
            {
                each.shutdown();
            } catch (Exception e)
            {
                log.error("", e);
            }
        }
        for (GameServerRpcClient each : gameServerRpcClientMap.values())
        {
            try
            {
                each.shutdown();
            } catch (Exception e)
            {
                log.error("", e);
            }
        }
        for (ChatClient each : chatClientMap.values())
        {
            try
            {
                each.shutdown();
            } catch (Exception e)
            {
                log.error("", e);
            }
        }
    }
}
