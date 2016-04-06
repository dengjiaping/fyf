package com.company.fyf.utils;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.lyx.utils.RegexUtil;

public class FyfUtils {
	
	public static boolean doCheckPhone(Context context, final String phoneNum) {
		return doCheckPhone(context,phoneNum,true);
	}

	public static boolean doCheckPhone(Context context, final String phoneNum,boolean toast) {
		// 校验合法性
		if (FyfUtils.checkInputEmpty(phoneNum)) {
			if(toast)
				showToast(context, "手机号不能为空");
			return false;
		}
		if (!RegexUtil.isMobile(phoneNum)) {
			if(toast)
				showToast(context, "请输入有效手机号");
			return false;
		}
		return true;
	}

	public static boolean doCheckPwd(Context context, final String pwd) {
		if (FyfUtils.checkInputEmpty(pwd)) {
			showToast(context, "密码不能为空");
			return false;
		}
		if (pwd.length() < 6) {
			showToast(context, "密码不能少于6位数字");
			return false;
		}
		if (pwd.length() > 20) {
			showToast(context, "密码过长");
			return false;
		}
		if (!RegexUtil.isOnlyNum(pwd)) {
			showToast(context, "密码只能为纯数字");
			return false;
		}
		return true;
	}

	public static boolean doCheckPhonePwd(Context context,
			final String phoneNum, final String pwd) {
		return doCheckPhone(context, phoneNum) && doCheckPwd(context, pwd);
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static String sexToId(String sex) {
		if ("男".equals(sex)) {
			return "1";
		} else if ("女".equals(sex)) {
			return "2";
		}
		return "0";
	}

	public static String sexFromId(String id) {
		if ("1".equals(id)) {
			return "男";
		} else if ("2".equals(id)) {
			return "女";
		}
		return "";
	}

	public static String ageFromBirthday(String time) {
		
		if(TextUtils.isEmpty(time)){
			return "" ;
		}
		
		if("0".equals(time)){
			return "0" ;
		}
		
		Date birthDay = null;
		try {
			birthDay = new Date(Long.parseLong(time));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		}
		
		if(birthDay == null){
			return "" ;
		}

		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}

		return age + "";
	}
	
	public static String getPhpTimeTemp(String javaTimeTemp){
		
		if(TextUtils.isEmpty(javaTimeTemp)){
			return "" ;
		}
		
		return javaTimeTemp.substring(0,javaTimeTemp.length()-3) ;
	}
	
	public static String getJavaTimeTemp(String phpTimeTemp){
		if(TextUtils.isEmpty(phpTimeTemp)){
			return "" ;
		}
		
		return phpTimeTemp + "000" ;
	}
	
	public static String encryptPhone(String phone){
		if(FyfUtils.checkInputEmpty(phone)){
			return "" ;
		}
		if(phone.length() <= 3){
			return phone ;
		}
		StringBuilder builder = new StringBuilder(phone) ;
		if(phone.length() == 4){
			builder.replace(3, 4, "*") ;
		}else if(phone.length() == 5){
			builder.replace(3, 5, "**") ;
		}else if(phone.length() == 6){
			builder.replace(3, 6, "***") ;
		}else{
			builder.replace(3, 7, "****") ;
		}
		return builder.toString() ;
	}
	
	public static boolean checkInputEmpty(String value){
		if(TextUtils.isEmpty(value)){
			return true ;
		}
		String temp = value.trim() ;
		temp = temp.replace("\n","") ;
		if(TextUtils.isEmpty(temp)){
			return true ;
		}
		return false ;
	}

}
