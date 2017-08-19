package com.company.fyf.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.UpdateinfoVo;
import com.company.fyf.db.CommPreference;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.utils.FinalUtils;
import com.company.fyf.utils.Logger;
import com.lyx.utils.CommUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;

public class T01SetupIndexActivity extends B01BaseActivity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_t01_layout) ;
		initComponent() ;
		initListenr();
	}

	private void initComponent() {
		// TODO Auto-generated method stub
		setCurrentVersion() ;
		setLogoutBtnVisiable() ;
	}


	private void initListenr() {
		findViewById(R.id.ll_show_personal_detail).setOnClickListener(this) ;
		findViewById(R.id.ll_show_feedback).setOnClickListener(this) ;
		findViewById(R.id.ll_show_app_intro).setOnClickListener(this) ;
		findViewById(R.id.btn_logout).setOnClickListener(this) ;
		findViewById(R.id.ll_check_update).setOnClickListener(this) ;
		findViewById(R.id.ll_tel).setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_show_personal_detail:
			doPersonalDetailAction();
			break;
		case R.id.ll_show_feedback:
			doFeedBackAction();
			break;
		case R.id.ll_show_app_intro:
			showActivity(T05AppIntroActivity.class) ;
			break;
		case R.id.btn_logout:
			doLogoutAction();
			break;
		case R.id.ll_check_update:
			doUpdateAction();
			break;
		case R.id.ll_tel:
			doTelAction();
			break;
		default:
			break;
		}
	}
	
	private void doTelAction() {
        new AlertDialog.Builder(this)
		.setTitle("温馨提示")
		.setMessage("客服专享电话：010-87609497\n（工作日9:00-18:00）")  
		.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss() ;
				 //用intent启动拨打电话  
		        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:01087609497"));  
		        startActivity(intent);  	
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss() ;
			}
		})
		.show() ;
	}
	
	private void setLogoutBtnVisiable() {
		// TODO Auto-generated method stub
		View v1 = findViewById(R.id.btn_logout_line_up) ;
		View v2 = findViewById(R.id.btn_logout) ;
		View v3 = findViewById(R.id.btn_logout_line_down) ;
		if(CommPreference.INSTANCE.getUserInfo() == null){
			v1.setVisibility(View.GONE);
			v2.setVisibility(View.GONE);
			v3.setVisibility(View.GONE);
		}
	}

	private void setCurrentVersion(){
		setCurrentVersion(CommUtil.getVersionName(this));
	}
	
	private void setCurrentVersion(String s){
		TextView version = (TextView) findViewById(R.id.version) ;
		version.setText("当前版本v" + s) ;
	}
	
	private void doUpdateAction() {
		ApptoolServer server = new ApptoolServer(this) ;
		server.checkUpdate(new CallBack<UpdateinfoVo>() {
			@Override
			public void onSuccess(UpdateinfoVo vo) {
				super.onSuccess(vo);
				String cur = CommUtil.getVersionName(T01SetupIndexActivity.this) ;
				
				if(cur.compareTo(vo.getNewversion()) >= 0){
					showWarnDlg("当前已经是最新版本") ;
				}else{
					showUpdateMsgDlg(vo) ;
				}
				
			}
		}) ;
	}

	private void doPersonalDetailAction() {
		UserInfo userInfo = CommPreference.INSTANCE.getUserInfo();
		if(userInfo == null){
			showActivity(L03LoginActivity.class) ;
		}else if("8".equals(userInfo.getGroupid())){//groupid=8是用户  9是分拣员
			showActivity(T02PersonalDetailActivity.class) ;
		}else if("9".equals(userInfo.getGroupid())){
			showActivity(T04SorterDetailActivity.class) ;
		}else{
			throw new RuntimeException("用户角色无效") ;
		}
	}

	private void doFeedBackAction() {
		UserInfo userInfo = CommPreference.INSTANCE.getUserInfo();
		if(userInfo == null)
			showActivity(L03LoginActivity.class) ;
		else
			showActivity(T03FeedBackActivity.class) ;
	}
	
	private void doLogoutAction() {

		CommPreference.INSTANCE.clearUserInfo();
		Logger.d("setUserCookie","doLogoutAction");
		CommPreference.INSTANCE.setUserCookie(null);
		new MemberServer().logout(null) ;
		finish() ;
	}
	
	private void showUpdateMsgDlg(final UpdateinfoVo info){
		new AlertDialog.Builder(this)
		.setTitle("版本更新")
		.setMessage("有新版本,是否更新？")  
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss() ;
				downApk(info) ;
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss() ;
			}
		})
		.show() ;
	}
	
	private void downApk(UpdateinfoVo info){
		showToast("已进入后台下载...") ;
		String newVersion = info.getNewversion() ;
		newVersion = newVersion.replace(".", "_") ;
		String apkName = "fyf_" + newVersion + ".apk" ;
		File file = new File(CommConfig.PATH_DOWNLOAD_DIR + apkName) ;
		if(file.exists()){
			file.delete() ;
		}
		FinalHttp finalHttp = FinalUtils.getHttpHelper() ;
		finalHttp.download(info.getDownloadurl(), file.getAbsolutePath(), new AjaxCallBack<File>() {
			public void onSuccess(File t) {
				super.onSuccess(t);
				openFile(t) ;
			}
		}) ;
	}
	
	private void openFile(File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
        startActivity(intent);
	}
}
