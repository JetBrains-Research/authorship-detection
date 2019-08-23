package com.weico.core.provider;

import android.os.Handler;
import android.os.Message;

import com.android.wpvolley.Request;
import com.android.wpvolley.RequestQueue;

import java.lang.ref.WeakReference;

/**
 * Created by zhoukai on 13-7-10.
 */
public abstract class DataProvider {
    public static final int LOAD_CACHE_FINISHED = 10000;
    public static final int LOAD_NEW_FINISHED = 10001;
    public static final int LOAD_MORE_FINISHED = 10002;
    public static final int LOAD_NEW_FAIL = 10003;
    public static final int LOAD_MORE_FAIL = 10004;
    public static final int LOAD_CACHE_FAIL = 10005;
    public static final int DELETE_FINISHED = 10006;
    public static final int DELETE_FAIL = 10007;
    public static final int RESET_DATA = 10008;

    public RequestQueue mQueue;
    public RequestQueue.RequestFilter mRequestFilter;
    /**
     * 加载更多的数量，moreCount为false时没有更多数据,
     */
    public boolean hasMore = true;
    public boolean isLoading;

    protected abstract int getLoadCount();

    protected WeakReference<DataConsumer> consumer;

    public DataConsumer getConsumer() {
        if (consumer == null) {
            return null;
        }
        return consumer.get();
    }

    protected DataProvider(DataConsumer consumer) {
        this.consumer = new WeakReference<DataConsumer>(consumer);
        mRequestFilter = new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                if (request.getTag() == null) {
                    return false;
                }
                return request.getTag().equals(getRequestFilterTag());
            }
        };
    }

    public void changeConsumer(DataConsumer consumer1) {
        this.consumer = new WeakReference<DataConsumer>(consumer1);
    }

    protected void cancelRequest() {
        if (mQueue != null && mRequestFilter != null)
            mQueue.cancelAll(mRequestFilter);
    }

    /**
     * request请求的tag标签，用于取消request请求的标识。
     *
     * @return
     */
    public abstract String getRequestFilterTag();

    public abstract String getCacheKey();

    protected boolean checkArgs() {
        return true;
    }

    public void clearConsumer() {
        cancelRequest();
        consumer.clear();
        consumer = null;
    }

    public final void loadCacheAsynchronous() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                loadCache();
            }
        });
        thread.setDaemon(true);
        thread.setName("loadCacheAsynchronous");
        thread.start();
    }

    public void loadCache() {
    }

    public void loadNew() {
        if (isLoading) {
            return;
        } else if (this.checkArgs()) {
            isLoading = true;
            doLoadNew();
        }
    }

    public void loadMore() {
        if (isLoading) {
            return;
        } else if (this.checkArgs()) {
            isLoading = true;
            doLoadMore();
        }
    }

    public void doLoadMore() {

    }

    public void doLoadNew() {

    }

    protected void loadFinished(Object arrayList, int msgType) {
        Message msg = handler.obtainMessage();
        msg.what = msgType;
        msg.obj = arrayList;
        handler.sendMessage(msg);
    }

    protected Handler handler = new providerHandler(this);

    private static class providerHandler extends Handler {

        private final WeakReference<DataProvider> mProvider;

        private providerHandler(DataProvider mProvider) {
            this.mProvider = new WeakReference<DataProvider>(mProvider);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mProvider.get() == null) {
                return;
            }
            WeakReference<DataConsumer> consumer = this.mProvider.get().consumer;
            if (msg.what == RESET_DATA) {
                if (consumer != null && consumer.get() != null)
                    consumer.get().didFinishedLoadingData(this.mProvider.get(), msg.obj, DataConsumer.LoadingType.RESET);
                this.mProvider.get().isLoading = false;
            }
            if (msg.what == LOAD_NEW_FINISHED || msg.what == LOAD_CACHE_FINISHED) {
                if (consumer != null && consumer.get() != null)
                    consumer.get().didFinishedLoadingData(this.mProvider.get(), msg.obj, DataConsumer.LoadingType.LOAD_NEW);
                this.mProvider.get().isLoading = false;
            } else if (msg.what == LOAD_MORE_FINISHED) {
                if (consumer != null && consumer.get() != null)
                    consumer.get().didFinishedLoadingData(this.mProvider.get(), msg.obj, DataConsumer.LoadingType.LOAD_MORE);
                this.mProvider.get().isLoading = false;
            } else if (msg.what == LOAD_NEW_FAIL) {
                String errorMsg = String.valueOf(msg.obj);
                if (consumer != null && consumer.get() != null)
                    consumer.get().didLoadDataFail(this.mProvider.get(), errorMsg, DataConsumer.LoadingType.LOAD_NEW);
                this.mProvider.get().isLoading = false;
            } else if (msg.what == LOAD_CACHE_FAIL) {
                String errorMsg = String.valueOf(msg.obj);;
                if (consumer != null && consumer.get() != null)
                    consumer.get().didLoadDataFail(this.mProvider.get(), errorMsg, DataConsumer.LoadingType.LOAD_CACHE);
                this.mProvider.get().isLoading = false;
            } else if (msg.what == LOAD_MORE_FAIL) {
                String errorMsg = String.valueOf(msg.obj);;
                if (consumer != null && consumer.get() != null)
                    consumer.get().didLoadDataFail(this.mProvider.get(), errorMsg, DataConsumer.LoadingType.LOAD_MORE);
                this.mProvider.get().isLoading = false;
            }
        }
    }

}
