package com.weico.album.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhoukai on 5/4/14.
 */
public class ImageLocation {
    public float  mLat,mLon;
    public String mAddress;
    public ImageLocation(float mLat, float mLon) {
        this.mLat = mLat;
        this.mLon = mLon;
        class LocationRunnable implements Runnable{
            private final static String TAG = "LocationRunnable ===> ";
            @Override
            public void run() {
               getLocationData(ImageLocation.this);
            }
        }
        new Thread(new LocationRunnable()).start();
    }

    public void getLocationData(ImageLocation location){
        String urlDate="http://maps.googleapis.com/maps/api/geocode/json?sensor=true&language=zh_cn&latlng="+location.mLat+","+location.mLon;
        try {
            //封装访问服务器的地址
            URL url= new URL(urlDate);
            try {
                //打开对服务器的连接
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                //连接服务器
                conn.connect();
                /**读入服务器数据的过程**/
                //得到输入流
                InputStream is=conn.getInputStream();
                //创建包装流
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                //定义String类型用于储存单行数据
                String line=null;
                //创建StringBuffer对象用于存储所有数据
                StringBuffer sb=new StringBuffer();
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                location.mAddress = sb.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
