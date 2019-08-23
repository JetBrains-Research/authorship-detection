package com.lz.center.dao;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * User: Teaey
 * Date: 13-9-17
 */
public class DaoHelper
{
    private static final String HIBERNATE_CONFIG_TEMPLET = initConfitTemplate();
    public static final String getConfitTemplate()
    {
        return HIBERNATE_CONFIG_TEMPLET;
    }
    private static final String initConfitTemplate()
    {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try
        {
            in = DaoManager.class.getClassLoader().getResourceAsStream("zone_hibernate.cfg_tpl.xml");
            out = new ByteArrayOutputStream();
            int data = -1;
            while ((data = in.read()) != -1)
            {
                out.write(data);
            }
            String str = new String(out.toByteArray(), "utf8");
            return str;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        } finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                }
            }
            if (null != out)
            {
                try
                {
                    out.close();
                } catch (IOException e)
                {
                }
            }
        }
    }
}
