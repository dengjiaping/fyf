package com.company.fyf.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DbUpdateListener;
import net.tsz.afinal.FinalHttp;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinalUtils {
	
	private static FinalBitmap finalBitmap ;
	private static FinalHttp finalHttp ;
	private static FinalDb db ;
	
	public static void init(Context ctx){
		finalBitmap = FinalBitmap.create(ctx) ;
		finalHttp = new FinalHttp() ;
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
	
	private static CookieStore cookieStore = new CookieStore() {
		
		private  List<Cookie>  cookies = new ArrayList<>() ;

		@Override
		public void addCookie(Cookie cookie) {
			// TODO Auto-generated method stub
			Logger.d("FinalUtils", "[cookieStore addCookie] cookie = " + cookie) ;
			cookies.add(cookie) ;
		}

		@Override
		public void clear() {
			// TODO Auto-generated method stub
			Logger.d("FinalUtils", "[cookieStore clear]") ;
		}

		@Override
		public boolean clearExpired(Date date) {
			// TODO Auto-generated method stub
			Logger.d("FinalUtils", "[cookieStore clearExpired] date = " + date) ;
			return false;
		}

		@Override
		public List<Cookie> getCookies() {
			Logger.d("FinalUtils", "[cookieStore getCookies]") ;
			return cookies;
		}
	};
	
	

}
