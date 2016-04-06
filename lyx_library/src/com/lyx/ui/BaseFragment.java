package com.lyx.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class BaseFragment extends Fragment {

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
}
