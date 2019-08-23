package com.qihigh.chinaweather;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.DisplayMetrics;

import com.qihigh.chinaweather.network.RequestManager;


public class WApplication extends Application {
    private static DisplayMetrics mDisplayMetrics;
    private static ContextWrapper mContext;
    public static float mScreenDensity;

    public Context getContext() {
        return mContext;
    }


    /**
     * Constructs the version code of the application.
     *
     * @return the versions string of the application
     */
    public static String getVersionCode() {
        // Get a version string for the app.
        return String.valueOf(BuildConfig.VERSION_CODE);
    }


    /**
     * Constructs the version string of the application.
     *
     * @return the versions string of the application
     */
    public static String getVersionName() {
        // Get a version string for the app.
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        RequestManager.init(this);
    }

    /**
     * Return the current display metrics that are in effect for this resource object.
     */
    private static DisplayMetrics getDefaultDisplayMetrics(boolean retry) {
        if (mDisplayMetrics == null || retry) {
            mDisplayMetrics = mContext.getResources().getDisplayMetrics();
            mScreenDensity = mDisplayMetrics.density;
        }
        return mDisplayMetrics;
    }

    /**
     * 获取屏幕宽度
     */
    public static int requestScreenWidth() {
        return getDefaultDisplayMetrics(false).widthPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int requestScreenHeight() {
        return getDefaultDisplayMetrics(false).heightPixels;
    }

}