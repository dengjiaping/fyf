package com.company.fyf.net;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.dao.RubbishVo;
import com.company.fyf.utils.CommConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class RubbishServer extends AbstractHttpServer {
	
	public RubbishServer() {
		super() ;
	}

	public RubbishServer(Context context) {
		super(context) ;
	}
	
	
	@Override
	protected String getModule() {
		return "rubbish";
	}
	
	public void upload(File pic){
		
		addParam("act", "upload") ;
		
		addParam("img", pic);
		doPost(new FilterAjaxCallBack(null) {
			public void onSuccess(String data) {
				// TODO Auto-generated method stub
			}
		}) ;
	}
	
	/*
	 * pic_url	否	图片列表，图片格式jpg|jpeg|gif|png
		typeid	是	垃圾分类
		name	是	垃圾名称
		num	是	数量
		unit	是	单位
		
		POST
	 */
	
	public void uploadRubbish(String pic,String name,String kilo,String complete,String note,final CallBack<Void> back){
		
		addParam("act", "uploadRubbish") ;
		
		addParam("pic_url", pic);
		addParam("complete", complete);
		addParam("name", name);
		addParam("kilo", kilo);
		addParam("note", note);
		addParam("typeid", CommConfig.CLASSIFY_TYPE_ID.KITCHEN);
		
		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				// TODO Auto-generated method stub
				if(back != null){
					back.onSuccess(null);
				}
			}
		}) ;
	}
	
	public void editMyRubbishInfoById(String id,String pic,String name,String complete,String kilo,String note,final CallBack<Void> back){
		
		addParam("act", "EditMyRubbishInfoById") ;
		
		addParam("id", id);
		addParam("pic_url", pic);
		addParam("complete", complete);
		addParam("name", name);
		addParam("kilo", kilo);
		addParam("note", note);
		addParam("typeid", CommConfig.CLASSIFY_TYPE_ID.KITCHEN);
		
		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				// TODO Auto-generated method stub
				if(back != null){
					back.onSuccess(null);
				}
			}
		}) ;
	}
	
	//待改为时间戳
	public void myRubbishList(String lastId,final CallBack<List<RubbishVo>> callback){
		
		addParam("act", "myRubbishList") ;
		addParam("id", lastId) ;
		addParam("pagesize", CommConfig.PAGE_SIZE) ;
		doGet(new FilterAjaxCallBack(null) {
			public void onSuccess(String data) {
				try {
					
					JSONObject jsonObj = new JSONObject(data) ;
					JSONArray list = jsonObj.getJSONArray("list") ;
					List<RubbishVo> vos = JSON.parseArray(list.toString(), RubbishVo.class)  ;
					if(callback != null){
						callback.onSuccess(vos);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					showAnalyticalException(null);
				}
			}
		});
		
	}
	
	//得到垃圾类别。此版本暂时不用此功能，APP内类别名、icon、介绍做成静态的
	public void publicType(){
		
	}

	public void callAdd(String mobile,final CallBack<Object> callback){
		addParam("act", "callAdd") ;
		addParam("mobile", mobile) ;
		doPost(new FilterAjaxCallBack(callback) {
			@Override
			public void onSuccess(String data) {
				if(callback != null) callback.onSuccess(null);
			}
		});
	}

}
