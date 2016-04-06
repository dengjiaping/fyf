package com.company.fyf;

import android.app.Application;

import com.company.fyf.db.CommPreference;
import com.company.fyf.utils.FinalUtils;
import com.lyx.utils.ImageLoaderUtils;

public class FyfApp extends Application {
	
	public static FyfApp INSTANCE = null ;
	
	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this ;
		FinalUtils.init(this) ;
		ImageLoaderUtils.init(this, R.drawable.ic_launcher) ;
		CommPreference.INSTANCE.init(this) ;
	}

}
