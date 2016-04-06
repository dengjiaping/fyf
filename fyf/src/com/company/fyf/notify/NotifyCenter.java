package com.company.fyf.notify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import com.company.fyf.utils.Logger;
import com.lyx.utils.CommUtil;

public class NotifyCenter {
	
	private static Map<String,List<INotifyClient>> map = CommUtil.obtainMap() ;
	
    static{
    	Class<KeyList> clz = KeyList.class ;
    	Field[] fs = clz.getDeclaredFields() ;
    	for (Field field : fs) {
    		try {
				String keyValue = (String) field.get(null) ;
				
				if(TextUtils.isEmpty(keyValue)){
					continue ;
				}
				
				List<INotifyClient> list = new ArrayList<>() ;
				map.put(keyValue, list) ;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
    }
	
	public static void register(String key,INotifyClient client){
		Logger.d("NotifyCenter", "[register] key = " + key + "-- client = " + client ) ;
		checkKey(key);
		List<INotifyClient> list = map.get(key) ;
		if(!list.contains(client)){
			list.add(client) ;
		}
	}
	
	public static void unRegister(String key,INotifyClient client){
		Logger.d("NotifyCenter", "[unRegister] key = " + key + "-- client = " + client ) ;
		checkKey(key);
		List<INotifyClient> list = map.get(key) ;
		if(list.contains(client)){
			list.remove(client) ;
		}
	}
	
	public static void sendMsg(IMsg<?> msg){
		String key = msg.getKey() ;
		checkKey(key);
		List<INotifyClient> list = map.get(key) ;
		for (INotifyClient client : list) {
			client.onRefresh(msg) ;
		}
	}
	
	public static void sendEmptyMsg(String key){
		checkKey(key);
		List<INotifyClient> list = map.get(key) ;
		IMsg<Void> msg = new EmptyMsg(key) ;
		for (INotifyClient client : list) {
			client.onRefresh(msg) ;
		}
	}

	private static void checkKey(String key) {
		if(!map.containsKey(key)){
			throw new IllegalArgumentException("订阅list中无此订阅号") ;
		}
	}
	

}
