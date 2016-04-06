package com.lyx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

public class CalendarUtil {

	public static int[] getCurDate(){
		Calendar calendar = Calendar.getInstance()  ;
		int year = calendar.get(Calendar.YEAR)   ;
		int month = calendar.get(Calendar.MONTH)   ;
		int day = calendar.get(Calendar.DAY_OF_MONTH)   ;
		return new int[]{year,month,day}   ;
	}
	public static String getCurDate(String format){
		Calendar calendar = Calendar.getInstance()  ;
		String FORMAT = "yyyy-MM-dd" ;
		if(format==null||TextUtils.isEmpty(format))
			format = FORMAT ;
		SimpleDateFormat SDF = new SimpleDateFormat(format)  ;
		return SDF.format(calendar.getTimeInMillis())   ;
	}
	
	public static String getStringDate(int...is){
		return is[0] + "-" + is[1]+ "-" + is[2]  ;
	}
	
	public static String getStringDate(int year,int month,int dayOfMonth){
		return year + "-" + month+ "-" + dayOfMonth ;
	}
	
	
	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(final String milliseconds) {
			if(TextUtils.isEmpty(milliseconds)) return false ;
	        return isToday(new Date(Long.parseLong(milliseconds)));
	}
	
	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(final long milliseconds) {
	        return isToday(new Date(milliseconds));
	}
	
	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(final Date date) {
	        return isTheDay(date, new Date());
	}
	/**
	 * 是否是指定日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static boolean isTheDay(final Date date, final Date day) {
	        return date.getTime() >= dayBegin(day).getTime()
	                        && date.getTime() <= dayEnd(day).getTime();
	}
	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 0);
	        c.set(Calendar.MINUTE, 0);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	        return c.getTime();
	}
	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.HOUR_OF_DAY, 23);
	        c.set(Calendar.MINUTE, 59);
	        c.set(Calendar.SECOND, 59);
	        c.set(Calendar.MILLISECOND, 999);
	        return c.getTime();
	}
}
