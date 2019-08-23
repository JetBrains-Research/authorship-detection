package com.qihigh.chinaweather.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qihigh.chinaweather.R;
import com.qihigh.chinaweather.UIManager;
import com.qihigh.chinaweather.model.WeeklyItemWeather;
import com.qihigh.chinaweather.model.WeeklyWeather;
import com.qihigh.chinaweather.provider.CityChangeWatcher;
import com.qihigh.chinaweather.provider.WeeklyProvider;
import com.qihigh.chinaweather.ui.adapter.WeeklyAdapter;
import com.weico.core.provider.DataConsumer;
import com.weico.core.provider.DataProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 */
public class WeatherWeeklyFragment extends BaseFragment implements DataConsumer {
    private WeeklyProvider mProvider;

    @InjectView(R.id.weekly_city)
    TextView mCity;
    @InjectView(R.id.weekly_tips)
    TextView mTips;
    @InjectView(R.id.weekly_list)
    ListView mList;
    private WeeklyAdapter mAdapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeatherWeeklyFragment newInstance(int sectionNumber) {
        WeatherWeeklyFragment fragment = new WeatherWeeklyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_weather_weekly, container, false);
        ButterKnife.inject(this, result);
        return result;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String beijingId = "101010100";
        mProvider = new WeeklyProvider(this, beijingId);
        mProvider.loadNew();

        CityChangeWatcher.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object data) {
                String cityCode = (String) data;
                mProvider.setCityCode(cityCode);
                mProvider.loadNew();
            }
        });

        mAdapter = new WeeklyAdapter(getActivity());
        mList.setAdapter(mAdapter);
    }

    @Override
    public void didFinishedLoadingData(DataProvider provider, Object objects, LoadingType type) {
        WeeklyWeather weeklyWeather = ((List<WeeklyWeather>) objects).get(0);
        //将信息分解
        mCity.setText(weeklyWeather.city);
        mTips.setText(weeklyWeather.tips);
        mAdapter.setItems(weeklyWeather.forecast);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void didLoadDataFail(DataProvider provider, String failMsg, LoadingType type) {
        UIManager.getInstance().Toast(failMsg);
    }
}
