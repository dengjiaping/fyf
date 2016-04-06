package com.company.fyf.widget.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class EmptyViewGridView extends GridView implements EmptyViewBehaviorImpl {
	
	

	public EmptyViewGridView(Context context) {
		super(context);
		init(context,null) ;
	}
	
	
	public EmptyViewGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs) ;
	}

	
	public EmptyViewGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs) ;
	}


	private void init(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		
	}

	
	
}
