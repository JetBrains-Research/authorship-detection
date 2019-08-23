package com.qihigh.chinaweather.provider;

import com.android.wpvolley.VolleyError;
import com.android.wpvolley.toolbox.RequestListener;
import com.qihigh.chinaweather.model.DailyWeather;
import com.qihigh.chinaweather.model.json.DailyResponse;
import com.qihigh.chinaweather.network.WeatherApi;
import com.weico.core.provider.DataConsumer;
import com.weico.core.provider.DataProvider;
import com.weico.core.utils.LogUtil;

import java.util.Arrays;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 * 每日天气的provider
 */
public class DailyProvider extends DataProvider {

    private String cityId;

    public DailyProvider(DataConsumer consumer,String cityId) {
        super(consumer);
        this.cityId = cityId;
    }

    @Override
    public void doLoadNew() {
        super.doLoadNew();
        WeatherApi.getDailyWeather(cityId, new RequestListener<DailyResponse>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.e(error);
                loadFinished(error.toString(), LOAD_NEW_FAIL);
            }

            @Override
            public void onResponse(DailyResponse response) {
                LogUtil.d(response.weather.getCity());
                loadFinished(Arrays.asList(new DailyWeather[]{response.weather}), LOAD_NEW_FINISHED);
            }
        });
    }

    @Override
    protected int getLoadCount() {
        return 0;
    }

    @Override
    public String getRequestFilterTag() {
        return this.toString();
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
