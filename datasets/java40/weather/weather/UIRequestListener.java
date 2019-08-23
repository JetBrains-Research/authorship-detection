package com.weico.core.provider;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.wpvolley.VolleyError;
import com.android.wpvolley.toolbox.RequestListener;

/**
 * Created by qihigh on 6/27/14.
 * Modified by ____.
 * 返回接口位于主线程，用于不需要provider进行中间层处理的情况
 */
public abstract class UIRequestListener<T> implements RequestListener<T> {
    private static final int REQUEST_SUCCESS = 1;
    private static final int REQUEST_ERROR = 2;

    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    UIResponse(response);
                    break;
                case REQUEST_ERROR:
                    UIErrorResponse(error);
                    break;
            }
            return true;
        }
    });

    private VolleyError error;
    private T response;

    @Override
    public void onErrorResponse(VolleyError error) {
        this.error = error;
        Message msg = handler.obtainMessage(REQUEST_ERROR);
        handler.sendMessage(msg);
    }

    @Override
    public void onResponse(T response) {
        this.response = response;
        Message msg = handler.obtainMessage(REQUEST_SUCCESS);
        handler.sendMessage(msg);
    }

    /**
     * 运行在UI线程，标示请求成功
     * @param response
     */
    public abstract void UIResponse(T response);

    /**
     * 运行在UI线程，标示请求失败
     * @param error
     */
    public abstract void UIErrorResponse(VolleyError error);
}
