package com.lyx.widget.dynsear;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.framework_lyx.R;

public class  SimListView  <T> extends ListView implements ListDisplay <T>{

	private List<T> list ;
	private ArrayAdapter<T> adapter ;
	private List<T> showList= new ArrayList<T>() ;
	
	public SimListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void refresh(String text) {
		
		if(list==null || list.size()==0) return ;
		
		showList.clear();
		if(TextUtils.isEmpty(text)){
			this.showList.addAll(list) ;
		}else
			for(T t : list){
				if(t.toString().contains(text)){
					this.showList.add(t) ;
				}
			}
		if(adapter!=null) adapter.notifyDataSetChanged();
	}

	@Override
	public void setList(List<T> list) {
		this.list = list ;
		this.showList.addAll(list) ;
		adapter = new ArrayAdapter<T>(getContext(), R.layout.i_simle_list_text01,
				showList)  ;
		setAdapter(adapter);
	}

	@Override
	public void setOnItemClick(
			final com.lyx.widget.mansear.OnItemClickListener<T> listener) {
		this.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				listener.itemClick(showList.get(position))  ;
			}
		})  ;
	}

}
