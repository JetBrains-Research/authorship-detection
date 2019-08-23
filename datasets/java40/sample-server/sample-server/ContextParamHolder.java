package com.lz.center;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
/**
 * User: Teaey
 * Date: 13-8-1
 */
public class ContextParamHolder
{
    private static final Logger              log                      = LoggerFactory.getLogger(ContextParamHolder.class);
    public static final String              WEB_SOCKET_PORT_KEY      = "web_socket_port";
//    public static final String              CHAT_RPC_SERVER_IP_KEY   = "chat_rpc_server_ip";
//    public static final String              CHAT_RPC_SERVER_PORT_KEY = "chat_rpc_server_port";
//    public static final String              GAME_RPC_SERVER_IP_KEY   = "game_rpc_server_ip";
//    public static final String              GAME_RPC_SERVER_PORT_KEY = "game_rpc_server_port";
    private static final Map<String, String> contextParamMap          = new HashMap<>();
    public static void init(ServletContext servletContext)
    {
        contextParamMap.put(WEB_SOCKET_PORT_KEY, servletContext.getInitParameter(WEB_SOCKET_PORT_KEY));
//        contextParamMap.put(CHAT_RPC_SERVER_IP_KEY, servletContext.getInitParameter(CHAT_RPC_SERVER_IP_KEY));
//        contextParamMap.put(CHAT_RPC_SERVER_PORT_KEY, servletContext.getInitParameter(CHAT_RPC_SERVER_PORT_KEY));
//
//        contextParamMap.put(GAME_RPC_SERVER_IP_KEY, servletContext.getInitParameter(GAME_RPC_SERVER_IP_KEY));
//        contextParamMap.put(GAME_RPC_SERVER_PORT_KEY, servletContext.getInitParameter(GAME_RPC_SERVER_PORT_KEY));

        for (Map.Entry<String, String> each : contextParamMap.entrySet())
        {
            log.info("添加ContentParam key={} limit={}", each.getKey(), each.getValue());
        }
    }
    public static Integer getInt(String key)
    {
        String value = getString(key);
        if (null == value)
        {
            return null;
        }
        else
        {
            return Integer.valueOf(getString(key));
        }
    }
    public static String getString(String key)
    {
        return contextParamMap.get(key);
    }
}
