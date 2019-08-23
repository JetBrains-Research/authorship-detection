package com.weico.core;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import com.koushikdutta.ion.Ion;
import com.umeng.analytics.MobclickAgent;
import com.weico.core.utils.ActivityUtil;
import com.weico.core.utils.TransactionUtil;
import com.weico.core.vender.SmartBarUtils;

/**
 * Created by hufeng on 13-7-1.
 */
public abstract class BaseActivity extends FragmentActivity implements BasicInitMethod {
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Ion.getDefault(this).cancelAll(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityUtil.addActivity(this);
        SmartBarUtils.hide(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this);
    }

    public void finishWithAnim(TransactionUtil.Transaction transaction) {
        super.finish();
        TransactionUtil.doAnimationWith(this, transaction);
    }

    public void onBackPressed() {
        finish();
    }

//    @Override
//    public Resources.Theme getTheme() {
//        if (!ThemeStore.getInstance().isDefaultTheme()){
//            return ThemeStore.getInstance().getTheme();
//        }
//        else{
//            return super.getTheme();
//        }
//    }
}
