package com.company.fyf.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.db.CommPreference;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.KeyList;

public class T05AppIntroActivity extends B01BaseActivity {
	
	private TextView textView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_t05_layout) ;
		textView = (TextView) findViewById(R.id.textView) ;
		String content = CommPreference.INSTANCE.getAboutUsContent() ;
		
		if(TextUtils.isEmpty(content)){
			new ApptoolServer(this).aboutus() ;
		}else{
			new ApptoolServer(this).aboutus() ;
			initTextView(content) ;
		}
		registerNotity(KeyList.KEY_ABOUT_US_UPDATE) ;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterNotity(KeyList.KEY_ABOUT_US_UPDATE) ;
	}
	
	@Override
	public <T> void onRefresh(IMsg<T> msg) {
		super.onRefresh(msg);
		
		initTextView( CommPreference.INSTANCE.getAboutUsContent()) ;
	}

	private void initTextView(String content) {
		// TODO Auto-generated method stub
		textView.setText(Html.fromHtml(content)) ;
	}

}
