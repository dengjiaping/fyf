package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.fyf.R;
import com.company.fyf.dao.BannerVo;
import com.company.fyf.db.UserInfoDb;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.INotifyClient;
import com.company.fyf.notify.KeyList;
import com.company.fyf.widget.CrouselImage;
import com.company.fyf.widget.TitleBar;
import com.zxing.ui.CaptureActivity;

public class M02HomeFragment extends B02BaseFragment {
	
	private View root = null ;
	
	private View ll_sweep,ll_commodity,ll_recoverydetail ;
	
	private CrouselImage crouselImage ;
	
	private TitleBar titlebar ;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (root == null) {
			root = inflater.inflate(R.layout.f_m02_layout, container, false);
			initComponent(root);
			getBannerDate() ;
		} else {
			ViewGroup group = (ViewGroup) root.getRootView();
			group.removeAllViews();
		}
		return root;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerNotity(KeyList.KEY_USER_INFO_UPDATE) ;
		registerNotity(KeyList.KEY_NETWORK_UPDATE) ;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unRegisterNotity(KeyList.KEY_USER_INFO_UPDATE) ;
		unRegisterNotity(KeyList.KEY_NETWORK_UPDATE) ;
	}
	
	@Override
	public <T> void onRefresh(IMsg<T> msg) {
		super.onRefresh(msg);
		if(KeyList.KEY_USER_INFO_UPDATE.equals(msg.getKey())){
			updateTitleBar() ;
		}else if(KeyList.KEY_NETWORK_UPDATE.equals(msg.getKey())){
			if(crouselImage != null && !crouselImage.hasData() ){
				getBannerDate() ;
			}
		}
	}

	private void initComponent(View root) {
		// TODO Auto-generated method stub
		ll_sweep = root.findViewById(R.id.ll_sweep) ;
		ll_sweep.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(C06SweepActivity.class) ;
			}
		}) ;
		ll_commodity = root.findViewById(R.id.ll_commodity) ;
		ll_commodity.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(P01CommodityListActivity.class) ;
			}
		}) ;
		crouselImage = (CrouselImage) root.findViewById(R.id.crouselImage) ;
		
		View click_area_kitchen = root.findViewById(R.id.click_area_kitchen) ;
		click_area_kitchen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle param = new Bundle() ;
				param.putInt(F01DiffRecoveryDetailActivity.PARAM_INT_POSITION, 0);
				showActivity(F01DiffRecoveryDetailActivity.class,param) ;
			}
		}) ;
		View click_area_other = root.findViewById(R.id.click_area_other) ;
		click_area_other.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle param = new Bundle() ;
				param.putInt(F01DiffRecoveryDetailActivity.PARAM_INT_POSITION, 1);
				showActivity(F01DiffRecoveryDetailActivity.class,param) ;
			}
		}) ;
		View click_area_recyclable = root.findViewById(R.id.click_area_recyclable) ;
		click_area_recyclable.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bundle param = new Bundle() ;
				param.putInt(F01DiffRecoveryDetailActivity.PARAM_INT_POSITION, 2);
				showActivity(F01DiffRecoveryDetailActivity.class,param) ;
			}
		}) ;
		
		titlebar = (TitleBar) root.findViewById(R.id.titlebar) ;
		titlebar.replaceBackBtn(R.drawable.ic_comm_msg, new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),S01MsgListActivity.class) ;
				startActivity(intent) ;
			}
		}) ;
		updateTitleBar() ;
	}
	
	private void updateTitleBar(){
		
		if(titlebar == null && root != null){
			titlebar = (TitleBar) root.findViewById(R.id.titlebar) ;
		}
		
		if(titlebar == null) return ;
		
		titlebar.setMenuBtn("注册",new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),L01RegisterFirstActivity.class) ;
				startActivity(intent) ;
			}
		}) ;
		if(UserInfoDb.INSTANCE.get() != null){
			titlebar.hideMenuBtn() ;
		}
	}
	
	private void getBannerDate(){
		ApptoolServer server = new ApptoolServer() ;
		server.banner(new CallBack<List<BannerVo>>() {
			@Override
			public void onSuccess(List<BannerVo> t) {
				super.onSuccess(t);
				
				List<BannerVo> removeVos = new ArrayList<>() ;
				for (BannerVo bannerVo : t) {
					if(TextUtils.isEmpty(bannerVo.getPicurl())){
						removeVos.add(bannerVo) ;
					}
				}
				t.removeAll(removeVos) ;
				
				crouselImage.add(t) ;
			}
		});
	}

}
