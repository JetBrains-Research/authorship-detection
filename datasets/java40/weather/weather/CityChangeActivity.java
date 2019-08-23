package com.qihigh.chinaweather.ui.activity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.google.gson.reflect.TypeToken;
import com.qihigh.chinaweather.R;
import com.qihigh.chinaweather.UIManager;
import com.qihigh.chinaweather.provider.CityChangeWatcher;
import com.qihigh.chinaweather.ui.adapter.CityChangeAdapter;
import com.weico.core.BaseActivity;
import com.weico.core.utils.JsonUtil;
import com.weico.core.utils.LogUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by qihigh on 7/15/14.
 * Modified by ____.
 */
public class CityChangeActivity extends BaseActivity {

    @InjectView(R.id.dialog_input)
    AutoCompleteTextView mInput;
    private CityChangeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_change);
        ButterKnife.inject(this);

        initView();
        initListener();
    }

    @Override
    public void initView() {
        String fileContent = JsonUtil.getInstance().jsonStringFromRawFile(this, R.raw.city_code);
        List<Pair<String, String>> data = JsonUtil.getInstance().fromJson(fileContent, new TypeToken<List<Pair<String, String>>>() {
        }.getType());
        mAdapter = new CityChangeAdapter(this, data);
        mInput.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {


        mInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.d("click" + position);
                mInput.setText(mAdapter.getItem(position).first);
                UIManager.getInstance().Toast("切换成功");
                CityChangeWatcher.getInstance().notifyCityChange(mAdapter.getItem(position).second);
                finish();
            }
        });

    }

    @Override
    public void initResourceAndColor() {

    }
}
