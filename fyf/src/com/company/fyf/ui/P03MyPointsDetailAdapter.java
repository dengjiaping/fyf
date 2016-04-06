package com.company.fyf.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.company.fyf.R;
import com.company.fyf.dao.CreditVo;
import com.company.fyf.utils.DateFormatUtils;

public class P03MyPointsDetailAdapter extends BaseAdapter {
	
	private Context context ;
	
	private List<CreditVo> dataList  = null ;
	
	public P03MyPointsDetailAdapter(Context context,List<CreditVo> dataList) {
		this.context = context ;
		this.dataList = dataList ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null ;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.i_p03_listview, null) ;
			
			viewHolder = new ViewHolder() ;
			viewHolder.addtime = (TextView) convertView.findViewById(R.id.addtime) ;
			viewHolder.reason = (TextView) convertView.findViewById(R.id.reason) ;
			viewHolder.amount = (TextView) convertView.findViewById(R.id.amount) ;
		
			convertView.setTag(viewHolder) ;
		}else{
			viewHolder = (ViewHolder) convertView.getTag() ;
		}
		
		setData(dataList.get(arg0),viewHolder) ;
		return convertView;
	}
	
	private void setData(CreditVo creditVo, ViewHolder viewHolder) {
		
		viewHolder.addtime.setText(DateFormatUtils.format02(creditVo.getJavaAddtime())) ;
		viewHolder.reason.setText(creditVo.getReason()) ;
		
		float amount = Float.parseFloat(creditVo.getAmount()) ;
		if(amount > 0){
			viewHolder.amount.setText("+" + creditVo.getAmount() ) ;
			viewHolder.amount.setTextColor(context.getResources().getColor(R.color.theme_blue)) ;
		}else{
			viewHolder.amount.setText(creditVo.getAmount() ) ;
			viewHolder.amount.setTextColor(context.getResources().getColor(R.color.theme_f31313)) ;
		}
		
	}

	class ViewHolder{
		TextView addtime,reason,amount ;
	}
	

}
