package com.company.fyf.net;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.company.fyf.FyfApp;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.Checkin;
import com.company.fyf.model.UserInfo;
import com.company.fyf.utils.FyfUtils;

public class CheckinServer extends AbstractHttpServer{
	
	public CheckinServer() {
		super() ;
	}

	public CheckinServer(Context context) {
		super(context) ;
	}

	@Override
	protected String getModule() {
		return "checkin";
	}
	
	/*
	 * content	否	签到内容，长度不可超过250个字符【可选功能】
	 * 
	 * POST
	 */
	public void checkin(String content, final CallBack<Checkin> back){
		
		addParam("act", "checkin") ;
		addParam("content", content) ;
		
		doPost(new FilterAjaxCallBack(back) {
			public void onSuccess(String data) {
				
				Checkin checkin = JSON.parseObject(data, Checkin.class) ;
				
				if(checkin == null){
					showAnalyticalException(back) ;
					return ;
				}
				UserInfo info = UserInfoDb.INSTANCE.get() ;
				info.setCheckin_month_total(checkin.getMdays()) ;
				info.setCheckin_lasted_time(checkin.getCheckintime()) ;
				info.setCredit(checkin.getCredit()) ;
				UserInfoDb.INSTANCE.update(info) ;
				if(back != null){
					back.onSuccess(checkin) ;
				}
				Toast.makeText(context, "签到成功+"+checkin.getCredit_add()+"积分", Toast.LENGTH_SHORT).show() ;
			}
		}) ;
	}
	
	/**
	 * page	否	页码
		pagesize	否	每页信息数
		
		待改为时间戳
	 */
	public void myCheckinList(){
		
		
	}


}
