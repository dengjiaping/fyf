package com.company.fyf.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

public class DateFormatUtils {
	
	private static SimpleDateFormat format01 = null ;
	@SuppressLint("SimpleDateFormat") 
	public static String format01(String time){
		if(format01 == null){
			format01 = new SimpleDateFormat("yyyy-MM-dd") ;
		}
		return format01.format(Long.parseLong(time)) ;
	}
	
	private static SimpleDateFormat format02 = null ;
	@SuppressLint("SimpleDateFormat") 
	public static String format02(String time){
		if(format02 == null){
			format02 = new SimpleDateFormat("yyyy-MM-dd HH:mm") ;
		}
		return format02.format(Long.parseLong(time)) ;
	}

}
