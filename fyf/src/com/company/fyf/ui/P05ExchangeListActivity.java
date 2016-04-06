package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.company.fyf.R;
import com.company.fyf.dao.CommodityVo;
import com.company.fyf.dao.SweepVo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MallOrderServer;
import com.company.fyf.net.MallServer;
import com.company.fyf.ui.P05ExchangeListAdapter.OnExChangeListener;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.widget.empty.EmptyViewLayout;
import com.company.fyf.widget.empty.EmptyViewRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class P05ExchangeListActivity extends B01BaseActivity implements OnRefreshListener2<GridView>{
	
	private EmptyViewLayout emptyViewLayout ;
	
	private P05ExchangeListAdapter adapter ;
	
	private List<CommodityVo> dataList  = new ArrayList<CommodityVo>();
	
	private EmptyViewRefreshGridView gridView ;
	
	public static final String PARAM_SERIALIZABLE_PERSONERINFO = "param_serializable_personer_info";
	private SweepVo personerinfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onGetIntentData() ;
		setContentView(R.layout.a_p01_layout) ;
		init() ;
	}

	private void init() {
		// TODO Auto-generated method stub
		emptyViewLayout = (EmptyViewLayout) findViewById(R.id.emptyViewLayout) ;
		initEmptyView() ;
		
		getData("-1",true) ;
	}

	private void initEmptyView() {
		
		emptyViewLayout.setAdapter(adapter = new P05ExchangeListAdapter(this,dataList)) ;
		adapter.setOnExChangeListener(new OnExChangeListener() {
			public void onExChange(final CommodityVo vo) {
				 new AlertDialog.Builder(P05ExchangeListActivity.this)
					.setTitle("温馨提示")
					.setMessage("确定兑换?")  
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.dismiss() ;
							MallOrderServer server = new MallOrderServer(P05ExchangeListActivity.this) ;
							server.buy(vo.getId(), personerinfo.getUsername(),new CallBack<Void>() {
								@Override
								public void onSuccess(Void t) {
									super.onSuccess(t);
									Intent data = new Intent() ;
									data.putExtra(P04PointsManagerActivity.RETURN_EXCHANGE, vo.getFee_credit()) ;
									setResult(RESULT_OK, data) ;
									showToast("兑换成功");
									finish() ;
								}
							}) ;
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.dismiss() ;
						}
					})
					.show() ;
			}
		}) ;
		gridView = emptyViewLayout.getDateShowView() ;
		gridView.setOnRefreshListener(this) ;
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle = new Bundle() ;
				CommodityVo vo = dataList.get(arg2) ;
				bundle.putString(C06ImageActivity.PARAM_STRING_URL, vo.getThumb_original());
				showActivity(C06ImageActivity.class, bundle);
			}
		});
	}
	
	private void getData(String lastId,final boolean isRefresh){
		
		MallServer mallServer = new MallServer(this) ;
		mallServer.productList(lastId,new CallBack<List<CommodityVo>>() {
			@Override
			public void onSuccess(List<CommodityVo> t) {
				super.onSuccess(t);
				gridView.onRefreshComplete();
				if(t == null || t.size() < Integer.valueOf(CommConfig.PAGE_SIZE)){
					gridView.setMode(Mode.PULL_FROM_START);
				}else{
					gridView.setMode(Mode.BOTH);
				}
				if(isRefresh) dataList.clear();
				dataList.addAll(t) ;
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onBadNet() {
				super.onBadNet();
				gridView.onRefreshComplete();
			}
			
			@Override
			public void onFail() {
				super.onFail();
				gridView.onRefreshComplete();
			}
		});
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
		getData("-1",true) ;
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
		getData(getLastId(),false) ;
	}
	
	private String getLastId(){
		if(dataList.size() == 0){
			return "-1" ;
		}
		return dataList.get(dataList.size() - 1).getId() ;
	}

}
