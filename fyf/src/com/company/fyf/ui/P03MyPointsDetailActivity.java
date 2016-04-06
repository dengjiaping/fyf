package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.company.fyf.R;
import com.company.fyf.dao.CreditVo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.CreditServer;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.widget.empty.EmptyViewLayout;
import com.company.fyf.widget.empty.EmptyViewRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class P03MyPointsDetailActivity extends B01BaseActivity  implements OnRefreshListener2<ListView>{
	
	private EmptyViewLayout emptyViewLayout ;
	
	private P03MyPointsDetailAdapter adapter ;
	
	private List<CreditVo> dataList  = new ArrayList<CreditVo>();
	
	private EmptyViewRefreshListView gridView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_p03_layout);
		
		initEmptyView() ;
	}
	
	private void initEmptyView() {
		emptyViewLayout = (EmptyViewLayout) findViewById(R.id.emptyViewLayout) ;
		emptyViewLayout.setAdapter(adapter = new P03MyPointsDetailAdapter(this,dataList)) ;
		gridView = emptyViewLayout.getDateShowView() ;
		gridView.setOnRefreshListener(this) ;
		getData("-1",true) ;
	}
	
	private void getData(String lastId,final boolean isRefresh){
		
		CreditServer mallServer = new CreditServer(this) ;
		mallServer.myCreditList(lastId,new CallBack<List<CreditVo>>() {
			@Override
			public void onSuccess(List<CreditVo> t) {
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
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		getData("-1",true) ;
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		getData(getLastId(),false) ;
	}
	
	private String getLastId(){
		if(dataList.size() == 0){
			return "-1" ;
		}
		return dataList.get(dataList.size() - 1).getId() ;
	}

}
