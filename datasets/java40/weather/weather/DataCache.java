package com.weico.core.data;

import java.lang.reflect.Type;

import android.app.Fragment;


import com.weico.core.utils.FileUtil;
import com.weico.core.utils.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by zhoukai on 13-12-11.
 */
public abstract class DataCache {
    public abstract String getIdString();

    public abstract String getCacheFolder();

    /**
     * 通过key获取对应的缓存数据
     * @param keyName
     * @param type    返回数据种类
     */
    public <T> T getDataByKey(String keyName, Type type) {
        return getDataByKey(getIdString(), keyName, type);
    }

    /**
     * 通过key获取对应的缓存数据
     * @param keyName
     * @param clazz    返回数据种类
     */

    public <T> T getDataByKey(String keyName, Class<T> clazz ) {
        return getDataByKey(getIdString(), keyName, clazz);
    }

    /**
     * 通过key获取对应的缓存数据
     * @param keyName
     * @param clazz    返回数据种类
     */
    public <T> T getDataByKey(String accountId, String keyName, Class<T> clazz) {
        T t = null;
        String path = getCacheFolder() + "/" + getIdString() + "/" + keyName;
        String jsonString = FileUtil.readString(path);
        if (jsonString != null) {
            JsonUtil util = JsonUtil.getInstance();
            t = util.fromJson(jsonString, clazz);
            return t;
        } else {
            return null;
        }
    }

    /**
     * 通过key获取对应的缓存数据
     * @param keyName
     * @param type    返回数据种类
     */
    public <T> T getDataByKey(String accountId, String keyName, Type type) {
        T t = null;
        String path = getCacheFolder() + "/" + getIdString() + "/" + keyName;
        String jsonString = FileUtil.readString(path);
        if (jsonString != null) {
            JsonUtil util = JsonUtil.getInstance();
            t = util.fromJson(jsonString, type);
            return t;
        } else {
            return null;
        }
    }


    /*
   *保存数据到缓存
   */
    public void saveObjectToCacheByKey(Object content, String key) {
         saveObjectToCacheByKey(JsonUtil.getInstance().toJson(content),key);
    }

    /*
     *保存数据到缓存
     */
    public void saveObjectToCacheByKey(String content, String key) {
        String idPath = getCacheFolder() + "/" + getIdString();
        FileUtil.checkFile(idPath);
        String realPath = idPath + "/" + key;
        try {
            FileUtil.writeString(realPath,content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 清理缓存
     */
    public void clearDataCache() {
        FileUtil.deleteFile(getCacheFolder());
    }

    /*
     * 计算缓存文件大小
     */
    public String calculateDataCacheSize() {
        File cacheFile = new File(getCacheFolder());
        try {
            long size1 = FileUtil.getFileSize(cacheFile, null);
            return FileUtil.FormetFileSize(size1);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

}
