package com.weico.core.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by zhoukai on 3/19/14.
 */
public class ResUtil {


    /**
     * 通过String 取得id 转换成国际化后的 String
     *
     * @param aString
     * @param context
     * @return
     */
    public static String localizedString(String aString, Context context) {
        if(TextUtils.isEmpty(aString)) {
            return "";
        }

        String packageName = context.getPackageName();
        int resId = context.getResources()
                .getIdentifier(aString, "string", packageName);
        if (resId == 0) {
            return aString;
        } else {
            return context.getString(resId);
        }
    }



    /**
     * 通过名称 查找对应的colorid
     *
     * @param name
     * @return
     */
    public static int getColorIdFromName(String name, Context mContext) {
        return mContext.getResources().getIdentifier(name, "color",
                mContext.getPackageName());
    }

    /**
     * 通过名称查找对应资源
     *
     * @param name
     * @return
     */
    public static String getResNameFromName(String name, Context mContext) {
        String result = null;
         int  id = mContext.getResources().getIdentifier(name, "string",
                mContext.getPackageName());
        if (id>0){
            result = mContext.getResources().getString(id);
        }
        return result;
    }


    /**
     * 通过名称 查找对应的drawableid
     *
     * @param name
     * @return
     */
    public static int getDrawableIdFromName(String name, Context mContext) {
        return mContext.getResources().getIdentifier(name, "drawable",
                mContext.getPackageName());
    }

    /**
     * @param appContext
     * @param packageContext
     * @param resid
     * @return
     */
    public static Drawable getThemeDrawableFromId(Context appContext,Context packageContext,int resid) {
          int id = getResidFromIdentifier(appContext,packageContext,resid,"drawable");
          return packageContext.getResources().getDrawable(id);
    }

    /**
     *
     * @param appContext
     * @param packageContext
     * @param resid
     * @return
     */
    public static int  getThemeColorFromId(Context appContext,Context packageContext,int resid) {
        int id = getResidFromIdentifier(appContext,packageContext,resid,"color");
        return packageContext.getResources().getColor(id);
    }



    /**
     * 通过color id 查找对应的color
     * @param id
     * @return
     */
    public static int getColorById(int id, Context mContext) {
        return mContext.getResources().getColor(id);
    }

    /**
     * 获取资源文件id
     */
    public static int getResidFromIdentifier(Context appContext,Context packageContext, int resid, String type) {
        String name = appContext.getResources().getResourceName(resid);
        name = name.substring(name.lastIndexOf("/") + 1);
        int id = packageContext.getResources().getIdentifier(name, type, packageContext.getPackageName());
        return id;
    }

}
