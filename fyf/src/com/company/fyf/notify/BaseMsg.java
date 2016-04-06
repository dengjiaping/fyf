package com.company.fyf.notify;

public  class BaseMsg<T> implements IMsg<T> {
	
	protected String key ;
	protected T obj ;
	@Override
	public void setContent(T t) {
		this.obj = t ;
	}
	@Override
	public T getContent() {
		// TODO Auto-generated method stub
		return obj;
	}
	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		this.key = key ;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}
	
	

}
