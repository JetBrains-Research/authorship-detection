package com.qihigh.chinaweather.network;

import com.android.wpvolley.NetworkResponse;
import com.android.wpvolley.Request;
import com.android.wpvolley.Response;
import com.android.wpvolley.VolleyError;
import com.android.wpvolley.toolbox.HttpHeaderParser;
import com.google.gson.JsonParseException;
import com.weico.core.utils.JsonUtil;
import com.weico.core.utils.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class GsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;

    private JsonUtil mGson;

    private String mUrl;

    private Class<T> mClass;

    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        mGson = JsonUtil.getInstance();
        mClass = clazz;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = getRealString(response.data);
        LogUtil.d("result:" + jsonString);
        try {
            return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonParseException e) {
            //当json解析失败的时候，直接将返回内容放在错误消息中
            return Response.error(new VolleyError("JSON_PARSE_FAIL", e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    private int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
    }

    private String getRealString(byte[] data) {
        byte[] h = new byte[2];
        h[0] = (data)[0];
        h[1] = (data)[1];
        int head = getShort(h);
        boolean t = head == 0x1f8b;
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            if (t) {
                in = new GZIPInputStream(bis);
            } else {
                in = bis;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}