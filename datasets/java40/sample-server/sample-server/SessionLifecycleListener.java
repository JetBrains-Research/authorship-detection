package com.lz.center.listener;
import com.lz.center.AttributeManager;
import com.lz.center.CenterContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * UserEntity: Teaey
 * Date: 13-7-11
 */
public class SessionLifecycleListener implements HttpSessionListener
{
    private static final Logger log = LoggerFactory.getLogger(SessionLifecycleListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {
        log.info("sessionCreated {}", httpSessionEvent.getSession().getId());
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent)
    {

        CenterContext cc = AttributeManager.getAttribute(AttributeManager.SESSION_ATTR_CTX, httpSessionEvent.getSession());
        if (null != cc)
        {
            AttributeManager.setAttribute(AttributeManager.SESSION_ATTR_CTX, httpSessionEvent.getSession(), null);
            log.info("登出 userId={} userName={}", cc.getUser().getId(), cc.getUser().getUsername());
        }
        //        log.info("{}", httpSessionEvent.getSession());
        //        log.info("{}", cc);
        //        log.info("{}", httpSessionEvent.getSession().getId());
        log.info("sessionDestroyed {}", httpSessionEvent.getSession().getId());
    }
}
