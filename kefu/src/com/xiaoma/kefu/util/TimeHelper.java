package com.xiaoma.kefu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

	public static String NORMAL_PATTERN = "MM/dd/yyyy";
	
	public static String OTHER_PATTERN = "yyyy-MM-dd HH:mm:ss";

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
}
