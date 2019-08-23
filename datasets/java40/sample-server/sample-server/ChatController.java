package com.lz.center.control;
import com.lz.center.AttributeManager;
import com.lz.center.CenterContext;
import com.lz.center.ContextParamHolder;
import com.lz.center.JsonResponse;
import com.lz.center.acl.ACL;
import com.lz.center.acl.Limit;
import com.lz.center.acl.RequestType;
import com.lz.center.websocket.RealtimeSession;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
/**
 * User: Teaey
 * Date: 13-8-27
 */
@Controller
@RequestMapping(value = "/chat")
public class ChatController
{
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    @ACL(limit = Limit.CHAT)
    @RequestMapping(value = {"/"})
    public void chat()
    {
    }
    @ACL(limit = Limit.CHAT,type = RequestType.JSON)
    @RequestMapping(value = "/init")
    @ResponseBody
    public String init(HttpServletRequest req)
    {
        JsonResponse resultCode = new JsonResponse();
        CenterContext cc = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, req.getSession());
        String session = RealtimeSession.create(cc.getUser().getUsername());
        String webHost = req.getHeader(HttpHeaders.Names.HOST);
        String host = webHost.contains(":") ? webHost.split(":")[0] : webHost;
        String url = "ws://" + host + ":" + ContextParamHolder.getString(ContextParamHolder.WEB_SOCKET_PORT_KEY) + "?session=" + session;
        resultCode.setUrl(url);
        log.info("RealtimeInitAction url:{}", url);
        return resultCode.toJson();
    }
}
