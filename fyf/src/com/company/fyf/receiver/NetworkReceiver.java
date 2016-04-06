package com.company.fyf.receiver;

import com.company.fyf.notify.KeyList;
import com.company.fyf.notify.NotifyCenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
//        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
//        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
//        NetworkInfo activeInfo = manager.getActiveNetworkInfo();  
//        Toast.makeText(context, "mobile:"+mobileInfo.isConnected()+"\n"+"wifi:"+wifiInfo.isConnected()  
//                        +"\n"+"active:"+activeInfo.getTypeName(), 1).show();  
		NotifyCenter.sendEmptyMsg(KeyList.KEY_NETWORK_UPDATE) ;
	}

}
