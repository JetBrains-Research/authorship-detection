package com.lz.center.utils;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;
/**
 * 暂时未用到
 * User: Teaey
 * Date: 13-8-23
 */
public class ObjectToJsonConvert extends AbstractHttpMessageConverter
{
    private static final Charset DEF_CHARSET = Charset.forName("UTF-8");
    @Override
    protected boolean supports(Class clazz)
    {
        throw new UnsupportedOperationException();
    }
    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException
    {
        byte[] data = new byte[inputMessage.getBody().available()];
        Object obj = JSON.parseObject(data, clazz);
        return obj;
    }
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException
    {
        String jsonStr = JSON.toJSONString(o);
        outputMessage.getBody().write(jsonStr.getBytes(DEF_CHARSET));
        outputMessage.getBody().flush();
    }
}
