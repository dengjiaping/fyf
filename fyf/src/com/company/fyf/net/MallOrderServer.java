package com.company.fyf.net;

import android.content.Context;

public class MallOrderServer extends AbstractHttpServer {
	
	public MallOrderServer() {
		super() ;
	}

	public MallOrderServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		return "mall_order";
	}
	
	public void buy(String id,String username,final CallBack<Void> callBack) {
		
		addParam("act",  "buy") ;
		addParam("id",  id) ;
		addParam("number", "1") ;
		addParam("to_username", username) ;
		
		doPost(new FilterAjaxCallBack(callBack) {
			public void onSuccess(String data) {
				if(callBack != null){
					callBack.onSuccess(null) ;
				}
			}
		}) ;
	}

}
