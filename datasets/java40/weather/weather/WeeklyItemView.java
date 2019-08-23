package com.qihigh.chinaweather.ui.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qihigh.chinaweather.R;
import com.qihigh.chinaweather.model.WeeklyItemWeather;
import com.weico.core.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by qihigh on 7/15/14.
 * Modified by ____.
 */
public class WeeklyItemView extends RelativeLayout {
    @InjectView(R.id.weekly_title)
    TextView mTitle;
    @InjectView(R.id.weekly_weather)
    TextView mWeather;
    @InjectView(R.id.weekly_temperature_high)
    TextView mTemperatureH;
    @InjectView(R.id.weekly_temperature_low)
    TextView mTemperatureL;

    public WeeklyItemView(Context context) {
        super(context);
    }

    public WeeklyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
    }

    public void bindTo(WeeklyItemWeather item) {
        LogUtil.d(item.toJson());
        mTitle.setText(item.date);
        mWeather.setText(item.type);
        mTemperatureH.setText(item.high);
        mTemperatureL.setText(item.low);
    }
}
