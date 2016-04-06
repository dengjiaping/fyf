package com.company.fyf.net;

public abstract class CallBack<T> {
	
	public void onBadNet() {};
	
	public void onFail()  {};
	
	public void onSuccess(T t) {} ;

}
