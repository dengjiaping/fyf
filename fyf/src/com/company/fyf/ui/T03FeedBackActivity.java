package com.company.fyf.ui;

import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.FeedbackServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.CountEditText;
import com.company.fyf.widget.TitleBar;

public class T03FeedBackActivity extends B01BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_t03_layout)  ;
		
		final CountEditText editText = (CountEditText) findViewById(R.id.editText) ;
		TextView textView = (TextView) findViewById(R.id.textView) ;
		editText.setCountView(textView, 200) ;
		
		TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar) ;
		titleBar.setMenuBtn("提交", new View.OnClickListener() {
			public void onClick(View arg0) {
				doFeedBackAdd(editText) ;
			}
		});
	}

	private void doFeedBackAdd(CountEditText editText) {
		// TODO Auto-generated method stub
		String content = editText.getText().toString() ;
		if(FyfUtils.checkInputEmpty(content)){
			showToast("请输入您的宝贵意见");
			return ;
		}
		new FeedbackServer(this).add(content, new CallBack<Void>() {
				@Override
				public void onSuccess(Void t) {
					super.onSuccess(t);
					showToast("提交成功");
					finish();
				}
		});
	}

}
