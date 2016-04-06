package com.company.fyf.notify;

public interface IMsg<T> {
	
	public void setContent(T t ) ;
	public T getContent() ;
	
	public void setKey(String key) ;
	public String getKey() ;
	

}
