package com.company.fyf.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.db.CommPreference;
import com.company.fyf.model.Daka;
import com.company.fyf.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DakaServer extends AbstractHttpServer{
	
	public DakaServer() {
		super() ;
	}

	public DakaServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		return "daka";
	}
	
	//上下班打卡。普通居民无权限  POST typeid	是	数字。1：上班，2：下班
	public void add(final String typeid,final CallBack<Void> back ){
		
		  addParam("act", "add") ;
		  addParam("typeid", typeid) ;
		  
		  doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				try {
					// {"result":{"id":"7","username":"13200000000","worktime":"1446991230","workofftime":"0","day":"20151108"},"success":1}
					Daka daka = JSON.parseObject(data, Daka.class) ;
					UserInfo info = CommPreference.INSTANCE.getUserInfo() ;
					info.setDaka_lasted_work(daka.getWorktime());
					info.setDaka_lasted_workoff(daka.getWorkofftime());
					CommPreference.INSTANCE.updateUserInfo(info);
					if(back != null){
						back.onSuccess(null) ;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					showAnalyticalException(back) ;
				}
			}
		}) ;
	}
	
	//我的打卡记录 GET  待改为时间戳
	public void myDakaList(final CallBack<List<Daka>> back){
		
		  addParam("act", "myDakaList") ;
		  
		  doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				
				try {
					List<Daka> list = JSON.parseArray(new JSONObject(data)
							.getJSONArray("list").toString(), Daka.class);

					if (back != null) {
						back.onSuccess(list);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(back);
				}
				
			}
		}) ;
		
	}


}
