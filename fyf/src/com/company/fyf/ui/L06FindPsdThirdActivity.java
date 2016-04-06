package com.company.fyf.ui;

import android.os.Bundle;
import android.view.View;

import com.company.fyf.R;

public class L06FindPsdThirdActivity extends B01BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_l06_layout) ; 
		
		findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(L03LoginActivity.class) ;
				finish(false) ;
			}
		}) ;
	}
}
