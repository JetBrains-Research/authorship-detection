package com.weico.core.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zhoukai on 13-12-23.
 */
public class KeyBoardUtil {

    /**
     * 隐藏软键盘，类型InputMethodManager.HIDE_NOT_ALWAYS
     *
     * @param activity
     */
    public static void hideSoftKeyboardNotAlways(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            IBinder windowToken = view.getWindowToken();
            hideSoftKeyboardNotAlways(activity, windowToken);
        }
    }

    /**
     * 隐藏软键盘，类型InputMethodManager.HIDE_NOT_ALWAYS
     *
     * @param activity
     */
    public static void hideSoftKeyboardNotAlways(Activity activity, IBinder windowToken) {
        hideSoftKeyboardNotAlways(activity, windowToken, null);
    }


    /**
     * 隐藏软键盘，类型InputMethodManager.HIDE_NOT_ALWAYS
     *
     * @param activity
     * @param resultReceiver 非常有用的参数，键盘隐藏完成回调，可知道隐藏完成时机。
     */
    public static void hideSoftKeyboardNotAlways(Activity activity, IBinder windowToken, ResultReceiver resultReceiver) {
        ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                windowToken, InputMethodManager.HIDE_NOT_ALWAYS, resultReceiver);
    }
    /**
     * 隐藏软键盘，类型InputMethodManager.HIDE_NOT_ALWAYS,弹出软键盘，类型InputMethodManager.
     * SHOW_IMPLICIT
     *
     * @param activity
     */
    public static void toggleSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        // 如果开启关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 弹出软键盘，类型InputMethodManager.SHOW_IMPLICIT
     *
     * @param activity
     */
    public static void showSoftKeyboard(Activity activity, View view) {
        showSoftKeyboard(activity, view, null);
    }

    /**
     * 弹出软键盘，类型InputMethodManager.SHOW_IMPLICIT
     *
     * @param activity
     * @param view
     * @param resultReceiver 非常有用的参数，键盘显示完成回调，可知道显示完成时机。
     */
    public static void showSoftKeyboard(Activity activity, View view, ResultReceiver resultReceiver) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, resultReceiver);
    }
}
