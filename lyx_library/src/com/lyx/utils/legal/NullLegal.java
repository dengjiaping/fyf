package com.lyx.utils.legal;

import android.text.TextUtils;

public class NullLegal implements Legal {
	
	public static NullLegal instance = new NullLegal() ;

	@Override
	public boolean check(String value) {
		
		if(TextUtils.isEmpty(value)){
			return false ;
		}
		if("null".equals(value)){
			return false ;
		}
		
		return true;
	}

	@Override
	public String warnMsg(String lable) {
		// TODO Auto-generated method stub
		return lable + "不能为空!";
	}

}
