package com.company.fyf.widget;

import com.company.fyf.db.MemberSettingDao;
import com.company.fyf.model.MemberSetting;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import android.view.View ;

public class ProtocolTextView extends TextView implements View.OnClickListener{
	
	private final String content = "<font color = '#797979'>点击“注册”按钮，即表示你同意</font><font color = '#4eb907'>&lt;分一分软件许可及服务协议&gt;</font>" ;

	public ProtocolTextView(Context context) {
		super(context);
		init(context,null) ;
	}
	public ProtocolTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs) ;
	}
	
	public ProtocolTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs) ;
	}
	
	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		setText(Html.fromHtml(content)) ;
		setOnClickListener(this) ;
	}
	
	@Override
	public void onClick(View v) {
		
//		String msg = MemberSettingDao.INSTANCE.getRegprotocol() ; 
//		if(TextUtils.isEmpty(msg)){
//			ApptoolServer apptoolServer = new ApptoolServer(getContext()) ;
//			
//			apptoolServer.memberSetting(new CallBack<Void>() {
//				public void onSuccess(Void t) {
//					super.onSuccess(t);
//					String msg = MemberSettingDao.INSTANCE.getRegprotocol() ; 
//					showDlg(msg);
//				}
//			}) ;
//			
//		}else{
//			showDlg(msg);
//		}
		
		//为了实时性 拒绝离线缓存
		ApptoolServer apptoolServer = new ApptoolServer(getContext()) ;
		
		apptoolServer.memberSetting(new CallBack<Void>() {
			public void onSuccess(Void t) {
				super.onSuccess(t);
				String msg = MemberSettingDao.INSTANCE.getRegprotocol() ; 
				showDlg(msg);
			}
		}) ;
	}
	private void showDlg(final String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
		.setTitle("注册协议")
		.setMessage(msg)
		.setNeutralButton("关闭", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss() ;
			}
		}) ;
		builder.create().show() ;
	}
	

}
