package com.lyx.widget.dynsear;

import java.util.List;

import com.lyx.widget.mansear.OnItemClickListener;

public  interface ListDisplay <T> {
	public void refresh(String text) ;
	public void setList(List<T> list) ;
	public void setOnItemClick(OnItemClickListener<T> listener) ;
}
