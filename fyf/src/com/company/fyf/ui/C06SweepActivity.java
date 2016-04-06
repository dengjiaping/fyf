package com.company.fyf.ui;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.company.fyf.dao.SweepVo;
import com.company.fyf.db.MemberSettingDao;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.CreditServer;
import com.company.fyf.net.MemberServer;
import com.company.fyf.utils.SweepHelper;
import com.umeng.analytics.MobclickAgent;
import com.zxing.ui.CaptureActivity;

public class C06SweepActivity extends CaptureActivity {

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onDecode(String data) {
		super.onDecode(data);
		if (SweepHelper.getType(data) == SweepHelper.TYPE.GET_USER_INFO) {
			UserInfo info = UserInfoDb.INSTANCE.get();
			if (info == null) {
				doErrorAction( "您还没有登录哦！") ;
				return;
			}
			if (!"9".equals(info.getGroupid())) {
				doErrorAction( "您目前不能使用此功能") ;
				return;
			}
			doGetUserInfoAction(data);
		}else if (SweepHelper.getType(data) == SweepHelper.TYPE.USER_ADD_CREDIT_BY_SELF) {
			UserInfo info = UserInfoDb.INSTANCE.get();
			if (info == null) {
				doErrorAction( "您还没有登录哦！") ;
				return;
			}
			if (!"8".equals(info.getGroupid())) {
				doErrorAction( "您目前不能使用此功能") ;
				return;
			}
			doUserAddCreditBySelf(data);
		}else{
			doErrorAction( "此二维码无法识别") ;
		}
	}

	private void doUserAddCreditBySelf(String data) {
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject(data) ;
			final int credit = obj.getInt("data") ;
			if(credit <=0 || credit > 3){
				doErrorAction( "此二维码无法识别") ;
				return ;
			}
			
			new AlertDialog.Builder(this).setTitle("提示").setMessage("是否为自己增加 " +credit+ " 积分")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						doUserAddCreditBySelf(credit) ;
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int arg1) {
						dlg.dismiss();
						restartPreviewAndDecode() ;
					}
				}).show();
		} catch (Exception e) {
			e.printStackTrace();
			doErrorAction( "数据异常，请重试") ;
		}
	}

	private void doUserAddCreditBySelf(int credit) {
		CreditServer creditServer = new CreditServer(C06SweepActivity.this) ;
		creditServer.setDlgCancelBtnOnClickListener(dlgCancelBtnOnClickListener) ;
		creditServer.addCreditBySelf(String.valueOf(credit), new CallBack<String>() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				new AlertDialog.Builder(C06SweepActivity.this)
								.setTitle("温馨提示")
								.setMessage("增加" + t + "积分") 
								.setNegativeButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										finish() ;
									}
								}) 
								.show() ;
			}
		}) ;
	}

	private void doGetUserInfoAction(String data) {

		try {
			JSONObject jsonObj = new JSONObject(data);
			String name = jsonObj.getString("data");
			MemberServer memberServer = new MemberServer(this);
			memberServer.setDlgCancelBtnOnClickListener(dlgCancelBtnOnClickListener) ;
			memberServer.publicProfile(name, new CallBack<SweepVo>() {
				@Override
				public void onSuccess(final SweepVo sweepVo) {
					super.onSuccess(sweepVo);
					UserInfo info = UserInfoDb.INSTANCE.get();
					if (sweepVo != null && info != null
							&& "9".equals(info.getGroupid())) {
						ApptoolServer apptoolServer = new ApptoolServer(C06SweepActivity.this) ;
						apptoolServer.setDlgCancelBtnOnClickListener(dlgCancelBtnOnClickListener) ;
						apptoolServer.memberSetting(new CallBack<Void>() {
							public void onSuccess(Void t) {
								super.onSuccess(t);
								String text = MemberSettingDao.INSTANCE.getScanCredits() ;
								if(!TextUtils.isEmpty(text)){
									String[] ss = text.split("[||]") ;
									int point_1 = Integer.parseInt(ss[0]) ;
									int point_2 = Integer.parseInt(ss[1]) ;
									int point_3 = Integer.parseInt(ss[2]) ;
									showManagerAcitivty(sweepVo, point_1, point_2, point_3) ;
								}else{
									doErrorAction( "数据异常，请重试") ;
								}
							}
						}) ;
//						showManagerAcitivty(t);
					}else{
						doErrorAction( "数据异常，请重试") ;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			doErrorAction( "数据异常，请重试") ;
		}
	}
	
	private void showManagerAcitivty(SweepVo t,int point_1,int point_2,int point_3) {
		Intent intent = new Intent(C06SweepActivity.this,P04PointsManagerActivity.class);
		intent.putExtra(P04PointsManagerActivity.PARAM_SERIALIZABLE_PERSONERINFO,t);
		intent.putExtra(P04PointsManagerActivity.PARAM_INT_POINT1,point_1);
		intent.putExtra(P04PointsManagerActivity.PARAM_INT_POINT2,point_2);
		intent.putExtra(P04PointsManagerActivity.PARAM_INT_POINT3,point_3);
		startActivity(intent);
	}
	
	private void doErrorAction(String errorMsg){
		Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
		restartPreviewAndDecode() ;
	}
	
	private View.OnClickListener dlgCancelBtnOnClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			restartPreviewAndDecode() ;
		}
	};

}
