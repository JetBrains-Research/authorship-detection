package com.lz.center.listener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;
/**
 * User: Teaey
 * Date: 13-8-26
 */
public class SpringContextUtil implements ApplicationContextAware
{
    private static ApplicationContext context;
    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext contex) throws BeansException
    {
        this.context = contex;
    }
    public static Object getBean(String beanName)
    {
        return context.getBean(beanName);
    }
    public static String getMessage(String key, Locale locale)
    {
        return getMessage(key, "", locale);
    }
    public static String getMessage(String key, String def, Locale locale)
    {
        if (null == def)
        {
            def = "";
        }
        //Locale locale = RequestContextUtils.getLocale(request);
        String msg = context.getMessage(key, null, def, locale);
        return msg;
    }
}
