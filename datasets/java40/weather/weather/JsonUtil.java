package com.weico.core.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.weico.core.utils.serializer.BooleanSerializer;
import com.weico.core.utils.serializer.DateSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 */
public class JsonUtil {

    private static JsonUtil instance;
    private Gson gson;

    static {
        instance = new JsonUtil();
    }

    private JsonUtil() {
        GsonBuilder builder = new GsonBuilder();
        //添加布尔类型转化的特殊处理
        builder.registerTypeAdapter(Boolean.class, new BooleanSerializer());
        builder.registerTypeAdapter(boolean.class, new BooleanSerializer());
        //添加日期类型转化的特殊处理
        builder.registerTypeAdapter(Date.class, new DateSerializer());
        builder.serializeNulls();
        gson = builder.create();
    }


    /**
     * 得到jsonUtil中使用的内置gson，推荐在外边使用的gson的时候，优先选用系统内部的gson
     * 1. 添加了布尔类型转换的特殊处理
     * 2. 添加了日期转换的特殊处理。（这部分是将日期转换为了long型）
     *
     * @return
     */
    public static Gson buildInGson() {
        return getInstance().gson;
    }

    /**
     * 返回默认实现的jackson单例
     * ps:如果只需要输出非空属性，使用nonEmptyMapper
     * 如果只需要输出和初始值不同的属性，使用
     *
     * @return
     */
    public static JsonUtil getInstance() {
        return instance;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回"null".
     * 如果集合为空集合, 返回"[]".
     *
     * @param object
     */
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 反序列化POJO
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        if (clazz.equals(String.class)) {
            return (T) jsonString;
        }
        return gson.fromJson(jsonString, clazz);
    }

    /**
     * 反序列化POJO
     */
    public <T> T fromJson(JsonElement jsonElement, Class<T> clazz) {
        if (jsonElement == null) {
            return null;
        }
        return gson.fromJson(jsonElement, clazz);
    }


    /**
     * 反序列化复杂Collection如List<Bean>
     */
    public <T> T fromJson(String jsonString, Type type) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        return gson.fromJson(jsonString, type);
    }

    public String jsonStringFromRawFile(Context context, int fileId) {
        return FileUtil.readString(context.getResources().openRawResource(fileId), "UTF-8");
    }

    public <T> T jsonObjectFromRawFile(Context context, int fileId, Class<T> clazz) {
        try {
            return fromJson(jsonStringFromRawFile(context, fileId), clazz);
        } catch (Exception e) {
            LogUtil.e(e);
            return null;
        }
    }

    public <T> T jsonObjectFromRawFile(Context context, int fileId, Type javaType) {
        try {
            return fromJson(jsonStringFromRawFile(context, fileId), javaType);
        } catch (Exception e) {
            LogUtil.e(e);
            return null;
        }
    }
}