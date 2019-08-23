package com.qihigh.chinaweather.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihigh.chinaweather.R;
import com.qihigh.chinaweather.UIManager;
import com.qihigh.chinaweather.model.DailyWeather;
import com.qihigh.chinaweather.network.WeatherApi;
import com.qihigh.chinaweather.provider.CityChangeWatcher;
import com.qihigh.chinaweather.provider.DailyProvider;
import com.qihigh.chinaweather.ui.activity.CityChangeActivity;
import com.squareup.picasso.Picasso;
import com.weico.core.provider.DataConsumer;
import com.weico.core.provider.DataProvider;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 * 当前天气
 */
public class WeatherDailyFragment extends BaseFragment implements DataConsumer {

    @InjectView(R.id.weather_pager_city)
    TextView mCity;
    @InjectView(R.id.weather_pager_weather)
    TextView mWeather;
    @InjectView(R.id.weather_pager_img1)
    ImageView mImg1;
    @InjectView(R.id.weather_pager_img2)
    ImageView mImg2;
    @InjectView(R.id.weather_pager_temperature)
    TextView mTemperature;
    @InjectView(R.id.weather_pager_time)
    TextView mTime;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private DailyProvider mProvider;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeatherDailyFragment newInstance(int sectionNumber) {
        WeatherDailyFragment fragment = new WeatherDailyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_weather_daily, container, false);
        ButterKnife.inject(this, result);
        return result;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String beijingId = "101010100";
        mProvider = new DailyProvider(this, beijingId);
        mProvider.loadNew();

        CityChangeWatcher.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                String cityCode = (String) data;
                mProvider.setCityCode(cityCode);
                mProvider.loadNew();
            }
        });
    }

    @Override
    public void didFinishedLoadingData(DataProvider provider, Object objects, LoadingType type) {
        DailyWeather dailyWeather = ((List<DailyWeather>) objects).get(0);
        mCity.setText(dailyWeather.getCity());
        mTime.setText(dailyWeather.getPtime());
        mTemperature.setText(dailyWeather.getTemp1() + "~" + dailyWeather.getTemp2());
        mWeather.setText(dailyWeather.getWeather());

//        Picasso.with(getActivity()).load(WeatherApi.API_URL.ICON + dailyWeather.getImg1()).into(mImg1);
//        Picasso.with(getActivity()).load(WeatherApi.API_URL.ICON + dailyWeather.getImg2()).into(mImg2);
        mImg1.setImageDrawable(getResources().getDrawable(R.drawable.cw1));
        mImg2.setImageDrawable(getResources().getDrawable(R.drawable.cw2));
    }

    @OnClick(R.id.weather_pager_setting)
    public void changeCity(){
        startActivity(new Intent(getActivity(),CityChangeActivity.class));
    }

    @Override
    public void didLoadDataFail(DataProvider provider, String failMsg, LoadingType type) {
        UIManager.getInstance().Toast(failMsg);
    }
}
