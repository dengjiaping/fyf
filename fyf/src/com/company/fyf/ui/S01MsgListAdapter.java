package com.company.fyf.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.MsgVo;
import com.company.fyf.utils.DateFormatUtils;

public class S01MsgListAdapter extends BaseAdapter {
	
	private List<MsgVo> list = new ArrayList<MsgVo>() ;
	
	private Context context ;

	public S01MsgListAdapter(List<MsgVo> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MsgVo vo = list.get(position) ;
		ViewHolder viewHolder = null ;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.i_s01_listview, null);
			viewHolder = new ViewHolder() ;
			viewHolder.catname = (TextView) convertView.findViewById(R.id.catname) ;
			viewHolder.updatetime = (TextView) convertView.findViewById(R.id.updatetime) ;
			viewHolder.description = (TextView) convertView.findViewById(R.id.description) ;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag() ;
		}
		
		viewHolder.catname.setText(vo.getCatname());
		viewHolder.updatetime.setText(DateFormatUtils.format01(vo.getJavaUpdatetime()));
		viewHolder.description.setText(vo.getTitle());
		
		return convertView;
	}
	
	class ViewHolder{
		TextView catname,updatetime,description ;
	}


}
