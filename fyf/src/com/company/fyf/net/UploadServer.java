package com.company.fyf.net;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.dao.FileVo;

public class UploadServer extends AbstractHttpServer {
	
	public UploadServer() {
		super() ;
	}

	public UploadServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		// TODO Auto-generated method stub
		return "upload";
	}
	
	public void upload(File pic,final CallBack<List<FileVo>> callBack){
		
		addParam("act", "upload") ;
		
		addParam("img", pic);
		doPost(new FilterAjaxCallBack(null) {
			public void onSuccess(String data) {
				// TODO Auto-generated method stub
				if(callBack != null){
					try {
						JSONObject obj = new JSONObject(data) ;//{"filelist":
						JSONArray array = obj.getJSONArray("filelist") ;
						List<FileVo> list = JSON.parseArray(array.toString(), FileVo.class) ;
						callBack.onSuccess(list);
					} catch (JSONException e) {
						e.printStackTrace();
						showAnalyticalException(null);
					}
				}
			}
		}) ;
	}

}
