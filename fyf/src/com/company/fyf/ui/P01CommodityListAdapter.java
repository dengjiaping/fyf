package com.company.fyf.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.CommodityVo;
import com.lyx.utils.ImageLoaderUtils;

public class P01CommodityListAdapter extends BaseAdapter {
	
	private Context context ;
	
	private List<CommodityVo> dataList  ;
	
	public P01CommodityListAdapter(Context context, List<CommodityVo> dataList) {
		this.context = context;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null ;
		CommodityVo vo = dataList.get(position) ;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.i_p02_gridview, null) ;
		
			holder = new ViewHolder() ;
			holder.fee_credit = (TextView) convertView.findViewById(R.id.fee_credit) ;
			holder.title = (TextView) convertView.findViewById(R.id.title) ;
			holder.thumb = (ImageView) convertView.findViewById(R.id.thumb) ;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag() ;
		}
		setData(vo,holder) ;
		return convertView;
	}
	
	private void setData(CommodityVo vo, ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.title.setText(vo.getTitle());
		holder.fee_credit.setText("积分：" + vo.getFee_credit());
		ImageLoaderUtils.displayPicWithAutoStretch(vo.getThumb(), holder.thumb);
	}

	class ViewHolder{
		ImageView thumb ;
		TextView title,fee_credit ;
	}
	

}
