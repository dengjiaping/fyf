package com.company.fyf.notify;

public interface INotifyClient {
	public <T>  void onRefresh(IMsg<T> msg) ;
}
