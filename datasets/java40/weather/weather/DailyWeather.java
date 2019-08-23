package com.qihigh.chinaweather.model;

/**
 * Created by qihigh on 7/4/14.
 * Modified by ____.
 */
public class DailyWeather extends BaseType {
    //{"weatherinfo":{"city":"北京","cityid":"101010100","temp1":"31℃","temp2":"23℃","weather":"多云","img1":"d1.gif","img2":"n1.gif","ptime":"11:00"}}

    private String city;//城市中文名
    private String cityid;//城市 ID
    private String temp1;//?
    private String temp2;//?
    private String weather;//天气
    private String img1;//? 天气图标编号
    private String img2;//? 天气图标编号
    private String ptime;//发布时间

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
}
