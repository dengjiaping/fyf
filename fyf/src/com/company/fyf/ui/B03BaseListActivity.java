//package com.company.fyf.ui;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.company.fyf.R;
//import com.company.fyf.dao.MsgVo;
//import com.company.fyf.net.ApptoolServer;
//import com.company.fyf.net.CallBack;
//import com.company.fyf.utils.CommConfig;
//import com.company.fyf.widget.empty.EmptyViewLayout;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
//
//public class Base03ListActivity<J, K extends PullToRefreshBase, L > extends B01BaseActivity  implements OnRefreshListener2{
//
//	protected List<J> list = new ArrayList<J>();
//
//	protected EmptyViewLayout layout;
//
//	protected K listView;
//	
//	protected BaseAdapter adapter ;
//	
//	protected void initListView(){
//		layout = (EmptyViewLayout) findViewById(R.id.emptyViewLayout) ;
//		listView = layout.getDateShowView() ;
//		listView.setOnRefreshListener(this);
//		listView.setMode(Mode.BOTH);
//		getData("-1", true);
//	}
//	
//	
//	private void getData(String lastId,final boolean isRefresh){
//		ApptoolServer apptoolServer = new ApptoolServer(this) ;
//		apptoolServer.announce(lastId,new CallBack<List<J>>() {
//			@Override
//			public void onSuccess(List<J> t) {
//				super.onSuccess(t);
//				listView.onRefreshComplete();
//				if(t == null || t.size() < Integer.valueOf(CommConfig.PAGE_SIZE)){
//					listView.setMode(Mode.PULL_FROM_START);
//				}else{
//					listView.setMode(Mode.BOTH);
//				}
//				
//				if(isRefresh) list.clear();
//				list.addAll(t) ;
//				adapter.notifyDataSetChanged();
//			}
//			
//			@Override
//			public void onBadNet() {
//				super.onBadNet();
//				listView.onRefreshComplete();
//			}
//			
//			@Override
//			public void onFail() {
//				super.onFail();
//				listView.onRefreshComplete();
//			}
//		});
//	}
//	
//	private CallBack<List<J>> getCallBack(){
//		return new CallBack<List<J>>() {
//			@Override
//			public void onSuccess(List<J> t) {
//				super.onSuccess(t);
//				listView.onRefreshComplete();
//				if(t == null || t.size() < Integer.valueOf(CommConfig.PAGE_SIZE)){
//					listView.setMode(Mode.PULL_FROM_START);
//				}else{
//					listView.setMode(Mode.BOTH);
//				}
//				
//				if(isRefresh) list.clear();
//				list.addAll(t) ;
//				adapter.notifyDataSetChanged();
//			}
//			
//			@Override
//			public void onBadNet() {
//				super.onBadNet();
//				listView.onRefreshComplete();
//			}
//			
//			@Override
//			public void onFail() {
//				super.onFail();
//				listView.onRefreshComplete();
//			}
//		}
//	}
//	
//	private String getLastId(){
//		if(list.size() == 0){
//			return "-1" ;
//		}
//		return list.get(list.size() - 1).getId() ;
//	}
//
//	@Override
//	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//		// TODO Auto-generated method stub
//		getData("-1",true) ;
//	}
//
//	@Override
//	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//		// TODO Auto-generated method stub
//		getData(getLastId(),false) ;
//	}
//}
