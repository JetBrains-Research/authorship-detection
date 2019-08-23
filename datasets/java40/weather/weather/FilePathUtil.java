package com.weico.core.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhoukai on 3/19/14.
 */
public class FilePathUtil {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; // 10 digits + 26 letters

    /**
     * SD卡存储路径
     * @param context sd卡的context
     * @return
     */
    public static String getStorageDirectory(Context context){
        String fileString = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            fileString =   Environment.getExternalStorageDirectory() + "/WeicoLove/";
        }
        else{
            fileString =  getCacheDirectory(context)+"/WeicoLove/";
        }
        File file = new File(fileString);
        if(!file.exists()){
            file.mkdir();
        }
        return  fileString;
    }

    /**
     * 根据图片url地址获取本地已经下载的图片文件
     * @param url
     * @param context
     * @return
     */
    public static File getImageFileInDiscCache(String url,Context context) {
        return new File(getCacheDirectory(context), generate(url));
    }

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted. Else - Android defines cache directory on
     * device's file system.
     *
     * @param context Application context
     * @return Cache {@link java.io.File directory}
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
//            Log.w("Can't define system cache directory!");
            appCacheDir = context.getCacheDir(); // retry
        }
        return appCacheDir;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }

    public static String generate(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        if (imageUri.endsWith(".gif")||imageUri.endsWith(".GIF")) {
            return bi.toString(RADIX) + ".gif";
        }
        return bi.toString(RADIX) + ".jpg";
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return hash;
    }
}
