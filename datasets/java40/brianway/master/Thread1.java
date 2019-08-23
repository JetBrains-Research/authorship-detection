package com.brianway.learning.java.multithread.supplement.example3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Brian on 2016/4/17.
 */
public class Thread1 extends Thread {
    private SimpleDateFormat sdf;
    private String dateString;

    public Thread1(SimpleDateFormat sdf, String dateString) {
        this.sdf = sdf;
        this.dateString = dateString;
    }

    @Override
    public void run() {
        try {
            Date date = DateTools1.parse("yyyy-MM-dd", dateString);
            String newDateString = DateTools1.format("yyyy-MM-dd", date).toString();
            if (!newDateString.equals(dateString)) {
                System.out.println("ThreadName = " + this.getName()
                        + "报错了 日期字符串：" + dateString
                        + " 转换成的日期为" + newDateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
