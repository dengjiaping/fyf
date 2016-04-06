package com.company.fyf.ui;

import java.io.File;
import java.lang.reflect.Field;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.company.fyf.R;
import com.company.fyf.dao.UpdateinfoVo;
import com.company.fyf.db.CommPreference;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.utils.FinalUtils;
import com.lyx.utils.CommUtil;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

public class C05LoadingActivity extends B01BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_c05_layout);
		
		//初始化一些网络数据
		//1.更新区域数据
		//2.版本信息
		//3.banner数据
		checkDirExists() ;
		updateBanner() ;
		updateMemberSetting() ;
		checkMustUpdate() ;
		AnalyticsConfig.enableEncrypt(true);
		MobclickAgent.updateOnlineConfig( this );
//		Logger.focus(getClass(), "[app start]") ;
	}
	
	//检查是否强制升级
	private void checkMustUpdate(){
		ApptoolServer server = new ApptoolServer(null) ;
		server.checkUpdate(new CallBack<UpdateinfoVo>() {
			@Override
			public void onSuccess(UpdateinfoVo vo) {
				super.onSuccess(vo);
				String cur = CommUtil.getVersionName(C05LoadingActivity.this) ;
				
				if(cur.compareTo(vo.getNewversion()) < 0){
					showUpdateMsgDlg(vo) ;
				}else{
					skip() ;
				}
				
			}
			
			@Override
			public void onFail() {
				super.onFail();
				skip() ;
			}
			
			@Override
			public void onBadNet() {
				super.onBadNet();
				skip() ;
			}
			
		}) ;
	}
	
	private void showUpdateMsgDlg(final UpdateinfoVo info){
		
		if(info == null){
			skip() ;
			return ;
		}
		String sMust = info.getMustupdate() ;
		if(!"1".equals(sMust)){
			skip() ;
			return ;
		}
		
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
				finish();
			}
		})
		.setCancelable(false)
		.show() ;
	}
	
	private void showDownloadDlg(){
		new AlertDialog.Builder(this)
		.setTitle("温馨提示")
		.setMessage("正在下载...	请稍等")  
		.setCancelable(false)
		.show() ;
	}
	
	public void downApk(UpdateinfoVo info){
		showDownloadDlg() ;
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
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				showDownloadFailDlg() ;
			}
		}) ;
	}
	
	private void showDownloadFailDlg(){
		showNormalDlg2("下载失败", "关闭", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish() ;
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
        finish();
	}
	
	
	//更新用户协议
	private void updateMemberSetting() {
	}

	private void checkDirExists(){
		Field[] fs = CommConfig.class.getFields() ;
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i] ;
			String pathName = f.getName() ;
			if(!pathName.startsWith("PATH_")){
				continue ;
			}
			String path = null;
			try {
				path = (String) f.get(null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} 
			File file = new File(path) ;
			if(!file.exists()){
				file.mkdirs() ;
			}
		}
	}
	
	
	private void updateBanner(){
		
	}
	
	private void skip(){
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(CommPreference.INSTANCE.shouldShowGuide()){
					showActivity(C07GuideActivity.class) ;
				}else{
					showActivity(M01MainActivity.class);
				}
				finish() ;
			}
		}).start(); 
	}
	

}
