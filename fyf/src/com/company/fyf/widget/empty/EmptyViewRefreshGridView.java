package com.company.fyf.widget.empty;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class EmptyViewRefreshGridView extends PullToRefreshGridView implements EmptyViewBehaviorImpl {

	public EmptyViewRefreshGridView(Context context) {
		super(context);
		init(context,null) ;
	}
	
	
	public EmptyViewRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs) ;
	}


	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
	}

	
	
}
