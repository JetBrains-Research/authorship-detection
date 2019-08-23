package com.weico.album;

import android.media.ExifInterface;

import com.weico.album.model.ImageEntity;
import com.weico.album.model.ImageLocation;
import com.weico.album.model.LocationTimeGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoukai on 5/4/14.
 */
public class ImageAnalysisTool {
    private static final double EARTH_RADIUS = 6378137;
    public static List<LocationTimeGroup> getLocationImageGroup(ArrayList<ImageEntity> images){
        boolean hasEntity = false;
        List<LocationTimeGroup> groups = new ArrayList<LocationTimeGroup>();
        LocationTimeGroup  curTimeGroup = null;
        for (ImageEntity entity :images){
            try {
                ExifInterface exifInterface = new ExifInterface(entity.getPath());
                if (curTimeGroup == null){
                    LocationTimeGroup  timeGroup = new LocationTimeGroup(exifInterface);
                    hasEntity = false;
                    curTimeGroup = timeGroup;
                    groups.add(curTimeGroup);
                    curTimeGroup.setAvatarEntity(entity);

                }
                if (curTimeGroup.isEntityInTimeGroup(LocationTimeGroup.getDateByExifInterface(exifInterface))){
                    curTimeGroup.insertImageEntity(entity);
                }
                else{
                    LocationTimeGroup  timeGroup = new LocationTimeGroup(exifInterface);
                    curTimeGroup = timeGroup;
                    curTimeGroup.insertImageEntity(entity);
                    groups.add(curTimeGroup);
                    curTimeGroup.setAvatarEntity(entity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }

    public static List<ImageEntity> getImageEntitiesWithHeaderId(ArrayList<ImageEntity> images){
        boolean hasEntity = false;
        LocationTimeGroup  curTimeGroup = null;

        long curHeaderId = 0;//images.get(0);
        for (ImageEntity entity :images){
            try {
                ExifInterface exifInterface = new ExifInterface(entity.getPath());
                if (curHeaderId == 0){
                    LocationTimeGroup  timeGroup = new LocationTimeGroup(exifInterface);
                    hasEntity = false;
                    curTimeGroup = timeGroup;
                    entity.headerId = entity.getDateTake();
                    curHeaderId = entity.getDateTake();
                }
                if (curTimeGroup.isEntityInTimeGroup(LocationTimeGroup.getDateByExifInterface(exifInterface))){
                    entity.headerId = curHeaderId;
                }
                else{
                    LocationTimeGroup  timeGroup = new LocationTimeGroup(exifInterface);
                    curTimeGroup = timeGroup;
                    entity.headerId = entity.getDateTake();
                    curHeaderId = entity.getDateTake();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }



    public static void analysisImage(ArrayList<ImageEntity> images){
         for (ImageEntity entity :images){
             try {
              ExifInterface exifInterface = new ExifInterface(entity.getPath());
             } catch (IOException e) {
                e.printStackTrace();
             }
         }
    }



    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离：单位为米
     */
    public static double distanceOfTwoPoints(double lat1, double lng1,
                                             double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}
