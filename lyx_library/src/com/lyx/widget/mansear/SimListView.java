package com.lyx.widget.mansear;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.framework_lyx.R;
import com.lyx.utils.CommUtil;

public class SimListView <T> extends ListView implements ListDisplay<T> {

	private ArrayAdapter<T> adapter ;
	private List<T> showList= new ArrayList<T>() ;
	
	public SimListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.showList = CommUtil.obtainList() ;
		adapter = new ArrayAdapter<T>(getContext(), R.layout.i_simle_list_text01,
				showList)  ;
		setAdapter(adapter);
	}

	@Override
	public void updateAll(List<T> list) {
		showList.clear(); 
		showList.addAll(list)  ;
		if(adapter!=null) adapter.notifyDataSetChanged();
	}

	@Override
	public void addAll(List<T> list) {
		// TODO Auto-generated method stub
		showList.addAll(list)  ;
		if(adapter!=null) adapter.notifyDataSetChanged();
	}

	@Override
	public void setOnItemClick(
			final com.lyx.widget.mansear.OnItemClickListener listener) {
		// TODO Auto-generated method stub
		this.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				listener.itemClick(showList.get(position))  ;
			}
		}) ;
	}

}
