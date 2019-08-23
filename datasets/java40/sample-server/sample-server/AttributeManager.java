package com.lz.center;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * User: Teaey
 * Date: 13-9-17
 */
public class AttributeManager
{
    public static final class AttributeKey<T>
    {
        final Class<T> clazz;
        final String   keyName;
        AttributeKey(Class<T> clazz, String keyName)
        {
            this.clazz = clazz;
            this.keyName = keyName;
        }
        public Class<T> getClazz()
        {
            return clazz;
        }
        public String getKeyName()
        {
            return keyName;
        }
    }
    public static final AttributeKey<CenterContext> SESSION_ATTR_CTX = new AttributeKey(CenterContext.class, "_CTX_");
    //    public static final AttributeKey<CenterContext> SESSION_TARGET_ZONE = new AttributeKey(CenterContext.class, "_TARGET_ZONE_");
    //    public static final String                      SESSION_ATTR_CTX         = "_CTX_";
    //    public static final String                      SESSION_TARGET_ZONE = "_TARGET_ZONE_";
    public static CenterContext getContext(HttpSession session)
    {
        return getAttribute(AttributeManager.SESSION_ATTR_CTX, session);
    }
    public static <T> T getAttribute(AttributeKey<T> key, HttpSession session)
    {
        return (T) session.getAttribute(key.getKeyName());
    }
    public static <T> T getAttribute(AttributeKey<T> key, HttpServletRequest request)
    {
        return (T) request.getAttribute(key.getKeyName());
    }
    public static <T> void setAttribute(AttributeKey<T> key, HttpSession session, T value)
    {
        session.setAttribute(key.getKeyName(), value);
    }
    public static <T> void setAttribute(AttributeKey<T> key, HttpServletRequest request, T value)
    {
        request.setAttribute(key.getKeyName(), value);
    }
}
