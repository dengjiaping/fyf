package com.company.fyf.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.company.fyf.FyfApp;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.utils.FinalUtils;
import com.company.fyf.utils.Logger;
import com.lyx.dlg.load.CancelListener;
import com.lyx.dlg.load.LoadingDialog;
import com.lyx.dlg.load.RetryListener;
import com.lyx.dlg.load.SimpleDialog;
import com.lyx.utils.CommUtil;
//整个类就是一个cell的概率
public abstract class AbstractHttpServer {

	private FinalHttp httpHelper = null;

	private LoadingDialog loadDialog = null;

	protected Context context = null;

	private AjaxParams params = null;

	protected boolean isDilog = true;
	
	private Object[] track = new Object[2] ;

	public AbstractHttpServer() {
		// TODO Auto-generated constructor stub
		this(null);
	}

	public AbstractHttpServer(Context context) {
		// TODO Auto-generated constructor stub
		this.httpHelper = FinalUtils.getHttpHelper();
		this.params = new AjaxParams();
		this.params.put("module", getModule());
		this.params.put("api_version",CommConfig.API_VERSION);
		this.params.put("sign", "1");
		this.params.put("timestamp", String.valueOf(System.currentTimeMillis()));

		if (context == null || !(context instanceof Activity)) {
			this.isDilog = false;
		} else {
			this.isDilog = true;
			this.loadDialog = new SimpleDialog(context) ;
			this.loadDialog.setRetryListener(new RetryListener() {
				public void retry() {
				   doRetry() ;
				}
			}) ;
		}
		this.context = context;
	}

	private void doRetry() {
		// TODO Auto-generated method stub
		if(0 == (Integer) track[0]){
			doPost((FilterAjaxCallBack) track[1]) ;
		}else if(1 == (Integer) track[0]){
			doGet((FilterAjaxCallBack) track[1]) ;
		}
	}

	protected final void doPost(FilterAjaxCallBack ajaxCallBack) {
		Logger.d("AbstractHttpServer","do post : url = " + CommConfig.NET_BASE_URL + "?" + params.getParamString()) ;
		track[0] = 0 ;
		track[1] = ajaxCallBack ;
		
		httpHelper.post(CommConfig.NET_BASE_URL, params, new MyAjaxCallBack(
				ajaxCallBack));
	}

	protected final void doGet(FilterAjaxCallBack ajaxCallBack) {
		Logger.d("AbstractHttpServer","do get : url = " + CommConfig.NET_BASE_URL + "?" + params.getParamString()) ;
		
		track[0] = 1 ;
		track[1] = ajaxCallBack ;
		
		httpHelper.get(CommConfig.NET_BASE_URL, params,
				new MyAjaxCallBack(ajaxCallBack));
	}

	private class MyAjaxCallBack extends AjaxCallBack<String> {

		private FilterAjaxCallBack callBack = null;

		private MyAjaxCallBack(FilterAjaxCallBack callBack) {
			this.callBack = callBack;
		}

		@Override
		public void onFailure(Throwable t, int errorNo, String strMsg) {
			super.onFailure(t, errorNo, strMsg);
			checkIsNeedStopDialog();
			if (callBack != null) {
				callBack.onBadNet();
			}
			if (callBack != null) {
				callBack.onFail();
			}
			checkIsNeedShowFailMsg("请求失败,请检查网络设置后重试") ;
			Logger.d("AbstractHttpServer","onFailure : errorNo = " + errorNo + "--strMsg = " + strMsg) ;
		}

		@Override
		public void onSuccess(String jsonStr) {
			super.onSuccess(jsonStr);
			Logger.d("AbstractHttpServer","onSuccess : jsonStr = " + jsonStr) ;
			checkIsNeedStopDialog() ;
			
			JSONObject jsonObject = null ;
			try {
				jsonObject = new JSONObject(jsonStr) ;
				
				int success = jsonObject.getInt("success") ;
				
				if(1 == success){
					JSONObject result = jsonObject.getJSONObject("result") ;
					
					callBack.onSuccess(result.toString()) ;
				}else{

					JSONObject msg = jsonObject.getJSONObject("message") ;
					
					String error_msg = msg.getString("error_msg") ;
					if("user_not_login".equals(error_msg)
							||"login_status_change_please_login".equals(error_msg)
							||"login_other_device_please_login".equals(error_msg)){
						UserInfoDb.INSTANCE.clear();
					}
					
					if(!TextUtils.isEmpty(msg.getString("error_str"))){
						checkIsNeedShowFailMsg(msg.getString("error_str")) ;
						return ;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				showAnalyticalException(callBack) ;
			}
			
		}

		@Override
		public void onStart() {
			super.onStart();
			checkIsNeedStartDialog();
		}
	}

	protected abstract class FilterAjaxCallBack extends CallBack<String> {

		private CallBack<?> callBack = null;

		protected FilterAjaxCallBack(CallBack<?> callBack) {
			this.callBack = callBack;
		}

		public abstract void onSuccess(String data);

		@Override
		public void onFail() {
			if (callBack != null)
				callBack.onFail();
		}

		@Override
		public void onBadNet() {
			if (callBack != null)
				callBack.onBadNet();
		}
	}

	private void checkIsNeedStartDialog() {
		if (isDilog && loadDialog != null)
			loadDialog.showLoading();
	}

	private void checkIsNeedStopDialog() {
		if (isDilog && loadDialog != null)
			loadDialog.closeLoading();
	}
	
	protected void showAnalyticalException(CallBack<?> callBack){
		checkIsNeedShowFailMsg(ErrorCodeMsg.getDefaultMsg(0)) ;
		if (callBack != null) {
			callBack.onFail();
		}
	}

	private void checkIsNeedShowFailMsg(String failMsg) {
		if (isDilog && loadDialog != null)
			loadDialog.showFail(failMsg);
	}

	protected abstract String getModule();

	protected void addParam(String key, String value) {
		params.put(key, value);
	}
	
	protected void addParam(String key, File value) {
		try {
			params.put(key, value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setDlgCancelBtnOnClickListener(final View.OnClickListener onClickListener){
		if(loadDialog != null)
			loadDialog.setCancelListener(new CancelListener() {
				public void onCancel() {
					onClickListener.onClick(null) ;
				}
			}) ;
	}

}
