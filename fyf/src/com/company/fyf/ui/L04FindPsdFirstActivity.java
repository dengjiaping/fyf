package com.company.fyf.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.net.SecureServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.ClearInputView;
import com.company.fyf.widget.CountDownText;
import com.company.fyf.widget.CountDownText.OnClickListener;
import com.lyx.utils.ImageLoaderUtils;

public class L04FindPsdFirstActivity extends B01BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l04_layout) ;

		final ImageView secodeImg = (ImageView) findViewById(R.id.seccode_img);
		secodeImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doGetSecode(secodeImg);
			}
		});
		doGetSecode(secodeImg);
		
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
				final EditText editText = (EditText) findViewById(R.id.seccode);
				String seccode = editText.getText().toString() ;

				if(FyfUtils.checkInputEmpty(seccode)){
					showToast("请输入图片验证码");
					return ;
				}

				MemberServer memberServer = new MemberServer(L04FindPsdFirstActivity.this) ;
				memberServer.sendCheckCode(strUsername, seccode,new CallBack<String>() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						showToast("验证码已发送") ;
					}
				}); 
			}
		});
	}

	private void doGetSecode(final ImageView imageView){
		new SecureServer(this).seccode(new CallBack<String>() {
			@Override
			public void onBadNet() {
				super.onBadNet();
			}

			@Override
			public void onFail() {
				super.onFail();
			}

			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
				ImageLoaderUtils.displayPicWithAutoStretch(s, imageView);
			}
		});
	}
}
