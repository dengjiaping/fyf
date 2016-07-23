package com.company.fyf.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.AreaVo;
import com.company.fyf.db.AreaDb;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.KeyList;
import com.company.fyf.utils.FyfUtils;
import com.company.fyf.utils.Logger;
import com.company.fyf.widget.DatePickerDialog.OnDateSetListener;
import com.company.fyf.widget.TitleBar;

import java.util.Calendar;
import java.util.List;

public class T04SorterDetailActivity extends B01BaseActivity {
	
	private TitleBar titleBar = null ;
	private boolean isEditMode = false ;
	private View ll_area_edit,ll_area_view ;
	private TextView edit_name,edit_sex,edit_age,view_name,view_sex,view_age ;
	private TextView view_agency,view_region,edit_agency,edit_region ;
	private View btn_finish ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_t04_layout)  ;
		
		titleBar = (TitleBar) findViewById(R.id.titlebar) ;
		ll_area_edit = findViewById(R.id.ll_area_edit) ;
		ll_area_view = findViewById(R.id.ll_area_view) ;
		edit_name = (TextView) findViewById(R.id.edit_name) ;
		edit_sex =  (TextView)findViewById(R.id.edit_sex) ;
		edit_sex.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showSexSelDlg() ;
			}
		});
		edit_age=  (TextView)findViewById(R.id.edit_age) ;
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
		edit_age.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showTimeSelDlg() ;
			}
		});
		view_name=  (TextView)findViewById(R.id.view_name) ;
		view_sex=  (TextView)findViewById(R.id.view_sex) ;
		view_age=  (TextView)findViewById(R.id.view_age) ;
		view_agency=  (TextView)findViewById(R.id.view_agency) ;
		view_region=  (TextView)findViewById(R.id.view_region) ;
		btn_finish=  findViewById(R.id.btn_finish) ;
		btn_finish.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				doFinish() ;
			}
		}) ;
		initData() ;
		initMode() ;
		registerNotity(KeyList.KEY_USER_INFO_UPDATE) ;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterNotity(KeyList.KEY_USER_INFO_UPDATE) ;
	}
	
	@Override
	public <T> void onRefresh(IMsg<T> msg) {
		super.onRefresh(msg);
		if(KeyList.KEY_USER_INFO_UPDATE.equals(msg.getKey())){
			 initData() ;
		}
	}
	
	private void doFinish() {
		// TODO Auto-generated method stub
		new MemberServer(this).accountProfileEdit(FyfUtils.sexToId(edit_sex.getText().toString()), edit_age.getTag().toString(), edit_agency.getTag().toString(), "", edit_name.getText().toString(),new CallBack<UserInfo>() {
			public void onSuccess(UserInfo t) {
				super.onSuccess(t);
				outEdit() ;
			}
		}) ;
	}

	private void initData() {
		UserInfo info = UserInfoDb.INSTANCE.get() ;
		if(info == null){
			return ;
		}
		//edit_name,edit_sex,,,,,, ;
		edit_name.setText( info.getNickname()) ;
		edit_sex.setText(FyfUtils.sexFromId(info.getSex()));
		edit_age.setText(FyfUtils.ageFromBirthday(info.getBirthday()));
		edit_age.setTag(info.getBirthday()) ;
		edit_region.setText(info.getFjy_quyu()) ;
		edit_agency.setText(info.getFjy_banshichu()) ;
		edit_agency.setTag(info.getAreaid()) ;
		view_name.setText("姓名：" + info.getNickname());
		view_sex.setText("性别："+FyfUtils.sexFromId(info.getSex()));
		view_age.setText("年龄：" + FyfUtils.ageFromBirthday(info.getBirthday()));
		view_region.setText("区域：" + info.getFjy_quyu());
		view_agency.setText("街道办事处：" + info.getFjy_banshichu());
	}

	private void initMode() {
		
		if(isEditMode){
			titleBar.setMenuBtn("取消", new View.OnClickListener() {
				public void onClick(View v) {
					outEdit() ;
				}
			}) ;
			ll_area_edit.setVisibility(View.VISIBLE) ;
			ll_area_view.setVisibility(View.GONE) ;
		}else{
			titleBar.setMenuBtn("编辑", new View.OnClickListener() {
				public void onClick(View v) {
					enterEdit() ;
				}
			}) ;
			ll_area_view.setVisibility(View.VISIBLE) ;
			ll_area_edit.setVisibility(View.GONE) ;
		}
	}
	
	private void enterEdit(){
		isEditMode = true ;
		initMode() ;
		edit_name.requestFocus() ;
	}
	
	private void outEdit(){
		isEditMode = false ;
		hideSoftInput() ;
		initMode() ;
	}
	
	private final void showTimeSelDlg(){
		showTimeSelDlg(new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Calendar c =  Calendar.getInstance();
				c.set(year, monthOfYear, dayOfMonth) ;
				edit_age.setText(FyfUtils.ageFromBirthday(String.valueOf(c.getTimeInMillis())));
				edit_age.setTag(c.getTimeInMillis()) ;
				Logger.d("T02PersonalDetailActivity", "time = " + c.getTimeInMillis()) ;
			}
		}) ;
	}
	
	private void showSexSelDlg(){
		final String array[] = {"男","女"} ;
		showRadioDlg(array, 0, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss() ;
				edit_sex.setText(array[which]) ;
			}
		}) ;
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
		
		showRadioDlg(list, 0, new OnClickListener() {
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
		showRadioDlg(list, 0, new OnClickListener() {
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
