package com.company.fyf.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.RubbishVo;
import com.company.fyf.utils.DateFormatUtils;
import com.lyx.utils.ImageLoaderUtils;

public class N02ClassificationDetailActivity extends B01BaseActivity {
	
	public static final String PARAM_SERIALIZABLE_RUBBISH = "param_serializable_rubbish";
	
	private RubbishVo rubbish ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_n02_layout);
		onGetIntentData();
		
		
		ImageView pic_url = (ImageView) findViewById(R.id.pic_url) ;
		ImageLoaderUtils.displayPicWithAutoStretch(rubbish.getPic_url(), pic_url) ;
		
		TextView name = (TextView) findViewById(R.id.name) ;
		name.setText(rubbish.getName());
		
		TextView time = (TextView) findViewById(R.id.time) ;
		name.setText(rubbish.getName());
		
		TextView note = (TextView) findViewById(R.id.note) ;
		note.setText(rubbish.getNote());
		
		if(!TextUtils.isEmpty(rubbish.getUpdatetime()) && !"0".equals(rubbish.getUpdatetime())){
			time.setText(DateFormatUtils.format02(rubbish.getJavaUpdatetime()));
		}else if(!TextUtils.isEmpty(rubbish.getAddtime()) && !"0".equals(rubbish.getAddtime())){
			time.setText(DateFormatUtils.format02(rubbish.getJavaAddtime()));
		}
		
	}

}
