package com.weico.album.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片路径集合实例
 *
 * @author huangjianfeng
 */
public class ImageEntities {
    /**
     *
     */
    private static final long serialVersionUID = -6650441391543497980L;
    private List<ImageEntity> listImageEntity = new ArrayList<ImageEntity>();
    private String imagePathDirectory;
    private String title;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBucketId() {
        return bucketId;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    private int bucketId;// BUCKET_ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageEntity> getListImageEntity() {
        return listImageEntity;
    }

    public void setListImageEntity(List<ImageEntity> listImageEntity) {
        this.listImageEntity = listImageEntity;
    }

    public String getImagePathDirectory() {
        return imagePathDirectory;
    }

    public void setImagePathDirectory(String imagePathDirectory) {
        this.imagePathDirectory = imagePathDirectory;
    }

}
