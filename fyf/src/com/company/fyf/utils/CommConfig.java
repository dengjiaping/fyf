package com.company.fyf.utils;

import android.os.Environment;

public class CommConfig {

	public static final boolean DEBUG = true ;
	
	public static final int DBVERSION = 1 ;
	
	public static final String API_VERSION = "1" ;

	public static final String DB_NAME = "fyf.db" ;
	public static final String PREFERENCE_NAME = "fyf" ;
	
	//test
//	public static final String HOST = "http://api.zzpinna.com/" ;
	//发布
	public static final String HOST = "http://www.fenyifen.cn/" ;
	//网络请求接口Url
	public static final String NET_BASE_URL = HOST + "mobile.php";
	
	
	
	public static final String UMENG_SHARE_DESCRIPTOR = "com.umeng.share";
	
	public static final String PAGE_SIZE = "10" ;
	
	public static final String PATH_ROOT = Environment.getExternalStorageDirectory() + "/fyf/" ;
	public static final String PATH_IMAGE_CACHE_DIR = PATH_ROOT + "image/" ;
	public static final String PATH_DOWNLOAD_DIR= PATH_ROOT + "download/" ;
	public static final String PATH_LOG_DIR= PATH_ROOT + "log/" ;
	
	public static class CLASSIFY_TYPE_ID{
		
		public static final String KITCHEN = "1" ;
		public static final String OTHER = "2" ;
		public static final String RECYCLABLE = "3" ;
		
	}
	
	public static class SHARE_CONTENT{
		public static final String CONTENT = "垃圾分一分，环境美十分" ;
		public static final String APPWEBSITE = "" ;
		public static final String IMAGE = "" ;
		public static final String MEDIA = "" ;
		public static final String VIDEO = "" ;
		public static final String TARGETURL = CommConfig.HOST + "m/index.php?op=member&act=register&inviter=" ;
		public static final String TITLE = "分一分" ;
		
	}
	
}
