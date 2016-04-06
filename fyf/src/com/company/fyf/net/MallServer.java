package com.company.fyf.net;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.dao.CommodityVo;
import com.company.fyf.utils.CommConfig;

public class MallServer  extends AbstractHttpServer {
	
	public MallServer() {
		super() ;
	}

	public MallServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		return "mall";
	}
	
	public void productList(String lastId,final CallBack<List<CommodityVo>> callback){
		addParam("act", "ProductList") ;
		addParam("id", lastId) ;
		addParam("pagesize", CommConfig.PAGE_SIZE) ;
		doGet(new FilterAjaxCallBack(callback) {
			public void onSuccess(String data) {
				try {
					
					JSONObject jsonObj = new JSONObject(data) ;
					JSONArray list = jsonObj.getJSONArray("list") ;
					List<CommodityVo> vos = JSON.parseArray(list.toString(), CommodityVo.class)  ;
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

}
