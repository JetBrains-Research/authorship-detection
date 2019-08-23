package com.weico.album.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * 图片实例
 *
 * @author huangjianfeng
 */
public class ImageEntity implements Serializable {
    private static final String[] PROJECTION = new String[]{"_id", MediaStore.MediaColumns.DATA};
    /**
     *
     */
    private static final long serialVersionUID = 1501566001919425929L;

    public boolean isButton;

    public int     buttonIcom;

    public String  buttonTitile;

    public int width;
    public int height;

    public long dateTake;

    public long getDateTake() {
        return dateTake;
    }

    public void setDateTake(long dateTake) {
        this.dateTake = dateTake;
    }

    /**
     * 图片id
     */
    public long headerId;

    /**
     * 图片id
     */
    public int id;
    /**
     * 图片路径
     */
    public String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ImageEntity [id=" + id + ", path=" + path + "]";
    }

    public Bitmap getMiniNail(Context context) {
        return MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), this.id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
    }

    public Bitmap getThumbnail(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        return MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), this.id, MediaStore.Images.Thumbnails.MINI_KIND, options);
    }
}
