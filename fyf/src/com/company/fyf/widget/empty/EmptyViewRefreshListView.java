package com.company.fyf.widget.empty;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class EmptyViewRefreshListView extends PullToRefreshListView implements EmptyViewBehaviorImpl {

	public EmptyViewRefreshListView(Context context) {
		super(context);
		init(context,null) ;
	}
	
	
	public EmptyViewRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs) ;
	}



	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		getRefreshableView().setDivider(null);
		getRefreshableView().setDividerHeight(0);
	}

	
	
}
