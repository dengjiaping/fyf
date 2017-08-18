package com.company.fyf.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.company.fyf.db.CommPreference;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DbUpdateListener;
import net.tsz.afinal.FinalHttp;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class FinalUtils {
	
	private static FinalBitmap finalBitmap ;
	private static FinalHttp finalHttp ;
	private static FinalDb db ;
	
	public static void init(Context ctx){
		finalBitmap = FinalBitmap.create(ctx) ;
		initHttpClient() ;
//		finalHttp.configCookieStore(cookieStore) ;
		db = FinalDb.create(ctx, null, CommConfig.DB_NAME,CommConfig.DEBUG, CommConfig.DBVERSION, new DbUpdateListener() {
			public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			}
		}) ;
	}
	
	public static FinalBitmap getDisplayer(){
		return finalBitmap ;
	}
	
	public static FinalHttp getHttpHelper(){
		return finalHttp ;
	}
	
	public static FinalDb getDb(){
		return db ;
	}


	private static void initHttpClient(){
		finalHttp = new FinalHttp() ;
		DefaultHttpClient httpClient = (DefaultHttpClient) finalHttp.getHttpClient();
		httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
			public void process(HttpResponse response, HttpContext context) {
				Header[] headers = response.getAllHeaders();
				if(headers != null || headers.length > 0) {
					StringBuilder cookie = new StringBuilder() ;
					for (int i = 0; i < headers.length; i++) {
						/*
						Set-Cookie	CAPkO_auth=1daaJQDaZPsDxZyl9mLGFrQGLPZA-NF6PpUKvEfzCB-wY15TqqkTb3tRB9NC5FWgElYkwnQbksTE0bGF5UkspHoD01YVBbMGg2UwF41iLXWnuR694F1e_-QKmyAzIE_JWm1ZotGj3P3QRi7wOmm2CFdyxOVI; expires=Sun, 17-Sep-2017 10:38:45 GMT
Set-Cookie	CAPkO__userid=ac026cdAV4k8dC3S3iRr3txEmdNk4KgO6nL9GQ1LLg-c; expires=Sun, 17-Sep-2017 10:38:45 GMT
Set-Cookie	CAPkO__username=937dyOV0wRk11p_EWY4VIWHsMwEc5ylNt-V4jAu5VCqhYF-TswsxXw; expires=Sun, 17-Sep-2017 10:38:45 GMT
Set-Cookie	CAPkO__groupid=b3755gAx-AihPPZh9mNtIGQq0p_xqkz3t1-vxBT9; expires=Sun, 17-Sep-2017 10:38:45 GMT
Set-Cookie	CAPkO__nickname=325eQG8Wz7aaf7cpiNHjk_fQZ9YmbNqi1enTmU-3SQ; expires=Sun, 17-Sep-2017 10:38:45 GMT
						 */
						String headName = headers[i].getName() ;
						if(!"Set-Cookie".equals(headName)){
							continue;
						}
						String cookieString = headers[i].getValue() ;
						if(cookieString.contains("CAPkO_auth")
								|| cookieString.contains("CAPkO__userid")
								|| cookieString.contains("CAPkO__username")
								|| cookieString.contains("CAPkO__groupid")
								|| cookieString.contains("CAPkO__nickname")){
							cookie.append(cookieString) ;
							cookie.append("&divide&") ;
						}
					}
					if(cookie.length() <= 0) return;
					int start = cookie.lastIndexOf("&divide&") ;
					int end = cookie.length();
					cookie.delete(start,end) ;
					CommPreference.INSTANCE.setUserCookie(cookie.toString());
				}
			}
		});
		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
				Header[] headers = httpRequest.getAllHeaders();
				if(headers != null || headers.length > 0) {
					for (int i = 0; i < headers.length; i++) {
						String headName = headers[i].getName() ;
						if(!"Set-Cookie".equals(headName)){
							continue;
						}
						String cookieString = headers[i].getValue() ;
						if(cookieString.contains("CAPkO_auth")
								|| cookieString.contains("CAPkO__userid")
								|| cookieString.contains("CAPkO__username")
								|| cookieString.contains("CAPkO__groupid")
								|| cookieString.contains("CAPkO__nickname")){
							httpRequest.removeHeader(headers[i]);
						}
					}
				}
				String cookie = CommPreference.INSTANCE.getUserCookie() ;
				if(TextUtils.isEmpty(cookie)){
					return;
				}
				String[] cookies = cookie.split("\\&divide\\&") ;
				if(cookies == null || cookies.length <= 0){
					return;
				}
				for (int i = 0; i < cookies.length; i++) {
					if(TextUtils.isEmpty(cookies[i])) continue;
					httpRequest.addHeader("Set-Cookie", cookies[i]);
				}
			}
		});
	}

	

}
