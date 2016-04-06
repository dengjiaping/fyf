package com.company.fyf.ui;

import android.os.Bundle;
import android.view.View;

import com.company.fyf.R;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.ClearInputView;

public class L05FindPsdSecondActivity extends B01BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l05_layout) ; 
		
		final ClearInputView psd = (ClearInputView) findViewById(R.id.psd);
		final ClearInputView confirmpsd = (ClearInputView) findViewById(R.id.confirmpsd);
		
		View finish = findViewById(R.id.finish) ;
		finish.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				String strPwd = psd.getText() ;
				String strConfirmPwd = confirmpsd.getText() ;
				if(!FyfUtils.doCheckPwd(L05FindPsdSecondActivity.this,strPwd)){
					return ;
				}
				if (!strPwd.equals(strConfirmPwd)) {
					showToast("两次密码输入不一致");
					return ;
				}
				new MemberServer(L05FindPsdSecondActivity.this).publicForgetPasswordMobile2(strPwd, new CallBack<Void>() {
					@Override
					public void onSuccess(Void t) {
						super.onSuccess(t);
						showActivity(L06FindPsdThirdActivity.class) ;
						finish(false) ;
					}
				}) ;
			}
		}) ;
				
		
	}
}
