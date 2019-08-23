package com.qihigh.chinaweather.network;

import com.android.wpvolley.AuthFailureError;
import com.android.wpvolley.DefaultRetryPolicy;
import com.android.wpvolley.Request;
import com.android.wpvolley.RequestQueue;
import com.android.wpvolley.Response;
import com.android.wpvolley.RetryPolicy;
import com.android.wpvolley.VolleyError;
import com.android.wpvolley.toolbox.RequestListener;
import com.qihigh.chinaweather.model.json.DailyResponse;
import com.qihigh.chinaweather.model.json.WeeklyResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 */
public class WeatherApi {

    private static final int TIME_OUT_TIME = 20000;
    private static Request weeklyWeather;


    public static class API_URL {
        public static String DAILY = "http://www.weather.com.cn/data/cityinfo/";//每日
        public static String WEEKLY = "http://wthrcdn.etouch.cn/weather_mini?citykey=";//星期
        public static String CURRENTLY = "http://www.weather.com.cn/data/sk/";//实时
        public static String ICON = "http://www.weather.com.cn/m/i/weatherpic/29x20/";//图标前缀
    }


    /**
     * 获取当日天气信息
     *
     * @param cityId
     * @param listener
     * @return
     */
    public static Request getDailyWeather(String cityId, RequestListener<DailyResponse> listener) {
        String url = API_URL.DAILY + cityId + ".html";
        return startGsonRequest(url, null, DailyResponse.class, listener);
    }


    /**
     * 获取之后7天的天气
     *
     * @param cityId
     * @param listener
     * @return
     */
    public static Request getWeeklyWeather(String cityId, RequestListener<WeeklyResponse> listener) {
        String url = API_URL.WEEKLY + cityId;
        return startGsonRequest(url, null, WeeklyResponse.class, listener);
    }


    /**
     * 处理get请求
     *
     * @param url
     * @param params
     * @param clazz
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Request startGsonRequest(String url, RequestParams params, Class<T> clazz, final RequestListener<T> listener) {
        if (null != params && params.size() > 0) {
            url += "?" + params.convert2url();
        }
        FakeX509TrustManager.allowAllSSL();
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {

                listener.onResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Charset", "UTF-8");
                map.put("Content-Type", "application/x-javascript");
                map.put("Accept-Encoding", "gzip,deflate");
                return map;//super.getHeaders();
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(30, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return retryPolicy;
            }
        };
        RequestManager.getRequestQueue().add(request);
        return request;
    }

    public static <T> Request startPostGsonRequest(String url, final RequestParams map, Class<T> clazz, final RequestListener listener) {
        return startPostGsonRequest(url, map.coverToHashMap(), clazz, RequestManager.getRequestQueue(), listener);
    }

    /**
     * start Post Request
     *
     * @param url
     * @param map
     * @param clazz
     * @param queue
     * @param listener
     * @param <T>
     * @return
     */
    public static <T> Request startPostGsonRequest(final String url, final Map<String, String> map, Class<T> clazz, RequestQueue queue, final RequestListener listener) {
        final String finalUrl = url;
        FakeX509TrustManager.allowAllSSL();
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, finalUrl, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(final T response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //POST 参数
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Charset", "UTF-8");
                return map;
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                RetryPolicy retryPolicy = new DefaultRetryPolicy(TIME_OUT_TIME, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                return retryPolicy;
            }
        };
        request.getTimeoutMs();
        queue.add(request);
        return request;
    }

}
