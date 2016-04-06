package com.company.fyf.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.db.CommPreference;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CreditServer;
import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.KeyList;

public class P06PointsRuleActivity extends B01BaseActivity{
	
	private TextView textView; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_p06_layout);
		textView = (TextView) findViewById(R.id.textView) ;
		String content = CommPreference.INSTANCE.getPointsRule() ;
		if(TextUtils.isEmpty(content)){
			new CreditServer(this).publicCreditRule() ;
		}else{
			new CreditServer(this).publicCreditRule() ;
			initTextView(content) ;
		}
		registerNotity(KeyList.KEY_POINTS_RULE_UPDATE) ;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterNotity(KeyList.KEY_POINTS_RULE_UPDATE) ;
	}
	
	@Override
	public <T> void onRefresh(IMsg<T> msg) {
		super.onRefresh(msg);
		
		initTextView( CommPreference.INSTANCE.getPointsRule()) ;
	}

	private void initTextView(String content) {
		// TODO Auto-generated method stub
		textView.setText(Html.fromHtml(content)) ;
	}

	

}
