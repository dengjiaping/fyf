package com.lyx.widget.mansear;

import java.util.List;


public  interface ListDisplay <T> {
	public void updateAll(List<T> list) ;
	public void addAll(List<T> list) ;
	public void setOnItemClick(OnItemClickListener<T> listener) ;
}
