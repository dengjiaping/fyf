package com.company.fyf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.model.User;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.net.SecureServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.CountDownText;
import com.company.fyf.widget.CountDownText.OnClickListener;
import com.lyx.utils.ImageLoaderUtils;

public class L02RegisterSecondActivity extends B01BaseActivity {
	
	public static final String PARAM_STRING_PHONE = "param_phone" ;
	public static final String PARAM_STRING_PSD = "param_psd" ;
	public static final String PARAM_STRING_AREAID = "param_areaid" ;
	public static final String PARAM_STRING_ADDRESS = "param_address" ;

	private String phone = "" ;
	private String psd = "" ;
	private String areaid = "" ;
	private String address = "" ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l02_layout) ;
		onGetIntentData() ;

		final ImageView secodeImg = (ImageView) findViewById(R.id.seccode_img);
		secodeImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doGetSecode(secodeImg);
			}
		});
		doGetSecode(secodeImg);
		
		final CountDownText countDownText = (CountDownText) findViewById(R.id.countDownText) ;
		countDownText.setOnClickListener(new OnClickListener() {
			public boolean onClick() {

				final EditText editText = (EditText) findViewById(R.id.seccode);
				String seccode = editText.getText().toString() ;

				if(FyfUtils.checkInputEmpty(seccode)){
					showToast("请输入图片验证码");
					return true;
				}

				MemberServer memberServer = new MemberServer(L02RegisterSecondActivity.this) ;
				memberServer.sendCheckCode(phone,seccode, new CallBack<String>() {
					@Override
					public void onSuccess(String t) {
						super.onSuccess(t);
						showToast("验证码已发送") ;
					}

					@Override
					public void onBadNet() {
						super.onBadNet();
						countDownText.reset();
					}

					@Override
					public void onFail() {
						super.onFail();
						countDownText.reset();
					}
				});
				return false;
			}
		});
		
		View register = findViewById(R.id.register) ;
		register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextView textView = (TextView) findViewById(R.id.mobile_verify) ;
				
				String mobile_verify = textView.getText().toString() ;
				
				if(FyfUtils.checkInputEmpty(mobile_verify)){
					showToast("请输入验证码");
					return ;
				}
				
				MemberServer memberServer = new MemberServer(L02RegisterSecondActivity.this) ;
				memberServer.register(phone, psd,areaid,address,mobile_verify, new CallBack<UserInfo>() {
					@Override
					public void onSuccess(UserInfo t) {
						super.onSuccess(t);
						MemberServer memberServer = new MemberServer(L02RegisterSecondActivity.this) ;
						memberServer.login(phone, psd, new CallBack<User>() {
							@Override
							public void onSuccess(User user) {
								// TODO Auto-generated method stub
								super.onSuccess(user);
								
								Bundle param = new Bundle() ;
								param.putInt(M01MainActivity.PARAM_INT_TABINDEX, M01MainActivity.TAB_INDEX_PERSONAL) ;
								param.putInt(M03PersonalFragment.PARAM_INT_FROM, M03PersonalFragment.FROM_REGISTER) ;
								showActivity(M01MainActivity.class,param,Intent.FLAG_ACTIVITY_NEW_TASK) ;
								finish() ;
							}
							@Override
							public void onBadNet() {
								super.onBadNet();
								finish() ;
							}
							@Override
							public void onFail() {
								super.onFail();
								finish() ;
							}
						}) ;
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
