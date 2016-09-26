package com.company.fyf.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.CommodityVo;
import com.company.fyf.db.CommPreference;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.MallServer;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.widget.TitleBar;
import com.company.fyf.widget.empty.EmptyViewLayout;
import com.company.fyf.widget.empty.EmptyViewRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import java.util.ArrayList;
import java.util.List;

public class P02MyPointsIndexActivity extends B01BaseActivity implements OnRefreshListener2<GridView>{
	
	private EmptyViewLayout emptyViewLayout ;
	
	private P01CommodityListAdapter adapter ;
	
	private List<CommodityVo> dataList  = new ArrayList<CommodityVo>();
	
	private EmptyViewRefreshGridView gridView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_p02_layout);
		 init() ;
	}
	
	private void init() {
		// TODO Auto-generated method stub
		TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar) ;
		titleBar.setMenuBtn("积分明细", new View.OnClickListener() {
			public void onClick(View arg0) {
				showActivity(P03MyPointsDetailActivity.class);
			}
		});
		
		TextView credit = (TextView) findViewById(R.id.credit) ;
		credit.setText(CommPreference.INSTANCE.getUserInfo().getCredit()) ;
		
		View pointsRule = findViewById(R.id.pointsRule) ;
		pointsRule.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showActivity(P06PointsRuleActivity.class) ;
			}
		}) ;
		
		emptyViewLayout = (EmptyViewLayout) findViewById(R.id.emptyViewLayout) ;
		initEmptyView() ;
		
		getData("-1",true) ;
	}

	private void initEmptyView() {
		
		emptyViewLayout.setAdapter(adapter = new P01CommodityListAdapter(this,dataList)) ;
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
