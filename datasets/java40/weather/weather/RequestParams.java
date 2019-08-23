package com.qihigh.chinaweather.network;


import com.weico.core.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by qihigh on 6/5/14.
 * Modified by ____.
 */
public class RequestParams extends LinkedHashMap<String, Object> {
    public void add(String key, Object obj) {
        put(key, obj);
    }


    /**
     * 当key和value不为空的时候，才加入到map中
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public Object put(String key, Object value) {
        if (null != value && !StringUtil.isAnyEmpty(key, value.toString())) {
            return super.put(key, value.toString());
        }
        return null;
    }

    public Map<String, String> coverToHashMap() {
        Map<String, String> result = new HashMap<String, String>();
        for (Entry<String, Object> entry : this.entrySet()) {
            result.put(entry.getKey(), (String) entry.getValue());
        }
        return result;
    }


    /**
     * 用于将键值对转换为url的参数形式，用于ActivityUrlMap中拼接参数
     *
     * @return
     */
    public String convert2url() {
        StringBuffer result = new StringBuffer();
        try {
            for (Entry<String, Object> entry : entrySet()) {

                result.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append("&");

            }
            if (result.length() > 0) {
                result.setLength(result.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
