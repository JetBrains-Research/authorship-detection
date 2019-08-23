package com.weico.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by hufeng on 13-7-8.
 */
public class NetWorkUtils {
    private static ConnectivityManager cConnectivityManager;

    /**
     * 判断网络是否可以
     */
    public static boolean isNetworkAvailable(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    /**
     * NetworkInfo实例
     *
     * @param context
     * @return
     */
    private static NetworkInfo getNetworInfoInstance(Context context) {
        if (cConnectivityManager == null) {
            cConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return cConnectivityManager.getActiveNetworkInfo();
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean hasNetwork(Context context) {
        NetworkInfo networkInfo = getNetworInfoInstance(context);
        // if (networkInfo != null)
        // return networkInfo.isAvailable();
        return isNetworkAvailable(networkInfo);
    }

    /**
     * 判断当前使用的网络状态，网络是否可用、是否是WiFi网络
     *
     * @param context
     * @return
     */
    public static boolean isWiFiUsed(Context context) {
        NetworkInfo netInfo = getNetworInfoInstance(context);
        boolean cIsNetWorkAvailable = isNetworkAvailable(netInfo);
        return cIsNetWorkAvailable ? ConnectivityManager.TYPE_WIFI == netInfo.getType() : false;
    }


    /**
     * 判断当前是否是2G移动网络
     */
    public static boolean is2GMobileNet(Context context) {
        NetworkInfo netInfo = getNetworInfoInstance(context);
        return isNetworkAvailable(netInfo) ? "mobile".equals(netInfo.getTypeName())
                && "EDGE".equals(netInfo.getSubtypeName()) : false;
    }

    /**
     * 判断当前是否是WiFi或3G移动网络
     */
    public static boolean isWiFiOr3GMobileNet(Context context) {
        NetworkInfo netInfo = getNetworInfoInstance(context);
        return isNetworkAvailable(netInfo) ? ("mobile".equals(netInfo.getTypeName())
                && !"EDGE".equals(netInfo.getSubtypeName())) || ConnectivityManager.TYPE_WIFI == netInfo.getType(): false;
    }
    /** 获取网络类型 */
//    public static int getNetworkType(Context context) {
//        NetworkInfo netInfo = getNetworInfoInstance(context);
//        return isNetworkAvailable(netInfo) ? netInfo.getType() : -1;
//    }

}
