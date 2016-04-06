package com.company.fyf.net;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.company.fyf.model.Feedback;

public class FeedbackServer extends AbstractHttpServer{
	
	public FeedbackServer() {
		super() ;
	}

	public FeedbackServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		return "feedback";
	}
	
	//待改为时间戳 GET
	public void myFeedbackList(final CallBack<List<Feedback>> back){
		
		addParam("act", "myFeedbackList");
		
		doGet(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				
				try {
					List<Feedback> list = JSON.parseArray(new JSONObject(data)
							.getJSONArray("list").toString(), Feedback.class);

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
	
	//发表意见反馈 POST
	public void add(String content , final CallBack<Void> back){
		
		addParam("act", "add");
		addParam("content", content) ;
		
		doPost(new FilterAjaxCallBack(back) {
			
			@Override
			public void onSuccess(String data) {
				if(back != null){
					back.onSuccess(null) ;
				}
			}
		}) ;
		
	}


}
