package com.company.fyf.ui;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.RubbishVo;
import com.company.fyf.utils.DateFormatUtils;
import com.lyx.utils.ImageLoaderUtils;

public class N01ClassificationListAdapter extends BaseAdapter {
	
	private Context context ;
	
	private List<RubbishVo> list =  null ;
	
	public N01ClassificationListAdapter(Context context,List<RubbishVo> list) {
		this.context = context ;
		this.list = list ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		
		RubbishVo vo = list.get(position) ;
		ViewHolder holder = null ;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.i_n01_listview, null);
			
			holder = new ViewHolder() ;
			holder.name = (TextView) convertView.findViewById(R.id.name) ;
			holder.updatetime = (TextView) convertView.findViewById(R.id.updatetime) ;
			holder.note = (TextView) convertView.findViewById(R.id.note) ;
			holder.pic_url = (ImageView) convertView.findViewById(R.id.pic_url) ;
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag() ;
		}
		setData(vo,holder) ;
		
		return convertView ;
	}
	
	private void setData(RubbishVo vo, ViewHolder holder) {
		// TODO Auto-generated method stub
		if(vo == null || holder == null) return ;
		ImageLoaderUtils.displayPicWithAutoStretch(vo.getPic_url(), holder.pic_url) ;
		holder.name.setText(vo.getName());
		if(!TextUtils.isEmpty(vo.getUpdatetime()) && !"0".equals(vo.getUpdatetime())){
			holder.updatetime.setText(DateFormatUtils.format01(vo.getJavaUpdatetime()));
		}else if(!TextUtils.isEmpty(vo.getAddtime()) && !"0".equals(vo.getAddtime())){
			holder.updatetime.setText(DateFormatUtils.format01(vo.getJavaAddtime()));
		}else{
			holder.updatetime.setText("");
		}
		holder.note.setText(vo.getNote());
	}

	class ViewHolder{
		TextView name,updatetime,note ;
		ImageView pic_url ;
	}

}
