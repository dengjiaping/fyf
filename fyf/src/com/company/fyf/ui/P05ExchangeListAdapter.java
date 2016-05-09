package com.company.fyf.ui;

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

import java.util.List;

public class P05ExchangeListAdapter extends BaseAdapter {
	
	private Context context ;
	
	private List<CommodityVo> dataList  ;
	
	private OnExChangeListener changeListener ;
	
	public P05ExchangeListAdapter(Context context, List<CommodityVo> dataList) {
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
		final CommodityVo vo = dataList.get(position) ;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.i_p05_gridview, null) ;
		
			holder = new ViewHolder() ;
			holder.fee_credit = (TextView) convertView.findViewById(R.id.fee_credit) ;
			holder.title = (TextView) convertView.findViewById(R.id.title) ;
			holder.thumb = (ImageView) convertView.findViewById(R.id.thumb) ;
			holder.exchange = convertView.findViewById(R.id.exchange) ;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag() ;
		}
		setData(vo,holder) ;
		return convertView;
	}
	
	private void setData(final CommodityVo vo, ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.title.setText(vo.getTitle());
		holder.fee_credit.setText("积分：" + vo.getFee_credit());
		ImageLoaderUtils.displayPicWithAutoStretch(vo.getThumb(), holder.thumb);

		holder.exchange.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(changeListener != null ) changeListener.onExChange(vo) ;
			}
		}) ;
	}

	class ViewHolder{
		ImageView thumb ;
		TextView title,fee_credit ;
		View  exchange ;
	}
	
	public interface OnExChangeListener{
		public void onExChange(CommodityVo vo) ;
	}

	public void setOnExChangeListener(OnExChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
	

}
