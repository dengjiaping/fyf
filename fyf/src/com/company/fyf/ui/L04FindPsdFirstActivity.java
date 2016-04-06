package com.company.fyf.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.ClearInputView;
import com.company.fyf.widget.CountDownText;
import com.company.fyf.widget.CountDownText.OnClickListener;

public class L04FindPsdFirstActivity extends B01BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l04_layout) ; 
		
		final ClearInputView username = (ClearInputView) findViewById(R.id.username) ;
		final CountDownText countDownText = (CountDownText) findViewById(R.id.countDownText) ;
		countDownText.setEnabled(false) ;
		username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(!FyfUtils.doCheckPhone(L04FindPsdFirstActivity.this,s.toString(),false)){
					countDownText.setEnabled(false) ;
				}else{
					countDownText.setEnabled(true) ;
				}
			}
		}) ;
		
		View nextStep = findViewById(R.id.nextStep) ;
		nextStep.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				String strUsername = username.getText() ;
				if(!FyfUtils.doCheckPhone(L04FindPsdFirstActivity.this, strUsername)){
					return ;
				}
				
				TextView textView = (TextView) findViewById(R.id.mobile_verify) ;
				
				String mobile_verify = textView.getText().toString() ;
				
				if(FyfUtils.checkInputEmpty(mobile_verify)){
					showToast("请输入验证码");
					return ;
				}
				
				MemberServer memberServer = new MemberServer(L04FindPsdFirstActivity.this) ;
				memberServer.publicForgetPasswordMobile(strUsername, mobile_verify,new CallBack<Void>() {
					@Override
					public void onSuccess(Void t) {
						super.onSuccess(t);
						showActivity(L05FindPsdSecondActivity.class)  ;
					}
				}) ;
			}
		}) ;
		
		
		countDownText.setOnClickListener(new OnClickListener() {
			public void onClick() {
				String strUsername = username.getText() ;
				if(!FyfUtils.doCheckPhone(L04FindPsdFirstActivity.this, strUsername)){
					return ;
				}
				MemberServer memberServer = new MemberServer(L04FindPsdFirstActivity.this) ;
				memberServer.sendCheckCode(strUsername, new CallBack<String>() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						showToast("验证码已发送") ;
					}
				}); 
			}
		});
	}
}
