package com.lyx.widget.dynsear;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.framework_lyx.R;
import com.lyx.widget.mansear.OnItemClickListener;

public class SimSearchDlg <T> extends Dialog{
	
	private SimSearchBar simSearchBar  ;
	private SimListView<T>  simListView  ;
	private List<T> list ;
	private OnItemClickListener<T> itemListener  ;

	public SimSearchDlg(Context context,OnItemClickListener<T> itemListener) {
		super(context,android.R.style.Theme_Translucent_NoTitleBar);
		this.itemListener = itemListener  ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d_dynsear_simsearch);
		simListView = (SimListView<T>) findViewById(R.id.simListView)  ;
		simListView.setList(list);
		simListView.setOnItemClick(new OnItemClickListener<T>() {
			public void itemClick(T t) {
				itemListener.itemClick(t) ;
				SimSearchDlg.this.dismiss()  ;
			}
		})  ;
		simSearchBar = (SimSearchBar) findViewById(R.id.simSearchBar)  ;
		simSearchBar.registeListView(simListView);
	}

	public void setList(List<T> list) {
		this.list = list ;
	}

}
