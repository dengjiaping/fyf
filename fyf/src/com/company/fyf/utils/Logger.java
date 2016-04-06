package com.company.fyf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class Logger {
	
	public static void i(String tag,String msg){
		
		if(!CommConfig.DEBUG){
			return ;
		}
		
		Log.i(tag, msg) ;
	}
	
	public static void d(String tag,String msg){
		
		if(!CommConfig.DEBUG){
			return ;
		}
		
		Log.d(tag, msg) ;
	}
	
	public static void w(String tag,String msg){
		
		if(!CommConfig.DEBUG){
			return ;
		}
		
		Log.w(tag, msg) ;
	}
	
	public static void e(String tag,String msg){
		
		if(!CommConfig.DEBUG){
			return ;
		}
		
		Log.e(tag, msg) ;
	}
	
	public static void focus(String focusLog){
		
		if(!CommConfig.DEBUG){
			return ;
		}
		
		long time = System.currentTimeMillis() ;
		
		String fileName = DateFormatUtils.format01("" + time) ;
		
		File file = new File(CommConfig.PATH_LOG_DIR + fileName) ;
		FileOutputStream outStream = null ;
		try {
			if(!file.exists()){
				file.createNewFile() ;
			}
			outStream = new FileOutputStream(file,true) ;
			StringBuilder builder = new StringBuilder() ;
			builder.append(DateFormatUtils.format02("" + time)) ;
			builder.append(" :: ") ;
			builder.append(focusLog) ;
			builder.append("\n") ;
			outStream.write(builder.toString().getBytes()) ;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				outStream.flush() ;
				outStream.close() ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void focus(Class<?> clz,String focusLog){
		focus(clz.getSimpleName() + "--->" + focusLog ) ;
	}
	

}
