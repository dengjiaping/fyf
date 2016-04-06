package com.lyx.utils.textformat;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.text.TextUtils;

/**
 * 1.各个金额填写保留两位小数，比如用户输入的是43，传给后台的数据要保留两位小数，43.00
 * 2.金额输入框格式：需要格式化，不允许单独输入“.”，或“05”等样式
 * 3.千分号
 */
public class DisplayCostFormator implements TextFormator{
	
	public static DisplayCostFormator instance = new DisplayCostFormator() ;
	protected NumberFormat format ;
	
	{
		format =  new DecimalFormat("#,##0.00;-#,##0.00") ;
	}

	@Override
	public String format(String text) {
		if(TextUtils.isEmpty(text))
			return "" ;
		double d = 0.00d ;
		try {
			d = Double.parseDouble(text) ;
		} catch (NumberFormatException e) {
			return text ;
		}
		return format.format(d);
	}

}
