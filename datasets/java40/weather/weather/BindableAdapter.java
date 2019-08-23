package com.weico.core.baseUi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link BaseAdapter} which uses the new/bind pattern for its views.
 * 基础的adapter，定义了绝大部分方法，需要自定义的重写相关方法即可。
 * 泛型部分是item对应的类型，继承时注意添加相应泛型数据
 */
public abstract class BindableAdapter<T> extends BaseAdapter {
    protected final Context context;
    protected final LayoutInflater inflater;

    public BindableAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public BindableAdapter(Context context, List<T> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 存储的数据
     */
    protected List<T> items = new ArrayList<T>();

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.size();
    }

    @Override
    public T getItem(int position) {
        return null == items || items.size() == 0 ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        if (view == null) {
            view = newView(inflater, position, container);
            if (view == null) {
                throw new IllegalStateException("newView result must not be null.");
            }
        }
        bindView(getItem(position), position, view);
        return view;
    }

    /**
     * Create a new instance of a view for the specified position.
     */
    public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

    /**
     * Bind the data for the specified {@code position} to the view.
     */
    public abstract void bindView(T item, int position, View view);

    @Override
    public final View getDropDownView(int position, View view, ViewGroup container) {
        if (view == null) {
            view = newDropDownView(inflater, position, container);
            if (view == null) {
                throw new IllegalStateException("newDropDownView result must not be null.");
            }
        }
        bindDropDownView(getItem(position), position, view);
        return view;
    }

    /**
     * Create a new instance of a drop-down view for the specified position.
     */
    public View newDropDownView(LayoutInflater inflater, int position, ViewGroup container) {
        return newView(inflater, position, container);
    }

    /**
     * Bind the data for the specified {@code position} to the drop-down view.
     */
    public void bindDropDownView(T item, int position, View view) {
        bindView(item, position, view);
    }


    /**
     * 根据index删除数据
     *
     * @param index
     */
    public void remove(int index) {
        if (getCount() == 0 || index < 0 || index >= getCount()) {
            return;
        }
        getItems().remove(index);
    }

    /**
     * 根据内容删除数据
     *
     * @param item
     */
    public void remove(T item) {
        if (getCount() == 0 || null == item) {
            return;
        }
        getItems().remove(item);
    }
}
