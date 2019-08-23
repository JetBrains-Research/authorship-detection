package com.weico.core.utils;

import android.app.Activity;
import android.content.Intent;

import com.weico.core.R;


/**
 * Created by zhoukai on 13-12-23.
 */
public class TransactionUtil {
    /**
     * transaction between Activities
     *
     * @param activity
     * @param transaction
     */
    public static void doAnimationWith(Activity activity, Transaction transaction) {
        switch (transaction) {
            //右进左出
            case PUSH_IN:
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            //左进右出
            case POP_OUT:
                activity.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
            //由下向上
            case PRESENT_UP:
                activity.overridePendingTransition(R.anim.present_up, R.anim.present_back_disappear);
                break;
            //由上向下
            case PRESENT_DOWN:
                activity.overridePendingTransition(R.anim.present_back_appear, R.anim.present_down);
                break;
            case NONE:
                activity.overridePendingTransition(0, 0);
                break;
            case GROW_FADE:
                activity.overridePendingTransition(R.anim.image_grow, R.anim.image_shrink);
                break;
            case DEFAULT:
                break;
        }
        ActivityUtil.convertActivityToTranslucent(activity);
    }

    /**
     * @param currentActivity
     * @param className
     * @param transaction
     */
    public static void startActivityWithAction(Activity currentActivity, Class className, Transaction transaction) {
        Activity ac = currentActivity;
        Intent in = new Intent(ac, className);
        ac.startActivity(in);
        doAnimationWith(ac, transaction);
    }

    /*
     Activity 动画种类
    */
    public static enum Transaction {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 右进左出
         */
        PUSH_IN,

        /**
         * 下沉push
         */
        PUSH_DEEP,

        /**
         * 上浮back
         */
        POP_DEEP,
        /**
         * 右出左进
         */
        POP_OUT,
        /**
         * 由下向上
         */
        PRESENT_UP,
        /**
         * 由上向下
         */
        PRESENT_DOWN,
        /**
         * 无动画
         */
        NONE,
        /**
         * 淡出
         */
        GROW_FADE
    }
}
