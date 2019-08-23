package com.weico.core.utils;

import android.content.Context;

import com.weico.core.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by zhoukai on 13-12-24.
 */
public class DateUtil {
    /**
     * Simple date format
     */
    public static final String SIMPLE_DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";//"EEE MMM dd HH:mm:ss z yyyy"
    /**
     * Simple date format
     */
    public static final String DATE_FORMAT_DEFAULT_ZONE = "yy-MM-dd HH:mm";
    /**
     * default date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 传入的日期与当前的时间相比较
     * <p/>
     * date:需要比较的日期时间
     *
     * @return
     */
    public static boolean compareDates(String date) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            return df.parse(date).after(new Date());
        } catch (ParseException e) {
            System.err.println("格式不正确");
            return false;
        }
    }

    /**
     * 字符串转换成日期
     */
    public static Date str2date(String dataStr) {
        return str2date(dataStr, DATE_FORMAT);
    }

    /**
     * 字符串转换成日期
     */
    public static Date str2date(String dataStr, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(dataStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期转换成字符串
     */
    public static String date2str(Date date) {
        return date2str(date, DATE_FORMAT);
    }

    /**
     * 日期转换成字符串
     */
    public static String date2str(Date date, String format) {
        if (null == date) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 两个日期是否间隔了weekCount个星期
     *
     * @param fromDate
     * @param toDate
     * @param weekCount
     * @return true 超过了n个星期 false 不超过n个星期
     */
    public static boolean beyondWeeks(Date fromDate, Date toDate, int weekCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);
        calendar.add(Calendar.DAY_OF_WEEK, weekCount);
        fromDate = calendar.getTime();
        return fromDate.before(toDate);
    }


    /**
     * 一分钟换算的毫秒数
     */
    private final static long MINUTE = 60000l;
    /**
     * 一天换算的毫秒数
     */
    private final static long DAY = 86400000l;

    private static Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();
    private static Calendar createAtCalendar = Calendar.getInstance();
    private static Calendar currentCalendar = Calendar.getInstance();
    /**
     * 时间显示转换
     *
     * @param date
     * @return
     */
    public static String dateStringSmart(Date date,Context context) {
        String string = "";
        Date currentDate = new Date();
        if (date != null) {
            long createTime = date.getTime();
            long currentTime = currentDate.getTime();
//            long currentTime = System.currentTimeMillis();
            long time = currentTime - createTime;
            int minute = (int) Math.floor(time / MINUTE);
            if(minute < 1){
                string = context.getString(R.string.just_now);
            }else if (minute < 60) {
                String minsAgo = context.getString(R.string.the_before_minute);
                string = minute + minsAgo;
            } else {
                String createStr = getSimpleDataFormat(DATE_FORMAT_DEFAULT_ZONE).format(date);
                createAtCalendar.set(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
                currentCalendar.set(currentDate.getYear(), currentDate.getMonth(), currentDate.getDate(), 0, 0, 0);
                long createDay = createAtCalendar.getTime().getTime();
                long currentDay = currentCalendar.getTime().getTime();
                long dayTime = currentDay - createDay;
                int day = (int) Math.floor(dayTime / DAY);
                if (day == 0) {
                    String today = context.getString(R.string.today);
                    string = today + " " + createStr.substring(9);
                } else if (day == 1) {
                    String yesterday = context.getString(R.string.yesterday);
                    string = yesterday + " " + createStr.substring(9);
                } else {
                    string = createStr.substring(0, 8);
                }
            }
        }
        return string;
    }

    private static SimpleDateFormat getSimpleDataFormat(String format) {
        SimpleDateFormat sdf = formatMap.get(format);

        if (sdf == null) {
            if(DATE_FORMAT_DEFAULT_ZONE.contentEquals(format)){
                sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            }else{
                sdf = new SimpleDateFormat(format);
            }
            formatMap.put(format, sdf);
        }
        return sdf;
    }

    public static String Now() {
        return date2str(new Date());
    }
}
