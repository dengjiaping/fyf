package com.company.fyf.widget.empty;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;

public interface EmptyViewBehaviorImpl {
	
	public void setEmptyView(View view) ;
	
	public void setAdapter(ListAdapter adapter) ;
	
	public void setOnItemClickListener(OnItemClickListener listener) ;

}
