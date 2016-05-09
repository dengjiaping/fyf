package com.company.fyf.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class SweepHelper {
	
	private static class TAG{
		/*
		 * {
				"tag":"25ec1187-c3e0-473c-ad49-71ee0b9f7d2b",
				"data":"%username%"
			}
		 */
		public static final String GET_USER_INFO = "25ec1187-c3e0-473c-ad49-71ee0b9f7d2b";
		
		public static final String USER_ADD_CREDIT_BY_SELF = "1db3dcdb-1fde-43d1-8e04-5e97c2496b96";
		
	}
	
	public static class TYPE{
		public static final int GET_USER_INFO = 1 ;
		public static final int USER_ADD_CREDIT_BY_SELF = GET_USER_INFO + 1 ;
		public static final int ERROR = -1 ;
	}
	
	public static int getType(String json){
		try {
			JSONObject obj = new JSONObject(json) ;
			if(!obj.has("tag") || !obj.has("data")){
				return TYPE.ERROR ;
			}
			String tag = obj.getString("tag") ;
			if(!containTag(tag)){
				return TYPE.ERROR ;
			}
			if(TAG.GET_USER_INFO.equals(tag)){
				return TYPE.GET_USER_INFO ;
			}else if(TAG.USER_ADD_CREDIT_BY_SELF.equals(tag)){
				return TYPE.USER_ADD_CREDIT_BY_SELF ;
			}
		} catch (JSONException e) {
			return TYPE.ERROR ;
		}
		return TYPE.ERROR ;
	}
	
	private static boolean containTag(String tag){
		Class<TAG> tagCls = TAG.class ;
		Field[] fields = tagCls.getFields() ;
		for(int i = 0;i < fields.length;i++){
			Field field = fields[i] ;
			try {
				String value = (String) field.get(null) ;
				if(value != null && value.equals(tag)){
					return true ;
				}
			} catch (IllegalAccessException | IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return false ;
	}
	

}
