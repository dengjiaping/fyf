package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.company.fyf.R;
import com.company.fyf.dao.MsgVo;
import com.company.fyf.net.ApptoolServer;
import com.company.fyf.net.CallBack;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.widget.TitleBar;
import com.company.fyf.widget.empty.EmptyViewLayout;
import com.company.fyf.widget.empty.EmptyViewRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class S01MsgListActivity extends B01BaseActivity implements OnRefreshListener2<ListView>{
	
	private List<MsgVo> list = new ArrayList<MsgVo>() ;
	
	private EmptyViewLayout layout ;
	
	private EmptyViewRefreshListView listView ;
	
	private TitleBar titleBar ;
	
	private BaseAdapter adapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_s01_layout) ;
		
		layout = (EmptyViewLayout) findViewById(R.id.emptyViewLayout) ;
		layout.setAdapter(adapter = new S01MsgListAdapter(list, this)) ;
		layout.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle param = new Bundle() ;
				param.putSerializable(S02MsgDetailActivity.PARAM_SERIALIZABLE_MSG, list.get(position - 1));
				showActivity(S02MsgDetailActivity.class,param) ;
			}
		}) ;
		listView = layout.getDateShowView() ;
		listView.setOnRefreshListener(this);
		listView.setMode(Mode.BOTH);
		getData("-1", true);
	}
	
	private void getData(String lastId,final boolean isRefresh){
		ApptoolServer apptoolServer = new ApptoolServer(this) ;
		apptoolServer.announce(lastId,new CallBack<List<MsgVo>>() {
			@Override
			public void onSuccess(List<MsgVo> t) {
				super.onSuccess(t);
				listView.onRefreshComplete();
				if(t == null || t.size() < Integer.valueOf(CommConfig.PAGE_SIZE)){
					listView.setMode(Mode.PULL_FROM_START);
				}else{
					listView.setMode(Mode.BOTH);
				}
				
				if(isRefresh) list.clear();
				list.addAll(t) ;
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onBadNet() {
				super.onBadNet();
				listView.onRefreshComplete();
			}
			
			@Override
			public void onFail() {
				super.onFail();
				listView.onRefreshComplete();
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
		if(list.size() == 0){
			return "-1" ;
		}
		return list.get(list.size() - 1).getId() ;
	}

}
