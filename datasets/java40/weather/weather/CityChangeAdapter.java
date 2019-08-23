package com.qihigh.chinaweather.ui.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.qihigh.chinaweather.R;
import com.weico.core.baseUi.BindableAdapter;
import com.weico.core.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qihigh on 7/15/14.
 * Modified by ____.
 */
public class CityChangeAdapter extends BindableAdapter<Pair<String, String>> implements Filterable {

    private final Map<String, String> cityPinyinMap;
    private ArrayFilter mFilter;
    private ArrayList<Pair<String, String>> mOriginalValues;
    private Object mLock = new Object();

    public CityChangeAdapter(Context context, List<Pair<String, String>> items) {
        super(context, items);
        //加载拼音 汉字 对照表
        String cityPinyin = JsonUtil.getInstance().jsonStringFromRawFile(context, R.raw.city_pinyin);
        cityPinyinMap = JsonUtil.getInstance().fromJson(cityPinyin, Map.class);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.item_city, container, false);
    }

    @Override
    public void bindView(Pair<String, String> item, int position, View view) {
        ((TextView) view).setText(cityPinyinMap.get(item.first));
    }

    /**
     * {@inheritDoc}
     */
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            //拷贝一份，用作过滤
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<Pair<String, String>>(items);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Pair<String, String>> list;
                synchronized (mLock) {
                    list = new ArrayList<Pair<String, String>>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                List<Pair<String, String>> values;
                synchronized (mLock) {
                    values = new ArrayList<Pair<String, String>>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<Pair<String, String>> newValues = new ArrayList<Pair<String, String>>();

                for (int i = 0; i < count; i++) {
                    //将列表的值 每一项和 输入的值进行对比，看看是否要保留
                    final Pair<String, String> value = values.get(i);
                    final String valueText = value.first;

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            items = (List<Pair<String, String>>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
