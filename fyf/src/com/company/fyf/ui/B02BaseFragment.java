package com.company.fyf.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.company.fyf.notify.IMsg;
import com.company.fyf.notify.INotifyClient;
import com.company.fyf.notify.NotifyCenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class B02BaseFragment extends Fragment implements INotifyClient  {
	
	protected boolean showActivity(String clsName)
	{
		try 
		{
			showActivity(Class.forName(clsName));
			return true;
		} 
		catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected void showActivity(Class<?> cls) {
		showActivity(cls, null);
	}

	protected void showActivity(Class<?> cls, Bundle param) {
		showActivity(cls, param,0);
	}

	protected void showActivity(Class<?> cls, Bundle param, int flags) {
		Intent intent = new Intent();
		if(param != null)
			intent.putExtras(param);
		intent.setClass(getActivity(), cls);
		if (flags != 0)
			intent.setFlags(flags);

		startActivity(intent);
	}
	
	private final String FORMAT = "yyyy-MM-dd" ;
	private final SimpleDateFormat SDF = new SimpleDateFormat(FORMAT)  ;
	protected String formatDate(Calendar cal){
		if(cal!=null)
			return SDF.format(cal.getTimeInMillis())  ;
		else
			return"" ;
	}
	
	protected void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String msg, int duration) {
		Toast.makeText(getActivity(), msg, duration).show();
	}

	protected void showWarnDlg(String msg) {
		new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int arg1) {
						dlg.dismiss();
					}
				}).show();
	}
	
	protected void showNormalDlg(String msg, String positiveText,
			DialogInterface.OnClickListener positiveClickListener) {
		new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage(msg)
				.setPositiveButton(positiveText, positiveClickListener)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int arg1) {
						dlg.dismiss();
					}
				}).show();
	}

	@Override
	public <T> void onRefresh(IMsg<T> msg) {
	}
	
	protected void registerNotity(String key) {
		NotifyCenter.register(key, this) ;
	}
	
	protected void unRegisterNotity(String key) {
		NotifyCenter.unRegister(key, this) ;
	}

}
