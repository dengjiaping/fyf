package com.company.fyf.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.AreaVo;
import com.company.fyf.db.AreaDb;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.widget.ClearInputView;
import com.company.fyf.widget.TitleBar;

import java.util.List;

public class L01RegisterFirstActivity extends B01BaseActivity {

	private ClearInputView username, psd, confirmpsd;
	private View nextStep;
	private TextView edit_addr,edit_name ;
	private TextView edit_region,edit_agency ;

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
				final String areaid = edit_agency.getTag().toString();
				final String address =  edit_addr.getText().toString() ;
				final String name =  edit_name.getText().toString() ;
				if(!doCheck(phoneNum,pwd,confirmPwd,name,areaid,address)){
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
							skipToNextActivity(phoneNum, pwd,name,areaid,address);
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

		edit_agency=  (TextView)findViewById(R.id.edit_agency) ;
		edit_agency.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showAgencyDlg() ;
			}
		}) ;
		edit_region=  (TextView)findViewById(R.id.edit_region) ;
		edit_region.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showRegionDlg() ;
			}
		}) ;
		edit_addr= (TextView) findViewById(R.id.edit_addr) ;
		edit_name= (TextView) findViewById(R.id.edit_name) ;

	}
	
	
	private void skipToNextActivity(final String phoneNum,
			final String pwd,String name,String areaid,String address) {
		Bundle param = new Bundle() ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_PHONE, phoneNum) ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_PSD, pwd) ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_AREAID, areaid) ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_ADDRESS, address) ;
		param.putString(L02RegisterSecondActivity.PARAM_STRING_NICKNAME, name) ;
		showActivity(L02RegisterSecondActivity.class,param);
	}

	private boolean doCheck(final String phoneNum, final String pwd,
			String confirmPwd,String name,String areaid,String address) {
		if(!FyfUtils.doCheckPhonePwd(this, phoneNum, pwd)){
			return false ;
		}
		
		if (!pwd.equals(confirmPwd)) {
			showToast("两次密码输入不一致");
			return false ;
		}

		if(TextUtils.isEmpty(name)){
			showToast("请输入姓名");
			return false ;
		}

		if(TextUtils.isEmpty(areaid)){
			showToast("请选择区域和街道办事处");
			return false ;
		}

		if(TextUtils.isEmpty(address)){
			showToast("请输入家庭地址");
			return false ;
		}
		return true ;
	}

	private void showRegionDlg(){

		new ApptoolServer(this).areas(new CallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				super.onSuccess(t);
				List<AreaVo> list = AreaDb.INSTANCE.getRegionList() ;
				showRegionDlg(list) ;
			}
		}) ;
	}

	private void showRegionDlg(final List<AreaVo> list){

		showRadioDlg(list, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss() ;
				edit_region.setText(list.get(which).toString()) ;
				edit_region.setTag(list.get(which).getAreaId()) ;
			}
		}) ;

	}

	private void showAgencyDlg(){

		String rootId = getRegionId();

		if(TextUtils.isEmpty(rootId)){
			showToast("请先选择区域") ;
			return ;
		}

		new ApptoolServer(this).areas(new CallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				super.onSuccess(t);
				String rootId = getRegionId();

				if(TextUtils.isEmpty(rootId)){
					showToast("请先选择区域") ;
					return ;
				}
				List<AreaVo> list = AreaDb.INSTANCE.getAgencyList(rootId) ;
				showAgencyDlg(list) ;
			}
		}) ;
	}

	private void showAgencyDlg(final List<AreaVo> list){
		showRadioDlg(list, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss() ;
				edit_agency.setText(list.get(which).toString()) ;
				edit_agency.setTag(list.get(which).getAreaId()) ;
			}
		}) ;
	}

	private String getRegionId() {
		String rootId = edit_region.getTag() == null ? "" :edit_region.getTag() .toString() ;

		if(TextUtils.isEmpty(rootId)){
			String agencyId = edit_agency.getTag() == null ? "" :edit_agency.getTag() .toString() ;
			rootId = AreaDb.INSTANCE.rootAreaIdById(agencyId) ;
		}
		return rootId;
	}

}
