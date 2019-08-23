package com.weico.core.utils;

import android.util.Log;

import java.util.Arrays;

/**
 * 日志工具类，直接已类名作为tag、日志中包含方法名
 */
public class LogUtil {

    static String className;
    static String methodName;
    static int lineNum;


    private static boolean isDebuggable() {
        //BuildConfig.DEBUG
        return true;
    }

    private static String createLog(String log) {
        return String.format("[%s:%d] %s", methodName, lineNum, log);
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNum = sElements[1].getLineNumber();
    }

    public static void callStack() {
        d(Arrays.toString(Thread.currentThread().getStackTrace()));
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

    public static void e(Exception e) {
        if (!isDebuggable())
            return;
        e("message:" + e.getMessage());
        e.printStackTrace();
    }
}