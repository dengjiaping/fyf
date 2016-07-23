package com.company.fyf.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.model.UserInfo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.CheckinServer;
import com.company.fyf.net.DakaServer;
import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.KeyList;
import com.company.fyf.utils.CommConfig.SHARE_CONTENT;
import com.company.fyf.utils.Logger;
import com.company.fyf.widget.TitleBar;
import com.lyx.utils.CalendarUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class M03PersonalFragment extends B02BaseFragment implements
		View.OnClickListener, M01MainActivity.OnIntentListener {

	public static final String PARAM_INT_FROM = "param_int_from";

	public static final int FROM_DEFAULT = 0;
	public static final int FROM_REGISTER = FROM_DEFAULT + 1;
	public static final int FROM_LOGIN = FROM_REGISTER + 1;

	private int from = FROM_DEFAULT;

	private View root = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (root == null) {
			root = inflater.inflate(R.layout.f_m03_layout, container, false);
			initComponent();
		} else {
			ViewGroup group = (ViewGroup) root.getRootView();
			group.removeAllViews();
		}
		return root;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerNotity(KeyList.KEY_USER_INFO_UPDATE);
		((M01MainActivity) getActivity()).registerOnIntentListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Logger.d("M03PersonalFragment", "[M03PersonalFragment onResume]")  ;
		initUserView();
		initShareComponent() ;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unRegisterNotity(KeyList.KEY_USER_INFO_UPDATE);
	}

	private void initComponent() {
		root.findViewById(R.id.btnLogin).setOnClickListener(this);
		root.findViewById(R.id.noinfoMypoints).setOnClickListener(this);
		root.findViewById(R.id.noinfoMyQRCode).setOnClickListener(this);
		root.findViewById(R.id.llShare).setOnClickListener(this);
		root.findViewById(R.id.userLlShare).setOnClickListener(this);
		root.findViewById(R.id.userMypoints).setOnClickListener(this);
		root.findViewById(R.id.btnGoToWork).setOnClickListener(this);
		root.findViewById(R.id.btnGoOffWork).setOnClickListener(this);
		root.findViewById(R.id.showClassify).setOnClickListener(this);
		root.findViewById(R.id.sweep).setOnClickListener(this);
		root.findViewById(R.id.userSign).setOnClickListener(this);
		root.findViewById(R.id.userMyQRCode).setOnClickListener(this);

		initUserView();
		initFrom();

		TitleBar titlebar = (TitleBar) root.findViewById(R.id.titlebar);
		titlebar.setMenuBtn(R.drawable.ic_comm_setup,
				new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								T01SetupIndexActivity.class);
						startActivity(intent);
					}
				});
		titlebar.replaceBackBtn(R.drawable.ic_comm_msg,
				new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								S01MsgListActivity.class);
						startActivity(intent);
					}
				});
		titlebar.setTitle("我的");
		initShareComponent();
	}

	void initFrom() {
		// TODO Auto-generated method stub
		from = getActivity().getIntent().getIntExtra(PARAM_INT_FROM,
				FROM_DEFAULT);
		if (from == FROM_REGISTER) {
			UserInfo userInfo = UserInfoDb.INSTANCE.get();
			if(userInfo != null && "8".equals(userInfo.getGroupid())){
				showFromLoginDlg();
			}
		}else if (from == FROM_LOGIN) {
			UserInfo userInfo = UserInfoDb.INSTANCE.get();
			if(userInfo != null && "8".equals(userInfo.getGroupid())){
				shouldCompleteAddress();
			}
		}
	}

	void shouldCompleteAddress(){
		UserInfo userInfo = UserInfoDb.INSTANCE.get() ;
		if(userInfo == null){
			return;
		}
		String areaId = userInfo.getAreaid() ;
		if(TextUtils.isEmpty(areaId) || "0".equals(areaId)){
			showNormalDlg("是否完善地址信息？", "确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Bundle bundle = new Bundle() ;
							bundle.putString(T02PersonalDetailActivity.PARAM_STRING_FROM,
									T02PersonalDetailActivity.FROM_FINISH_DETAIL_RIGHT_NOW) ;
							showActivity(T02PersonalDetailActivity.class,bundle);
							dialog.dismiss();
						}
					});
		}
	}

	private void showFromLoginDlg() {
		showNormalDlg("恭喜您，注册成功！", "立即完善资料",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Bundle bundle = new Bundle() ;
						bundle.putString(T02PersonalDetailActivity.PARAM_STRING_FROM, 
								T02PersonalDetailActivity.FROM_FINISH_DETAIL_RIGHT_NOW) ;
						showActivity(T02PersonalDetailActivity.class,bundle);
						dialog.dismiss();
					}
				});
	}

	private void initUserView() {

		if (root == null)
			return;

		UserInfo userInfo = UserInfoDb.INSTANCE.get();

		if (userInfo == null) {
			root.findViewById(R.id.ll_noinfo_area).setVisibility(View.VISIBLE);
			root.findViewById(R.id.ll_user_area).setVisibility(View.GONE);
			root.findViewById(R.id.ll_worker_area).setVisibility(View.GONE);
		} else if ("8".equals(userInfo.getGroupid())) {// groupid=8是用户 9是分拣员
			root.findViewById(R.id.ll_user_area).setVisibility(View.VISIBLE);
			root.findViewById(R.id.ll_noinfo_area).setVisibility(View.GONE);
			root.findViewById(R.id.ll_worker_area).setVisibility(View.GONE);
			TextView userName = (TextView) root.findViewById(R.id.username);
			TextView userPhone = (TextView) root.findViewById(R.id.userPhone);
			TextView userSign = (TextView) root.findViewById(R.id.userSign);
			TextView userSignedDays = (TextView) root
					.findViewById(R.id.userSignedDays);
			TextView userPoints = (TextView) root.findViewById(R.id.userPoints);
			TextView userRank = (TextView) root.findViewById(R.id.userRank);

			userName.setText(userInfo.getNickname());
			userPhone.setText(userInfo.getEncryptUsername());
			initIsSign(userSign, userInfo.getJavaCheckin_lasted_time());
			userSignedDays.setText("已签到" + userInfo.getCheckin_month_total()
					+ "天");
			userPoints.setText(userInfo.getCredit());
			userRank.setText(userInfo.getCredit_rank());

		} else if ("9".equals(userInfo.getGroupid())) {
			root.findViewById(R.id.ll_worker_area).setVisibility(View.VISIBLE);
			root.findViewById(R.id.ll_noinfo_area).setVisibility(View.GONE);
			root.findViewById(R.id.ll_user_area).setVisibility(View.GONE);

			TextView workerUsername = (TextView) root
					.findViewById(R.id.worker_username);
			TextView workerAddr = (TextView) root
					.findViewById(R.id.worker_addr);
			TextView btnGoToWork = (TextView) root
					.findViewById(R.id.btnGoToWork);
			initIsWorkDaka(btnGoToWork, userInfo.getJavaDaka_lasted_work());
			TextView btnGoOffWork = (TextView) root
					.findViewById(R.id.btnGoOffWork);
			initIsWorkOffDaka(btnGoOffWork,
					userInfo.getJavaDaka_lasted_workoff());

			workerUsername.setText(userInfo.getNickname());
			workerAddr.setText(userInfo.getFjy_quyu() + "\n"
					+ userInfo.getFjy_banshichu());

		} else {
			throw new RuntimeException("用户角色无效");
		}
	}

	// check是否已经签到
	private void initIsSign(TextView userSign, String lastCheckInTime) {
		// TODO Auto-generated method stub
		if (CalendarUtil.isToday(lastCheckInTime)) {
			userSign.setText("今日已签到");
			userSign.setEnabled(false);
		} else {
			userSign.setText("签到");
			userSign.setEnabled(true);
		}
	}

	// check是否已经上班打卡
	private void initIsWorkDaka(TextView textView, String lastWorkInTime) {
		// TODO Auto-generated method stub
		if (CalendarUtil.isToday(lastWorkInTime)) {
			textView.setText("上班卡已打");
			textView.setEnabled(false);
		} else {
			textView.setText("上班打卡");
			textView.setEnabled(true);
		}
	}

	// check是否已经下班打卡
	private void initIsWorkOffDaka(TextView textView, String lastWorkoffTime) {
		// TODO Auto-generated method stub
		if (CalendarUtil.isToday(lastWorkoffTime)) {
			textView.setText("下班卡已打");
			textView.setEnabled(false);
		} else {
			textView.setText("下班打卡");
			textView.setEnabled(true);
		}
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.btnLogin:
		case R.id.noinfoMypoints:
		case R.id.noinfoMyQRCode:
		case R.id.llShare:
			showActivity(L03LoginActivity.class);
			break;
		case R.id.userLlShare:
			doShareAction();
			break;
		case R.id.userMypoints:
			showActivity(P02MyPointsIndexActivity.class);
			break;
		case R.id.btnGoToWork:
			doGotoWork();
			break;
		case R.id.btnGoOffWork:
			doGoOffWork();
			break;
		case R.id.showClassify:
			showClassify();
			break;
		case R.id.sweep:
			sweep();
			break;
		case R.id.userSign:
			userSign();
			break;
		case R.id.userMyQRCode:
			showActivity(C08QRCodeActivity.class);
			break;
		default:
			break;
		}

	}

	private void userSign() {
		// TODO Auto-generated method stub
		CheckinServer checkinServer = new CheckinServer(getActivity());
		checkinServer.checkin(null, null);
	}

	private void sweep() {
		// TODO Auto-generated method stub
		showActivity(C06SweepActivity.class);
	}

	private void showClassify() {
		// TODO Auto-generated method stub
		showActivity(N01ClassificationListActivity.class);
	}

	private void doGoOffWork() {
		UserInfo userInfo = UserInfoDb.INSTANCE.get();
		if(!CalendarUtil.isToday(userInfo.getJavaDaka_lasted_work())){
			showToast("你还没有打上班卡哦！") ;
			return ;
		}
		new DakaServer(getActivity()).add("2", new CallBack<Void>() {
			public void onSuccess(Void t) {
				super.onSuccess(t);
				showToast("下班卡打卡成功") ;
			}
		});
	}

	private void doGotoWork() {
		new DakaServer(getActivity()).add("1", new CallBack<Void>() {
			@Override
			public void onSuccess(Void t) {
				super.onSuccess(t);
				showToast("上班卡打卡成功") ;
			}
		});
	}

	private void doShareAction() {
		mController.openShare(getActivity(), false);
	}

	@Override
	public <T> void onRefresh(IMsg<T> msg) {
		if (KeyList.KEY_USER_INFO_UPDATE.equals(msg.getKey())) {
			initUserView();
			initShareComponent() ;
		}
	}

	@Override
	public void onIntent() {
		// TODO Auto-generated method stub
		initFrom();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.d("M03PersonalFragment", "[onActivityResult]");
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/*--------------------分享---start--------------------*/
	private UMSocialService mController = null;

	private void initShareComponent() {
		/* !!!!!!!!!!!! 注意：不同平台设置内容的方式不一样，请参考umeng的demo */
		// 首先在您的Activity中添加如下成员变量
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
		mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,SHARE_MEDIA.QZONE,SHARE_MEDIA.TENCENT);

		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().setSinaCallbackUrl("http://sns.whalecloud.com/sina2/callback") ;
		addWXPlatform();
		addQQQPlatform();

		setShareData();

	}

	private void setShareData() {
		// 设置分享内容
		mController.setShareContent(SHARE_CONTENT.CONTENT);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(getActivity(),
				R.drawable.ic_launcher));
		// http://www.umeng.com/images/pic/social/integrated_3.png
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(SHARE_CONTENT.CONTENT);
		weixinContent.setTitle(SHARE_CONTENT.TITLE);
		weixinContent.setTargetUrl(getShareTargerUrl());
		weixinContent.setShareMedia(new UMImage(getActivity(),
				R.drawable.ic_launcher));
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(SHARE_CONTENT.CONTENT);
		circleMedia.setTitle(SHARE_CONTENT.TITLE);
		circleMedia.setShareMedia(new UMImage(getActivity(),
				R.drawable.ic_launcher));
		circleMedia.setTargetUrl(getShareTargerUrl());
		mController.setShareMedia(circleMedia);

		SinaShareContent sinaContent = new SinaShareContent();
		sinaContent.setShareContent(SHARE_CONTENT.CONTENT + getShareTargerUrl());
		sinaContent.setShareImage(new UMImage(getActivity(),
				R.drawable.ic_launcher));
		mController.setShareMedia(sinaContent);
	}

	private void addQQQPlatform() {
		String appId = "1104983298";
		String appKey = "f9OfXti92amWhqCy";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(), appId,
				appKey);
		qqSsoHandler.setTargetUrl(getShareTargerUrl());
		qqSsoHandler.setTitle(SHARE_CONTENT.TITLE) ;
		qqSsoHandler.addToSocialSDK();
	}
	
	private String getShareTargerUrl(){
		UserInfo user =  UserInfoDb.INSTANCE.get() ;
		return  SHARE_CONTENT.TARGETURL
				+ (user == null ? "" : user.getUserid()) ;
	}

	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appId = "wx7300ba9727b3e653";
		String appSecret = "d4624c36b6795d1d99dcf0547af5443d";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(), appId, appSecret);
		wxHandler.setTitle(SHARE_CONTENT.TITLE);
		wxHandler.setTargetUrl(getShareTargerUrl());
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appId,
				appSecret);
		wxCircleHandler.setTitle(SHARE_CONTENT.CONTENT);
		wxCircleHandler.setTargetUrl(getShareTargerUrl());
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}
	/*--------------------分享---end--------------------*/
}
