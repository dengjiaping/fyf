package com.company.fyf.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.SweepVo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.CreditServer;
import com.company.fyf.utils.FyfUtils;

public class P04PointsManagerActivity extends B01BaseActivity implements View.OnClickListener{
	
	public static final String PARAM_SERIALIZABLE_PERSONERINFO = "param_serializable_personer_info";
	public static final String PARAM_INT_POINT1 = "param_int_point_1";
	public static final String PARAM_INT_POINT2 = "param_int_point_2";
	public static final String PARAM_INT_POINT3 = "param_int_point_3";
	public static final String RETURN_EXCHANGE = "return_exchange";
	
	public static final int REQUESTCODE_EXCHANGE = 101;
	
	private SweepVo personerinfo = null;
	
	private TextView name,phone,credit ;
	private TextView jfffPoint1,jfffPoint2,jfffPoint3 ;
	private View llexchange ;
	
	private int point1 = 1 ;
	private int point2 = 2 ;
	private int point3 = 3 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onGetIntentData() ;
		setContentView(R.layout.a_p04_layout);
		initComponent() ;
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		name = (TextView) findViewById(R.id.name) ;
		name.setText(personerinfo.getNickname()) ;
		phone = (TextView) findViewById(R.id.phone) ;
		phone.setText(personerinfo.getEncryptUsername()) ;
		credit = (TextView) findViewById(R.id.credit) ;
		credit.setText(personerinfo.getCredit()) ;
		jfffPoint1 = (TextView) findViewById(R.id.jfff_point_1) ;
		jfffPoint1.setText(String.valueOf(point1)) ;
		jfffPoint1.setOnClickListener(this) ;
		jfffPoint2 = (TextView) findViewById(R.id.jfff_point_2) ;
		jfffPoint2.setText(String.valueOf(point2)) ;
		jfffPoint2.setOnClickListener(this) ;
		jfffPoint3 = (TextView) findViewById(R.id.jfff_point_3) ;
		jfffPoint3.setText(String.valueOf(point3)) ;
		jfffPoint3.setOnClickListener(this) ;
		llexchange = findViewById(R.id.ll_exchange) ;
		llexchange.setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.jfff_point_1:
			doJfff(point1) ;
			break;
		case R.id.jfff_point_2:
			doJfff(point2) ;
			break;
		case R.id.jfff_point_3:
			doJfff(point3) ;
			break;
		case R.id.ll_exchange:
			doExchange() ;
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(RESULT_OK == resultCode
				&& requestCode == REQUESTCODE_EXCHANGE){
			int i = Integer.parseInt(data.getStringExtra(RETURN_EXCHANGE)) ;
			int credit = Integer.parseInt(this.credit.getText().toString()) ;
			this.credit.setText(credit - i + "") ;
		}
	}
	
	private void doExchange() {
		
		Intent intent = new Intent(this,P05ExchangeListActivity.class) ;
		intent.putExtra(P05ExchangeListActivity.PARAM_SERIALIZABLE_PERSONERINFO, personerinfo) ;
		startActivityForResult(intent, REQUESTCODE_EXCHANGE) ;
	}

	private void doJfff(final int point){
		
		showNormalDlg("您确定为" + FyfUtils.encryptPhone(personerinfo.getUsername()) + "增加" + point + "积分吗?", "确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				CreditServer server = new CreditServer(P04PointsManagerActivity.this) ;
				server.creditAddByUsername(String.valueOf(point), personerinfo.getUsername(), getCreditAddReason(point), "saoyisao",new CallBack<String>() {
					@Override
					public void onSuccess(String text) {
						super.onSuccess(text);
						if(point == point1){
							jfffPoint1.setSelected(true) ;
						}else if(point == point2){
							jfffPoint2.setSelected(true) ;
						}else if(point == point3){
							jfffPoint3.setSelected(true) ;
						}
						jfffPoint1.setEnabled(false) ;
						jfffPoint2.setEnabled(false) ;
						jfffPoint3.setEnabled(false) ;
						credit.setText(text) ;
					}
				}) ;
			}
		}) ;
	}
	
	private String getCreditAddReason(int point){
		if(point == point1){
			return "分类得积分（劣）";
		}else if(point == point2){
			return "分类得积分（中）";
		}else if(point == point3){
			return "分类得积分（优）";
		}
		return "";
	}

}
