package com.company.fyf.ui;

import android.os.Bundle;
import android.view.View;

import com.company.fyf.R;
import com.company.fyf.dao.SweepVo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.ClearInputView;
import com.company.fyf.widget.TitleBar;

public class L01RegisterFirstActivity extends B01BaseActivity {

	private ClearInputView username, psd, confirmpsd;
	private View nextStep;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l01_layout);

		username = (ClearInputView) findViewById(R.id.username);
		psd = (ClearInputView) findViewById(R.id.psd);
		confirmpsd = (ClearInputView) findViewById(R.id.confirmpsd);
		nextStep = findViewById(R.id.nextStep);
		nextStep.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				final String phoneNum = username.getText() ;
				final String pwd = psd.getText() ;
				String confirmPwd = confirmpsd.getText() ;
				if(!doCheck(phoneNum,pwd,confirmPwd)){
					return ;
				}
				MemberServer memberServer = new MemberServer(L01RegisterFirstActivity.this) ;
				memberServer.publicCheckUsername(phoneNum, new CallBack<Integer>() {
					@Override
					public void onSuccess(Integer t) {
						super.onSuccess(t);
						if(t == 1){
							showToast("该手机号已经存在") ;
						}else{
							skipToNextActivity(phoneNum, pwd);
						}
					}
				}) ;
			}
		});

		TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar);
		titleBar.setMenuBtn("登录", new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(L03LoginActivity.class);
				finish(false) ;
			}
		});

	}
	
	
	private void skipToNextActivity(final String phoneNum,
			final String pwd) {
		Bundle param = new Bundle() ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_PHONE, phoneNum) ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_PSD, pwd) ;
		showActivity(L02RegisterSecondActivity.class,param);
	}

	private boolean doCheck(final String phoneNum, final String pwd,
			String confirmPwd) {
		if(!FyfUtils.doCheckPhonePwd(this, phoneNum, pwd)){
			return false ;
		}
		
		if (!pwd.equals(confirmPwd)) {
			showToast("两次密码输入不一致");
			return false ;
		}
		return true ;
	}

}
