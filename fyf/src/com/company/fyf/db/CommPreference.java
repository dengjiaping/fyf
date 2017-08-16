package com.company.fyf.db;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.company.fyf.model.UserInfo;
import com.company.fyf.notify.KeyList;
import com.company.fyf.notify.NotifyCenter;
import com.company.fyf.utils.CommConfig;
import com.lyx.utils.SharedPreferencesUtils;

public class CommPreference {
	
	private final String KEY_ABOUT_US = "key_about_us" ;
	private final String KEY_POINTS_RULE = "key_points_rule" ;
	private final String KEY_SHOULD_SHOW_GUIDE = "key_should_show_guide" ;
	private final String KEY_USER_INFO = "key_user_info" ;
	private final String KEY_ORDER_MARK_TIMESTAMP = "key_order_mark_timestamp" ;


	public static CommPreference INSTANCE = new CommPreference() ;
	
	private SharedPreferencesUtils sp ;
	
	private CommPreference() {
	}
	
	public void init(Context context){
		sp = new SharedPreferencesUtils(context, CommConfig.PREFERENCE_NAME) ;
	}
	
	public void setAboutUsContent(String content){
		if(TextUtils.isEmpty(content)){
			return ;
		}
		sp.setString(KEY_ABOUT_US, content) ;
		NotifyCenter.sendEmptyMsg(KeyList.KEY_ABOUT_US_UPDATE) ;
	}
	
	public String getAboutUsContent(){
		return sp.getString(KEY_ABOUT_US) ;
	}
	
	public void setPointsRule(String content){
		if(TextUtils.isEmpty(content)){
			return ;
		}
		sp.setString(KEY_POINTS_RULE, content) ;
		NotifyCenter.sendEmptyMsg(KeyList.KEY_POINTS_RULE_UPDATE) ;
	}
	
	public String getPointsRule(){
		return sp.getString(KEY_POINTS_RULE) ;
	}
	
	public boolean shouldShowGuide(){
		if(!sp.hasKey(KEY_SHOULD_SHOW_GUIDE)){
			return true;
		}
		return sp.getBoolean(KEY_SHOULD_SHOW_GUIDE) ;
	}
	
	public void setGuideNoShow(){
		sp.setBoolean(KEY_SHOULD_SHOW_GUIDE, false) ;
	}


	public void setUserInfo(String userInfo){
		if(TextUtils.isEmpty(userInfo)){
			sp.setString(KEY_USER_INFO,"");
		}else {
			sp.setString(KEY_USER_INFO,userInfo);
		}
		NotifyCenter.sendEmptyMsg(KeyList.KEY_USER_INFO_UPDATE) ;
	}

	public void updateUserInfo(UserInfo userInfo){

		if(userInfo == null){
			clearUserInfo();
			return;
		}

		try {
			String s = JSON.toJSONString(userInfo) ;
			setUserInfo(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearUserInfo(){
		setUserInfo(null);
	}

	public UserInfo getUserInfo(){

		String s = sp.getString(KEY_USER_INFO) ;

		if(TextUtils.isEmpty(s)){
			return null ;
		}

		try {
			return JSON.parseObject(s,UserInfo.class) ;
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}
	}

	public void setOrderMark(){
		sp.setlong(KEY_ORDER_MARK_TIMESTAMP,System.currentTimeMillis());
	}

	public long getOrderMark(){
		return sp.getlong(KEY_ORDER_MARK_TIMESTAMP) ;
	}


}
