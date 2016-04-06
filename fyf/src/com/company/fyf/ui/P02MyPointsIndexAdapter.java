package com.company.fyf.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.company.fyf.R;

public class P02MyPointsIndexAdapter extends BaseAdapter {
	
	private Context context ;
	
	public P02MyPointsIndexAdapter(Context context) {
		this.context = context ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.i_p02_gridview, null) ;
		}
		return convertView;
	}
	
	

}
