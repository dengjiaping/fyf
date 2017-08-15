package com.company.fyf.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalDb.DbUpdateListener;
import net.tsz.afinal.FinalHttp;

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
	

	

}
