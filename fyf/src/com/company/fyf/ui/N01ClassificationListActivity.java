package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.company.fyf.R;
import com.company.fyf.dao.RubbishVo;
import com.company.fyf.net.CallBack;
import com.company.fyf.net.RubbishServer;
import com.company.fyf.utils.CommConfig;
import com.company.fyf.widget.TitleBar;
import com.company.fyf.widget.empty.EmptyViewLayout;
import com.company.fyf.widget.empty.EmptyViewRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lyx.utils.CalendarUtil;

//分类情况提交
public class N01ClassificationListActivity extends B01BaseActivity implements OnRefreshListener2<ListView>{
	
	public static final int REQUESTCODE_ADD_MODIFY = 101;
	
	public static final String NAME = "";
	
	private final List<RubbishVo> list = new ArrayList<RubbishVo>() ;
	
	private EmptyViewLayout layout ;
	
	private EmptyViewRefreshListView listView ;
	
	private BaseAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_n01_layout);
		
		layout = (EmptyViewLayout) findViewById(R.id.emptyViewLayout) ;
		layout.setAdapter(adapter = new N01ClassificationListAdapter(this,list)) ;
		layout.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String time = "" ;
				RubbishVo vo = list.get(position - 1) ;
				if(!TextUtils.isEmpty(vo.getUpdatetime()) && !"0".equals(vo.getUpdatetime())){
					time = vo.getJavaUpdatetime();
				}else if(!TextUtils.isEmpty(vo.getAddtime()) && !"0".equals(vo.getAddtime())){
					time = vo.getJavaAddtime();
				}
				
				if(CalendarUtil.isToday(time)){
					Bundle param = new Bundle() ;
					param.putSerializable(N03ClassificationAddActivity.PARAM_SERIALIZABLE_RUBBISH, vo);
					param.putSerializable(N03ClassificationAddActivity.PARAM_INT_FROM, N03ClassificationAddActivity.FROM_EDIT);
					showActivityForResult(N03ClassificationAddActivity.class,param,REQUESTCODE_ADD_MODIFY);
				}else{
					Bundle param = new Bundle() ;
					param.putSerializable(N02ClassificationDetailActivity.PARAM_SERIALIZABLE_RUBBISH, vo);
					showActivity(N02ClassificationDetailActivity.class,param);
				}
			}
		}) ;
		listView = layout.getDateShowView() ;
		listView.setOnRefreshListener(this);
		listView.setMode(Mode.BOTH);
		getData("-1", true);
		
		TitleBar titleBar = (TitleBar) findViewById(R.id.titlebar) ;
		titleBar.setMenuBtn("添加", new View.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showActivityForResult(N03ClassificationAddActivity.class,REQUESTCODE_ADD_MODIFY);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == REQUESTCODE_ADD_MODIFY ){
			getData("-1", true);
		}
	}
	
	private void getData(String lastId,final boolean isRefresh){
		RubbishServer apptoolServer = new RubbishServer(this) ;
		apptoolServer.myRubbishList(lastId,new CallBack<List<RubbishVo>>() {
			@Override
			public void onSuccess(List<RubbishVo> t) {
				super.onSuccess(t);
				listView.onRefreshComplete();
				if(t == null || t.size() < Integer.valueOf(CommConfig.PAGE_SIZE)){
					listView.setMode(Mode.PULL_FROM_START);
				}else{
					listView.setMode(Mode.BOTH);
				}
				if(isRefresh) list.clear();
				list.addAll(t) ;
				notifyDataSetChanged();
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
	
	private void notifyDataSetChanged() {
		// TODO Auto-generated method stub
//		Collections.reverse(list);
		adapter.notifyDataSetChanged();
	}

}
