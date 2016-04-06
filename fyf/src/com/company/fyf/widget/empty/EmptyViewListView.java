package com.company.fyf.widget.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class EmptyViewListView extends ListView implements EmptyViewBehaviorImpl {
	
	

	public EmptyViewListView(Context context) {
		super(context);
		init(context,null) ;
	}
	
	
	public EmptyViewListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs) ;
	}

	
	public EmptyViewListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs) ;
	}


	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		
	}

	
	
}
