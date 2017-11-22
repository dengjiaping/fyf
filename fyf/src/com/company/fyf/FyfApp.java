package com.company.fyf;

import android.app.Application;

import com.company.fyf.db.CommPreference;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.utils.FinalUtils;
import com.lyx.utils.ImageLoaderUtils;
import com.tencent.bugly.Bugly;

public class FyfApp extends Application {
	
	public static FyfApp INSTANCE = null ;


	
	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this ;
		FinalUtils.init(this) ;
		ImageLoaderUtils.init(this, R.drawable.ic_launcher) ;
		CommPreference.INSTANCE.init(this) ;
		Bugly.init(getApplicationContext(), "6751dbc95b", CommConfig.DEBUG);
	}

}
