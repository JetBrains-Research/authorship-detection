package com.lz.game.rpc.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
/**
 * User: Teaey
 * Date: 13-7-10
 */
public class MD5Encrypt
{
    private static final Logger log = LoggerFactory.getLogger(MD5Encrypt.class);
    public static String encrypt(String sourceStr)
    {
        String signStr = "";
        try
        {
            byte[] bytes = sourceStr.getBytes("utf-8");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            byte[] md5Byte = md5.digest();
            if (md5Byte != null)
            {
                signStr = HexBin.encode(md5Byte);
            }
        } catch (Exception e)
        {
            log.error("", e);
        }
        return signStr.toLowerCase();
    }
    public static String internalEncrypt(Object... params)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i++)
        {
            if (i != 0)
            {
                sb.append(":");
            }
            sb.append(params[i]);
        }
        return encrypt(sb.toString());
    }
}
