package com.weico.album.model;

import android.media.ExifInterface;
import android.util.Log;

import com.weico.album.ImageAnalysisTool;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zhoukai on 5/4/14.
 */
public class LocationTimeGroup {
    private ImageEntity mAvatarEntity;
    private Date mBeginDate;
    private ImageLocation mLocation;
    private boolean isLowQuality;
    private List<ImageEntity> listImageEntity = new ArrayList<ImageEntity>();
    private static SimpleDateFormat  sFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

    public ImageEntity getAvatarEntity() {
        return mAvatarEntity;
    }

    public void setAvatarEntity(ImageEntity mAvatarEntity) {
        this.mAvatarEntity = mAvatarEntity;
    }

    public LocationTimeGroup(ExifInterface exifInterface) {
        sFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        mBeginDate = getDateByExifInterface(exifInterface);
        this.mLocation = getLocationByExifInterFace(exifInterface);
    }

    public void insertImageEntity(ImageEntity entity){
        listImageEntity.add(entity);
    }

    public static ImageLocation getLocationByExifInterFace(ExifInterface exifInterface){
        float[] latLong = new float[2];
        exifInterface.getLatLong(latLong);
        ImageLocation location = new ImageLocation(latLong[0],latLong[1]);
        return location;
    }

    public static Date getDateByExifInterface(ExifInterface exifInterface){
        Date date;
        sFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        sFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateTimeString = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
        if (dateTimeString == null)
            date = new Date();
        ParsePosition pos = new ParsePosition(0);
        try {
            date = sFormatter.parse(dateTimeString, pos);
        } catch (IllegalArgumentException ex) {
            date = new Date();
        }
        catch (NullPointerException ex){
            date = new Date();
        }
        return date;
    }

    public boolean isEntityInTimeGroup(Date date){
        long timeInterval = Math.abs(date.getTime() - mBeginDate.getTime());
        if (timeInterval > 6*60*60*1000*1000){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean isEntityInLocationGroup(ImageLocation location,Date date){
        double distance = ImageAnalysisTool.distanceOfTwoPoints(mLocation.mLat,mLocation.mLon,location.mLat,location.mLon);
        long timeInterval = Math.abs(date.getTime() - mBeginDate.getTime());
        if (distance > 10000){
            return false;
        }
        else{
            return true;
        }
    }
}
