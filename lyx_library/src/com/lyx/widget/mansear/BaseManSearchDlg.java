package com.lyx.widget.mansear;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.framework_lyx.R;

public abstract class BaseManSearchDlg<T> extends Dialog {

	protected Search<T> search;
	protected ListDisplay<T> listView;
	protected OnItemClickListener<T> itemListener  ;

	public BaseManSearchDlg(Context context,OnItemClickListener<T> itemListener) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.itemListener = itemListener  ;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(obtainLayoutId());
		search = (Search<T>) findViewById(R.id.BaseManSearchDlg_searchBar);
		listView = (ListDisplay<T>) findViewById(R.id.BaseManSearchDlg_listView);
		listView.setOnItemClick(new OnItemClickListener<T>() {
			public void itemClick(T t) {
				itemListener.itemClick(t) ;
				BaseManSearchDlg.this.dismiss()  ;
			}
		})  ;
		search.registeListView(listView);
	}
	
	protected abstract int obtainLayoutId() ;
	
}