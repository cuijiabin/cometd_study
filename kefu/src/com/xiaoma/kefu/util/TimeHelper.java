package com.xiaoma.kefu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

	public static String NORMAL_PATTERN = "MM/dd/yyyy";
	
	public static String OTHER_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String Time_PATTERN = "HH:mm:ss";

	public static String convertMillisecondToStr(Long milliSecond,
			String pattern) {

		if (milliSecond == null) {
			return "";
		}

		Date date = new Date(milliSecond);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(date);
	}

	public static Date convertM2D(Long mill) {
		if (mill == null) {
			return null;
		}

		return new Date(mill);
	}

	public static Long getZeroMill() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime().getTime();
	}
	
	public static Date addDate(Date d, Integer day){ 
		
		Calendar fromCal=Calendar.getInstance();
		fromCal.setTime(d);
		fromCal.add(Calendar.DATE, day);
		  
        return fromCal.getTime();  
  
    }  
	
	public static boolean isOverdue(Date date){
		if(date.getTime()<=new Date().getTime())
			return true;
		else
			return false;
	}
	
	/**
	 * int类型格式化位 xx:xx:xx 格式
	 * 例如  传入60, 返回 00:01:00
	* @Description: TODO
	* @param time	秒数
	* @return
	* @Author: wangxingfei
	* @Date: 2015年4月10日
	 */
	public static String secToTime(Integer time) {
		if(time==null) time=0;
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            hour = minute / 60;
            if (hour > 99)
                return "99:59:59";
            minute = minute % 60;
            second = time - hour * 3600 - minute * 60;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }
	
	//格式化用
    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
