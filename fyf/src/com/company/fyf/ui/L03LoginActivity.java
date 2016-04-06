package com.company.fyf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.company.fyf.R;
import com.company.fyf.db.UserDb;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.User;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.utils.Logger;
import com.company.fyf.widget.ClearInputView;
import com.company.fyf.widget.TitleBar;

public class L03LoginActivity extends B01BaseActivity {
	
	private ClearInputView username,psd ;
	private CheckBox rememberName ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l03_layout) ;
		
		View findpsd = findViewById(R.id.findpsd) ;
		findpsd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(L04FindPsdFirstActivity.class) ;
//				finish(false) ;
			}
		}) ;
		
		View login = findViewById(R.id.login) ;
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				doLogin() ;
			}
		}) ;
		
		TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar);
		titleBar.setMenuBtn("免费注册", new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(L01RegisterFirstActivity.class);
				finish(false) ;
			}
		});
		
		rememberName = (CheckBox) findViewById(R.id.rememberName) ;
		username = (ClearInputView) findViewById(R.id.username) ;
		psd = (ClearInputView) findViewById(R.id.psd) ;
		
		User user = UserDb.INSTANCE.getVisiableLast() ;
		if(user != null){
			username.setText(user.getUsername()) ;
			rememberName.setChecked(true) ;
		}
	}

	private void doLogin() {
		// TODO Auto-generated method stub
		
		final String strUserName = username.getText() ;
		final String strPsd = psd.getText() ;
		
		if(!(FyfUtils.doCheckPhonePwd(this, strUserName, strPsd))) return ;
		
		new MemberServer(this).login(strUserName, strPsd, new CallBack<User>() {
			public void onSuccess(User user) {
				super.onSuccess(user);
				Logger.d("L03LoginActivity", "rememberName.isChecked() = " + rememberName.isChecked()) ;
				if(rememberName.isChecked()){
					user.setRememberName(1) ;
					UserDb.INSTANCE.update(user) ;
				}else{
					user.setRememberName(0) ;
					UserDb.INSTANCE.update(user) ;
				}
				
				Bundle param = new Bundle() ;
				param.putInt(M01MainActivity.PARAM_INT_TABINDEX, M01MainActivity.TAB_INDEX_PERSONAL) ;
				showActivity(M01MainActivity.class,param,Intent.FLAG_ACTIVITY_NEW_TASK) ;
				finish(false) ;
			}
		}) ;
	}
}
