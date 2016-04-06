package com.company.fyf.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.MsgVo;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.utils.DateFormatUtils;

public class S02MsgDetailActivity extends B01BaseActivity {
	
	public static final String PARAM_SERIALIZABLE_MSG = "param_serializable_msg" ;
	
	private MsgVo msg = null ;
	private ApptoolServer apptoolServer = null ;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_s02_layout) ;
		onGetIntentData() ;
		final TextView title = (TextView) findViewById(R.id.title) ;
		final TextView updatetime = (TextView) findViewById(R.id.updatetime) ;
		final TextView description = (TextView) findViewById(R.id.description) ;
		title.setText(msg.getTitle());
		updatetime.setText(DateFormatUtils.format01(msg.getJavaUpdatetime()));
		description.setMovementMethod(ScrollingMovementMethod.getInstance());
		description.setText(msg.getDescription());
		apptoolServer = new ApptoolServer(this) ;
		apptoolServer.announceView(msg.getId(), new CallBack<MsgVo>() {
			@Override
			public void onSuccess(final MsgVo msg) {
				super.onSuccess(msg);
				description.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						title.setText(msg.getTitle());
						updatetime.setText(DateFormatUtils.format01(msg.getJavaUpdatetime()));
						description.setMovementMethod(ScrollingMovementMethod.getInstance());
						description.setText(Html.fromHtml(msg.getContent()));
						
					}
				}) ;
			
			}
		}) ;
		
	}

}
