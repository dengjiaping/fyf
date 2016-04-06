package com.lyx.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class DateTextView extends TextView {
	
	public static final String FORMAT = "yyyy-MM-dd";
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat SDF = new SimpleDateFormat(FORMAT)  ;
	
	private Calendar date ;

	public DateTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Calendar getDate() {
		return date;
	}
	public int[] getDateInArray() {
		int[] date = null;
		if(this.date!=null){
			date = new int[]{
					this.date.get(Calendar.YEAR) ,
					this.date.get(Calendar.MONTH) ,
					this.date.get(Calendar.DAY_OF_MONTH) 
			} ;
		}else{
			Calendar calendar = Calendar.getInstance()  ;
			date = new int[]{
					calendar.get(Calendar.YEAR) ,
					calendar.get(Calendar.MONTH) ,
					calendar.get(Calendar.DAY_OF_MONTH) };
		}
		return date;
	}

	public void setDate(Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 0)  ;
		date.set(Calendar.MINUTE, 0)  ;
		date.set(Calendar.SECOND, 0)  ;
		date.set(Calendar.MILLISECOND, 0)  ;
		this.date = date;
		setText(SDF.format(date.getTimeInMillis()))  ;
		dateChange(date) ;
	}
	public void setDate(int... date) {
		Calendar cDate = Calendar.getInstance();
		cDate.set(Calendar.YEAR, date[0])  ;
		cDate.set(Calendar.MONTH, date[1])  ;
		cDate.set(Calendar.DAY_OF_MONTH, date[2])  ;
		cDate.set(Calendar.HOUR_OF_DAY, 0)  ;
		cDate.set(Calendar.MINUTE, 0)  ;
		cDate.set(Calendar.SECOND, 0)  ;
		cDate.set(Calendar.MILLISECOND, 0)  ;
		setDate(cDate) ;
	}
	protected void dateChange(Calendar newDate){
		
	}
	

}
