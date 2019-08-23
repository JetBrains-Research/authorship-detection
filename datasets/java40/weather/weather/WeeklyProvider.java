package com.qihigh.chinaweather.provider;

import com.android.wpvolley.VolleyError;
import com.android.wpvolley.toolbox.RequestListener;
import com.qihigh.chinaweather.model.WeeklyWeather;
import com.qihigh.chinaweather.model.json.WeeklyResponse;
import com.qihigh.chinaweather.network.WeatherApi;
import com.weico.core.provider.DataConsumer;
import com.weico.core.provider.DataProvider;
import com.weico.core.utils.LogUtil;

import java.util.Arrays;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 */
public class WeeklyProvider extends DataProvider {
    private String cityId;

    public WeeklyProvider(DataConsumer consumer, String cityId) {
        super(consumer);
        this.cityId = cityId;
    }

    @Override
    public void doLoadNew() {
        super.doLoadNew();
        WeatherApi.getWeeklyWeather(cityId, new RequestListener<WeeklyResponse>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e(error);
                loadFinished(error.getMessage(), LOAD_NEW_FAIL);
            }

            @Override
            public void onResponse(WeeklyResponse response) {
                LogUtil.d(response.weather.city);
                loadFinished(Arrays.asList(new WeeklyWeather[]{response.weather}), LOAD_NEW_FINISHED);
            }
        });
    }

    @Override
    protected int getLoadCount() {
        return 0;
    }

    @Override
    public String getRequestFilterTag() {
        return toString();
    }

    @Override
    public String getCacheKey() {
        return null;
    }

    public void setCityCode(String cityCode) {
        this.cityId = cityCode;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
