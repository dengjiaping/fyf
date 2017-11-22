package com.company.fyf.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.company.fyf.db.CommPreference;
import com.company.fyf.model.AuthCookie;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DbUpdateListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.exception.HttpException;

import org.apache.http.Header;
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
					AuthCookie authCookie = CommPreference.INSTANCE.getUserCookie();
					if(authCookie == null) authCookie = new AuthCookie() ;
					for (int i = 0; i < headers.length; i++) {
						String headName = headers[i].getName() ;
						if(!"Set-Cookie".equals(headName)){
							continue;
						}
						String cookieString = headers[i].getValue() ;
						if(cookieString.contains("CAPkO_auth")){
							authCookie.setAuth(cookieString);
						}else if(cookieString.contains("CAPkO__userid")){
							authCookie.setUserid(cookieString);
						}else if(cookieString.contains("CAPkO__username")){
							authCookie.setUsername(cookieString);
						}else if(cookieString.contains("CAPkO__groupid")){
							authCookie.setGroupid(cookieString);
						}else if(cookieString.contains("CAPkO__nickname")){
							authCookie.setNickname(cookieString);
						}
					}
					Logger.d("setUserCookie","set authCookie : " + authCookie);
					CommPreference.INSTANCE.setUserCookie(authCookie);
				}
			}
		});
		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
				Header[] headers = httpRequest.getAllHeaders();
				String oriCookie = "" ;
				Header cookieHeader = null ;
				if(headers != null || headers.length > 0) {
					for (int i = 0; i < headers.length; i++) {
						String headName = headers[i].getName() ;
						if(!"Cookie".equals(headName)){
							continue;
						}
						oriCookie = headers[i].getValue() ;
						cookieHeader = headers[i];
					}
				}

				if(oriCookie != null && oriCookie.contains("CAPkO_auth")){
					return;
				}
				httpRequest.removeHeader(cookieHeader);
				AuthCookie authCookie = CommPreference.INSTANCE.getUserCookie() ;
				if(authCookie == null){
					return;
				}
				StringBuilder cookieBuilder = new StringBuilder(oriCookie) ;
				if(cookieBuilder.length() > 0)
					cookieBuilder.append(";");
				if(!TextUtils.isEmpty(authCookie.getAuth())){
					cookieBuilder.append(authCookie.getAuth()) ;
					cookieBuilder.append(";");
				}
				if(!TextUtils.isEmpty(authCookie.getGroupid())){
					cookieBuilder.append(authCookie.getGroupid()) ;
					cookieBuilder.append(";");
				}
				if(!TextUtils.isEmpty(authCookie.getNickname())){
					cookieBuilder.append(authCookie.getNickname()) ;
					cookieBuilder.append(";");
				}
				if(!TextUtils.isEmpty(authCookie.getUserid())){
					cookieBuilder.append(authCookie.getUserid()) ;
					cookieBuilder.append(";");
				}
				if(!TextUtils.isEmpty(authCookie.getUsername())){
					cookieBuilder.append(authCookie.getUsername()) ;
					cookieBuilder.append(";");
				}
				Logger.d("setUserCookie","cookieBuilder ： " + cookieBuilder);
				httpRequest.addHeader("Cookie", cookieBuilder.toString());
			}
		});
	}

	

}
