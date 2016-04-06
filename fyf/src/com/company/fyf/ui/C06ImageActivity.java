package com.company.fyf.ui;

import com.company.fyf.R;
import com.lyx.utils.ImageLoaderUtils;
import com.polites.android.GestureImageView;

import android.os.Bundle;
import android.view.View;

public class C06ImageActivity extends B01BaseActivity implements View.OnClickListener{
	
	public static final String PARAM_STRING_URL = "param_string_url";
	
	private String url = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onGetIntentData() ;
		setContentView(R.layout.a_c06_layout);
		GestureImageView imageView = (GestureImageView) findViewById(R.id.imageView);
		ImageLoaderUtils.displayPicWithAutoStretch(url, imageView);
		View frameLayout = findViewById(R.id.frameLayout) ;
		frameLayout.setOnClickListener(this);
		imageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView:
		case R.id.frameLayout:
			finish();
			break;

		default:
			break;
		}
	}

}
