package com.qihigh.chinaweather.network;

import com.android.wpvolley.VolleyError;
import com.android.wpvolley.toolbox.RequestListener;
import com.qihigh.chinaweather.SimpleSyncTest;
import com.qihigh.chinaweather.model.json.DailyResponse;
import com.weico.core.utils.LogUtil;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 */
public class WeatherApiTest extends SimpleSyncTest {

    /**
    * 测试获取北京的天气数据
    */
    public void testDialyWeather() {
        String beijing = "101010100";
        WeatherApi.getDailyWeather(beijing, new RequestListener<DailyResponse>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e(error);
                fail(error.toString());
                syncRequestCompleted();
            }

            @Override
            public void onResponse(DailyResponse response) {
                LogUtil.d(response.toJson());
                syncRequestCompleted();
            }
        });
    }
}
