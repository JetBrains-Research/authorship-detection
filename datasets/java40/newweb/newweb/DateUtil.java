package com.newweb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取距当前日期上一个月的日期
	 * 返回格式：yyyy-MM-dd
	 * @return
	 */
	public static String getLastMonthDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);    //得到前一天
		calendar.add(Calendar.MONTH, -1);    //得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		String monthS = month+"";
		String dateS = date + "";
		if(month <=9){
			monthS = "0" + month;
		}
		if(date <= 9){
			dateS = "0" + date;
		}
		return year+"-" + monthS + "-" + dateS;
	}
	
	/**
	 * 进行日期的减法计算
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int DateSub(String startDate,String endDate){
		Date sd = getDate(startDate);
		Date ed = getDate(endDate);
		long time = ed.getTime() - sd.getTime();
		int date = (int) (time/1000/60/60/24);
		return date;
	}
	
	/**
	 * 将‘yyyy-MM-dd’的字符串日期转化为Date对象
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getDate(String date){
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		int day = Integer.parseInt(date.split("-")[2]);
		return new Date(year, month, day);
	}
	
	/**
	 * 返回当前日期的yyyy-MM-dd的日期字符串格式
	 * @return
	 */
	public static String getLocationCurrentDate(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 返回当前日期的HH:mm:ss的时间字符串格式
	 * @return
	 */
	public static String getLocationCurrentTime(){
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
}
